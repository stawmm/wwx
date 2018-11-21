package com.xgw.wwx.mapper;

import java.util.List;

import com.xgw.wwx.dto.db.TaskFileDTO;

public interface TaskFileMapper {

	public TaskFileDTO getTaskFileById(Long id);

	public List<TaskFileDTO> findTaskFiles(Long taskId);

	public void createTaskFile(TaskFileDTO taskFileDTO);

	public void updateTaskFile(TaskFileDTO taskFileDTO);

	public void deleteTaskFile(Long id);

}
