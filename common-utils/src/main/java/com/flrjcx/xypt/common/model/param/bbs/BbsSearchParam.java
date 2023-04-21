package com.flrjcx.xypt.common.model.param.bbs;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gyyst
 * @Description
 * @Create by 2022/11/23 8:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BbsSearchParam {
    @ApiModelProperty(value = "查询内容", name = "searchBody")
    private String searchBody;
}
