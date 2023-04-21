package com.flrjcx.xypt.common.model.result.ip;

import lombok.Data;

import java.io.Serializable;

/**
 * ip详情查询结构体
 *
 * @author Flrjcx
 * {"ip":"114.84.222.174","pro":"上海市","proCode":"310000","city":"上海市","cityCode":"310000","region":"","regionCode":"0","addr":"上海市
 * 电信","regionNames":"","err":""}
 */
@Data
public class DetailsIpParam implements Serializable {
    private static final long serialVersionUID = -5409755764508331906L;
    private String ip;
    private String pro;
    private String proCode;
    private String city;
    private String cityCode;
    private String region;
    private String regionCode;
    private String addr;
    private String regionNames;
    private String err;
}
