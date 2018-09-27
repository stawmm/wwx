package com.xgw.wwx.common.em;

public enum TempStatusType {

	NORMAL(0, "正常"),

	THRESHOLD(1, "阈值"),

	ERROR(2, "报警"),

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

	private TempStatusType(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

}
