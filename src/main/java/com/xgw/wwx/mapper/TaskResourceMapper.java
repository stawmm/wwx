package com.xgw.wwx.mapper;

import java.util.List;

import com.xgw.wwx.dto.db.TaskResourceDTO;

public interface TaskResourceMapper {

	public TaskResourceDTO getTaskResourceById(Long id);

	public List<TaskResourceDTO> findTaskResourcesByTaskId(Long taskId);
	
	public List<TaskResourceDTO> findTaskResources();

	public void createTaskResource(TaskResourceDTO taskResourceDTO);

	public void updateTaskResource(TaskResourceDTO taskResourceDTO);

	public void deleteTaskResource(Long id);

}
