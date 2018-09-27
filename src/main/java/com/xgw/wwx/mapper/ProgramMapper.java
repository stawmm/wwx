package com.xgw.wwx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xgw.wwx.dto.db.ProgramDTO;

public interface ProgramMapper {
	
	public ProgramDTO getProgramById(Long id);
	
	public ProgramDTO getProgramByName(String name);
	
	public List<ProgramDTO> findPrograms(@Param("params") Map<String,Object> params);

    public void createProgram(ProgramDTO programDTO);
    
    public void updateProgram(ProgramDTO programDTO);

    public void deleteProgram(Long id);
    
}
