package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.TokenService;
import com.flrjcx.xypt.service.ManageUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: aftermath
 * @Date: 2022-10-23 19:51:00
 * @Desc:
 */
@Api(tags = "管理用户模块")
@ApiRestController("/manageUser")
@Log4j2
public class ManageUserController {
    @Resource
    ManageUserService manageUserService;

    @Resource
    TokenService tokenService;

    @OpenPage
    @Validation
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/getUserList")
    public ResponseData getUserList() {
        try {
            return ResponseData.buildPageResponse(manageUserService.getUserList());
        } catch (Exception e) {
            log.error("/getUserList error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_QUERY_LIST);
        }
    }

    @OpenPage
    @Validation
    @ApiOperation(value = "查询正常或异常用户列表")
    @GetMapping("/getUserListByStatus")
    public ResponseData getUserListByStatus(Integer status) {
        try {
            return ResponseData.buildPageResponse(manageUserService.getUserListByStatus(status));
        } catch (Exception e) {
            log.error("/getUserListByStatus error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_QUERY_STATUS_LIST);
        }
    }

    @OpenPage
    @Validation
    @ApiOperation(value = "注册时间范围查询用户列表")
    @GetMapping("/getUserListByRegisterTime")
    public ResponseData getUserListByRegisterTime(@RequestParam(value = "begin") String begin,
                                                  @RequestParam(value = "end") String end) {
        try {
            return ResponseData.buildPageResponse(manageUserService.getUserListByRegisterTime(begin, end));
        } catch (Exception e) {
            log.error("/getUserListByRegisterTime error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_QUERY_REGISTER_TIME_LIST);
        }
    }

    @Validation
    @ApiOperation(value = "查询用户详情")
    @GetMapping("/getUserInfo")
    public ResponseData getUserInfo(@RequestParam(value = "userId") long userId) {
        try {
            return ResponseData.buildResponse(manageUserService.getUserInfo(userId));
        } catch (Exception e) {
            log.error("/getUserInfo error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_DETAIL);
        }
    }

    @Validation
    @ApiOperation(value = "修改用户")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/updateUser")
    public ResponseData updateUser(@RequestBody Users user, @RequestHeader("Authorization") String token) {
        try {
            return ResponseData.buildResponse(manageUserService.updateUser(user, token));
        } catch (Exception e) {
            log.error("/updateUser error" + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_UPDATE);
        }
    }

    @Validation
    @ApiOperation(value = "封禁用户")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/deleteUser")
    public ResponseData deleteUser(@RequestBody Users user) {
        try {
            if(manageUserService.deleteUser(user.getUserId(),user.getBanReason())) {
                return ResponseData.buildSuccess();
            } else {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_DELETE);
            }
        } catch (Exception e) {
            log.error("/deleteUser error" + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_DELETE);
        }
    }

    /**
     * 根据账号或昵称模糊查询列表
     *
     * @param account 账号或昵称
     * @return 用户列表
     */
    @OpenPage
    @Validation
    @ApiOperation(value = "根据账号或昵称模糊查询列表")
    @GetMapping("/findByNickNameOrAccount")
    public ResponseData findByNickNameOrAccount(String account) {
        try {
            return ResponseData.buildPageResponse(manageUserService.findByNickNameOrAccount(account));
        } catch (Exception e) {
            log.error("/findByNickNameOrAccount error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_QUERY_LIST);
        }
    }

    @Validation
    @ApiOperation(value = "解除封禁用户")
    @GetMapping("/rescindUser")
    public ResponseData rescindUser(@RequestParam long userId) {
        try {
            manageUserService.rescindUser(userId);
            return ResponseData.buildResponse();
        } catch (Exception e) {
            log.error("/rescindUser error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_BAN_REASON_NULL);
        }
    }

    @Validation
    @ApiOperation(value = "授权用户")
    @GetMapping("/impowerUser")
    public ResponseData impowerUser(@RequestParam long userId) {
        try {
            manageUserService.impowerUser(userId);
            return ResponseData.buildResponse();
        } catch (Exception e) {
            log.error("/impowerUser error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_IMPOWER_USER);
        }
    }
    @Validation
    @ApiOperation(value = "取消授权用户")
    @GetMapping("/cancelImpowerUser")
    public ResponseData cancelImpowerUser(@RequestParam long userId) {
        try {
            manageUserService.cancelImpowerUser(userId);
            return ResponseData.buildResponse();
        } catch (Exception e) {
            log.error("/cancelImpowerUser error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CANCEL_IMPOWER_USER);
        }
    }
    @Validation
    @ApiOperation(value = "查询已授权用户")
    @GetMapping("/selectImpowerUser")
    public ResponseData selectImpowerUser() {
        try {
            return ResponseData.buildPageResponse(manageUserService.selectImpowerUser());
        } catch (Exception e) {
            log.error("/selectImpowerUser error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_SELECT_IMPOWER_USER);
        }
    }
}
