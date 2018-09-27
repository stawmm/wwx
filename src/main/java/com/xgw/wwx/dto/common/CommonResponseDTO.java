package com.xgw.wwx.dto.common;

import java.io.Serializable;

public class CommonResponseDTO<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -881348333756020210L;

    private boolean result;
    private String code;
    private String message;
    private T data;// 返回的数据对象

    public CommonResponseDTO() {
    }

    public CommonResponseDTO(String code, String message) {
        this.result = false;
        this.code = code;
        this.message = message;
    }

    public CommonResponseDTO(T data) {
        this.result = true;
        this.data = data;
    }

    public CommonResponseDTO(T data, boolean result) {
        this.result = result;
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
