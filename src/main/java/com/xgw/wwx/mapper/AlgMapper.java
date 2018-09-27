package com.xgw.wwx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xgw.wwx.dto.db.AlgDTO;

public interface AlgMapper {

	public AlgDTO getAlgById(Long id);

	public AlgDTO getAlgByAlgIdAndNodeType(@Param("algId") Long algId, @Param("nodeType") Integer nodeType);

	public List<AlgDTO> findAlgs(Long deviceId);

	public void createAlg(AlgDTO algDTO);

	public void updateAlg(AlgDTO algDTO);

	public void deleteAlg(Long id);

	public List<AlgDTO> getAlgByAlgId(Long algId);

	public List<AlgDTO> findAllAlgs();

}
