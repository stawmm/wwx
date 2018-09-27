package com.xgw.wwx.mapper;

import java.util.List;

import com.xgw.wwx.dto.db.TaskStrategyDTO;

public interface TaskStrategyMapper {

	public TaskStrategyDTO getTaskStrategyById(Long id);

	public List<TaskStrategyDTO> findTaskStrategys(Long taskId);

	public void createTaskStrategy(TaskStrategyDTO taskStrategyDTO);

	public void updateTaskStrategy(TaskStrategyDTO taskStrategyDTO);

	public void deleteTaskStrategy(Long id);

}
