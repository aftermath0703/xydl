package com.flrjcx.xypt.common.model.result;


import com.flrjcx.xypt.common.enums.PageParam;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author s
 * @title: ResponseData
 * @description: TODO
 * @date 2021/8/1715:24
 */
public class ResponseData<T> extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = -2276444028766844527L;
//    private int code;
//    private String message;
//    public T data;

    public final static String PAGE_TOTAL_TAG = PageParam.PAGE_TOTAL_TAG;

    public final static String CODE_TAG = "code";
    public final static String MESSAGE_TAG = "message";
    public final static String DATA_TAG = "data";

    public ResponseData() {
        this.put(CODE_TAG, ResultCodeEnum.SUCCESS.getCode());
        this.put(MESSAGE_TAG, ResultCodeEnum.SUCCESS.getMessage());
    }

    public ResponseData(Integer code, String message, T data) {
        this.put(CODE_TAG, ResultCodeEnum.SUCCESS.getCode());
        this.put(MESSAGE_TAG, ResultCodeEnum.SUCCESS.getMessage());
        this.put(DATA_TAG, data);
    }

    public static ResponseData buildResponse() {
        ResponseData data = new ResponseData();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        data.setData(null);
        return data;
    }

    public static ResponseData buildResponse(String message) {
        ResponseData data = new ResponseData();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(message);
        data.setData(null);
        return data;
    }


    public static <T> ResponseData<T> buildResponse(String message, T t) {
        ResponseData<T> data = new ResponseData<>();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(message);
        data.setData(t);
        return data;
    }

    public static <T> ResponseData<T> buildResponseToStandard(T t) {
        ResponseData<T> data = new ResponseData<>();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        data.setData(t);
        return data;
    }

    public static <T> ResponseData<T> buildResponse(T t) {
        ResponseData<T> data = new ResponseData<>();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        data.setData(t);
        return data;
    }

    public static ResponseData buildErrorResponse(Integer code, String message) {
        ResponseData data = new ResponseData();
        data.setCode(code);
        data.setMessage(message);
        data.setData(null);
        return data;
    }

    public static ResponseData buildResponse(ResultCodeEnum httpResultCode) {
        ResponseData data = new ResponseData();
        data.setCode(httpResultCode.getCode());
        data.setMessage(httpResultCode.getMessage());
        data.setData(null);
        return data;
    }

    public static <T> ResponseData buildPageResponse(List<T> list) {
        ResponseData<Collection<T>> data = buildResponse(list);
        long total = new PageInfo(list).getTotal();
        data.put(PAGE_TOTAL_TAG, total);
        return data;
    }


    public static ResponseData buildErrorResponse(ResultCodeEnum httpResultCode) {
        ResponseData data = new ResponseData();
        data.setCode(httpResultCode.getCode());
        data.setMessage(httpResultCode.getMessage());
        data.setData(null);
        return data;
    }

    public static <T> ResponseData<T> buildErrorResponse(Integer code, String message, T t) {
        ResponseData<T> data = new ResponseData<>();
        data.setCode(code);
        data.setData(t);
        data.setMessage(message);
        return data;
    }

    public static <T> ResponseData<T> buildErrorResponse(ResultCodeEnum httpResultCode, T t) {
        ResponseData<T> data = new ResponseData<>();
        data.setCode(httpResultCode.getCode());
        data.setMessage(httpResultCode.getMessage());
        data.setData(t);
        return data;
    }

    public static ResponseData buildSuccess() {
        ResponseData data = new ResponseData();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return data;
    }

    public static <T> ResponseData<T> buildOnlyResponse(T t,String key,T value) {
        ResponseData<T> data = new ResponseData<>();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        data.setData(t);
        data.put(key,value);
        return data;
    }

    public static <T> ResponseData<T> buildOnlyResponse(String key,T value) {
        ResponseData<T> data = new ResponseData<>();
        data.setCode(ResultCodeEnum.SUCCESS.getCode());
        data.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        data.setData(null);
        data.put(key,value);
        return data;
    }

    @Override
    public ResponseData put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public boolean judgeSuccess() {
        return this.get(CODE_TAG) == ResultCodeEnum.SUCCESS.getCode();
    }

    public boolean judgeSuccessAndNotnull() {
        return this.get(CODE_TAG) == ResultCodeEnum.SUCCESS.getCode() && this.get(CODE_TAG) != null;
    }

    public Integer getCode() {
        return Integer.parseInt(this.get(CODE_TAG).toString());
    }

    public void setCode(int code) {
        this.put(CODE_TAG, code);
    }

    public String getMessage() {
        return this.get(MESSAGE_TAG).toString();
    }

    public void setMessage(String message) {
        this.put(MESSAGE_TAG, message);
    }

    public T getData() {
        return (T) this.get(DATA_TAG);
    }

    public void setData(T data) {
        this.put(DATA_TAG, data);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseData)) {
            return false;
        } else {
            ResponseData<?> other = (ResponseData) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (ObjectUtils.nullSafeEquals(this.getCode(), other.getCode())) {
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
        return other instanceof ResponseData;
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
        return ObjectUtils.nullSafeEquals(this.getCode(), ResultCodeEnum.SUCCESS.getCode());
    }

    @Override
    public String toString() {
        return "ResponseData(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }
}
