package com.xgw.wwx.dto.request;

import com.google.gson.annotations.SerializedName;

public class AlgRespDTO {

	@SerializedName("Cardtype")
	private Integer cardType;

	@SerializedName("Algtype")
	private Integer algType;

	@SerializedName("Algname")
	private String algName;

	@SerializedName("Speed")
	private Integer speed;

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getAlgType() {
		return algType;
	}

	public void setAlgType(Integer algType) {
		this.algType = algType;
	}

	public String getAlgName() {
		return algName;
	}

	public void setAlgName(String algName) {
		this.algName = algName;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

}
