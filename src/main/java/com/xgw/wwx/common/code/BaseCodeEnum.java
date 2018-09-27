package com.xgw.wwx.common.code;

public enum BaseCodeEnum {

	SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),

	SYSTEM_UNKNOW_ERROR("SYSTEM_UNKNOW_ERROR", "系统未知错误"),

	;

	private String code;

	private String message;

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

	private BaseCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
