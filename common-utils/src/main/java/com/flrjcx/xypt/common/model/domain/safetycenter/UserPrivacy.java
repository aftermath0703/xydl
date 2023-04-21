package com.flrjcx.xypt.common.model.domain.safetycenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gyyst
 * @purpose
 * @Create by 2022/10/22 23:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrivacy implements Serializable {
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String BIRTHDAY = "birthday";
    private static final long serialVersionUID = -5341483894628771389L;
    
    private Integer address = 0;
    private Integer phone = 0;
    private Integer email = 0;
    private Integer birthday = 0;

}
