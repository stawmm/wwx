package com.xgw.wwx.common.em;

public enum DeviceStatusType {

	ENABLE(1, "可用"),

	DISABLE(2, "不可用"),

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

	private DeviceStatusType(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

}
