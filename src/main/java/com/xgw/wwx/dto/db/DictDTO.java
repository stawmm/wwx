package com.xgw.wwx.dto.db;

import java.util.List;

public class DictDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long deviceId;

	private String name;

	private String group;//字典组

	private Long size;

	private Integer type;

	private String desc;

	private Integer status;

	private List<DeviceDTO> deviceList;

	private List<Long> deviceIds;

	public List<Long> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<Long> deviceIds) {
		this.deviceIds = deviceIds;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DeviceDTO> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DeviceDTO> deviceList) {
		this.deviceList = deviceList;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
