package com.flrjcx.xypt.common.model.param.email;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 忘记密码参数实体
 * @author &nbsp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordParam implements Serializable {

    private static final long serialVersionUID = -1709843759629878823L;

    @ApiModelProperty(value = "用户id", name = "userId")
    private Long userId;
    @ApiModelProperty(value = "邮箱账号", name = "emailAddress")
    private String emailAddress;
    @ApiModelProperty(value = "新密码", name = "newPassword")
    private String newPassword;
    @ApiModelProperty(value = "验证码", name = "verifyCode")
    private String verifyCode;

}
