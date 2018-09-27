package com.xgw.wwx.common.em;

public enum LoggerViewEnum {

	UNVIEW("unview","未查看"),
	
	VIEW("view","已查看");
	
	;

	private String code;

	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private LoggerViewEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
