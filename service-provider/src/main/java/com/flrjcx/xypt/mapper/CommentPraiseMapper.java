package com.flrjcx.xypt.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author xwl
 * @date 2022-11-14
 * @desc: 点赞评论接口
 */
public interface CommentPraiseMapper {

    /**
     * 插入点赞评论信息是否成功
     *
     * @param commentId 评论id
     * @param userId 用户id
     * @return 返回插入点赞评论信息
     */
    boolean insertPraiseComment(@Param("commentId") Long commentId,
                                @Param("userId") Long userId);

    /**
     * 删除点赞评论信息是否成功
     *
     * @param commentId 评论id
     * @param userId 用户id
     * @return 返回插入点赞评论信息
     */
    boolean deletePraiseComment(@Param("commentId") Long commentId,
                                @Param("userId") Long userId);
}