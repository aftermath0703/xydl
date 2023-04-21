package com.flrjcx.xypt.common.model.param.register;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册接收参数
 *
 * @author Flrjcx
 */
@Data
public class AddUserParam implements Serializable {
    private static final long serialVersionUID = 2551934374245952646L;
    @ApiModelProperty(value = "密码", name = "password")
    private String password;
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;
    @ApiModelProperty(value = "账号", name = "account")
    private String account;
    @ApiModelProperty(value = "验证token", name = "token")
    private String token;
    @ApiModelProperty(value = "验证码", name = "code")
    private String code;
}
