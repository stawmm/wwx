package com.xgw.wwx.mapper;

import com.xgw.wwx.dto.db.TaskDesSliceDTO;

public interface TaskDesSliceMapper {

	public TaskDesSliceDTO getTaskDesSliceBySubTaskId(Long subTaskId);

	public void createTaskDesSlice(TaskDesSliceDTO taskDesSliceDTO);
	
}
