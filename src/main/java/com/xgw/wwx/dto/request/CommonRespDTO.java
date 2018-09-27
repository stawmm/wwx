package com.xgw.wwx.dto.request;

import java.io.Serializable;

public class CommonRespDTO<T> implements Serializable {

	private static final long serialVersionUID = -881348333756020210L;
	private boolean result;
	private String code;
	private String message;
	private T data;

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

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public CommonRespDTO(String code, String message, boolean result) {
		super();
		this.result = result;
		this.code = code;
		this.message = message;
	}

	public CommonRespDTO(boolean result, T data) {
		super();
		this.result = result;
		this.data = data;
	}

	public CommonRespDTO(T data) {
		super();
		this.data = data;
	}

	public CommonRespDTO() {
		super();
	}
}