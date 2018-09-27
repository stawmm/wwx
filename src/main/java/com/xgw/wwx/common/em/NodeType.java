package com.xgw.wwx.common.em;

public enum NodeType {

	GPU(0, "GPU节点"),

	FPGA1(1, "FPGA一代"),

	FPGA2(2, "FPGA二代"),

	MIX(3, "混插"),

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

	private NodeType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

}
