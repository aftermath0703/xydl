package com.flrjcx.xypt.common.model.param.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/11/5 20:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLogParam implements Serializable {
    private static final long serialVersionUID = 6363794433359226789L;

    @ApiModelProperty(value = "异常名", name = "errorName")
    private String errorName;
    @ApiModelProperty(value = "服务名", name = "serviceHost")
    private String serviceHost;
    @ApiModelProperty(value = "服务ip", name = "serviceIp")
    private String serviceIp;
    @ApiModelProperty(value = "访问地址", name = "uri")
    private String uri;
    @ApiModelProperty(value = "分页大小", name = "pageSize")
    private Integer pageSize;
    @ApiModelProperty(value = "分页页码", name = "pageNum")
    private Integer pageNum;
    @ApiModelProperty(value = "开始时间", name = "beforeTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beforeTime;
    @ApiModelProperty(value = "结束时间", name = "afterTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date afterTime;

}
