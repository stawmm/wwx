package com.xgw.wwx.common.exception;

import com.xgw.wwx.common.code.UserCodeEnum;

public class UserRuntimeException extends WxxRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserCodeEnum userCodeEnum = null;

	public UserRuntimeException(UserCodeEnum userCodeEnum) {
		this.userCodeEnum = userCodeEnum;
		this.setCode(userCodeEnum.getCode());
		this.setMessage(userCodeEnum.getMessage());
		this.setCategory(userCodeEnum.name());
	}

	public UserCodeEnum getUserCodeEnum() {
		return userCodeEnum;
	}

	public void setUserCodeEnum(UserCodeEnum userCodeEnum) {
		this.userCodeEnum = userCodeEnum;
	}

}
