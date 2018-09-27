package com.xgw.wwx.common.code;

public enum UserCodeEnum {

	USER_USERNAME_NOT_EXIST("USER_USERNAME_NOT_EXIST", "用户名不存在"),

	USER_USERNAME_EMPTY("USER_USERNAME_EMPTY", "用户名为空"),

	USER_PASSWORD_ERROR("USER_PASSWORD_ERROR", "用户密码错误"),

	USER_PASSWORD_EMPTY("USER_PASSWORD_EMPTY", "密码为空"),

	USER_STATUS_DISABLED("USER_STATUS_DISABLED", "用户已被禁用"),

	USER_SESSION_TIMEOUT("USER_SESSION_TIMEOUT", "用户回话过期"),

	USER_USERNAME_EXIST("USER_USERNAME_EXIST", "用户名已存在"),

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

	private UserCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
