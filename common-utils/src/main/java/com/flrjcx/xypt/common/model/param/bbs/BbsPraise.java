package com.flrjcx.xypt.common.model.param.bbs;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author : aftermath
 * @date : 2022-11-04 11:14:52
 */
@Data
@AllArgsConstructor
public class BbsPraise implements Serializable {
    private static final long serialVersionUID = -669023758078111458L;

    @ApiModelProperty(value = "点赞帖子id", name = "praiseBbsId")
    private Long praiseBbsId;
    @ApiModelProperty(value = "点赞用户id", name = "praiseUserId")
    private Long praiseUserId;
}