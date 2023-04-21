package com.flrjcx.xypt.common.model.param.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author malaka
 * @Description 邮箱发送
 * @date 2022/10/26 0:57
 */
@Data
public class EmailSendParam implements Serializable {
    @ApiModelProperty(value = "发送地址",name = "address")
    private String address;
    @ApiModelProperty(value = "验证token",name = "token")
    private String token;
    @ApiModelProperty(value = "发送给的用户名",name = "name")
    private String name;
    @ApiModelProperty(value = "验证码",name = "code")
    private String code;

}
