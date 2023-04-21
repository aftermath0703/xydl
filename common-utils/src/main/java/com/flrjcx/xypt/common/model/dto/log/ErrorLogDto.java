package com.flrjcx.xypt.common.model.dto.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flrjcx.xypt.common.model.domain.log.ErrorLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author malaka
 * @Description 异常日志返回数据
 * @date 2022/11/5 17:56
 */
@Data
@ToString
public class ErrorLogDto  implements Serializable {
    private static final long serialVersionUID = 7872487683467307200L;

    public ErrorLogDto(){}

    public ErrorLogDto(@NotNull ErrorLog errorLog){
        this.errorId = errorLog.getErrorId();
        this.errorName = errorLog.getErrorName();
        this.errorClass = errorLog.getErrorClass();
        this.errorMethod = errorLog.getErrorMethod();
        this.errorStackTrace = errorLog.getErrorStackTrace();
        this.errorLineNumber = errorLog.getErrorLineNumber();
        this.errorMessage = errorLog.getErrorMessage();
        this.errorServiceHost = errorLog.getErrorServiceHost();
        this.errorServiceIp = errorLog.getErrorServiceIp();
        this.errorUri = errorLog.getErrorUri();
        this.errorCreateTime = errorLog.getErrorCreateTime().getTime();
        this.errorParam = new ErrorLogParamDto();
        String errorParamStr = errorLog.getErrorParam();
        this.errorParam = JSON.parseObject(errorParamStr, ErrorLogParamDto.class);
    }


    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id", name = "errorId")
    private Long errorId;

    @ApiModelProperty(value = "异常名", name = "errorName")
    private String errorName;

    @ApiModelProperty(value = "异常类", name = "errorClass")
    private String errorClass;

    @ApiModelProperty(value = "异常方法", name = "errorMethod")
    private String errorMethod;

    @ApiModelProperty(value = "异常行数", name = "errorLineNumber")
    private int errorLineNumber;

    @ApiModelProperty(value = "跟踪栈列表", name = "errorStackTrace")
    private String errorStackTrace;


    @ApiModelProperty(value = "异常消息", name = "errorMessage")
    private String errorMessage;

    @ApiModelProperty(value = "服务名", name = "errorHost")
    private String errorServiceHost;

    @ApiModelProperty(value = "服务器ip", name = "errorIp")
    private String errorServiceIp;

    @ApiModelProperty(value = "网络参数", name = "errorParam")
    private ErrorLogParamDto errorParam;

    @ApiModelProperty(value = "操作地址", name = "errorUri")
    private String errorUri;

    @ApiModelProperty(value = "创建时间", name = "errorCreateTime")
    private Long errorCreateTime;


}
