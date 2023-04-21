package com.flrjcx.xypt.common.enums;

/**
 * @title: ValidStatusEnum
 * @description: 可用状态
 * @author Flrjcx
 */
public enum  ValidStatusEnum {
    InValidStatus(0),
    ValidStatus(1),
    MinusStatus(-1);

    private Integer code;

    ValidStatusEnum(int code) {
        this.code = code;
    }

    public static ValidStatusEnum getValidStatusEnum(Integer validStatus) {
        ValidStatusEnum[] states = ValidStatusEnum.values();
        ValidStatusEnum ts = null;
        for (ValidStatusEnum state : states) {
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
