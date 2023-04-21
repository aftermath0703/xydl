package com.flrjcx.xypt.common.model.param.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author gyyst
 * @Description 邮箱发送
 * @date 2022/10/31 22:30
 */
@Data
public class ModifyPasswordEmailSendParam implements Serializable {

    private static final long serialVersionUID = 5424200171831047928L;
    @ApiModelProperty(value = "发送地址", name = "address")
    private String address;
    @ApiModelProperty(value = "验证码", name = "code")
    private String code;

}
