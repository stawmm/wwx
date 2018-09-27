package com.xgw.wwx.dto.request;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class BaseRespDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("Result")
	private String result;

	@SerializedName("Messege")
	private String message;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
