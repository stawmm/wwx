package com.xgw.wwx.common.em;

public enum StrategyEnum {

	FIXED_LENGTH(1, "定长策略"),
	
	RANDOM_LENGTH(2, "变长策略"),
	
	HIGH_LENGTH(3, "高级策略"),

	;

	private int type;

	private String desc;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private StrategyEnum(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

}
