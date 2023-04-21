package com.flrjcx.xypt.common.enums;

/**
 * 字符串提示枚举类
 * @author Flrjcx
 */
public enum MessageHintEnum {

    UserNamePass("恭喜小姿力，信息校验通过！"),
    StrIsNull(""),
    SpecialStr("@");

    private String message;

    MessageHintEnum( String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
