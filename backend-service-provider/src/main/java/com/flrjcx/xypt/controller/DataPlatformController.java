package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.service.DataPlatFormService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 数据平台
 *
 * @author Flrjcx
 */
@Api(tags = "数据平台")
@ApiRestController("/dataPlatForm")
@Log4j2
public class DataPlatformController {
    @Resource
    private DataPlatFormService dataPlatFormService;

    /**
     * 获取平台总交易额度
     *
     * @return
     */
    @GetMapping("/totalTransaction")
    @Validation
    public ResponseData totalTransaction(){
        return ResponseData.buildResponse(ResultCodeEnum.SUCCESS.getMessage(),dataPlatFormService.totalTransaction());
    }

    /**
     * 获取平台总支出额度(提现)
     *
     * @return
     */
    @GetMapping("/totalDeposit")
    @Validation
    public ResponseData totalDeposit(){
        return ResponseData.buildResponse(ResultCodeEnum.SUCCESS.getMessage(),dataPlatFormService.totalDeposit());
    }

    /**
     * 获取平台总交易额度(图表)
     *
     * @param beforeTime:开始时间
     * @param afterTime:结束时间
     * @return
     */
    @GetMapping("/totalTransactionChart")
    @Validation
    public ResponseData totalTransactionChart(Long beforeTime,Long afterTime,@RequestParam(defaultValue = "DATE_NULL")String date){
        return ResponseData.buildResponse(dataPlatFormService.totalTransactionChart(beforeTime,afterTime,date));
    }

    /**
     * 获取平台总支出额度(图表)
     *
     * @param beforeTime:开始时间
     * @param afterTime:结束时间
     * @return
     */
    @GetMapping("/totalDepositChart")
    @Validation
    public ResponseData totalDepositChart(Long beforeTime,Long afterTime,@RequestParam(defaultValue = "DATE_NULL")String date){
        return ResponseData.buildResponse(dataPlatFormService.totalDepositChart(beforeTime,afterTime,date));
    }

}
