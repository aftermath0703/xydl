package com.flrjcx.xypt.common.model.param.bbs;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : aftermath
 * @date : 2022-11-04 11:15:07
 */
@Data
@AllArgsConstructor
public class BbsNo implements Serializable {
    private static final long serialVersionUID = -669023758078111458L;

    @ApiModelProperty(value = "点踩帖子id", name = "noBbsId")
    private Long noBbsId;
    @ApiModelProperty(value = "点踩用户id", name = "noUserId")
    private Long noUserId;
}
