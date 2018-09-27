package com.xgw.wwx.dto.request;

import com.google.gson.annotations.SerializedName;

public class CardStatusRespDTO extends BaseRespDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("Temp")
	private int temp;

	@SerializedName("Status")
	private int status;

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
