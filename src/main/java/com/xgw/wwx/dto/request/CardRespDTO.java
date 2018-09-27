package com.xgw.wwx.dto.request;

import com.google.gson.annotations.SerializedName;

/**
 * 板卡DTO
 * 
 * @author Administrator
 *
 */
public class CardRespDTO {

	@SerializedName("Cardtype")
	private String cardType;

	@SerializedName("Cardversion")
	private String cardVersion;

	@SerializedName("Chipnum")
	private Integer chipNum;

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
