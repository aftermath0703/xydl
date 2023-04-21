package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.mapper.ManageUserInfoMapper;
import com.flrjcx.xypt.service.ManageUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: aftermath
 * @Date: 2022-10-27 23:51:23
 * @Desc:
 */
@Service
public class ManageUserInfoServiceImpl implements ManageUserInfoService {
    @Resource
    ManageUserInfoMapper manageUserInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePic(Users user) {
        return manageUserInfoMapper.updatePic(user);
    }
}
