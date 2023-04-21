package com.flrjcx.xypt.common.model.domain.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author aftermath
 * @date 2022-12-02 18:36:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperLog implements Serializable {
    private static final long serialVersionUID = 56089704589575876L;
    /**
     * 日志主键
     */
    @ApiModelProperty(value = "操作id")
    private Long operId;

    /**
     * 操作模块
     */
    @ApiModelProperty(value = "操作模块")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @ApiModelProperty(value = "操作类型", notes = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer operateType;

    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    private String method;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 操作人员类别（0=其它,1=管理员,2=用户）
     */
    @ApiModelProperty(value = "操作人员类别", notes = "0=其它,1=管理员,2=用户")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员")
    private String operatorName;

    /**
     * 请求url
     */
    @ApiModelProperty(value = "请求地址")
    private String operUrl;

    /**
     * 操作地址
     */
    @ApiModelProperty(value = "操作ip")
    private String operIp;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String operParam;

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "状态", notes = "0=正常,1=异常")
    private Integer status;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    @ApiModelProperty(value = "操作时间", name = "errorCreateTime")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date operTime;
}
