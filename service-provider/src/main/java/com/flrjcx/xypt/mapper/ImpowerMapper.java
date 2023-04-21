package com.flrjcx.xypt.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 授权用户
 *
 * @author Flrjcx
 */
public interface ImpowerMapper {

    /**
     * 查询用户是否被授权
     * 
     * @param userId
     * @return
     */
    boolean checkImpower(@Param("userId") Long userId);
}
