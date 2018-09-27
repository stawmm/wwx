package com.xgw.wwx.dto.db;

public class CardDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long deviceId;

	private String cardType;

	private String cardVersion;

	private Integer chipNum;

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardVersion() {
		return cardVersion;
	}

	public void setCardVersion(String cardVersion) {
		this.cardVersion = cardVersion;
	}

	public Integer getChipNum() {
		return chipNum;
	}

	public void setChipNum(Integer chipNum) {
		this.chipNum = chipNum;
	}

}
