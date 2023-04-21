package com.flrjcx.xypt.common.model.result.ip;

import lombok.Data;

import java.io.Serializable;

/**
 * ip归属查询返回体
 *
 * @author Flrjcx
 */
@Data
public class IpLocalResult implements Serializable {
    private static final long serialVersionUID = 9195756862955910664L;
    private IpLocalParam data;
    private Integer ret;
    private String msg;
    private String log_id;
}
