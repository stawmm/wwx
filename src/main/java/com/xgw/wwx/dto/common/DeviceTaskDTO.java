package com.xgw.wwx.dto.common;

import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.request.DeviceRespDTO;

public class DeviceTaskDTO {

	private boolean result = false;

	private int temp = 0;

	private DeviceDTO deviceDTO;

	private DeviceRespDTO deviceRespDTO;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public DeviceDTO getDeviceDTO() {
		return deviceDTO;
	}

	public void setDeviceDTO(DeviceDTO deviceDTO) {
		this.deviceDTO = deviceDTO;
	}

	public DeviceRespDTO getDeviceRespDTO() {
		return deviceRespDTO;
	}

	public void setDeviceRespDTO(DeviceRespDTO deviceRespDTO) {
		this.deviceRespDTO = deviceRespDTO;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

}
