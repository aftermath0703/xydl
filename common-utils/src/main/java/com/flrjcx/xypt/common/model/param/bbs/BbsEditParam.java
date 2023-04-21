package com.flrjcx.xypt.common.model.param.bbs;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gyyst
 * @Description
 * @Create by 2022/11/17 15:24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BbsEditParam implements Serializable {

    private static final long serialVersionUID = -990141828392098766L;
    @ApiModelProperty(value = "主键id", name = "bbsId")
    private Long bbsId;
    @ApiModelProperty(value = "标题", name = "bbsTitle")
    private String bbsTitle;
    @ApiModelProperty(value = "内容markdown格式", name = "bbsContextMd")
    private String bbsContextMd;
    @ApiModelProperty(value = "内容", name = "bbsContext")
    private String bbsContext;
    @ApiModelProperty(value = "类型", name = "type")
    private Integer type;

}
