package com.flrjcx.xypt.controller;

import cn.hutool.core.util.ObjectUtil;
import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.param.bbs.*;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.UserThreadLocal;
import com.flrjcx.xypt.mapper.MyWalletMapper;
import com.flrjcx.xypt.service.BbsService;
import com.flrjcx.xypt.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 与论坛表相关的操作
 *
 * @author : aftermath
 * @date : 2022-11-03 19:48:43
 */
@Api(tags = "论坛")
@ApiRestController("/bbs")
@Log4j2
public class BbsController {

    @Resource
    CommentService commentService;
    @Resource
    private BbsService bbsService;
    @Resource
    private MyWalletMapper myWalletMapper;

    /**
     * 用户点赞帖子接口
     * <p>
     * 如果前端接收到错误信息, 不要更新点赞图标, 以免后续取消点赞出现bug
     * 点/踩接口都加了事务, 有错误会直接回滚
     * 点/踩接口都加了事务, 有错误会直接回滚,
     *
     * @return 统一响应
     */
    @Validation
    @ApiOperation("点赞")
    @PostMapping("/praise")
    public ResponseData praise(@RequestParam("bbsId") Long bbsId) {
        Long userId = UserThreadLocal.get().getUserId();
        ResultCodeEnum resultCodeEnum = bbsService.praise(bbsId, userId);
        return ResponseData.buildResponse(resultCodeEnum);
    }

    @Validation
    @ApiOperation("取消点赞")
    @PostMapping("/cancelPraise")
    public ResponseData cancelPraise(@RequestParam("bbsId") Long bbsId) {
        Long userId = UserThreadLocal.get().getUserId();
        ResultCodeEnum resultCodeEnum = bbsService.cancelPraise(bbsId, userId);
        return ResponseData.buildResponse(resultCodeEnum);
    }

    /**
     * 用户点踩帖子接口
     *
     * @return 统一响应
     */
    @Validation
    @ApiOperation("点踩")
    @PostMapping("/no")
    public ResponseData no(@RequestParam("bbsId") Long bbsId) {
        Long userId = UserThreadLocal.get().getUserId();
        ResultCodeEnum resultCodeEnum = bbsService.no(bbsId, userId);
        return ResponseData.buildResponse(resultCodeEnum);
    }

    @Validation
    @ApiOperation("取消点踩")
    @PostMapping("/cancelNo")
    public ResponseData cancelNo(@RequestParam("bbsId") Long bbsId) {
        Long userId = UserThreadLocal.get().getUserId();
        ResultCodeEnum resultCodeEnum = bbsService.cancelNo(bbsId, userId);
        return ResponseData.buildResponse(resultCodeEnum);
    }

    /**
     * 打赏
     *
     * @param reward
     * @return
     */
    @Validation
    @ApiOperation("打赏作者")
    @PostMapping("/reward")
    public ResponseData reward(@RequestBody BbsReward reward) {
        if (Objects.equals(reward.getMoney(), new BigDecimal(0))) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_REWARD_NULL);
        }
        if (myWalletMapper.checkMoney(reward.getMoney(), UserThreadLocal.get().getUserId())) {
            bbsService.reward(reward);
            return ResponseData.buildResponse();
        }
        return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_REWARD_MAX);
    }

    @Validation
    @ApiOperation("编辑帖子")
    @PostMapping("/edit")
    public ResponseData editPost(@RequestBody BbsEditParam param) {
        if (ObjectUtil.isNull(param) && ObjectUtil.isNull(param.getBbsId())) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY, "参数不能为空");
        }
        Users users = UserThreadLocal.get();
        boolean editPost = bbsService.editPost(param, users);
        if (editPost) {
            return ResponseData.buildResponse(editPost);
        } else {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_BBS_EDIT_ERROR);
        }
    }

    @Validation
    @ApiOperation("删除帖子")
    @PostMapping("/delete")
    public ResponseData deletePost(@RequestParam Long bbsId) {
        if (ObjectUtil.isNull(bbsId)) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY, "bbsId不能为空");
        }
        Users users = UserThreadLocal.get();
        boolean deletePost = bbsService.deletePostById(bbsId, users);
        if (deletePost) {
            return ResponseData.buildResponse(deletePost);
        } else {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_BBS_DEL_ERROR);
        }
    }

    @ApiOperation("搜索帖子")
    @PostMapping("/search")
    @OpenPage
    public ResponseData searchPost(@RequestBody BbsSearchParam bbsSearchParam,
                                   @RequestParam Integer pageSize,
                                   @RequestParam Integer pageNum,
                                   Integer type) {
        String searchBody = bbsSearchParam.getSearchBody();
        if (ObjectUtil.isNull(bbsSearchParam) || ObjectUtil.isNull(searchBody)) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY);
        }
        if (ObjectUtil.isNull(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtil.isNull(pageSize)) {
            pageSize = 10;
        }
        List<String> searchKeys = Arrays.stream(searchBody.split(" ")).collect(Collectors.toList());
        List<Bbs> bbs = bbsService.searchPosts(searchKeys, pageNum, pageSize,type);
        if (ObjectUtil.isNull(bbs)) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_SEARCH_POST_EMPTY);
        }
        return ResponseData.buildResponse(bbs);
    }

    @ApiOperation("发帖")
    @PostMapping("/production")
    @Validation
    public ResponseData production(@RequestBody Bbs bbs) {
        if (ObjectUtils.isEmpty(bbs.getBbsTitle())) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_BBS_TITLE_NULL);
        }
        if (ObjectUtils.isEmpty(bbs.getBbsContext())) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_BBS_CONTEXT_NULL);
        }
        if (ObjectUtils.isEmpty(bbs.getBbsDescription())){
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_BBS_DESCRIPTION_NULL);
        }
        if (ObjectUtils.isEmpty(bbs.getBbsCoverPic())){
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_BBS_COVERPIC_NULL);
        }
        bbsService.production(bbs);
        return ResponseData.buildResponse();
    }

    @ApiOperation("查询帖子列表")
    @GetMapping("/bbsList")
    public ResponseData bbsList() {
        return ResponseData.buildPageResponse(bbsService.bbsList());
    }

    @ApiOperation("查询帖子详情")
    @GetMapping("/bbsDetails")
    public ResponseData bbsDetails(@RequestParam long bbsId) {
        return ResponseData.buildResponse(bbsService.bbsDetails(bbsId));
    }

    @ApiOperation("查询用户热门文章")
    @GetMapping("/bbsUserHot")
    public ResponseData bbsUserHot(@RequestParam long userId) {
        List<BbsHot> hotList = bbsService.bbsUserHot(userId);
        if (ObjectUtils.isEmpty(hotList)){
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_BBS_HOT_NULL);
        }
        return ResponseData.buildResponse(hotList);
    }
}
