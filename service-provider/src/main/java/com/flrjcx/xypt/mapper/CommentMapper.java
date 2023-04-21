package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.model.param.comment.Comment;
import feign.Param;

import java.util.List;

/**
 * @author Yyyyyyy
 */
public interface CommentMapper {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    int insert(Comment comment);

    /**
     * 回复评论
     * @param comment
     * @return
     */
    int reply(Comment comment);

    /**
     * 查询全部评论
     * @return
     */
    List<Comment> findAll();

    /**
     * 根据用户id查询评论
     * @param commentUserId
     * @return
     */
    Comment findByCommentUserId(@Param("commentUserId") long commentUserId);

    /**
     * 根据评论id查询评论
     * @param commentId
     * @return
     */
    Comment findByCommentId(@Param("commentId") String commentId);

    /**
     * 根据帖子id查询评论
     * @param bbsId 帖子id
     * @return
     */
    @OpenPage
    List<Comment> findByBbsId(@Param("bbsId") String bbsId);

    /**
     * 删除评论
     * @param bbsId
     * @return
     */
    int delete(@Param("bbsId") String bbsId);

    /**
     * 更新评论点赞数量信息是否成功
     *
     * @param commentId 评论id
     * @param bbsId
     * @param userId
     * @return 返回更新评论表信息
     */
    boolean updateComment(@Param("commentId") Long commentId,
                          @Param("bbsId") Long bbsId,
                          @Param("userId")Long userId);

    /**
     * 更新评论点赞数量信息是否成功
     *
     * @param commentId 评论id
     * @param bbsId 帖子
     * @param userId
     * @return 返回更新评论表信息
     */
    boolean cancelComment(@Param("commentId") Long commentId,
                          @Param("bbsId") Long bbsId,
                          @Param("userId") Long userId);
}
