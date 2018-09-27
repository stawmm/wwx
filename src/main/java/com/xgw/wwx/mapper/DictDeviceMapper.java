package com.xgw.wwx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xgw.wwx.dto.db.DictDeviceDTO;

public interface DictDeviceMapper {

	public void deleteByDictId(Long dictId);

	public void deleteByDeviceId(Long deviceId);

	public void createDictDeviceAsso(DictDeviceDTO dictDeviceDTO);

	public DictDeviceDTO selectByDeviceAndDict(@Param("dictId") Long dictId, @Param("deviceId") Long deviceId);

	public List<DictDeviceDTO> selectByDeviceID(Long deviceId);

	public List<DictDeviceDTO> selectByDictId(Long dictId);

	public void deleteByDictIdAndDeviceId(@Param("dictId") Long dictId, @Param("deviceId") Long deviceId);

}
