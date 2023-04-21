package com.flrjcx.xypt.common.model.param.bbs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : aftermath
 * @date : 2022-11-04 10:12:47
 */
@Data
public class BbsHot implements Serializable {

    private static final long serialVersionUID = 586356564338303200L;

    @ApiModelProperty(value = "主键id", name = "bbsId")
    private Long bbsId;
    @ApiModelProperty(value = "标题", name = "bbsTitle")
    private String bbsTitle;
}
