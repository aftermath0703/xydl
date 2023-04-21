package com.flrjcx.xypt.common.model.param.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实名注册实体类
 *
 * @author &nbsp
 */
@Data
public class RealRegister implements Serializable {
    private static final long serialVersionUID = -299455171644912717L;
    @ApiModelProperty(value = "实名注册id主键", name = "realRegisterId")
    private Long realRegisterId;
    @ApiModelProperty(value = "实名注册的用户id", name = "realRegisterUserId")
    private Long realRegisterUserId;
    @ApiModelProperty(value = "用户真实姓名", name = "realName")
    private String realName;
    @ApiModelProperty(value = "用户身份证", name = "idCard")
    private String idCard;
    @ApiModelProperty(value = "用户实名时间", name = "createTime")
    private Date createTime;
    @ApiModelProperty(value = "用户更新时间", name = "updateTime")
    private Date updateTime;
}
