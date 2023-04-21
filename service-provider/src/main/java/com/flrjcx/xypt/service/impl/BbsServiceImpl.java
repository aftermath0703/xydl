package com.flrjcx.xypt.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.exception.WebServiceEnumException;
import com.flrjcx.xypt.common.exception.WebServiceException;
import com.flrjcx.xypt.common.model.param.bbs.Bbs;
import com.flrjcx.xypt.common.model.param.bbs.BbsEditParam;
import com.flrjcx.xypt.common.model.param.bbs.BbsHot;
import com.flrjcx.xypt.common.model.param.bbs.BbsReward;
import com.flrjcx.xypt.common.model.param.common.TransactionParam;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.utils.*;
import com.flrjcx.xypt.mapper.*;
import com.flrjcx.xypt.service.BbsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author : aftermath
 * @date : 2022-11-04 09:36:39
 */
@Service
public class BbsServiceImpl implements BbsService {

    public static final String SEARCH_UPDATETIME_POST_KEY = "search:post:updatetime:key:";

    private static final ExecutorService CACHE_SEARCH_POST_SERVICE = new ThreadPoolExecutor(
            // 核心线程数量
            10,
            // 线程池容量
            10,
            // 线程保持活动时间
            1000,
            // 线程保持活动单位
            TimeUnit.SECONDS,
            // 线程任务队列
            new LinkedBlockingQueue<>(1024),
            // 线程创建工厂
            Executors.defaultThreadFactory(),
            // 线程拒绝策略
            new ThreadPoolExecutor.AbortPolicy());

    @Resource
    BbsMapper bbsMapper;
    @Resource
    BbsPraiseMapper bbsPraiseMapper;
    @Resource
    BbsNoMapper bbsNoMapper;
    @Resource
    private KafkaUtils kafkaUtils;
    @Resource
    private MyWalletMapper myWalletMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private SnowflakeIdWorker snowflakeIdWorker;
    @Resource
    private ImpowerMapper impowerMapper;

    /**
     * 编辑帖子
     *
     * @param param 帖子更新对象
     * @param users 发送修改帖子请求的用户
     * @return 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editPost(BbsEditParam param, Users users) {
        Long bbsId = param.getBbsId();
        String bbsContext = param.getBbsContext();
        String bbsTitle = param.getBbsTitle();
        String bbsContextMd = param.getBbsContextMd();
        if (!StrUtil.isAllNotBlank(bbsContextMd, bbsContext) && StrUtil.isEmpty(bbsTitle)) {
            return false;
        }
        //检查该用户是否为帖子的发帖人
        Long userId = bbsMapper.selectBbsOwner(bbsId);
        if (ObjectUtil.isNull(userId)) {
            return false;
        }
        if (!userId.equals(users.getUserId())) {
            return false;
        }
        //编辑帖子
        int updatePost = bbsMapper.updatePost(param);
        return updatePost > 0;
    }

    /**
     * 根据id删除帖子
     *
     * @param bbsId 帖子id
     * @param users 发送删帖请求的用户
     * @return 是否删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePostById(Long bbsId, Users users) {
        //判断需要删帖的人与帖子的主人是否为同一个人
        Long ownerId = bbsMapper.selectBbsOwner(bbsId);
        //为空则代表帖子不存在
        if (ObjectUtil.isNull(ownerId)) {
            return false;
        }
        if (users.getUserId().equals(ownerId)) {
            int deletePostById = bbsMapper.deletePostById(bbsId);
            return deletePostById > 0;
        }
        return false;
    }

    /**
     * 根据keys搜索帖子
     *
     * @param searchKeys 关键词集合
     * @param pageNum    当前页数
     * @param pageSize   每页大小
     * @return 帖子集合
     */
    @Override
    public List<Bbs> searchPosts(List<String> searchKeys, Integer pageNum, Integer pageSize,Integer type) {
        Set<Bbs> cacheBbs = new HashSet<>();
        searchKeys.forEach(key -> {
            String redisKey = SEARCH_UPDATETIME_POST_KEY + key + ":" + pageSize + ":" + pageNum;
            Set<Bbs> cacheSet = redisCache.getCacheSet(redisKey);
            if (!ObjectUtil.isNull(cacheSet) && !cacheSet.isEmpty()) {
                cacheBbs.addAll(cacheSet);
            }
        });

        if (!cacheBbs.isEmpty()) {
            List<Bbs> bbsList = new ArrayList<>(cacheBbs);
            //如果list数量少于每页显示数量，则再去数据库查询，补充完整
            if (bbsList.size() < pageSize) {
                return getBbs(searchKeys, pageNum, pageSize,type);
            }
            //按更新时间降序排列
            bbsList.sort((a, b) -> -a.getBbsUpdateTime().compareTo(b.getBbsUpdateTime()));
            //返回切割后的帖子
            return ListUtil.sub(bbsList, (pageNum - 1) * pageSize, pageNum * pageSize);
        }

        return getBbs(searchKeys, pageNum, pageSize,type);
    }

