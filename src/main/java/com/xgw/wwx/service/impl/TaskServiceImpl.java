package com.xgw.wwx.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.constant.WebUrlConstant;
import com.xgw.wwx.common.em.LoggerLevelType;
import com.xgw.wwx.common.em.LoggerStatusType;
import com.xgw.wwx.common.em.NodeType;
import com.xgw.wwx.common.em.ResultStatusType;
import com.xgw.wwx.common.em.SliceCommaEnum;
import com.xgw.wwx.common.em.SliceStatusType;
import com.xgw.wwx.common.em.TaskStatusType;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.helper.DictHelper;
import com.xgw.wwx.common.util.CloneUtil;
import com.xgw.wwx.common.util.GsonUtil;
import com.xgw.wwx.common.util.HttpUtil;
import com.xgw.wwx.dto.db.AlgDTO;
import com.xgw.wwx.dto.db.CarryDTO;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.db.DictDTO;
import com.xgw.wwx.dto.db.LoggerDTO;
import com.xgw.wwx.dto.db.StrategyDTO;
import com.xgw.wwx.dto.db.SubTaskDTO;
import com.xgw.wwx.dto.db.TaskDTO;
import com.xgw.wwx.dto.db.TaskDesSliceDTO;
import com.xgw.wwx.dto.db.TaskDictDTO;
import com.xgw.wwx.dto.db.TaskFileDTO;
import com.xgw.wwx.dto.db.TaskMaskSliceDTO;
import com.xgw.wwx.dto.db.TaskResourceDTO;
import com.xgw.wwx.dto.db.TaskStrategyDTO;
import com.xgw.wwx.dto.db.TaskStrategyFileDTO;
import com.xgw.wwx.dto.db.TaskTimeCountDTO;
import com.xgw.wwx.dto.db.TaskVpndesDTO;
import com.xgw.wwx.dto.jna.DesSliceLocationDTO;
import com.xgw.wwx.dto.jna.MaskSliceLocationDTO;
import com.xgw.wwx.dto.request.BaseRespDTO;
import com.xgw.wwx.dto.request.PwdRespDTO;
import com.xgw.wwx.dto.request.StatusRespDTO;
import com.xgw.wwx.mapper.AlgMapper;
import com.xgw.wwx.mapper.DictMapper;
import com.xgw.wwx.mapper.StrategyMapper;
import com.xgw.wwx.mapper.SubTaskMapper;
import com.xgw.wwx.mapper.TaskDesSliceMapper;
import com.xgw.wwx.mapper.TaskDictMapper;
import com.xgw.wwx.mapper.TaskFileMapper;
import com.xgw.wwx.mapper.TaskMapper;
import com.xgw.wwx.mapper.TaskMaskSliceMapper;
import com.xgw.wwx.mapper.TaskResourceMapper;
import com.xgw.wwx.mapper.TaskStrategyFileMapper;
import com.xgw.wwx.mapper.TaskStrategyMapper;
import com.xgw.wwx.mapper.TaskVpndesMapper;
import com.xgw.wwx.service.CommandService;
import com.xgw.wwx.service.DeviceService;
import com.xgw.wwx.service.FileService;
import com.xgw.wwx.service.HzLoggerService;
import com.xgw.wwx.service.JnaService;
import com.xgw.wwx.service.RequestService;
import com.xgw.wwx.service.TaskService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class TaskServiceImpl implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	HzLoggerService hzLoggerService;

	@Value("${wwx.pwdslice.mask.output}")
	private String maskOutput;

	@Value("${wwx.pwdslice.des.output}")
	private String desOutput;

	@Value("${wwx.device.card.num}")
	private int deviceCardNum;

	@Autowired
	private CommandService commandService;

	@Autowired
	private FileService fileService;

	@Autowired
	private JnaService jnaService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private TaskFileMapper taskFileMapper;

	@Autowired
	private TaskResourceMapper taskResourceMapper;

	@Autowired
	private TaskStrategyMapper taskStrategyMapper;

	@Autowired
	private TaskDictMapper taskDictMapper;

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private StrategyMapper strategyMapper;

	@Autowired
	private AlgMapper algMapper;

	@Autowired
	private TaskStrategyFileMapper taskStrategyFileMapper;

	@Autowired
	private SubTaskMapper subTaskMapper;

	@Autowired
	private TaskVpndesMapper taskVpndesMapper;

	@Autowired
	private TaskMaskSliceMapper taskMaskSliceMapper;

	@Autowired
	private TaskDesSliceMapper taskDesSliceMapper;

	@Override
	public TaskDTO getTaskById(Long id) {
		TaskDTO taskDTO = taskMapper.getTaskById(id);
		if (null != taskDTO) {
			// file
			List<TaskFileDTO> taskFiles = taskFileMapper.findTaskFiles(id);
			if (null != taskFiles && !taskFiles.isEmpty()) {
				taskDTO.setFile(taskFiles.get(0));
			}
			// resource
			List<TaskResourceDTO> resources = taskResourceMapper.findTaskResourcesByTaskId(id);
			taskDTO.setResources(resources);
			// dicts
			List<DictDTO> dicts = dictMapper.findDictListByTaskId(id);
			taskDTO.setDicts(dicts);
			// strategy
			List<StrategyDTO> strategys = strategyMapper.findStrategysByTaskId(id);
			taskDTO.setStrategys(strategys);
			// 设备
			List<DeviceDTO> devices = deviceService.findDevicesByTaskId(id);
			taskDTO.setDevices(devices);
		}
		return taskDTO;
	}

	@Override
	public TaskDTO getTaskByName(String name) {
		return taskMapper.getTaskByName(name);
	}

	@Override
	public PageInfo<TaskDTO> findTasksByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<TaskDTO> list = taskMapper.findTasks(params);
		if (null != list && !list.isEmpty()) {
			for (TaskDTO taskDTO : list) {
				// file
				List<TaskFileDTO> taskFiles = taskFileMapper.findTaskFiles(taskDTO.getId());
				if (null != taskFiles && !taskFiles.isEmpty()) {
					taskDTO.setFile(taskFiles.get(0));
				}
				// resource
				List<TaskResourceDTO> resources = taskResourceMapper.findTaskResourcesByTaskId(taskDTO.getId());
				taskDTO.setResources(resources);
			}
		}
		return new PageInfo<TaskDTO>(list);
	}

	@Override
	public List<TaskDTO> findTasks(Map<String, Object> params) {
		return taskMapper.findTasks(params);
	}

	@Override
	public void createTask(TaskDTO taskDTO) {
		// 新增任务
		taskMapper.createTask(taskDTO);
		// 任务Id
		Long taskId = taskDTO.getId();
		// 新增TaskFileDTO
		TaskFileDTO taskFileDTO = taskDTO.getFile();
		taskFileDTO.setTaskId(taskId);
		taskFileMapper.createTaskFile(taskFileDTO);
		// 新增TaskResourceDTO
		int nodeType = -1;
		List<TaskResourceDTO> resources = taskDTO.getResources();
		if (null != resources && !resources.isEmpty()) {
			for (TaskResourceDTO taskResourceDTO : resources) {
				if (null != taskResourceDTO.getNodeNum() && taskResourceDTO.getNodeNum().intValue() != 0) {
					nodeType = taskResourceDTO.getNodeType();
					taskResourceDTO.setTaskId(taskId);
					taskResourceMapper.createTaskResource(taskResourceDTO);
				}
			}
		}
		// 新增TaskDictDTO
		List<TaskDictDTO> dicts = taskDTO.getTaskDicts();
		if (null != dicts && !dicts.isEmpty()) {
			for (TaskDictDTO taskDictDTO : dicts) {
				taskDictDTO.setTaskId(taskId);
				taskDictMapper.createTaskDict(taskDictDTO);
			}
		}
		// 新增TaskStrategyDTO
		List<TaskStrategyDTO> strategys = taskDTO.getTaskStrategys();
		if (null != strategys && !strategys.isEmpty()) {
			for (TaskStrategyDTO taskStrategyDTO : strategys) {
				taskStrategyDTO.setTaskId(taskId);
				taskStrategyMapper.createTaskStrategy(taskStrategyDTO);
			}
			// 新增策略文件
			List<StrategyDTO> dbStrategys = strategyMapper.findStrategysByTaskId(taskId);
			TaskStrategyFileDTO taskStrategyFileDTO = null;
			if (dbStrategys != null && !dbStrategys.isEmpty()) {
				taskStrategyFileDTO = new TaskStrategyFileDTO();
				// 创建策略文件
				File file = fileService.createTaskFile();
				fileService.writeStrategyToFile(file, dbStrategys);
				taskStrategyFileDTO.setVaild(1);
				taskStrategyFileDTO.setFinish(0);
				taskStrategyFileDTO.setFileLoc(0l);
				taskStrategyFileDTO.setOffsetLoc(0);
				taskStrategyFileDTO.setOutput(maskOutput);
				taskStrategyFileDTO.setCurrentMask(SliceCommaEnum.MASK.getValue());
				taskStrategyFileDTO.setFileName(file.getName());
				taskStrategyFileDTO.setFilePath(file.getPath());
				taskStrategyFileDTO.setTaskId(taskId);
				taskStrategyFileDTO.setPrepare(SliceStatusType.UNFINISH.getStatus());
				taskStrategyFileMapper.createTaskStrategyFile(taskStrategyFileDTO);
			}
		}

		// 新增VPNDES记录
		if (taskDTO.getHasVpndes()) {
			TaskVpndesDTO taskVpndesDTO = new TaskVpndesDTO();
			taskVpndesDTO.setTaskId(taskId);
			taskVpndesDTO.setVaild(1);
			taskVpndesDTO.setFinish(0);
			taskVpndesDTO.setOutput(desOutput);
			if (nodeType == 0) {
				taskVpndesDTO.setDesLoc(SliceCommaEnum.GPU.getValue());
			} else {
				taskVpndesDTO.setDesLoc(SliceCommaEnum.OTHER.getValue());
			}
			taskVpndesMapper.createTaskVpndes(taskVpndesDTO);
		}

	}

	@Override
	public void updateTask(TaskDTO taskDTO) {
		taskMapper.updateTask(taskDTO);
	}

	@Override
	public PageInfo<TaskDTO> findHistoryTasksByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<TaskDTO> list = taskMapper.findHistoryTasks(params);
		if (null != list && !list.isEmpty()) {
			for (TaskDTO taskDTO : list) {
				// file
				List<TaskFileDTO> taskFiles = taskFileMapper.findTaskFiles(taskDTO.getId());
				if (null != taskFiles && !taskFiles.isEmpty()) {
					taskDTO.setFile(taskFiles.get(0));
				}
				// resource
				List<TaskResourceDTO> resources = taskResourceMapper.findTaskResourcesByTaskId(taskDTO.getId());
				taskDTO.setResources(resources);
			}
		}
		return new PageInfo<TaskDTO>(list);
	}

	@Override
	public void deleteHistoryTask(Long id) {
		taskMapper.deleteHistoryTask(id);
	}

	@Override
	public void deleteTask(Long id) {
		// 获取主任务
		TaskDTO mainTask = taskMapper.getTaskById(id);
		mainTask.setStatus(TaskStatusType.DELETE.getStatus());
		taskMapper.updateTask(mainTask);
		if (null != mainTask) {
			// 获取子任务
			List<SubTaskDTO> subTaskDTOs = subTaskMapper.findRunSubTasks(id);
			if (null != subTaskDTOs && !subTaskDTOs.isEmpty()) {
				for (SubTaskDTO subTaskDTO : subTaskDTOs) {
					// 获取设备信息
					DeviceDTO deviceDTO = deviceService.getDeviceByTaskId(subTaskDTO.getId());
					// 给节点发送暂停任务请求
					if (null != deviceDTO) {
						stopSubTask(deviceDTO, subTaskDTO.getId());
						// 解绑关系
						deviceDTO.setTaskId(null);
						deviceService.updateDevice(deviceDTO);
						// 删除子任务
						subTaskMapper.deleteSubTask(subTaskDTO.getId());
					}
				}
			}
		}
		// 删除主任务
		// taskMapper.deleteTask(id);
	}

	@Override
	public void runTask(Long id) {
		// 获取主任务
		TaskDTO mainTask = taskMapper.getTaskById(id);
		if (null != mainTask) {
			if (TaskStatusType.SUSPEND.getStatus() == mainTask.getStatus()) {
				// 更新主任状态
				mainTask.setStatus(TaskStatusType.QUEUE.getStatus());
				taskMapper.updateTask(mainTask);
				// 获取子任务
				/*
				 * List<SubTaskDTO> subTaskDTOs =
				 * subTaskMapper.findSuspendSubTasks(id); if (null !=
				 * subTaskDTOs && !subTaskDTOs.isEmpty()) { for (SubTaskDTO
				 * subTaskDTO : subTaskDTOs) { // 获取设备信息 DeviceDTO deviceDTO =
				 * deviceService.getDeviceByTaskId(subTaskDTO.getId()); //
				 * 给节点发送暂停任务请求 if (null != deviceDTO) { runSubTask(deviceDTO,
				 * subTaskDTO.getId()); } } }
				 */
			}
		}
	}

	@Override
	public void stopTask(Long id) {
		// 获取主任务
		TaskDTO mainTask = taskMapper.getTaskById(id);
		if (null != mainTask) {
			if (TaskStatusType.RUNNING.getStatus() == mainTask.getStatus()) {
				// 更新主任状态
				mainTask.setStatus(TaskStatusType.SUSPEND.getStatus());
				taskMapper.updateTask(mainTask);
				// 获取子任务
				List<SubTaskDTO> subTaskDTOs = subTaskMapper.findRunSubTasks(id);
				if (null != subTaskDTOs && !subTaskDTOs.isEmpty()) {
					for (SubTaskDTO subTaskDTO : subTaskDTOs) {
						// 子任务故障
						subTaskDTO.setStatus(TaskStatusType.FAULT.getStatus());
						subTaskMapper.updateSubTask(subTaskDTO);

						// 获取设备信息
						DeviceDTO deviceDTO = deviceService.getDeviceByTaskId(subTaskDTO.getId());

						// 给节点发送暂停任务请求
						if (null != deviceDTO) {
							stopSubTask(deviceDTO, subTaskDTO.getId());
						}

					}
				}
			}
		}
	}

	@Override
	public List<String> hitPwdResult(Long taskId) {
		// 获取设备信息
		DeviceDTO deviceDTO = deviceService.getDeviceByTaskId(taskId);
		if (null != deviceDTO) {
			String nodeType = deviceDTO.getNodeType() + "";
			String url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_SUCCESS_PWD, deviceDTO.getIp(), deviceDTO.getPort(), nodeType, taskId + "");
			PwdRespDTO pwdRespDTO = requestService.doHttpGet(url, PwdRespDTO.class);
			if (null != pwdRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(pwdRespDTO.getResult())) {
				return pwdRespDTO.getPwds();
			}
		}
		return null;
	}

	@Override
	public Integer getTaskStatus(Long taskId) {
		// 获取设备信息
		DeviceDTO deviceDTO = deviceService.getDeviceByTaskId(taskId);
		if (null != deviceDTO) {
			String nodeType = deviceDTO.getNodeType() + "";
			String url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_STATUS, deviceDTO.getIp(), deviceDTO.getPort(), nodeType, taskId + "");
			StatusRespDTO statusRespDTO = requestService.doHttpGet(url, StatusRespDTO.class);
			if (null != statusRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(statusRespDTO.getResult())) {
				return statusRespDTO.getStatus();
			}
		}
		return null;
	}

	@Override
	public List<TaskResourceDTO> findTaskResourceList() {
		List<TaskResourceDTO> resources = taskResourceMapper.findTaskResources();
		if (resources != null && !resources.isEmpty()) {
			List<TaskResourceDTO> returnRes = new ArrayList<TaskResourceDTO>();
			for (TaskResourceDTO resource : resources) {
				resource.setCardNum(deviceCardNum);
				returnRes.add(resource);
			}
			return returnRes;
		}
		return resources;
	}

	@Override
	public List<TaskDTO> findCompeleteTasks() {
		return taskMapper.findCompeleteTasks();
	}

	@Override
	public void setTaskToHistory(Long id) {
		taskMapper.setTaskToHistory(id);
	}

	@Override
	@Async
	public void updateSubTask(SubTaskDTO subTaskDTO) {
		subTaskMapper.updateSubTask(subTaskDTO);
	}

	@Override
	public CarryDTO taskCarryOn(String filePath, Long algId) {
		return commandService.getFileCarryMessage(filePath, algId);
	}

	@Override
	public Long strategyTimeCount(TaskTimeCountDTO taskTimeCountDTO) {
		File tempFile = null;
		try {
			tempFile = fileService.createTempFile();
			fileService.writeStrategyToFile(tempFile, taskTimeCountDTO.getStrategys());
			logger.debug("-- strategyTimeCount path:{}, speed:{} --", tempFile.getPath(), taskTimeCountDTO.getSpeed());
			Long count = commandService.getStrategyTimeCount(tempFile.getPath(), taskTimeCountDTO.getSpeed());
			return count;
		} catch (Exception e) {
			throw new WxxRuntimeException("STRATEGY_TIME_COUNT_ERROR", "策略计算时间出错");
		} finally {
			if (null != tempFile) {
				FileUtils.deleteQuietly(tempFile);
			}
		}
	}

	@Override
	public Long vpndesTimeCount(TaskTimeCountDTO taskTimeCountDTO) {
		return commandService.getVpndesTimeCount(taskTimeCountDTO.getSpeed());
	}

	@Override
	public List<AlgDTO> getAlgList() {
		return algMapper.findAllAlgs();
	}

	@Override
	public List<AlgDTO> getAlgListByAlgId(Long algId) {
		return algMapper.getAlgByAlgId(algId);
	}

	@Override
	public SubTaskDTO getDeviceSubTask(Long deviceId) {
		return subTaskMapper.getDeviceSubTask(deviceId);
	}

	@Override
	public void stopSubTask(DeviceDTO deviceDTO, Long id) {
		try {
			String url = null;
			if (NodeType.GPU.getType() == deviceDTO.getNodeType().intValue()) {
				url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_STOP_GPU, deviceDTO.getIp(), deviceDTO.getPort());
			} else {
				url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_STOP_FPGA, deviceDTO.getIp(), deviceDTO.getPort(), id + "");
			}
			BaseRespDTO baseRespDTO = requestService.doHttpGet(url, BaseRespDTO.class);
			if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
				logger.debug("-- stopSubTask ip:{}, port:{}, subTaskId:{} success --", deviceDTO.getIp(), deviceDTO.getPort(), id);
			}

			// 是否设备
			deviceDTO.setTaskId(null);
			deviceService.updateDevice(deviceDTO);
		} catch (Exception e) {
			logger.debug("-- stopSubTask ip:{}, port:{}, subTaskId:{} error --", deviceDTO.getIp(), deviceDTO.getPort(), id);
		}
	}

	public void runSubTask(DeviceDTO deviceDTO, Long id) {
		try {
			// 给节点发送运行任务请求
			if (null != deviceDTO) {
				String type = deviceDTO.getNodeType().intValue() == 0 ? "gpu" : "fpga";
				String url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_RUN, deviceDTO.getIp(), deviceDTO.getPort(), type);
				BaseRespDTO baseRespDTO = requestService.doHttpGet(url, BaseRespDTO.class);
				if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
					// 更新任务状态
					logger.debug("-- runSubTask ip:{}, port:{}, subTaskId:{} success --", deviceDTO.getIp(), deviceDTO.getPort(), id);
				}
			}
		} catch (Exception e) {
			logger.debug("-- runSubTask ip:{}, port:{}, subTaskId:{} error --", deviceDTO.getIp(), deviceDTO.getPort(), id);
		}
	}

	@Override
	public int getSubTaskStatus(DeviceDTO deviceDTO, SubTaskDTO subTaskDTO) {
		Long id = subTaskDTO.getId();
		try {
			int cardNum = getCardNum(subTaskDTO);
			logger.debug("-- getSubTaskStatus subTaskId:{}, cardNum:{} --", id, cardNum);
			String url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_STATUS_NUMBER, deviceDTO.getIp(), deviceDTO.getPort(), deviceDTO.getNodeType() + "", id + "", cardNum + "");
			StatusRespDTO statusRespDTO = requestService.doHttpGet(url, StatusRespDTO.class);
			if (null != statusRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(statusRespDTO.getResult())) {
				return statusRespDTO.getStatus();
			} else {
				logger.debug("-- getSubTaskStatus ip:{}, port:{}, subTaskId:{} failed --", deviceDTO.getIp(), deviceDTO.getPort(), id);
				return ResultStatusType.FAILED.getStatus();
			}

		} catch (Exception e) {
			logger.debug("-- getSubTaskStatus ip:{}, port:{}, subTaskId:{} error --", deviceDTO.getIp(), deviceDTO.getPort(), id);
		}
		return ResultStatusType.IDLE.getStatus();
	}

	public int getCardNum(SubTaskDTO subTaskDTO) {
		// 获取任务需要用的办卡数
		int count = 0;
		List<String> cutInfos = GsonUtil.GsonToBean(subTaskDTO.getCutInfoNames(), List.class);
		if (null != cutInfos && !cutInfos.isEmpty()) {
			for (String cutInfo : cutInfos) {
				if (StringUtils.isNotBlank(cutInfo)) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public String hitSubTaskResult(DeviceDTO deviceDTO, Long id) {
		try {
			String nodeType = deviceDTO.getNodeType() + "";
			String url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_SUCCESS_PWD, deviceDTO.getIp(), deviceDTO.getPort(), nodeType, id + "");
			PwdRespDTO pwdRespDTO = requestService.doHttpGet(url, PwdRespDTO.class);
			if (null != pwdRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(pwdRespDTO.getResult())) {
				return GsonUtil.GsonString(pwdRespDTO.getPwds());
			}
			logger.debug("-- hitSubTaskResult ip:{}, port:{}, subTaskId:{} failed --", deviceDTO.getIp(), deviceDTO.getPort(), id);
		} catch (Exception e) {
			logger.debug("-- hitSubTaskResult ip:{}, port:{}, subTaskId:{} error --", deviceDTO.getIp(), deviceDTO.getPort(), id);
		}
		return "";
	}

	@Override
	public List<String> hitResultList(DeviceDTO deviceDTO, Long id) {
		try {
			String nodeType = deviceDTO.getNodeType() + "";
			String url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_SUCCESS_PWD, deviceDTO.getIp(), deviceDTO.getPort(), nodeType, id + "");
			PwdRespDTO pwdRespDTO = requestService.doHttpGet(url, PwdRespDTO.class);
			if (null != pwdRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(pwdRespDTO.getResult())) {
				return pwdRespDTO.getPwds();
			}
			logger.debug("-- hitSubTaskResult ip:{}, port:{}, subTaskId:{} failed --", deviceDTO.getIp(), deviceDTO.getPort(), id);
		} catch (Exception e) {
			logger.debug("-- hitSubTaskResult ip:{}, port:{}, subTaskId:{} error --", deviceDTO.getIp(), deviceDTO.getPort(), id);
		}
		return null;
	}

	@Override
	public SubTaskDTO getSubTask(DeviceDTO deviceDTO) {
		// 节点类型获取主任务列表(排序)
		SubTaskDTO subTaskDTO = null;
		List<TaskDTO> mainTasks = taskMapper.findNoCompleteTasks(deviceDTO.getNodeType());
		logger.debug("-- taskService findNoCompleteTasks size:{} --", mainTasks.size());
		if (null != mainTasks && !mainTasks.isEmpty()) {
			int size = mainTasks.size();
			for (int i = 0; i < size; i++) {
				TaskDTO mainTask = mainTasks.get(i);
				Long mainTaskId = mainTask.getId();
				Long totalSpeed = mainTask.getDictCommand();
				logger.debug("-- taskService mainTask totalSpeed:{} --", totalSpeed);
				TaskDTO detailTask = getTaskById(mainTaskId);
				long algId = detailTask.getFile().getAlgId();
				// 查询正在运行的子任务
				List<SubTaskDTO> runSubTasks = subTaskMapper.findRunSubTasks(mainTaskId);
				logger.debug("-- taskService runSubTasks size:{} --", runSubTasks.size());
				boolean isAllot = checkAllotTask(deviceDTO, runSubTasks, detailTask);
				logger.debug("-- taskService runSubTasks isAllot:{} --", isAllot);
				if (!isAllot) {
					continue;
				}

				// 查询运行故障的子任务
				List<SubTaskDTO> faultSubTasks = subTaskMapper.findfaultSubTasks(mainTaskId);
				logger.debug("-- taskService faultSubTasks size:{} --", faultSubTasks.size());
				if (null != faultSubTasks && !faultSubTasks.isEmpty()) {
					// 循环遍历故障任务
					for (SubTaskDTO faultSubTask : faultSubTasks) {
						// 节点类型判断
						if (deviceDTO.getNodeType().intValue() != faultSubTask.getCardType().intValue()) {
							continue;
						}
						// mask任务
						TaskMaskSliceDTO taskMaskSliceDTO = taskMaskSliceMapper.getTaskMaskSliceBySubTaskId(faultSubTask.getId());
						if (null != taskMaskSliceDTO) {
							int cardNum = taskMaskSliceDTO.getCardnum();
							if (cardNum > deviceDTO.getCardNum().intValue()) {
								continue;
							}
							List<String> strategyInfos = getCutInfo(taskMaskSliceDTO);
							logger.debug("-- taskService getCutInfo strategyInfos:{} --", strategyInfos);
							if (null != strategyInfos && !strategyInfos.isEmpty()) {
								faultSubTask.setCutInfos(strategyInfos);
								faultSubTask.setCutInfoNames(GsonUtil.GsonString(strategyInfos));
								logger.debug("-- taskService faultSubTask strategyInfos return --");
								return faultSubTask;
							}
						}
						// des任务
						TaskDesSliceDTO taskDesSliceDTO = taskDesSliceMapper.getTaskDesSliceBySubTaskId(faultSubTask.getId());
						if (null != taskDesSliceDTO) {
							int cardNum = taskDesSliceDTO.getCardnum();
							if (cardNum > deviceDTO.getCardNum().intValue()) {
								continue;
							}
							// 获取vpndes切片信息
							List<String> vpndesInfos = getCutInfo(taskDesSliceDTO);
							logger.debug("-- taskService getCutInfo vpndesInfos:{} --", vpndesInfos);
							if (null != vpndesInfos && !vpndesInfos.isEmpty()) {
								faultSubTask.setCutInfos(vpndesInfos);
								faultSubTask.setCutInfoNames(GsonUtil.GsonString(vpndesInfos));

								logger.debug("-- taskService faultSubTask vpndesInfos return --");
								return faultSubTask;
							}
						}
						// 字典任务
						String cutFileNames = faultSubTask.getCutInfoNames();
						logger.debug("-- cutinfoNames:{} --", cutFileNames);
						if (StringUtils.isBlank(cutFileNames)) {
							continue;
						}
						List<String> cutInfos = GsonUtil.GsonToBean(faultSubTask.getCutInfoNames(), List.class);
						if (null != cutInfos && !cutInfos.isEmpty()) {
							logger.debug("-- cutInfos size:{} --", cutInfos.size());
							logger.debug("-- card size:{} --", deviceDTO.getCardNum().intValue());
							// 切片大于办卡数
							if (cutInfos.size() > deviceDTO.getCardNum().intValue()) {
								continue;
							}
							// 如果字典不在设备上
							boolean containsFlag = deviceContainsDicts(cutInfos, deviceDTO);
							logger.debug("-- deviceContainsDicts result:{} --", containsFlag);
							if (!containsFlag) {
								continue;
							}
							faultSubTask.setCutInfos(cutInfos);
							logger.debug("-- taskService faultSubTask dict return --");
							return faultSubTask;
						}
					}
				}

				// 获取未完的字典信息
				List<DictDTO> dicts = dictMapper.findNoCheckDictByTaskId(mainTaskId);
				logger.debug("-- taskService dicts size:{} --", dicts.size());
				if (null != dicts && !dicts.isEmpty()) {
					List<String> cutInfos = getDictCutInfos(mainTaskId, dicts, deviceDTO);
					if (null == cutInfos || cutInfos.isEmpty()) {
						continue;
					}
					// 创建子任务
					subTaskDTO = new SubTaskDTO();
					subTaskDTO.setTaskId(mainTaskId);
					subTaskDTO.setCardNum(deviceDTO.getCardNum());
					subTaskDTO.setCardType(deviceDTO.getNodeType());
					subTaskDTO.setAlgId(algId);
					subTaskDTO.setCrackInfo(detailTask.getFile().getSpecialChar());
					subTaskDTO.setCrackMode(0);
					subTaskDTO.setCutInfos(cutInfos);
					subTaskDTO.setCutInfoNames(GsonUtil.GsonString(cutInfos));
					subTaskDTO.setTimeCount(getDictSubTaskTime(cutInfos, totalSpeed));
					subTaskMapper.createSubTask(subTaskDTO);
					return subTaskDTO;
				}

				// 查看策略切片信息
				TaskStrategyFileDTO strategyFileDTO = taskStrategyFileMapper.getTaskStrategyFileByTaskId(mainTaskId);
				logger.debug("-- taskService strategyFileDTO: {} --", strategyFileDTO);
				if (null != strategyFileDTO && SliceStatusType.FINISH.getStatus() != strategyFileDTO.getFinish().intValue()) {
					// 获取策略切片信息
					List<String> strategyInfos = getCutInfo(mainTaskId, algId, deviceDTO, strategyFileDTO);
					logger.debug("-- taskService getCutInfo strategyInfos:{} --", strategyInfos);
					if (null != strategyInfos && !strategyInfos.isEmpty()) {
						subTaskDTO = new SubTaskDTO();
						subTaskDTO.setTaskId(mainTaskId);
						subTaskDTO.setCardNum(deviceDTO.getCardNum());
						subTaskDTO.setCardType(deviceDTO.getNodeType());
						subTaskDTO.setAlgId(algId);
						subTaskDTO.setCrackInfo(detailTask.getFile().getSpecialChar());
						subTaskDTO.setCrackMode(1);
						subTaskDTO.setCutInfos(strategyInfos);
						subTaskDTO.setCutInfoNames(GsonUtil.GsonString(strategyInfos));
						subTaskDTO.setTimeCount(getStrategySubTaskTime(strategyInfos, totalSpeed));
						subTaskMapper.createSubTask(subTaskDTO);

						// 保存切片前的信息
						AlgDTO algDTO = algMapper.getAlgByAlgIdAndNodeType(algId, deviceDTO.getNodeType());
						String mapKey = "mask_pwdslice_" + mainTaskId;
						int jmtype = deviceDTO.getNodeType();
						int cardnum = deviceDTO.getCardNum();
						long speed = algDTO.getSpeed();
						int sunzinum = algDTO.getCoreNum();
						TaskMaskSliceDTO taskMaskSliceDTO = new TaskMaskSliceDTO();
						taskMaskSliceDTO.setVaild(strategyFileDTO.getVaild());
						taskMaskSliceDTO.setFinish(strategyFileDTO.getFinish());
						taskMaskSliceDTO.setCurrentMask(strategyFileDTO.getCurrentMask());
						taskMaskSliceDTO.setFileLoc(strategyFileDTO.getFileLoc());
						taskMaskSliceDTO.setFilePath(strategyFileDTO.getFilePath());
						taskMaskSliceDTO.setOffsetLoc(strategyFileDTO.getOffsetLoc());
						taskMaskSliceDTO.setOutput(strategyFileDTO.getOutput());
						taskMaskSliceDTO.setCutFileName(strategyFileDTO.getCutFileName());
						taskMaskSliceDTO.setSubTaskId(subTaskDTO.getId());
						taskMaskSliceDTO.setMapKey(mapKey);
						taskMaskSliceDTO.setJmtype(jmtype);
						taskMaskSliceDTO.setCardnum(cardnum);
						taskMaskSliceDTO.setSpeed(speed);
						taskMaskSliceDTO.setSunzinum(sunzinum);
						taskMaskSliceMapper.createTaskMaskSlice(taskMaskSliceDTO);

						return subTaskDTO;
					}
				}

				// 查看vpn-des信息
				logger.debug("-- taskService hasVpndes:{} --", mainTask.getHasVpndes());
				if (mainTask.getHasVpndes()) {
					TaskVpndesDTO taskVpndesDTO = taskVpndesMapper.getTaskVpndesByTaskId(mainTaskId);
					logger.debug("-- taskService taskVpndesDTO:{} --", taskVpndesDTO);
					if (null != taskVpndesDTO && SliceStatusType.FINISH.getStatus() != taskVpndesDTO.getFinish().intValue()) {
						// 获取vpndes切片信息
						List<String> vpndesInfos = getCutInfo(mainTaskId, algId, deviceDTO, taskVpndesDTO);
						logger.debug("-- taskService getCutInfo vpndesInfos:{} --", vpndesInfos);
						if (null != vpndesInfos && !vpndesInfos.isEmpty()) {
							subTaskDTO = new SubTaskDTO();
							subTaskDTO.setTaskId(mainTaskId);
							subTaskDTO.setCardNum(deviceDTO.getCardNum());
							subTaskDTO.setCardType(deviceDTO.getNodeType());
							subTaskDTO.setAlgId(algId);
							subTaskDTO.setCrackInfo(detailTask.getFile().getSpecialChar());
							subTaskDTO.setCrackMode(2);
							subTaskDTO.setCutInfos(vpndesInfos);
							subTaskDTO.setCutInfoNames(GsonUtil.GsonString(vpndesInfos));
							// 时间计算
							long timeCount = getVpndesSubTaskTime(detailTask.getResources().get(0));
							subTaskDTO.setTimeCount(timeCount);
							// 创建策略子任务
							subTaskMapper.createSubTask(subTaskDTO);

							// 保存切片前的信息
							AlgDTO algDTO = algMapper.getAlgByAlgIdAndNodeType(algId, deviceDTO.getNodeType());
							String mapKey = "des_pwdslice_" + mainTaskId;
							int jmtype = deviceDTO.getNodeType();
							int cardnum = deviceDTO.getCardNum();
							long speed = algDTO.getSpeed();
							int sunzinum = algDTO.getCoreNum();
							TaskDesSliceDTO taskDesSliceDTO = new TaskDesSliceDTO();
							taskDesSliceDTO.setVaild(taskVpndesDTO.getVaild());
							taskDesSliceDTO.setFinish(taskVpndesDTO.getFinish());
							taskDesSliceDTO.setFilePath(taskVpndesDTO.getFilePath());
							taskDesSliceDTO.setDesLoc(taskVpndesDTO.getDesLoc());
							taskDesSliceDTO.setOutput(taskVpndesDTO.getOutput());
							taskDesSliceDTO.setCutFileName(taskVpndesDTO.getCutFileName());
							taskDesSliceDTO.setSubTaskId(subTaskDTO.getId());
							taskDesSliceDTO.setMapKey(mapKey);
							taskDesSliceDTO.setJmtype(jmtype);
							taskDesSliceDTO.setCardnum(cardnum);
							taskDesSliceDTO.setSpeed(speed);
							taskDesSliceDTO.setSunzinum(sunzinum);
							taskDesSliceMapper.createTaskDesSlice(taskDesSliceDTO);

							return subTaskDTO;
						}
					}
				}
			}
		}
		return subTaskDTO;
	}

	private boolean deviceContainsDicts(List<String> cutInfos, DeviceDTO deviceDTO) {
		boolean contains = true;
		if (null == cutInfos || cutInfos.isEmpty()) {
			return contains;
		}
		List<DictDTO> dbDicts = dictMapper.findDeviceDicts(deviceDTO.getId());
		if (null == dbDicts || dbDicts.isEmpty()) {
			contains = false;
			return contains;
		}
		List<String> dictNames = new ArrayList<String>();
		for (DictDTO dbDict : dbDicts) {
			dictNames.add(dbDict.getName());
		}
		for (String cutInfo : cutInfos) {
			if (StringUtils.isNotBlank(cutInfo) && !dictNames.contains(cutInfo)) {
				contains = false;
				break;
			}
		}
		return contains;
	}

	private List<String> getDictCutInfos(Long mainTaskId, List<DictDTO> dicts, DeviceDTO deviceDTO) {
		List<String> cutInfos = new ArrayList<String>();
		if (null == dicts || dicts.isEmpty()) {
			return cutInfos;
		}
		List<DictDTO> dbDicts = dictMapper.findDeviceDicts(deviceDTO.getId());
		if (null != dbDicts && !dbDicts.isEmpty()) {
			List<String> dictNames = new ArrayList<String>();
			for (DictDTO dbDict : dbDicts) {
				dictNames.add(dbDict.getName());
			}

			logger.debug("-- taskService device dict size:{} --", dbDicts.size());
			for (DictDTO dict : dicts) {
				// 如果设备上包含
				if (dictNames.contains(dict.getName())) {
					if (cutInfos.size() >= deviceDTO.getCardNum().intValue()) {
						break;
					}
					cutInfos.add(dict.getName());
					// 更新已完成的字典
					TaskDictDTO taskDictDTO = new TaskDictDTO();
					taskDictDTO.setTaskId(mainTaskId);
					taskDictDTO.setDictId(dict.getId());
					taskDictDTO.setUpdateUser("cutsys");
					taskDictMapper.updateTaskDictByTaskIdAndDictId(taskDictDTO);
					logger.debug("-- taskService update dict dictName:{} --", dict.getName());
				} else {
					logger.debug("-- taskService device donot have dict dictName:{} --", dict.getName());
				}
			}

			logger.debug("-- taskService update cutInfos size:{}, cardNum:{} --", cutInfos.size(), deviceDTO.getCardNum());
			if (!cutInfos.isEmpty()) {
				if (cutInfos.size() < deviceDTO.getCardNum().intValue()) {
					int size = cutInfos.size();
					int cardNum = deviceDTO.getCardNum().intValue();
					for (int i = size; i < cardNum; i++) {
						cutInfos.add("");
					}
				}
			}

		}
		return cutInfos;
	}

	/**
	 * 匹配是否要继续分配任务
	 * 
	 * @param deviceDTO
	 * @param runSubTasks
	 * @param detailTask
	 * @return
	 */
	private boolean checkAllotTask(DeviceDTO deviceDTO, List<SubTaskDTO> runSubTasks, TaskDTO detailTask) {
		boolean hasNodeTypeResource = false;
		int useNum = 0;
		List<TaskResourceDTO> resources = detailTask.getResources();
		logger.debug("-- checkAllotTask resource size:{} --", resources.size());
		if (null == resources || resources.isEmpty()) {
			logger.debug("-- checkAllotTask return false, resources is empty --");
			return hasNodeTypeResource;
		}
		// 根据资源类型判读
		for (TaskResourceDTO taskResourceDTO : resources) {
			int resourceNodeType = taskResourceDTO.getNodeType().intValue();
			if (deviceDTO.getNodeType().intValue() == resourceNodeType) {
				useNum = taskResourceDTO.getNodeNum().intValue();
				hasNodeTypeResource = true;
				break;
			}
		}

		logger.debug("-- checkAllotTask hasNodeTypeResource result:{} --", hasNodeTypeResource);
		if (hasNodeTypeResource) {
			if (null == runSubTasks || runSubTasks.isEmpty()) {
				logger.debug("-- checkAllotTask runSubTasks is Empty --");
				hasNodeTypeResource = true;
			} else {
				int nodeTypeTask = 0;
				for (SubTaskDTO runSubTask : runSubTasks) {
					int cardType = runSubTask.getCardType().intValue();
					if (deviceDTO.getNodeType().intValue() == cardType) {
						nodeTypeTask++;
					}
				}
				logger.debug("-- checkAllotTask useNum:{}, nodeTypeSize:{} --", useNum, nodeTypeTask);
				if (nodeTypeTask >= useNum) {
					hasNodeTypeResource = false;
				}
			}
		}

		logger.debug("-- checkAllotTask return result:{} --", hasNodeTypeResource);
		return hasNodeTypeResource;
	}

	private List<String> getCutInfo(TaskDesSliceDTO taskDesSliceDTO) {
		int jmtype = taskDesSliceDTO.getJmtype();
		int cardnum = taskDesSliceDTO.getCardnum();
		long speed = taskDesSliceDTO.getSpeed();
		int sunzinum = taskDesSliceDTO.getSunzinum();

		DesSliceLocationDTO sliceLoc = new DesSliceLocationDTO();
		sliceLoc.setVaild((byte) taskDesSliceDTO.getVaild().intValue());
		sliceLoc.setFinish((byte) taskDesSliceDTO.getFinish().intValue());
		sliceLoc.setDesLoc(taskDesSliceDTO.getDesLoc());
		sliceLoc.setOutput(desOutput);

		DesSliceLocationDTO result = jnaService.desSlice(jmtype, cardnum, speed, sunzinum, sliceLoc);
		logger.debug("-- jnaService desSlices result:[{}] --", result);
		List<String> cutInfos = null;
		if (null != result) {
			cutInfos = new ArrayList<String>();
			String[] cutFileNames = result.getCutFileName();
			for (int i = 0; i < cutFileNames.length; i++) {
				cutInfos.add(cutFileNames[i]);
			}
		}
		return cutInfos;
	}

	private List<String> getCutInfo(TaskMaskSliceDTO taskMaskSliceDTO) {
		int jmtype = taskMaskSliceDTO.getJmtype();
		int cardnum = taskMaskSliceDTO.getCardnum();
		long speed = taskMaskSliceDTO.getSpeed();
		int sunzinum = taskMaskSliceDTO.getSunzinum();
		MaskSliceLocationDTO sliceLoc = new MaskSliceLocationDTO();
		sliceLoc.setVaild((byte) taskMaskSliceDTO.getVaild().intValue());
		sliceLoc.setFinish((byte) taskMaskSliceDTO.getFinish().intValue());
		sliceLoc.setCurrentMask(taskMaskSliceDTO.getCurrentMask());
		sliceLoc.setFileLoc(taskMaskSliceDTO.getFileLoc());
		sliceLoc.setFilePath(taskMaskSliceDTO.getFilePath());
		sliceLoc.setOffsetLoc(taskMaskSliceDTO.getOffsetLoc());
		sliceLoc.setOutput(maskOutput);

		MaskSliceLocationDTO result = jnaService.maskSlice(jmtype, cardnum, speed, sunzinum, sliceLoc);
		logger.debug("-- jnaService maskSlice result:[{}] --", result);
		List<String> cutInfos = null;
		if (null != result) {
			cutInfos = new ArrayList<String>();
			// 切片信息传入
			String[] cutFileNames = result.getCutFileName();
			for (int i = 0; i < cutFileNames.length; i++) {
				cutInfos.add(cutFileNames[i]);
			}
		}

		return cutInfos;
	}

	/**
	 * 获取策略切片信息
	 * 
	 * @param deviceDTO
	 * @param strategyFileDTO
	 * @return
	 */
	private List<String> getCutInfo(long mainTaskId, long algId, DeviceDTO deviceDTO, TaskStrategyFileDTO strategyFileDTO) {
		AlgDTO algDTO = algMapper.getAlgByAlgIdAndNodeType(algId, deviceDTO.getNodeType());
		String mapKey = "mask_pwdslice_" + mainTaskId;
		int jmtype = deviceDTO.getNodeType();
		int cardnum = deviceDTO.getCardNum();
		long speed = algDTO.getSpeed();
		int sunzinum = algDTO.getCoreNum();

		MaskSliceLocationDTO sliceLoc = new MaskSliceLocationDTO();
		sliceLoc.setVaild((byte) strategyFileDTO.getVaild().intValue());
		sliceLoc.setFinish((byte) strategyFileDTO.getFinish().intValue());
		sliceLoc.setCurrentMask(strategyFileDTO.getCurrentMask());
		sliceLoc.setFileLoc(strategyFileDTO.getFileLoc());
		sliceLoc.setFilePath(strategyFileDTO.getFilePath());
		sliceLoc.setOffsetLoc(strategyFileDTO.getOffsetLoc());
		sliceLoc.setOutput(maskOutput);

		if (SliceStatusType.UNFINISH.getStatus() == strategyFileDTO.getPrepare().intValue()) {
			int prepareResult = jnaService.maskfileExpansion(strategyFileDTO.getFilePath());
			if (prepareResult == 0) {
				logger.debug("-- jnaService maskfileExpansion success --");
			} else {
				logger.debug("-- jnaService maskfileExpansion failed --");
			}
			strategyFileDTO.setPrepare(SliceStatusType.FINISH.getStatus());
			taskStrategyFileMapper.updateTaskStrategyFile(strategyFileDTO);
		}

		MaskSliceLocationDTO result = jnaService.maskSlice(mapKey, jmtype, cardnum, speed, sunzinum, sliceLoc);
		logger.debug("-- jnaService maskSlice result:[{}] --", result);
		List<String> cutInfos = null;
		if (null != result) {
			cutInfos = new ArrayList<String>();
			// 保存切片完的信息
			String[] cutFileNames = result.getCutFileName();

			TaskStrategyFileDTO fileDTO = CloneUtil.cloneBean(strategyFileDTO);
			fileDTO.setVaild((int) result.getVaild());
			fileDTO.setFinish((int) result.getFinish());
			fileDTO.setCurrentMask(result.getCurrentMask());
			fileDTO.setFileLoc(result.getFileLoc());
			fileDTO.setFilePath(result.getFilePath());
			fileDTO.setOffsetLoc(result.getOffsetLoc());
			fileDTO.setOutput(maskOutput);
			fileDTO.setCutFileName(StringUtils.join(cutFileNames, ","));
			taskStrategyFileMapper.updateTaskStrategyFile(fileDTO);

			// 切片信息传入
			for (int i = 0; i < cutFileNames.length; i++) {
				cutInfos.add(cutFileNames[i]);
			}
		}

		return cutInfos;
	}

	/**
	 * 获取vpndes切片信息
	 * 
	 * @param deviceDTO
	 * @param taskVpndesDTO
	 * @return
	 */
	private List<String> getCutInfo(long mainTaskId, long algId, DeviceDTO deviceDTO, TaskVpndesDTO taskVpndesDTO) {

		AlgDTO algDTO = algMapper.getAlgByAlgIdAndNodeType(algId, deviceDTO.getNodeType());
		String mapKey = "des_pwdslice_" + mainTaskId;
		int jmtype = deviceDTO.getNodeType();
		int cardnum = deviceDTO.getCardNum();
		long speed = algDTO.getSpeed();
		int sunzinum = algDTO.getCoreNum();

		DesSliceLocationDTO sliceLoc = new DesSliceLocationDTO();
		sliceLoc.setVaild((byte) taskVpndesDTO.getVaild().intValue());
		sliceLoc.setFinish((byte) taskVpndesDTO.getFinish().intValue());
		if (jmtype == 0) {
			sliceLoc.setDesLoc(SliceCommaEnum.GPU.getValue());
		} else {
			sliceLoc.setDesLoc(SliceCommaEnum.OTHER.getValue());
		}
		sliceLoc.setOutput(desOutput);

		DesSliceLocationDTO result = jnaService.desSlice(mapKey, jmtype, cardnum, speed, sunzinum, sliceLoc);
		logger.debug("-- jnaService desSlices result:[{}] --", result);
		List<String> cutInfos = null;
		if (null != result) {
			// 结果信息更新
			cutInfos = new ArrayList<String>();
			String[] cutFileNames = result.getCutFileName();

			TaskVpndesDTO vpndesDTO = CloneUtil.cloneBean(taskVpndesDTO);
			vpndesDTO.setVaild((int) result.getVaild());
			vpndesDTO.setFinish((int) result.getFinish());
			vpndesDTO.setDesLoc(result.getDesLoc());
			vpndesDTO.setOutput(maskOutput);
			vpndesDTO.setCutFileName(StringUtils.join(cutFileNames, ","));
			taskVpndesMapper.updateTaskVpndes(vpndesDTO);

			// 切片信息传入
			for (int i = 0; i < cutFileNames.length; i++) {
				cutInfos.add(cutFileNames[i]);
			}
		}
		return cutInfos;

	}

	@Override
	public boolean createSubTask(DeviceDTO deviceDTO, SubTaskDTO newSubTask, SubTaskDTO subTaskDTO) {
		boolean flag = false;
		try {
			// 构建参数信息
			Map<String, Object> taskParamsMap = new HashMap<String, Object>();
			taskParamsMap.put("Nodetype", deviceDTO.getNodeType());
			taskParamsMap.put("Taskid", newSubTask.getId() + "");
			taskParamsMap.put("Tasktype", newSubTask.getAlgId() + "");
			taskParamsMap.put("CrackInfo", newSubTask.getCrackInfo());
			taskParamsMap.put("CrackMode", newSubTask.getCrackMode());
			int cardNum = 0;
			List<String> cutInfos = newSubTask.getCutInfos();
			if (null != cutInfos && !cutInfos.isEmpty()) {
				for (String cutInfo : cutInfos) {
					if (StringUtils.isNotBlank(cutInfo)) {
						cardNum++;
					}
				}
			}
			taskParamsMap.put("Cardnum", cardNum);
			taskParamsMap.put("Cutinfonames", newSubTask.getCutInfos());
			// 判断主任务ID是否一致
			String url = "";
			if (null != subTaskDTO && newSubTask.getCrackMode().intValue() == subTaskDTO.getCrackMode().intValue() && newSubTask.getTaskId().longValue() == subTaskDTO.getTaskId().longValue()) {
				// 追加
				url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_ADD_INFO, deviceDTO.getIp(), deviceDTO.getPort());
			} else {
				// 创建
				url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_CREATE, deviceDTO.getIp(), deviceDTO.getPort());
			}
			// 下发任务
			String jsonBody = GsonUtil.GsonString(taskParamsMap);
			Map<String, Object> result = HttpUtil.doHttpPost(url, jsonBody, Map.class);
			logger.debug("-- remote create subTask result:{} --", GsonUtil.GsonString(result));
			if (null != result && WebUrlConstant.REQUEST_SUCCESS.equals(MapUtils.getString(result, "Result"))) {
				flag = true;
				logger.debug("-- remote create subTask success --");
			} else {
				logger.debug("-- remote create subTask error --");
			}
		} catch (Exception e) {
			logger.debug("-- remote create subTask error --", e);
			logger.debug("-- remote create subTask error --");
			flag = false;
		}
		return flag;
	}

	@Override
	public void updateTaskPwd(String passWord, Long id) {
		taskMapper.updateTaskPwd(passWord, id);
	}

	@Override
	public List<SubTaskDTO> findRunSubTasks(Long id) {
		return subTaskMapper.findRunSubTasks(id);
	}

	@Override
	@Async
	public void updateMainTaskStatus(TaskDTO taskDTO, SubTaskDTO subTaskDTO) {
		boolean dictFinish = true;
		boolean strategyFinish = true;
		boolean desFinish = true;
		Long taskId = taskDTO.getId();
		List<DictDTO> dicts = dictMapper.findNoCheckDictByTaskId(taskId);
		if (null != dicts && !dicts.isEmpty()) {
			dictFinish = false;
		}
		TaskStrategyFileDTO taskStrategyFileDTO = taskStrategyFileMapper.getTaskStrategyFileByTaskId(taskId);
		if (null != taskStrategyFileDTO && SliceStatusType.FAILED.getStatus() == taskStrategyFileDTO.getFinish().intValue()) {
			strategyFinish = false;
		}
		TaskVpndesDTO taskVpndesDTO = taskVpndesMapper.getTaskVpndesByTaskId(taskId);
		if (null != taskVpndesDTO && SliceStatusType.FAILED.getStatus() == taskVpndesDTO.getFinish().intValue()) {
			desFinish = false;
		}

		logger.debug("-- updateMainTaskStatus[dictFinish={},strategyFinish={},desFinish={}] --", dictFinish, strategyFinish, desFinish);
		boolean allCompelete = false;
		if (dictFinish && desFinish && strategyFinish) {
			boolean isAll = true;
			List<SubTaskDTO> subTasks = subTaskMapper.findSubTasks(taskId);
			for (SubTaskDTO subTask : subTasks) {
				if (TaskStatusType.FINISH.getStatus() != subTask.getStatus().intValue()) {
					isAll = false;
					break;
				}
			}
			allCompelete = isAll;
		}

		// 更新口令数,计算口令重新生成
		if (allCompelete) {
			taskDTO.setStatus(TaskStatusType.FINISH.getStatus());
			taskDTO.setFinishTime(new Date());
			taskDTO.setRemainTime(0l);
			this.updateTask(taskDTO);

			// 更新主任状态日志
			LoggerDTO loggerDTO = new LoggerDTO();
			loggerDTO.setUserName("system");
			loggerDTO.setCreateUser("system");
			loggerDTO.setActionMsg("任务完成");
			loggerDTO.setModeType("任务模块");
			loggerDTO.setSuccessMsg(taskDTO.getName() + " 任务完成");
			loggerDTO.setLevel(LoggerLevelType.INFO.getLevel());
			loggerDTO.setStatus(LoggerStatusType.SUCCESS.getStatus());
			hzLoggerService.createLogger(loggerDTO);

		} else {
			long remainTime = taskDTO.getRemainTime();
			long subTaskTime = subTaskDTO.getTimeCount();
			if (remainTime < subTaskTime) {
				taskDTO.setRemainTime(0l);
			} else {
				taskDTO.setRemainTime(remainTime - subTaskTime);
			}
			this.updateTask(taskDTO);
		}
	}

	private long getDictSubTaskTime(List<String> dicts, Long totalSpeed) {
		try {
			if (null != totalSpeed && totalSpeed.longValue() != 0l) {
				if (null != dicts && !dicts.isEmpty()) {
					long totalSize = 0l;
					for (String dict : dicts) {
						if (StringUtils.isNotBlank(dict)) {
							long size = DictHelper.getDictSize(dict);
							totalSize = totalSize + size;
						}
					}
					return totalSize / totalSpeed;
				}
			}
		} catch (Exception e) {
			logger.error("-- getDictSubTaskTime error --", e);
		}
		return 0;
	}

	private long getStrategySubTaskTime(List<String> strategys, Long totalSpeed) {
		try {
			if (null != totalSpeed && totalSpeed.longValue() != 0l) {
				if (null != strategys && !strategys.isEmpty()) {
					long timeCount = 0l;
					for (String strategy : strategys) {
						if (StringUtils.isNotBlank(strategy)) {
							String filePath = maskOutput + strategy;
							long time = commandService.getStrategyTimeCount(filePath, totalSpeed);
							timeCount = timeCount + time;
						}
					}
					return timeCount;
				}
			}
		} catch (Exception e) {
			logger.error("-- getStrategySubTaskTime error --", e);
		}
		return 0;
	}

	private long getVpndesSubTaskTime(TaskResourceDTO taskResourceDTO) {
		// 总时间
		try {
			if (null != taskResourceDTO) {
				return 1200l / taskResourceDTO.getNodeNum();
			}
		} catch (Exception e) {
			logger.error("-- getVpndesSubTaskTime error --", e);
		}
		return 0l;
	}

	@Override
	public boolean checkHashMode(Long hashMode) {
		List<AlgDTO> algs = algMapper.getAlgByAlgId(hashMode);
		if (null != algs && !algs.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findTaskStatus() {
		List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
		Map<String, Object> statusMap = null;
		for (TaskStatusType statuType : TaskStatusType.values()) {
			if (statuType.getStatus() <= 5) {
				statusMap = new HashMap<String, Object>();
				statusMap.put("statusId", statuType.getStatus());
				statusMap.put("statusName", statuType.getDesc());
				statusList.add(statusMap);
			}
		}
		return statusList;
	}

	@Override
	public List<Map<String, Object>> findNodeType() {
		List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();
		Map<String, Object> nodeMap = null;
		for (NodeType nodeType : NodeType.values()) {
			nodeMap = new HashMap<String, Object>();
			nodeMap.put("nodeId", nodeType.getType());
			nodeMap.put("nodeName", nodeType.getDesc());
			nodeList.add(nodeMap);
		}
		return nodeList;
	}

}
