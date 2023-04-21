package com.flrjcx.xypt.common.model.param.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author Flrjcx
 */
@Data
public class Users implements Serializable {
    private static final long serialVersionUID = -5818798255750404065L;
    @ApiModelProperty(value = "备用用户id", name = "userIdTwo")
    private String userIdTwo;
    @ApiModelProperty(value = "用户id", name = "userId")
    private Long userId;
    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;
    @ApiModelProperty(value = "头像", name = "nickPic")
    private String nickPic;
    @ApiModelProperty(value = "个人描述", name = "content")
    private String content;
    @ApiModelProperty(value = "性别", name = "sex")
    private String sex;
    @ApiModelProperty(value = "生日", name = "birthday")
    private Date birthday;
    @ApiModelProperty(value = "手机号", name = "phone")
    private String phone;
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;
    @ApiModelProperty(value = "账号", name = "account")
    private String account;
    @ApiModelProperty(value = "密码", name = "password")
    private String password;
    @ApiModelProperty(value = "余额", name = "balance")
    private BigDecimal balance;
    @ApiModelProperty(value = "粉丝数量", name = "fansNum")
    private Integer fansNum;
    @ApiModelProperty(value = "关注数量", name = "focusNum")
    private Integer focusNum;
    @ApiModelProperty(value = "所在地址", name = "address")
    private String address;
    @ApiModelProperty(value = "学校", name = "school")
    private String school;
    @ApiModelProperty(value = "用户注册时间", name = "createTime")
    private Date createTime;
    @ApiModelProperty(value = "修改时间", name = "updateTime")
    private Date updateTime;
    @ApiModelProperty(value = "账号状态", name = "status")
    private String status;
    @ApiModelProperty(value = "隐私状态", name = "privacySetting")
    private String privacySetting;
    @ApiModelProperty(value = "封禁原因", name = "banReason")
    private String banReason;

}
