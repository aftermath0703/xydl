package com.flrjcx.xypt.common.model.param.register;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录接收参数
 *
 * @author Flrjcx
 */
@Data
public class LoginParam implements Serializable {

    private static final long serialVersionUID = -833137292223017257L;
    @ApiModelProperty(value = "登录用户", name = "user")
    private String user;
    @ApiModelProperty(value = "登录密码", name = "userPassword")
    private String userPassword;
    @ApiModelProperty(value = "验证码uuid", name = "uuid")
    private String uuid;
    @ApiModelProperty(value = "登录类型", name = "loginType")
    private String loginType;
    @ApiModelProperty(value = "验证码", name = "verifyCode")
    private String verifyCode;

}
