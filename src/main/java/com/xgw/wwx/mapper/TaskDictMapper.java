package com.xgw.wwx.mapper;

import java.util.List;

import com.xgw.wwx.dto.db.TaskDictDTO;

public interface TaskDictMapper {

	public TaskDictDTO getTaskDictById(Long id);

	public List<TaskDictDTO> findTaskDicts(Long taskId);

	public void createTaskDict(TaskDictDTO taskDictDTO);
	
	public void updateTaskDictByTaskIdAndDictId(TaskDictDTO taskDictDTO);

	public void updateTaskDict(TaskDictDTO taskDictDTO);

	public void deleteTaskDict(Long id);

}
