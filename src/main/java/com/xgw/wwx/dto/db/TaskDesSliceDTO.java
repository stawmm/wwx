package com.xgw.wwx.dto.db;

public class TaskDesSliceDTO extends TaskVpndesDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long subTaskId;

	private String mapKey;

	private Integer jmtype;

	private Integer cardnum;

	private Long speed;

	private Integer sunzinum;

	public String getMapKey() {
		return mapKey;
	}

	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}

	public Integer getJmtype() {
		return jmtype;
	}

	public void setJmtype(Integer jmtype) {
		this.jmtype = jmtype;
	}

	public Integer getCardnum() {
		return cardnum;
	}

	public void setCardnum(Integer cardnum) {
		this.cardnum = cardnum;
	}

	public Long getSpeed() {
		return speed;
	}

	public void setSpeed(Long speed) {
		this.speed = speed;
	}

	public Integer getSunzinum() {
		return sunzinum;
	}

	public void setSunzinum(Integer sunzinum) {
		this.sunzinum = sunzinum;
	}

	public Long getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(Long subTaskId) {
		this.subTaskId = subTaskId;
	}

}
