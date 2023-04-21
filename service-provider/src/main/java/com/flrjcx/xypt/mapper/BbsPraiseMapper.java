package com.flrjcx.xypt.mapper;

/**
 * @author : aftermath
 * @date : 2022-11-04 12:01:50
 */
public interface BbsPraiseMapper {
    /**
     * 添加 bbsId-userId 字段
     * @param bbsId 帖子id
     * @param userId 用户id
     * @return 添加是否成功
     */
    boolean insertPraise(Long bbsId, Long userId);

    /**
     * 删除 bbsId-userId 字段
     * @param bbsId 帖子id
     * @param userId 用户id
     * @return 删除是否成功
     */
    boolean deletePraise(Long bbsId, Long userId);
}