    /**
     * 发帖
     *
     * @param bbs
     */
    @Override
    public void production(Bbs bbs) {
        bbs.setBbsUserId(UserThreadLocal.get().getUserId());
        bbs.setBbsId(snowflakeIdWorker.nextId());
        if (!ObjectUtils.isEmpty(bbs.getBbsLabel())){
        bbs.setBbsLabelJson(JSONObject.toJSONString(bbs.getBbsLabel()));
        }
//        如果发帖用户是被授权的用户
        if (impowerMapper.checkImpower(bbs.getBbsUserId())){
            bbs.setBbsType(1);
        }
        bbsMapper.production(bbs);
    }

    /**
     * 查询帖子列表
     *
     * @return
     */
    @Override
    public List<Bbs> bbsList() {
        List<Bbs> bbsList = bbsMapper.bbsList();
        for (Bbs bbs : bbsList) {
            Users users = bbsMapper.selectAccount(bbs.getBbsUserId());
            if (!ObjectUtils.isEmpty(users.getNickName())){
            bbs.setNickName(users.getNickName());
            }
            if (!ObjectUtils.isEmpty(users.getNickPic())){
            bbs.setAccountPic(users.getNickPic());
            }
            bbs.setBbsIdTwo(bbs.getBbsId().toString());
            bbs.setBbsUserIdTwo(bbs.getBbsUserId().toString());
        }
        return bbsList;
    }

    /**
     * 查询帖子详情
     *
     * @param bbsId
     * @return
     */
    @Override
    public Bbs bbsDetails(long bbsId) {
        Bbs bbs = bbsMapper.bbsDetails(bbsId);
        Users users = bbsMapper.selectAccount(bbs.getBbsUserId());
        if (!ObjectUtils.isEmpty(users.getNickName())){
            bbs.setNickName(users.getNickName());
        }
        if (!ObjectUtils.isEmpty(users.getNickPic())){
            bbs.setAccountPic(users.getNickPic());
        }
        bbs.setBbsIdTwo(bbs.getBbsId().toString());
        bbs.setBbsUserIdTwo(bbs.getBbsUserId().toString());
        return bbs;
    }

