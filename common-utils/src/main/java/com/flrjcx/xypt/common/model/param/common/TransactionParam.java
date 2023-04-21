package com.flrjcx.xypt.common.model.param.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金明细,平台交易额实体类
 * @JsonInclude(JsonInclude.Include.NON_NULL):为空的属性不返回
 *
 * @author Flrjcx
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionParam implements Serializable {
    private static final long serialVersionUID = -1901513045220126055L;
    @ApiModelProperty(value = "订单号", name = "transactionId")
    private String transactionId;
    @ApiModelProperty(value = "交易金额", name = "transactionAmount")
    private BigDecimal transactionAmount;
    @ApiModelProperty(value = "用户id", name = "transactionUserId")
    private Long transactionUserId;
    @ApiModelProperty(value = "交易对方昵称", name = "transactionBeUserNick")
    private String transactionBeUserNick;
    @ApiModelProperty(value = "交易对方ID", name = "transactionBeUserId")
    private Long transactionBeUserId;
    @ApiModelProperty(value = "交易内容", name = "transactionContent")
    private String transactionContent;
    @ApiModelProperty(value = "交易类型", name = "transactionType")
    private int transactionType;
    @ApiModelProperty(value = "交易后余额", name = "transactionBalance")
    private BigDecimal transactionBalance;
    @ApiModelProperty(value = "交易时间", name = "transactionCreateTime")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date transactionCreateTime;
}
