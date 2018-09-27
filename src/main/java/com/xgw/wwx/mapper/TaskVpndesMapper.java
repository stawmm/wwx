package com.xgw.wwx.mapper;

import com.xgw.wwx.dto.db.TaskVpndesDTO;

public interface TaskVpndesMapper {

	public TaskVpndesDTO getTaskVpndesByTaskId(Long taskId);

	public void createTaskVpndes(TaskVpndesDTO taskVpndesDTO);

	public void updateTaskVpndes(TaskVpndesDTO taskVpndesDTO);

}
