package com.flrjcx.xypt.common.model.result;


import com.flrjcx.xypt.common.enums.PageParam;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author malaka
 * @title: ResponseData
 * @description: ResponseData文件备份
 * @date 2021/8/1715:24
 */
public class ResponseDataBak<T> implements Serializable {
    private static final long serialVersionUID = -2276444028766844527L;
    private int code;
    private String message;
    public T data;

    public final static String PAGE_TOTAL_TAG = PageParam.PAGE_TOTAL_TAG;


    public ResponseDataBak() {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
    }

    public ResponseDataBak(Integer code, String message, T data) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseDataBak buildResponse() {
        ResponseDataBak data = new ResponseDataBak();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        data.setData(null);
        return data;
    }

    public static ResponseDataBak buildResponse(String message) {
        ResponseDataBak data = new ResponseDataBak();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(message);
        data.setData(null);
        return data;
    }


    public static <T> ResponseDataBak<T> buildResponse(String message, T t) {
        ResponseDataBak<T> data = new ResponseDataBak<>();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(message);
        data.setData(t);
        return data;
    }

    public static <T> ResponseDataBak<T> buildResponseToStandar(T t) {
        ResponseDataBak<T> data = new ResponseDataBak<>();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        data.setData(t);
        return data;
    }

    public static <T> ResponseDataBak<T> buildResponse(T t) {
        ResponseDataBak<T> data = new ResponseDataBak<>();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        data.setData(t);
        return data;
    }

    public static ResponseDataBak buildErrorResponse(Integer code, String message) {
        ResponseDataBak data = new ResponseDataBak();
        data.setCode(code);
        data.setMessage(message);
        data.setData(null);
        return data;
    }

    public static ResponseDataBak buildResponse(ResultCodeEnum httpResultCode) {
        ResponseDataBak data = new ResponseDataBak();
        data.setCode(httpResultCode.getCode());
        data.setMessage(httpResultCode.getMessage());
        data.setData(null);
        return data;
    }



    public static ResponseDataBak buildErrorResponse(ResultCodeEnum httpResultCode) {
        ResponseDataBak data = new ResponseDataBak();
        data.setCode(httpResultCode.getCode());
        data.setMessage(httpResultCode.getMessage());
        data.setData(null);
        return data;
    }

    public static <T> ResponseDataBak<T> buildErrorResponse(Integer code, String message, T t) {
        ResponseDataBak<T> data = new ResponseDataBak<>();
        data.setCode(code);
        data.setData(t);
        data.setMessage(message);
        return data;
    }

    public static <T> ResponseDataBak<T> buildErrorResponse(ResultCodeEnum httpResultCode, T t) {
        ResponseDataBak<T> data = new ResponseDataBak<>();
        data.setCode(httpResultCode.getCode());
        data.setMessage(httpResultCode.getMessage());
        data.setData(t);
        return data;
    }

    public static ResponseDataBak buildSuccess() {
        ResponseDataBak data = new ResponseDataBak();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return data;
    }

    public boolean judgeSuccess() {
        return this.code == ResultCodeEnum.SUCCESS.getCode();
    }

    public boolean judgeSuccessAndNotnull() {
        return this.code == ResultCodeEnum.SUCCESS.getCode() && this.data != null;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseDataBak)) {
            return false;
        } else {
            ResponseDataBak<?> other = (ResponseDataBak) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                label49:
                {
                    Object thisMessage = this.getMessage();
                    Object otherMessage = other.getMessage();
                    if (thisMessage == null) {
                        if (otherMessage == null) {
                            break label49;
                        }
                    } else if (thisMessage.equals(otherMessage)) {
                        break label49;
                    }

                    return false;
                }

                Object thisData = this.getData();
                Object otherData = other.getData();
                if (thisData == null) {
                    if (otherData != null) {
                        return false;
                    }
                } else if (!thisData.equals(otherData)) {
                    return false;
                }


                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResponseDataBak;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getCode();
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object data = this.getData();
        result = result * 59 + (data == null ? 43 : data.hashCode());

        return result;
    }

    public boolean isSuccess() {
        return this.code == ResultCodeEnum.SUCCESS.getCode();
    }

    @Override
    public String toString() {
        return "ResponseData(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }
}
