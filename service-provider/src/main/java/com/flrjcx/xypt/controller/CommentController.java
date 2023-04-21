package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.param.comment.Comment;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.SensitiveWordUtils;
import com.flrjcx.xypt.common.utils.TokenService;
import com.flrjcx.xypt.common.utils.UserThreadLocal;
import com.flrjcx.xypt.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论
 *
 * @author Yyyyyyy
 */

@Api(tags = "评论")
@ApiRestController("/comment")
@Log4j2
public class CommentController {

    @Resource
    private CommentService commentService;


    @Validation
    @ApiOperation(value = "进行评论")
    @PostMapping("/post")
    public ResponseData save(@RequestBody Comment comment) {
        try {
            if (StringUtils.isAnyBlank(comment.getCommentParentId(), comment.getCommentContext())) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_COMMENT_UPDATE_WORKFLOW);
            }
            if (comment.getCommentBbsId() <= 0 || comment.getCommentFloor() < 0 || comment.getLevel() <= 0) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_COMMENT_UPDATE_WORKFLOW);
            }
            Comment data = commentService.post(comment.getCommentBbsId(), comment.getCommentParentId(),
                    comment.getLevel(), comment.getCommentContext(), comment.getCommentFloor());
            return ResponseData.buildResponse(data);
        } catch (Exception e) {
            log.error("post comment error");
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR);
        }
    }

    @Validation
    @ApiOperation(value = "回复评论")
    @PostMapping("/reply")
    public ResponseData reply(@RequestBody Comment comment) {
        try {
            if (StringUtils.isAnyBlank(comment.getCommentParentId(), comment.getCommentContext())) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_COMMENT_UPDATE_WORKFLOW);
            }
            if (comment.getCommentBbsId() <= 0 || comment.getCommentFloor() < 0 || comment.getLevel() <= 0) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_COMMENT_UPDATE_WORKFLOW);
            }
            Comment data = commentService.reply(comment.getCommentBbsId(), comment.getCommentParentId(),
                    comment.getLevel(), comment.getCommentContext(), comment.getCommentFloor());
            return ResponseData.buildResponse(data);
        } catch (Exception e) {
            log.error("reply comment error");
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR);
        }
    }

    @Validation
    @ApiOperation(value = "删除评论")
    @GetMapping("/del")
    public ResponseData delete(@RequestParam("bbsId") String bbsId) {
        try {
            List<Comment> result = commentService.query(bbsId);
            if (result == null) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_COMMENT_QUERY_WORKFLOW);
            }
            return ResponseData.buildResponse(commentService.delete(bbsId));
        } catch (Exception e) {
            log.error("cancel comment error");
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_COMMENT_DELETE_WORKFLOW);
        }

    }

    @OpenPage
    @ApiOperation(value = "查询全部评论")
    @GetMapping("/pageQueryAll")
    public ResponseData<List<Comment>> queryAll() {
        try {
            List<Comment> res = commentService.queryCommentsList();
            if (res.size() < 0) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_COMMENT_QUERY_WORKFLOW);
            } else {
                return ResponseData.buildResponse(res);
            }
        } catch (Exception e) {
            log.error("Error querying comments", e);
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "根据BbsId查询评论")
    @GetMapping("/queryByBbsId")
    public ResponseData<List<Comment>> queryByBbsId(@RequestParam("bbsId") String bbsId) {
        try {
            List<Comment> res = commentService.query(bbsId);
            if (res.size() < 0) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_COMMENT_QUERY_WORKFLOW);
            } else {
                return ResponseData.buildPageResponse(res);
            }
        } catch (Exception e) {
            log.error("Error querying comments", e);
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }


    /**
     * 用户评论点赞接口
     * 如果前端接收到错误信息, 不要更新点赞评论图标, 以免后续取消点赞评论出现bug
     * 点赞品论接口添加了事务处理, 有错误会直接回滚
     *
     * @return 统一响应
     */
    @Validation
    @ApiOperation("点赞评论")
    @PostMapping("/praiseComment")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData praiseComment(@RequestParam(value = "commentId") Long commentId,
                                      @RequestParam(value = "bbsId") Long bbsId) {
        Long userId = UserThreadLocal.get().getUserId();
        ResultCodeEnum resultCodeEnum = commentService.praiseComment(commentId, userId, bbsId);
        return ResponseData.buildResponse(resultCodeEnum);
    }

    @Validation
    @ApiOperation("取消点赞评论")
    @PostMapping("/cancelPraiseComment")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData cancelPraiseComment(@RequestParam(value = "commentId") Long commentId,
                                            @RequestParam(value = "bbsId") Long bbsId) {
        Long userId = UserThreadLocal.get().getUserId();
        ResultCodeEnum resultCodeEnum = commentService.cancelPraiseComment(commentId, userId, bbsId);
        return ResponseData.buildResponse(resultCodeEnum);
    }

}
