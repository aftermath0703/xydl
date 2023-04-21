package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.EmailSendUtils;
import com.flrjcx.xypt.service.LogService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * 日志服务
 *
 * @author Flrjcx
 */
@Api(tags = "日志模块")
@ApiRestController("/log")
@Log4j2
public class LogController {
    @Resource
    private LogService LogService;

    @Resource
    private EmailSendUtils emailSendUtils;

    /**
     * 获取Api访问日志列表
     *
     * @return
     */
    @GetMapping("/apiLog")
    @Validation
    public ResponseData getApiLogList(@RequestParam Long beforeTime,@RequestParam Long afterTime,
                                      String ip,String uri,String city) {
        try {
            return ResponseData.buildPageResponse(LogService.getApiLogList(beforeTime,afterTime,ip,uri,city));
        }catch (Exception e){
            log.error("/apiLog error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 获取操作日志列表
     */
    @GetMapping("/operLog")
    @Validation
    @OpenPage
    public ResponseData getOperLogList() {
        return null;
    }

    /**
     * 查询ip详情
     *
     * @param ip
     * @return
     */
    @GetMapping("/detailsIp")
    @Validation
    public ResponseData detailsIp(@RequestParam String ip) {
        try {
            return ResponseData.buildResponse(LogService.detailsIp(ip));
        }catch (Exception e){
            log.error("/detailsIp error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

}
