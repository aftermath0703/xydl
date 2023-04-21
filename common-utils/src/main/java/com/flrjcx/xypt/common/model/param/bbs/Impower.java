package com.flrjcx.xypt.common.model.param.bbs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 授权用户
 *
 * @author Flrjcx
 */
@Data
public class Impower implements Serializable {
    private static final long serialVersionUID = -8181112386072800428L;
    @ApiModelProperty(value = "授权id", name = "impower_id")
    private String impowerId;
    @ApiModelProperty(value = "被授权用户id", name = "impower_user_id")
    private Long impowerUserId;
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Date createTime;
    @ApiModelProperty(value = "修改时间", name = "updateTime")
    private Date updateTime;

}
