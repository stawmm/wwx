package com.xgw.wwx.common.em;

public enum ErrorTimesType {

	ZERO(0, "正常"),

	FIVE(5, "异常"),

	;

	private int status;

	private String desc;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private ErrorTimesType(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

}
