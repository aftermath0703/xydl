package com.flrjcx.xypt.common.model.param.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 管理员实体类
 *
 * @author wby
 */
@Data
public class Manager implements Serializable {
    private static final long serialVersionUID = -5818798255750404065L;
    @ApiModelProperty(value = "管理员id", name = "managerId")
    private Long managerId;
    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;
    @ApiModelProperty(value = "头像", name = "nickPic")
    private String nickPic;
    @ApiModelProperty(value = "管理员账号", name = "managerAccount")
    private String managerAccount;
    @ApiModelProperty(value = "密码", name = "nickPassword")
    private String managerPassword;
}
