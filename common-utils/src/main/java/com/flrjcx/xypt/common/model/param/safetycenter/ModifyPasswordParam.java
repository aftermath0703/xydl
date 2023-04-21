package com.flrjcx.xypt.common.model.param.safetycenter;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gyyst
 * @purpose
 * @Create by 2022/10/20 22:38
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordParam implements Serializable {
    @ApiModelProperty(value = "密码", name = "password")
    private String password;

    @ApiModelProperty(value = "验证码", name = "validateCode")
    private String validateCode;
}
