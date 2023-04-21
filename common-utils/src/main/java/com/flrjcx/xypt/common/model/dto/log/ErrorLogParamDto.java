package com.flrjcx.xypt.common.model.dto.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author malaka
 * @Description 异常日志返回中的request
 * @date 2022/11/5 17:57
 */
@Data
public class ErrorLogParamDto implements Serializable {

    private static final long serialVersionUID = -1985194564588843311L;
    private String method;
    private Map<String, String> headers;
    private Map<String, String[]> params;
    private String body;
    private String ip;

}
