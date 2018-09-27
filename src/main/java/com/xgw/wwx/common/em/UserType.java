package com.xgw.wwx.common.em;

public enum UserType {

	COMMON_USER(0, "普通用户"),

	ADMIN_USER(1, "管理员")

	;

	private int type;

	private String message;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private UserType(int type, String message) {
		this.type = type;
		this.message = message;
	}

	public static UserType parse(int type) {
		for (UserType _type : UserType.values()) {
			if (_type.getType() == type) {
				return _type;
			}
		}
		return null;
	}

}
