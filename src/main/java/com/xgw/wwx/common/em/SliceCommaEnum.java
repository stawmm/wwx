package com.xgw.wwx.common.em;

public enum SliceCommaEnum {

	GPU("gpu","0000000000000000"),

	OTHER("other","0,0"),
	
	MASK("mask","0x00")
	
	;

	private String code;

	private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private SliceCommaEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

}
