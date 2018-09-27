package com.xgw.wwx.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.ProgramDTO;

public interface ProgramService {
	
	public ProgramDTO getProgramById(Long id);
	
	public ProgramDTO getProgramByName(String name);
	
	public PageInfo<ProgramDTO> findProgramsByPage(Integer pageSize, Integer pageNum, Map<String, Object> params);
	
	public List<ProgramDTO> findPrograms(Map<String,Object> params);

    public void createProgram(ProgramDTO programDTO);
    
    public void updateProgram(ProgramDTO programDTO);

    public void deleteProgram(Long id);

	public void gpuUpgrade(List<String> fileList, List<Long> deviceList);

	public void fpgaUpgrade(List<String> fileList, List<Long> deviceList);

	public void dictUpgrade(List<String> fileList, List<Long> deviceList);

	public void fpgaOneUpgrade(List<String> fileList, List<Long> deviceList);

	public void fpgaTwoUpgrade(List<String> fileList, List<Long> deviceList);
	
    
}
