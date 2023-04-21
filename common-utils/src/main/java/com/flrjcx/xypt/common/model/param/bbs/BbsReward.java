package com.flrjcx.xypt.common.model.param.bbs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 打赏参数
 *
 * @author Flrjcx
 */
@Data
public class BbsReward implements Serializable {
    private static final long serialVersionUID = 1945855218749116478L;
    @ApiModelProperty(value = "被打赏人id", name = "beUserId")
    private Long beUserId;
    @ApiModelProperty(value = "打赏金额", name = "money")
    private BigDecimal money;
    @ApiModelProperty(value = "打赏原因", name = "content")
    private String content;
    @ApiModelProperty(value = "被打赏用户昵称", name = "transactionBeUserNick")
    private String transactionBeUserNick;
}
