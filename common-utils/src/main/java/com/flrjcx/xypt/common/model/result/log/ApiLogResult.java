package com.flrjcx.xypt.common.model.result.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口访问日志参数
 *
 * @author Flrjcx
 */
@Data
public class ApiLogResult implements Serializable {
    private static final long serialVersionUID = -1186565379246250355L;
    @ApiModelProperty(value = "日志id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "客户端访问ip", name = "ip")
    private String ip;
    @ApiModelProperty(value = "api访问路径", name = "uri")
    private String uri;
    @ApiModelProperty(value = "访问端口", name = "port")
    private int port;
    @ApiModelProperty(value = "运营商", name = "isp")
    private String isp;
    @ApiModelProperty(value = "区域", name = "area")
    private String area;
    @ApiModelProperty(value = "地区", name = "region")
    private String region;
    @ApiModelProperty(value = "城市", name = "city")
    private String city;
    @ApiModelProperty(value = "区", name = "district")
    private String district;
    @ApiModelProperty(value = "访问时间戳", name = "timestamp")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date timestamp;
}
