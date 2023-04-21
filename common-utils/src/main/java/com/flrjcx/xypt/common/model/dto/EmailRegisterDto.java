package com.flrjcx.xypt.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author malaka
 * @Description 邮箱验证
 * @date 2022/10/29 18:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRegisterDto implements Serializable {
    private static final long serialVersionUID = 8226678090641877775L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 邮箱验证码
     */
    private String code;

}
