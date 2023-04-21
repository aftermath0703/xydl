package com.flrjcx.xypt.common.model.result.ip;

import lombok.Data;

import java.io.Serializable;

/**
 * ip归属查询返回体
 *
 * @author Flrjcx
 */
@Data
public class IpLocalParam implements Serializable {
    private static final long serialVersionUID = -2580317626128845966L;
    private String ip;
    private String long_ip;
    private String isp;
    private String area;
    private String region_id;
    private String region;
    private String city_id;
    private String city;
    private String district;
    private String district_id;
    private String country_id;
    private String country;
}
