package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.UserThreadLocal;
import com.flrjcx.xypt.service.FocusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

/**
 * @Author: code muxiaoming
 * @Date Created in 2022/10/20 21:54
 * @Description:
 * @Modified By:
 * @version: $
 */
@Api(tags = "关注模块")
@ApiRestController("/focus")
@Log4j2
public class FocusController {

    @Resource
    private FocusService focusService;

    @ApiOperation("用户关注")
    @PostMapping("/clickFocus")
    public ResponseData focus(long idolId) {
        try {
            boolean focus = focusService.focus(idolId);
            return ResponseData.buildResponse();
        } catch (Exception e) {
            log.error("/focus error {}", e);
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

    @ApiOperation("用户关注")
    @PostMapping("/cancelFocus")
    public ResponseData cancel(long idolId) {
        try {
            boolean focus = focusService.cancel(idolId);
            return ResponseData.buildResponse();
        } catch (Exception e) {
            log.error("/user cancel focus error {}", e);
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }


    @ApiOperation("关注列表")
    @GetMapping("/focusList")
    @Validation
    public ResponseData focusList() {
        try {
            Users users = UserThreadLocal.get();
            return ResponseData.buildPageResponse(focusService.focusList(users.getUserId()));
        } catch (Exception e) {
            log.error("/focusList error {}", e);
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

    @ApiOperation("粉丝列表")
    @GetMapping("/fansList")
    @Validation
    public ResponseData fansList() {
        try {
            Users users = UserThreadLocal.get();
            return ResponseData.buildPageResponse(focusService.fansList(users.getUserId()));
        } catch (Exception e) {
            log.error("/fansList error {}", e);
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }
}
