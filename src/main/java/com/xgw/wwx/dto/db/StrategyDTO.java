package com.xgw.wwx.dto.db;

public class StrategyDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String desc;

	private Long startLength;

	private Long endLength;

	private String express;

	private Integer status;

	private Integer type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getStartLength() {
		return startLength;
	}

	public void setStartLength(Long startLength) {
		this.startLength = startLength;
	}

	public Long getEndLength() {
		return endLength;
	}

	public void setEndLength(Long endLength) {
		this.endLength = endLength;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}