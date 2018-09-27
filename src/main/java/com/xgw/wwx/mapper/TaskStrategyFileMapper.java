package com.xgw.wwx.mapper;

import com.xgw.wwx.dto.db.TaskStrategyFileDTO;

public interface TaskStrategyFileMapper {

	public TaskStrategyFileDTO getTaskStrategyFileByTaskId(Long taskId);

	public void createTaskStrategyFile(TaskStrategyFileDTO taskStrategyFileDTO);

	public void updateTaskStrategyFile(TaskStrategyFileDTO taskStrategyFileDTO);

}
