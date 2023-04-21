package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.param.bbs.Bbs;
import com.flrjcx.xypt.common.model.param.bbs.BbsEditParam;
import com.flrjcx.xypt.common.model.param.bbs.BbsHot;
import com.flrjcx.xypt.common.model.param.bbs.BbsReward;
import com.flrjcx.xypt.common.model.param.common.Users;

import java.util.List;

/**
 * @author : aftermath
 * @date : 2022-11-04 09:36:20
 */
public interface BbsService {


    /**
     * 点赞
     *
     * @param bbsId  点赞贴子id
     * @param userId 点赞用户id
     * @return 点赞是否成功
     */
    ResultCodeEnum praise(Long bbsId, Long userId);

    /**
     * 取消点赞
     *
     * @param bbsId  取消点赞贴子id
     * @param userId 取消点赞用户id
     * @return 点赞是否成功
     */
    ResultCodeEnum cancelPraise(Long bbsId, Long userId);

    /**
     * 点踩
     *
     * @param bbsId  点赞贴子id
     * @param userId 点赞用户id
     * @return 点踩是否成功
     */
    ResultCodeEnum no(Long bbsId, Long userId);

    /**
     * 取消点踩
     *
     * @param bbsId  取消点踩贴子id
     * @param userId 取消点踩用户id
     * @return 点踩是否成功
     */
    ResultCodeEnum cancelNo(Long bbsId, Long userId);

    /**
     * 打赏
     *
     * @param reward
     */
    void reward(BbsReward reward);

    /**
     * 编辑帖子
     *
     * @param param 帖子更新对象
     * @param users 发送修改帖子请求的用户
     * @return 是否修改成功
     */
    boolean editPost(BbsEditParam param, Users users);

    /**
     * 根据id删除帖子
     *
     * @param bbsId 帖子id
     * @param users 发送删帖请求的用户
     * @return 是否删除成功
     */
    boolean deletePostById(Long bbsId, Users users);

    /**
     * 根据keys搜索帖子
     *
     * @param searchKeys 关键词集合
     * @param pageNum    当前页数
     * @param pageSize   每页大小
     * @param type       查询类型
     * @return 帖子集合
     */
    List<Bbs> searchPosts(List<String> searchKeys, Integer pageNum, Integer pageSize,Integer type);

    /**
     * 发帖
     *
     * @param bbs
     * @return
     */
    void production(Bbs bbs);

    /**
     * 查询帖子列表
     *
     * @return
     */
    List<Bbs> bbsList();

    /**
     * 查询帖子详情
     *
     * @param bbsId
     * @return
     */
    Bbs bbsDetails(long bbsId);

    /**
     * 查询用户热门文章
     *
     * @param userId
     * @return
     */
    List<BbsHot> bbsUserHot(long userId);

}
