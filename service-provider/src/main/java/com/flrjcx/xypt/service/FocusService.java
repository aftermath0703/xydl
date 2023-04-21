package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.model.param.focus.UserPartList;

import java.util.List;

/**
 * @Author: code muxiaoming
 * @Date Created in 2022/10/19 21:09
 * @Description:
 * @Modified By:
 * @version: $
 */
public interface FocusService {

    /**
     * 粉丝关注
     *
     * @param idolId 偶像id
     */
    boolean focus(long idolId);

    /**
     * 查询用户(粉丝)的关注列表
     *
     * @param id
     * @return
     */
    List<UserPartList> focusList(Long id);

    /**
     * 查询用户(偶像)的粉丝列表
     *
     * @param id
     * @return
     */
    List<UserPartList> fansList(Long id);

    /**
     *  粉丝取关,粉转路
     * @param idolId
     * @return
     */
    boolean cancel(long idolId);
}
