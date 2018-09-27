package com.xgw.wwx.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.request.CardStatusRespDTO;

public interface DeviceService {

	public DeviceDTO getDeviceById(Long id);

	public DeviceDTO getDeviceByName(String name);

	public DeviceDTO getDeviceByIp(String ip);

	public DeviceDTO getDeviceByTaskId(Long taskId);

	public PageInfo<DeviceDTO> findDevicesByPage(Integer pageSize, Integer pageNum, Map<String, Object> params);

	public List<DeviceDTO> findDevices(Map<String, Object> params);

	public void createDevice(DeviceDTO deviceDTO);

	public void updateDevice(DeviceDTO deviceDTO);

	public void deleteDevice(Long id);

	public List<DeviceDTO> findSearchDevices(String startIp, String endIp);

	public void distributionDict(DeviceDTO deviceDTO);

	public List<DeviceDTO> findAllDevices();

	public List<DeviceDTO> findDevicesByDictId(Long dictId);

	public List<CardStatusRespDTO> getDeviceCardStatus(Long id);

	public void updateDeviceErrorTimes(Long id, Integer errorTimes);

	public List<DeviceDTO> findDevicesByNodeType(String nodeType);

	public void updateDeviceStatus(Long deviceId, int status);

	public List<DeviceDTO> findDevicesByTaskId(Long taskId);

	public List<DeviceDTO> findAliveDevices();

	public List<DeviceDTO> findAliveDevicesByNodeType(String nodeType);

}
