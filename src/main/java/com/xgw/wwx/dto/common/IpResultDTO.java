package com.xgw.wwx.dto.common;

import com.xgw.wwx.dto.db.DeviceDTO;

public class IpResultDTO {

	private String host;

	private int port;

	private Boolean result;
	
	private DeviceDTO deviceDTO;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public DeviceDTO getDeviceDTO() {
		return deviceDTO;
	}

	public void setDeviceDTO(DeviceDTO deviceDTO) {
		this.deviceDTO = deviceDTO;
	}
	
}
