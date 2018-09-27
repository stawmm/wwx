package com.xgw.wwx.dto.request;

import com.google.gson.annotations.SerializedName;

public class DictRespDTO {

	@SerializedName("Name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
