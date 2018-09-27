package com.xgw.wwx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xgw.wwx.dto.db.DeviceDTO;

public interface DeviceMapper {

	public DeviceDTO getDeviceById(Long id);

	public DeviceDTO getDeviceByName(String name);

	public List<DeviceDTO> findDevices(Map<String, Object> params);

	public void createDevice(DeviceDTO deviceDTO);

	public void updateDevice(DeviceDTO deviceDTO);

	public void deleteDevice(Long id);

	public DeviceDTO getDeviceByIp(String ip);

	public DeviceDTO getDeviceByTaskId(Long taskId);

	public List<DeviceDTO> findAllDevices();

	public List<DeviceDTO> findDevicesByDictId(Long dictId);

	public List<DeviceDTO> findDevicesByGpu();

	public List<DeviceDTO> findDevicesByFpga();

	public void updateDeviceErrorTimes(@Param("id") Long id, @Param("errorTimes") Integer errorTimes);

	public List<DeviceDTO> findDevicesByTaskId(Long taskId);

	public void updateDeviceStatus(@Param("deviceId") Long deviceId, @Param("status") Integer status);

}
