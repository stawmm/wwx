package com.xgw.wwx.common.em;

public enum SliceStatusType {

	FINISH(1, "切片完成"),

	UNFINISH(2, "切片失败"),
	
	FAILED(0, "切片失败"),
	
	SUCCESS(1, "切片失败"),

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

	private SliceStatusType(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

}
