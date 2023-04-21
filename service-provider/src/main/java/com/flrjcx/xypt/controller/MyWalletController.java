package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.constants.DigitalConstants;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.enums.ValidStatusEnum;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.service.MyWalletService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 我的钱包
 *
 * @author Flrjcx
 */
@Api(tags = "我的钱包")
@ApiRestController("/myWallet")
@Log4j2
public class MyWalletController {
    @Resource
    private MyWalletService myWalletService;

    /**
     * 充值/提现
     *
     * @param money
     * @return
     */
    @GetMapping("/recharge")
    @Validation
    public ResponseData recharge(@RequestParam BigDecimal money, @RequestParam int type) {
        if (ObjectUtils.compare(money, new BigDecimal(DigitalConstants.MAX_RECHARGE)) == 1 && type == 0) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_RECHARGE_MAX);
        }
        if (type == ValidStatusEnum.InValidStatus.getCode() && Objects.equals(money, new BigDecimal(0))) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_RECHARGE_NULL);
        }
        if (type == ValidStatusEnum.ValidStatus.getCode() && Objects.equals(money, new BigDecimal(0))) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DEPOSIT_NULL);
        }
        if (Objects.equals(type, ValidStatusEnum.ValidStatus.getCode()) && Objects.equals(money, ValidStatusEnum.InValidStatus.getCode())) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_RECHARGE_NULL);
        }
        int result = myWalletService.recharge(money, type);
        if (Objects.equals(result, ValidStatusEnum.InValidStatus.getCode())) {
            return ResponseData.buildResponse(ResultCodeEnum.SUCCESS_RECHARGE.getMessage() + money, money);
        }
        if (Objects.equals(result, ValidStatusEnum.ValidStatus.getCode())) {
            return ResponseData.buildResponse(ResultCodeEnum.SUCCESS_DEPOSIT.getMessage() + money, money);
        }
        return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DEPOSIT);
    }

    /**
     * 余额
     *
     * @return
     */
    @GetMapping("/getBalance")
    @Validation
    public ResponseData getBalance() {
        return ResponseData.buildResponse(ResultCodeEnum.SUCCESS.getMessage(), myWalletService.getBalance());
    }


    /**
     * 资金明细
     *
     * @return
     */
    @GetMapping("/moneyDetails")
    @Validation
    public ResponseData moneyDetails(@RequestParam Long time,Integer type){
        return ResponseData.buildResponse(ResultCodeEnum.SUCCESS.getMessage(), myWalletService.moneyDetails(time,type));
    }
}
