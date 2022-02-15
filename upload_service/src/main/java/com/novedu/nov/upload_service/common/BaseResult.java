package com.novedu.nov.upload_service.common;

import java.util.HashMap;
import java.util.Map;


public class BaseResult<T> {
    private Integer code;
    private String msg;
    private T data;
    private Map map = new HashMap<String, Object>();

    public BaseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static BaseResult success() {
        return new BaseResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), "");
    }

    public static <T> BaseResult success(T data) {
        return new BaseResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static BaseResult success(String msg) {
        return new BaseResult(ResultCode.SUCCESS.getCode(), msg, "");
    }

    public static <T> BaseResult success(String msg, T data) {
        return new BaseResult<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static BaseResult error() {
        return new BaseResult<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), "");
    }

    public static <T> BaseResult error(T data) {
        return new BaseResult<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), data);
    }

    public static BaseResult error(String msg) {
        return new BaseResult<>(ResultCode.ERROR.getCode(), msg, "");
    }

    public static <T> BaseResult error(String msg, T data) {
        return new BaseResult<>(ResultCode.ERROR.getCode(), msg, data);
    }

    public static BaseResult successOrError(boolean flag) {
        if (flag)
            return new BaseResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), "");
        else
            return new BaseResult<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), "");
    }

    public static BaseResult setStatus(ResultCode result) {
        return new BaseResult<>(result.getCode(), result.getMsg(), null);
    }

    public static <T> BaseResult setStatus(ResultCode result, T data) {
        return new BaseResult<>(result.getCode(), result.getMsg(), data);
    }

    public BaseResult mapSet(String key, Object data) {
        map.put(key, data);
        this.data = (T) map;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