    /**
     * 查询用户热门文章
     *
     * @param userId
     * @return
     */
    @Override
    public List<BbsHot> bbsUserHot(long userId) {
        List<BbsHot> hotListCopy = bbsMapper.bbsUserHot(userId);
        List<BbsHot> hotList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(hotListCopy)){
            if (hotListCopy.size() == 1){
                hotList.add(hotListCopy.get(0));
            }
            hotList.add(hotListCopy.get(0));
            hotList.add(hotListCopy.get(1));
        }else {
            return null;
        }
        return hotList;
    }

    private List<Bbs> getBbs(List<String> searchKeys, Integer pageNum, Integer pageSize,Integer type) {
        List<Bbs> bbs = bbsMapper.searchPostByKeys(searchKeys,type);
        if (ObjectUtil.isNull(bbs)) {
            return ListUtil.empty();
        }
        CACHE_SEARCH_POST_SERVICE.submit(() -> {
            //将搜索到的帖子根据key缓存到redis中
            searchKeys.forEach(key -> {
                        Set<Bbs> bbsByKey = new HashSet<>();
                        //根据key将bbs进行分类
                        bbs.forEach(bbs1 -> {
                            if (bbs1.getBbsTitle().contains(key)) {
                                bbsByKey.add(bbs1);
                            }
                        });
                        String redisKey = SEARCH_UPDATETIME_POST_KEY + key + ":" + pageSize + ":" + pageNum;
                        Set<Bbs> cacheSet = redisCache.getCacheSet(redisKey);
                        //防止redis中的内容丢失
                        if (!ObjectUtil.isNull(cacheSet) && !cacheSet.isEmpty()) {
                            bbsByKey.addAll(cacheSet);
                        }
                        redisCache.setCacheSet(redisKey, bbsByKey);
                        redisCache.expire(redisKey, 30, TimeUnit.MINUTES);
                    }
            );
        });
        return bbs;
    }

    /**
     * 点赞
     * 先查 bbs 表中是否有对应的 bbsId , 再向 bbs_praise 添加字段
     *
     * @param bbsId  点赞贴子id
     * @param userId 点赞用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultCodeEnum praise(Long bbsId, Long userId) throws WebServiceException {
        if (!bbsMapper.updatePraise(bbsId)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERROR_CODE_BBS_UPDATE_ERROR);
        }
        if (!bbsPraiseMapper.insertPraise(bbsId, userId)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERROR_CODE_BBS_PRAISE_INSERT_ERROR);
        }
        return ResultCodeEnum.SUCCESS;
    }

    /**
     * 取消点赞
     * 先查 bbs_praise 表中是否有对应的 bbsId-userId 字段并删除, 再更新 bbs 表
     *
     * @param bbsId  取消点赞贴子id
     * @param userId 取消点赞用户id
     * @return 点赞是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultCodeEnum cancelPraise(Long bbsId, Long userId) throws WebServiceException {
        if (!bbsPraiseMapper.deletePraise(bbsId, userId)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERROR_CODE_BBS_PRAISE_DELETE_ERROR);
        }
        if (!bbsMapper.cancelPraise(bbsId)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERROR_CODE_BBS_UPDATE_ERROR);
        }
        return ResultCodeEnum.SUCCESS;
    }

    /**
     * 点踩
     * 先查 bbs 表中是否有对应的 bbsId , 再向 bbs_no 添加字段
     *
     * @param bbsId  点赞贴子id
     * @param userId 点赞用户id
     * @return 点踩是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultCodeEnum no(Long bbsId, Long userId) throws WebServiceException {
        if (!bbsMapper.updateNo(bbsId)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERROR_CODE_BBS_UPDATE_ERROR);
        }
        if (!bbsNoMapper.insertNo(bbsId, userId)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERROR_CODE_BBS_NO_INSERT_ERROR);
        }
        return ResultCodeEnum.SUCCESS;
    }

    /**
     * 取消点踩
     * 先查 bbs_no 表中是否有对应的 bbsId-userId 字段并删除, 再更新 bbs 表
     *
     * @param bbsId  取消点踩贴子id
     * @param userId 取消点踩用户id
     * @return 点踩是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultCodeEnum cancelNo(Long bbsId, Long userId) throws WebServiceException {
        if (!bbsNoMapper.deleteNo(bbsId, userId)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERROR_CODE_BBS_NO_DELETE_ERROR);
        }
        if (!bbsMapper.cancelNo(bbsId)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERROR_CODE_BBS_UPDATE_ERROR);
        }
        return ResultCodeEnum.SUCCESS;
    }

    /**
     * 打赏
     *
     * @param bbsReward
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reward(BbsReward bbsReward) {
        BigDecimal balance = myWalletMapper.getBalance(UserThreadLocal.get().getUserId());
        BigDecimal subtractBalance = balance.subtract(bbsReward.getMoney());
        sendMessageAsync(KafkaTopicEnum.TOPIC_BBS_REWARD_SEND, JSONObject.toJSONString(OrderUtils.makeTransaction(bbsReward.getMoney(), UserThreadLocal.get().getUserId(),
                bbsReward.getBeUserId(), bbsReward.getContent(), bbsReward.getTransactionBeUserNick(), subtractBalance)));
    }


    /**
     * kafka生产者
     *
     * @param topic
     * @param s
     */
    @Async
    public void sendMessageAsync(String topic, String s) {
        kafkaUtils.sendMessage(topic, s);
    }
}
