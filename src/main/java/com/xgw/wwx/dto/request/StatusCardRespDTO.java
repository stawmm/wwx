package com.xgw.wwx.dto.request;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StatusCardRespDTO extends BaseRespDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("Cardnum")
	private Integer cardNum;

	@SerializedName("Statuslist")
	private List<CardStatusRespDTO> statuslist;

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public List<CardStatusRespDTO> getStatuslist() {
		return statuslist;
	}

	public void setStatuslist(List<CardStatusRespDTO> statuslist) {
		this.statuslist = statuslist;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
