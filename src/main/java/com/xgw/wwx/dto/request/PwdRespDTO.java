package com.xgw.wwx.dto.request;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PwdRespDTO extends BaseRespDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("Password")
	private List<String> pwds;

	public List<String> getPwds() {
		return pwds;
	}

	public void setPwds(List<String> pwds) {
		this.pwds = pwds;
	}

}
