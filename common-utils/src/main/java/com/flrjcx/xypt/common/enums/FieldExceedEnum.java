package com.flrjcx.xypt.common.enums;

/**
 * 字段限制枚举
 * @author Flrjcx
 */
public enum FieldExceedEnum {
    AddressLengthExceed(50),
    NameLengthExceed(20);
    private Integer code;

    FieldExceedEnum(int code) {
        this.code = code;
    }

    public static FieldExceedEnum getValidStatusEnum(Integer validStatus) {
        FieldExceedEnum[] states = FieldExceedEnum.values();
        FieldExceedEnum ts = null;
        for (FieldExceedEnum state : states) {
            if (state.getCode().toString().equals(validStatus.toString())) {
                ts = state;
                break;
            }
        }
        return ts;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
