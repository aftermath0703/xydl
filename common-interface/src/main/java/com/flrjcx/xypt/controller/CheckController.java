package com.flrjcx.xypt.controller;

import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.handler.ValidationInterceptor;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.SensitiveWordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @author : aftermath
 * @date : 2022-11-16 14:31:06
 */
@Api("通用校验模块")
@ApiRestController("/verify")
@Log4j2
public class CheckController {

    @Resource
    private ValidationInterceptor validationInterceptor;

    @ApiOperation("文本检查")
    @PostMapping("/text/check")
    public ResponseData textCheck(@RequestBody String text) {
        List<FoundWord> foundAllSensitive = SensitiveUtil.getFoundAllSensitive(text, false, false);
        if (foundAllSensitive.size() == 0) {
            return ResponseData.buildSuccess();
        }
        return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_SENSITIVE_TEXT, foundAllSensitive);
    }

    @ApiOperation("文本过滤")
    @PostMapping("/text/filter")
    public ResponseData textFilter(@RequestBody String text) {
        String t = SensitiveWordUtils.sensitiveWordFiltering(text, false, false);
        return ResponseData.buildResponseToStandard(t);
    }

    @ApiOperation("校验token")
    @GetMapping("/verifyToken")
    @Validation
    public void verifyToken() {

    }

}
