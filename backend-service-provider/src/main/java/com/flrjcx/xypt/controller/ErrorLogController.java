package com.flrjcx.xypt.controller;

import cn.hutool.core.date.DateUtil;
import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.model.domain.log.ErrorLog;
import com.flrjcx.xypt.common.model.dto.log.ErrorLogDto;
import com.flrjcx.xypt.common.model.param.log.ErrorLogParam;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.service.ErrorLogService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.beans.PropertyEditorSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author malaka
 * @Description 异常日志
 * @date 2022/11/5 20:49
 */
@Api(tags = "异常日志")
@ApiRestController("/error")
@Log4j2
public class ErrorLogController {

    /**
     * 将前台传递过来的时间戳格式的字符串，自动转化为Date类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(new Date(Long.parseLong(text)));
            }
        });
    }

    @Resource
    private ErrorLogService errorLogService;

    @GetMapping("/list")
    public ResponseData getErrorList(ErrorLogParam errorLogParam) {
        Long total = errorLogService.errorListTotal(errorLogParam);
        List<ErrorLog> errorLogList = errorLogService.errorList(errorLogParam);
        List<ErrorLogDto> res = errorLogList.stream().map(ErrorLogDto::new).collect(Collectors.toList());

        return ResponseData.buildResponse(res).put(ResponseData.PAGE_TOTAL_TAG, total);
    }

}
