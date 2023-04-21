package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.focus.Attent;
import com.flrjcx.xypt.common.model.param.focus.UserPartList;
import com.flrjcx.xypt.common.utils.UserThreadLocal;
import com.flrjcx.xypt.mapper.FocusMapper;
import com.flrjcx.xypt.service.FocusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: code muxiaoming
 * @Date Created in 2022/10/19 21:12
 * @Description:
 * @Modified By:
 * @version: $
 */
@Service
@Slf4j
public class FocusServiceImpl implements FocusService {

    @Resource
    private FocusMapper focusMapper;

    /**
     * 粉丝关注
     *
     * @param idolId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean focus(long idolId) {
        Users user = UserThreadLocal.get();
        Attent attent = new Attent();
        //fansId
        attent.setUserId(user.getUserId());
        //idolId
        attent.setAttentUserId(idolId);
        int i = focusMapper.addAttent(attent);
        process(idolId, user.getUserId());
        return true;
    }

    private void process(long idolId, long fansId) {
        int fansNum = focusMapper.fansNum(idolId);
        int idolNum = focusMapper.focusNum(fansId);
        Users fans = new Users();
        fans.setUserId(fansId);
        fans.setFocusNum(idolNum);
        Users idol = new Users();
        idol.setUserId(idolId);
        idol.setFansNum(fansNum);
        int i1 = focusMapper.updateFocus(fans);
        int i2 = focusMapper.updateFocus(idol);
    }

    /**
     * 查询用户的关注列表
     *
     * @param id
     * @return
     */
    @Override
    public List<UserPartList> focusList(Long id) {
        List<Long> userIdList = focusMapper.idolIdList(id);
        return focusMapper.getUserPartList(userIdList);
    }

    /**
     * 查询用户的粉丝列表
     *
     * @param id
     * @return
     */
    @Override
    public List<UserPartList> fansList(Long id) {
        List<Long> fansIdList = focusMapper.fansIdList(id);
        return focusMapper.getUserPartList(fansIdList);
    }

    /**
     * 粉丝取关,粉转路
     *
     * @param idolId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(long idolId) {
        Users user = UserThreadLocal.get();
        Attent attent = new Attent();
        //fansId
        attent.setUserId(user.getUserId());
        //idolId
        attent.setAttentUserId(idolId);

        int i = focusMapper.deleteAttent(attent);
        process(idolId, user.getUserId());
        return true;
    }
}
