package com.flrjcx.xypt.common.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 验证码对象
 * @author malaka
 */
@Data
public class VerifyCodeDto implements Serializable{

    private static final long serialVersionUID = 5463130596284218609L;
    private String uuid;
    private String img;

}
