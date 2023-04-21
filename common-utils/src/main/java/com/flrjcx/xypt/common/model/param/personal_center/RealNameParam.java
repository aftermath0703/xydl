package com.flrjcx.xypt.common.model.param.personal_center;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实名接收参数
 *
 * @author &nbsp
 */
@Data
public class RealNameParam implements Serializable {
    private static final long serialVersionUID = 5556738691755672160L;
    @ApiModelProperty(value = "实名注册的用户id", name = "realRegisterUserId")
    private Long realRegisterUserId;
    @ApiModelProperty(value = "用户真实姓名", name = "realName")
    private String realName;
    @ApiModelProperty(value = "用户身份证", name = "idCard")
    private String idCard;
}
