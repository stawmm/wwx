package com.xgw.wwx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xgw.wwx.dto.db.TaskDTO;

public interface TaskMapper {

	public TaskDTO getTaskById(Long id);

	public TaskDTO getTaskByName(String name);

	public List<TaskDTO> findTasks(Map<String, Object> params);

	public void createTask(TaskDTO taskDTO);

	public void updateTask(TaskDTO taskDTO);

	public void deleteTask(Long id);

	public List<TaskDTO> findHistoryTasks(Map<String, Object> params);

	public void deleteHistoryTask(Long id);

	public List<TaskDTO> findCompeleteTasks();

	public void setTaskToHistory(Long id);

	public void updateTaskPwd(@Param("passWord") String passWord, @Param("id") Long id);

	public List<TaskDTO> findNoCompleteTasks(Integer nodeType);

}
