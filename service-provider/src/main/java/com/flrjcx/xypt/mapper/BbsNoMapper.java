package com.flrjcx.xypt.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author : aftermath
 * @date : 2022-11-04 12:02:15
 */
public interface BbsNoMapper {
    /**
     * 添加 bbsId-userId 字段
     * @param bbsId 帖子id
     * @param userId 用户id
     * @return 添加是否成功
     */
    boolean insertNo(@Param("bbsId") Long bbsId, @Param("userId") Long userId);

    /**
     * 删除 bbsId-userId 字段
     * @param bbsId 帖子id
     * @param userId 用户id
     * @return 删除是否成功
     */
    boolean deleteNo(@Param("bbsId") Long bbsId, @Param("userId") Long userId);
}
