package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.param.comment.Comment;
import com.flrjcx.xypt.common.model.result.ResponseData;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * 评论服务
 *
 * @author Yyyyyyy
 *
 */

public interface CommentService {

    /**
     * 发布评论
     *
     * @param bbsId 帖子id
     * @param parentId 父评论
     * @param level 评论等级
     * @param context 评论内容
     * @param commentFloor 评论楼层
     * @return
     */
    Comment post(long bbsId, String parentId, int level,
                                  String context, int commentFloor);

    Comment reply(long bbsId, String parentId, int level,
                  String context, int commentFloor);

    /**
     * 删除评论
     * @param bbsId
     * @return
     */
    Boolean delete(@RequestParam("bbsId") String bbsId);

    /**
     * 根据帖子id查询评论
     *
     * @param bbsId
     * @return
     */
    List<Comment> query(String bbsId);


    /**
     * 查询评论列表
     *
     * @return 评论集合
     */
    List<Comment> queryCommentsList();


    /**
     * 点赞
     *
     * @param commentId 评论id
     * @param userId 用户id
     * @param bbsId 帖子id
     * @return 点赞是否成功
     */
    ResultCodeEnum praiseComment(Long commentId, Long userId, Long bbsId);

    /**
     * 取消点赞
     *
     * @param commentId 评论id
     * @param userId 用户id
     * @param bbsId 帖子id
     * @return 取消点赞是否成功
     */
    ResultCodeEnum cancelPraiseComment(Long commentId, Long userId, Long bbsId);
}