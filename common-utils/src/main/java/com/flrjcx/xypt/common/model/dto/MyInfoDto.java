package com.flrjcx.xypt.common.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : aftermath
 * @date : 2022-11-05 21:08:51
 */
@Data
public class MyInfoDto implements Serializable {
    private static final long serialVersionUID = -5383949320336831473L;
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
    @ApiModelProperty(value = "粉丝数量", name = "fansNum")
    private Integer fansNum;
    @ApiModelProperty(value = "关注数量", name = "focusNum")
    private Integer focusNum;
    @ApiModelProperty(value = "所在地址", name = "address")
    private String address;
    @ApiModelProperty(value = "学校", name = "school")
    private String school;
    @ApiModelProperty(value = "用户真实姓名", name = "realName")
    private String realName;
}
