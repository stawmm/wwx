package com.xgw.wwx.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.AlgDTO;
import com.xgw.wwx.dto.db.CarryDTO;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.db.SubTaskDTO;
import com.xgw.wwx.dto.db.TaskDTO;
import com.xgw.wwx.dto.db.TaskResourceDTO;
import com.xgw.wwx.dto.db.TaskTimeCountDTO;

public interface TaskService {

	public TaskDTO getTaskById(Long id);

	public TaskDTO getTaskByName(String name);

	public PageInfo<TaskDTO> findTasksByPage(Integer pageSize, Integer pageNum, Map<String, Object> params);

	public List<TaskDTO> findTasks(Map<String, Object> params);

	public PageInfo<TaskDTO> findHistoryTasksByPage(Integer pageSize, Integer pageNum, Map<String, Object> params);

	public void deleteHistoryTask(Long id);

	public void updateTask(TaskDTO taskDTO);

	public void createTask(TaskDTO taskDTO);

	public void deleteTask(Long id);

	public void runTask(Long id);

	public void stopTask(Long id);

	public List<String> hitPwdResult(Long taskId);

	public Integer getTaskStatus(Long taskId);

	public List<TaskResourceDTO> findTaskResourceList();

	public List<TaskDTO> findCompeleteTasks();

	public void setTaskToHistory(Long id);

	public void updateSubTask(SubTaskDTO subTaskDTO);

	public CarryDTO taskCarryOn(String filePath, Long algId);

	public Long strategyTimeCount(TaskTimeCountDTO taskTimeCountDTO);

	public Long vpndesTimeCount(TaskTimeCountDTO taskTimeCountDTO);

	public List<AlgDTO> getAlgList();

	public List<AlgDTO> getAlgListByAlgId(Long algId);

	public SubTaskDTO getDeviceSubTask(Long deviceId);

	public void stopSubTask(DeviceDTO deviceDTO, Long id);

	public int getSubTaskStatus(DeviceDTO deviceDTO, SubTaskDTO subTaskDTO);

	public String hitSubTaskResult(DeviceDTO deviceDTO, Long id);
	
	public List<String> hitResultList(DeviceDTO deviceDTO, Long id);

	public SubTaskDTO getSubTask(DeviceDTO deviceDTO);

	public boolean createSubTask(DeviceDTO deviceDTO, SubTaskDTO newSubTask, SubTaskDTO subTaskDTO);

	public void updateTaskPwd(String passWord, Long id);

	public List<SubTaskDTO> findRunSubTasks(Long id);

	public void updateMainTaskStatus(TaskDTO taskDTO,SubTaskDTO subTaskDTO);

	public boolean checkHashMode(Long hashMode);

	public List<Map<String, Object>> findTaskStatus();

	public List<Map<String, Object>> findNodeType();
	
}
