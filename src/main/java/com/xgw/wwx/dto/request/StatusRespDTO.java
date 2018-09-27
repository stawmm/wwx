package com.xgw.wwx.dto.request;

import com.google.gson.annotations.SerializedName;

public class StatusRespDTO extends BaseRespDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("Status")
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
