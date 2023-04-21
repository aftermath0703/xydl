package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.focus.Attent;
import com.flrjcx.xypt.common.model.param.focus.UserPartList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: code muxiaoming
 * @Date Created in 2022/10/20 20:49
 * @Description:
 * @Modified By:
 * @version: $
 */
public interface FocusMapper {

    /**
     *  根据偶像id查询其粉丝数
     * @param idolId
     * @return
     */
    int fansNum(Long idolId);

    /**
     * 查询用户的关注(偶像)数
     * @param fansId
     * @return
     */
    int focusNum(Long fansId);

    /**
     * 查询粉丝与偶像的信息
     *
     * @param ids
     * @return
     */
    public List<Users> queryByArray(long[] ids);

    /**
     * 根据id查询用户信息,主要是查询fansNum与focusNum
     *
     * @param UserId userId
     * @return
     */
    Users getById(Long UserId);

    /**
     * 根据id更新用户信息(动态sql,只更新users对象中非空字段)
     * 如果user为粉丝,则focusNum不为空,fansNum为空
     * 如果user为偶像(被关注者)则反之
     *
     * @param user 用户
     * @return
     */
    int updateFocus(@Param("user") Users user);

    /**
     * 动态批量更新
     *
     * @param users
     * @return
     */
    int updateByList(List<Users> users);

    /**
     * 生成关注记录:关注者与被关注者
     *
     * @param param
     * @return
     */
    int addAttent(@Param("param") Attent param);

    /**
     * 获取用户关注的用户id
     * 即偶像们的idList
     * @param id
     * @return
     */
    List<Long> idolIdList(@Param("id") Long id);

    /**
     * 根据id获取用户列表(粉丝/关注列表)
     *
     * @param userIdList
     * @return
     */
    @OpenPage
    List<UserPartList> getUserPartList(@Param("userIdList")List<Long> userIdList);

    /**
     * 获取用户的粉丝id
     *
     * @param id
     * @return
     */
    List<Long> fansIdList(@Param("id") Long id);

    /**
     * 用户取关,根据用户和粉丝id删除对应数据
     * @param attent
     * @return
     */
    int deleteAttent(@Param("attent")Attent attent);
}
