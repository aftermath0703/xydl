package com.flrjcx.xypt.controller;

import cn.hutool.core.util.ObjectUtil;
import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.safetycenter.ModifyPasswordParam;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.UserThreadLocal;
import com.flrjcx.xypt.service.SafetyCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * @author gyyst
 * @purpose
 * @Create by 2022/10/20 15:36
 */
@Api(tags = "安全中心")
@ApiRestController("/safetyCenter")
@Log4j2
public class SafetyCenterController {
    @Resource
    private SafetyCenterService safetyCenterService;


    @PostMapping("/modifyPassword/sendmail")
    @ApiOperation("发送修改密码邮件")
    @Validation
    public ResponseData modifyPasswordSendMail() {
        Users Users = UserThreadLocal.get();
        String userMail = Users.getEmail();
        if (StringUtils.isEmpty(userMail)) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY.getCode(), "未绑定邮箱");
        }
        Long userId = Users.getUserId();
        if (ObjectUtil.isNull(userId)) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY.getCode(), "未找到用户id");
        }
        boolean sendResetPassWordMail = safetyCenterService.sendResetPassWordMail(userId, userMail);

        return ResponseData.buildResponse(sendResetPassWordMail);
    }

    @PostMapping("/modifyPassword")
    @ApiOperation("修改密码")
    @Validation
    public ResponseData modifyPassword(@RequestBody ModifyPasswordParam modifyPasswordParam) {
        Users Users = UserThreadLocal.get();
        if (ObjectUtil.isNull(modifyPasswordParam)) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY.getCode(), "参数不能为空");
        }
        String submitPassword = modifyPasswordParam.getPassword();
        String validateCode = modifyPasswordParam.getValidateCode();
        if (StringUtils.isAnyEmpty(submitPassword, validateCode)) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY.getCode(), "参数不能为空");
        }
        Long userId = Users.getUserId();
        if (ObjectUtil.isNull(userId)) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY.getCode(), "未找到用户id");
        }
        boolean modifyPassword = safetyCenterService.modifyPassword(userId, submitPassword, validateCode);
        if (modifyPassword) {
            return ResponseData.buildResponse(modifyPassword);
        } else {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_50328);
        }
    }

    @PostMapping("/setPrivacy")
    @ApiOperation("设置隐私")
    @Validation
    public ResponseData setPrivacy(@RequestBody String privacy) {
        Users user = UserThreadLocal.get();
        Long userId = user.getUserId();
        boolean setPrivacy = safetyCenterService.setPrivacy(userId, privacy);
        if (!setPrivacy) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_USER_UPDATE_USER_INFO);
        }
        return ResponseData.buildResponse(setPrivacy);
    }

//    @GetMapping("/getPrivacy")
//    @ApiOperation("查询隐私")
//    @Validation
//    public ResponseData setPrivacy() {
//        Users user = UserThreadLocal.get();
//        Long userId = user.getUserId();
//        UserPrivacy getPrivacy = safetyCenterService.getPrivacy(userId);
//        if (ObjectUtil.isNull(getPrivacy)) {
//            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_DETAIL, "获取隐私设置失败");
//        }
//        return ResponseData.buildResponse(getPrivacy);
//    }

}
