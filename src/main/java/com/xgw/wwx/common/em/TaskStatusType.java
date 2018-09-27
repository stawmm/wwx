package com.xgw.wwx.common.em;

public enum TaskStatusType {

	QUEUE(0, "排队中"),

	FINISH(1, "口令跑完未命中"),

	HIT(2, "命中"),

	RUNNING(3, "运行中"),

	FAULT(4, "已故障"),

	SUSPEND(5, "已暂停"),

	KEY11(6, "key11命中"),

	KEY12(7, "key12命中"),

	DELETE(9, "删除")

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

	private TaskStatusType(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

}
