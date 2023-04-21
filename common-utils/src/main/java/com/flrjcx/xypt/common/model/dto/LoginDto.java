package com.flrjcx.xypt.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录成功返回参数
 * @author malaka
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginDto implements Serializable{

    private static final long serialVersionUID = 21454515614481L;
    private String token;

}
