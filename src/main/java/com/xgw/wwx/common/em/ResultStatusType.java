package com.xgw.wwx.common.em;

public enum ResultStatusType {

	IDLE(0, "空闲中"),

	RUNNING(1, "运行中"),

	HIT(2, "命中"),
	
	HIT_KEY11(11, "命中"),
	
	HIT_KEY12(12, "命中"),

	FINISH(3, "完成"),
	
	FAILED(9, "失败"),

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

	private ResultStatusType(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

}
