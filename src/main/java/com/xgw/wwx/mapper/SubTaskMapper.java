package com.xgw.wwx.mapper;

import java.util.List;

import com.xgw.wwx.dto.db.SubTaskDTO;

public interface SubTaskMapper {

	public SubTaskDTO getSubTaskById(Long id);

	public List<SubTaskDTO> findSubTasks(Long taskId);
	
	public List<SubTaskDTO> findRunSubTasks(Long taskId);

	public void createSubTask(SubTaskDTO subTaskDTO);

	public void updateSubTask(SubTaskDTO subTaskDTO);

	public void deleteSubTask(Long id);

	public SubTaskDTO getDeviceSubTask(Long deviceId);

	public List<SubTaskDTO> findfaultSubTasks(Long taskId);

	public List<SubTaskDTO> findSuspendSubTasks(Long taskId);

}
