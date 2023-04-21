package com.flrjcx.xypt.common.model.param.focus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: code muxiaoming
 * @Date Created in 2022/10/19 22:33
 * @Description:
 * @Modified By:
 * @version: $
 */
@Data
public class Attent implements Serializable {
    private static final long serialVersionUID = 5426215250016949594L;
//    加上name
    @ApiModelProperty(value = "用户id",name = "userId", notes = "就是粉丝id")
    private Long userId;
    @ApiModelProperty(value = "被关注用户id", notes = "就是偶像id")
    private Long attentUserId;
    @ApiModelProperty(value = "创建时间", notes = "就是关注时间")
    private Date createTime;
}
