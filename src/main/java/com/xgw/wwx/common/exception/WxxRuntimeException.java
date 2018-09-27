package com.xgw.wwx.common.exception;

public class WxxRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private String message;

	private String category;

	public WxxRuntimeException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public WxxRuntimeException() {

	}

	public WxxRuntimeException(String message) {
		this("SYS_ERROR", message);
	}

	public WxxRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public WxxRuntimeException(String message, String code, String category) {
		this.code = code;
		this.message = message;
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}