package com.xgw.wwx.mapper;

import com.xgw.wwx.dto.db.TaskMaskSliceDTO;

public interface TaskMaskSliceMapper {

	public TaskMaskSliceDTO getTaskMaskSliceBySubTaskId(Long subTaskId);

	public void createTaskMaskSlice(TaskMaskSliceDTO taskMaskSliceDTO);
	
}
