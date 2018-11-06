package com.xgw.wwx.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.helper.AuthHelper;
import com.xgw.wwx.common.util.FileUtil;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.AlgDTO;
import com.xgw.wwx.dto.db.CarryDTO;
import com.xgw.wwx.dto.db.TaskDTO;
import com.xgw.wwx.dto.db.TaskFileDTO;
import com.xgw.wwx.dto.db.TaskResourceDTO;
import com.xgw.wwx.dto.db.TaskTimeCountDTO;
import com.xgw.wwx.service.HzLoggerService;
import com.xgw.wwx.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@Value("${wwx.carry.task.dir}")
	private String fileCarryOnPath;

	@Autowired
	HzLoggerService hzLoggerService;

	@GetMapping("/find/{id}")
	public ResponseEntity<CommonResponseDTO<TaskDTO>> getTaskById(@PathVariable("id") Long id) {
		try {
			logger.info("-- info message, id={} --", id);
			TaskDTO taskDTO = taskService.getTaskById(id);
			return ResponseEntity.ok(new CommonResponseDTO<TaskDTO>(taskDTO));
		} catch (WxxRuntimeException e) {
			logger.error("-- getTaskById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<TaskDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- getTaskById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<TaskDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/find/list")
	public ResponseEntity<CommonResponseDTO<PageInfo<TaskDTO>>> findTasks(HttpServletRequest request, @RequestBody TaskDTO taskDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (taskDTO.getAlgId().longValue() != -1l) {
				params.put("algId", taskDTO.getAlgId());
			}
			if (taskDTO.getNodeId().longValue() != -1l) {
				params.put("nodeId", taskDTO.getNodeId());
			}
			if (taskDTO.getStatusId().longValue() != -1l) {
				params.put("statusId", taskDTO.getStatusId());
			}
			if (null != taskDTO.getStartDate()) {
				params.put("startDate", taskDTO.getStartDate());
			}
			if (null != taskDTO.getEndDate()) {
				params.put("endDate", taskDTO.getEndDate());
			}
			params.put("searchWord", taskDTO.getSearchWord());
			params.put("createUser", userName);
			PageInfo<TaskDTO> pageList = taskService.findTasksByPage(taskDTO.getPageSize(), taskDTO.getPageNum(), params);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<TaskDTO>>(pageList));
		} catch (WxxRuntimeException e) {
			logger.error("-- findTasks Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<TaskDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findTasks Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<TaskDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/history/list")
	public ResponseEntity<CommonResponseDTO<PageInfo<TaskDTO>>> findHistoryTasks(HttpServletRequest request, @RequestBody TaskDTO taskDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (taskDTO.getAlgId().longValue() != -1l) {
				params.put("algId", taskDTO.getAlgId());
			}
			if (taskDTO.getNodeId().longValue() != -1l) {
				params.put("nodeId", taskDTO.getNodeId());
			}
			if (taskDTO.getStatusId().longValue() != -1l) {
				params.put("statusId", taskDTO.getStatusId());
			}
			if (null != taskDTO.getStartDate()) {
				params.put("startDate", taskDTO.getStartDate());
			}
			if (null != taskDTO.getEndDate()) {
				params.put("endDate", taskDTO.getEndDate());
			}
			params.put("createUser", userName);
			params.put("searchWord", taskDTO.getSearchWord());
			PageInfo<TaskDTO> pageList = taskService.findHistoryTasksByPage(taskDTO.getPageSize(), taskDTO.getPageNum(), params);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<TaskDTO>>(pageList));
		} catch (WxxRuntimeException e) {
			logger.error("-- findHistoryTasks Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<TaskDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findHistoryTasks Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<TaskDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/resource/list")
	public ResponseEntity<CommonResponseDTO<List<TaskResourceDTO>>> findTaskResourceList() {
		try {
			List<TaskResourceDTO> resourceList = taskService.findTaskResourceList();
			return ResponseEntity.ok(new CommonResponseDTO<List<TaskResourceDTO>>(resourceList));
		} catch (WxxRuntimeException e) {
			logger.error("-- findTaskResourceList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<TaskResourceDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findTaskResourceList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<TaskResourceDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<CommonResponseDTO<Boolean>> updateTask(@RequestBody TaskDTO taskDTO) {
		try {
			taskService.updateTask(taskDTO);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- updateTask Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- updateTask Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/history/delete/{id}")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteHistoryTask(@PathVariable("id") Long id) {
		try {
			taskService.deleteHistoryTask(id);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteHistoryTask Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deleteHistoryTask Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/create")
	public ResponseEntity<CommonResponseDTO<Boolean>> createTask(HttpServletRequest request, @RequestBody TaskDTO taskDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			List<TaskFileDTO> files = taskDTO.getFiles();
			if (null == files || files.isEmpty()) {
				throw new WxxRuntimeException("TASK_FILE_CARRY_EMPTY", "任务破解文件列表为空");
			}
			// 批量创建任务
			for (TaskFileDTO file : files) {
				taskDTO.setFile(file);
				taskDTO.setCreateUser(userName);
				taskService.createTask(taskDTO);
				// 添加日志
				hzLoggerService.createSuccessLogger(userName, "创建任务", "任务模块", "用户“" + userName + "”创建任务“" + taskDTO.getName() + "”成功！");
			}
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- createTask Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "创建任务", "任务模块", e.getCode(), "用户“" + userName + "”创建任务“" + taskDTO.getName() + "”失败！" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- createTask Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "创建任务", "任务模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”创建任务“" + taskDTO.getName() + "”失败！" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/batch/stop")
	public ResponseEntity<CommonResponseDTO<Boolean>> stopTask(HttpServletRequest request, @RequestBody List<TaskDTO> taskDTOs) {
		String userName = AuthHelper.getUserName(request);
		try {
			if (null != taskDTOs && !taskDTOs.isEmpty()) {
				for (TaskDTO taskDTO : taskDTOs) {
					taskService.stopTask(taskDTO.getId());
				}
			}
			hzLoggerService.createSuccessLogger(userName, "停止任务", "任务模块", "用户“" + userName + "”批量停止任务成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- stopTask Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "停止任务", "任务模块", e.getCode(), "用户“" + userName + "”批量停止任务失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- stopTask Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "停止任务", "任务模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”批量停止任务失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/batch/run")
	public ResponseEntity<CommonResponseDTO<Boolean>> runTask(@RequestBody List<TaskDTO> taskDTOs) {
		try {
			if (null != taskDTOs && !taskDTOs.isEmpty()) {
				for (TaskDTO taskDTO : taskDTOs) {
					taskService.runTask(taskDTO.getId());
				}
			}
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- runTask Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- runTask Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/batch/delete")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteTask(HttpServletRequest request, @RequestBody List<TaskDTO> taskDTOs) {
		String userName = AuthHelper.getUserName(request);
		String deleteMsg = "";
		try {
			if (null != taskDTOs && !taskDTOs.isEmpty()) {
				deleteMsg = taskDTOs.size() > 1 ? "批量删除任务" : "" +
						"" +
						"删除任务";
				for (TaskDTO taskDTO : taskDTOs) {
					taskService.deleteTask(taskDTO.getId());
				}
			}
			hzLoggerService.createSuccessLogger(userName, "删除任务", "任务模块", "用户“" + userName + "”" + deleteMsg + "成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteTask Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除任务", "任务模块", e.getCode(), "用户“" + userName + "”" + deleteMsg + "失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deleteTask Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除任务", "任务模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”" + deleteMsg + "失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping(value = "/carryOn")
	public ResponseEntity<CommonResponseDTO<List<CarryDTO>>> fileCarryOn(@RequestParam("files") MultipartFile[] files, @RequestParam(value = "algId", required = false) Long algId, HttpServletRequest request) {
		List<CarryDTO> carryDTOS = null;
		try {
			if (null == files || files.length == 0) {
				throw new WxxRuntimeException("FILE_CARRY_EMPTY", "上传破解文件为空");
			}
			// 遍历、破解文件
			carryDTOS = new ArrayList<CarryDTO>();
			Set<Long> hashModelSet = new HashSet<Long>();
			for (MultipartFile file : files) {
				String carryPath = fileCarryOnPath + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
				File tempFile = new File(carryPath);
				FileUtil.createFile(tempFile);
				file.transferTo(tempFile);

				CarryDTO carryDTO = taskService.taskCarryOn(carryPath, algId);
				logger.info("-- fileCarryOn message carryDTO=[{}] --", carryDTO);
				if (!carryDTO.isResult()) {
					throw new WxxRuntimeException("FILE_CARRY_ERROR", "提串结果失败");
				} else {
					Long hashMode = carryDTO.getHashModel();
					if (null == hashMode || !taskService.checkHashMode(hashMode)) {
						throw new WxxRuntimeException("ALG_ID_IS_EMPTY", "算法不支持: " + hashMode);
					}
				}
				carryDTO.setFileName(file.getOriginalFilename());
				carryDTO.setCarryPath(carryPath);
				// 天加到set
				hashModelSet.add(carryDTO.getHashModel());
				// 添加到list
				carryDTOS.add(carryDTO);
			}
			// 算法类型不一致
			if (hashModelSet.size() != 1) {
				throw new WxxRuntimeException("FILE_ALG_ID_ERROR", "破解文件算法ID不一致");
			}
			return ResponseEntity.ok(new CommonResponseDTO<List<CarryDTO>>(carryDTOS));
		} catch (WxxRuntimeException e) {
			logger.error("-- fileCarryOn Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<CarryDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- fileCarryOn Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<CarryDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping(value = "/strategyTimeCount")
	public ResponseEntity<CommonResponseDTO<Long>> strategyTimeCount(@RequestBody TaskTimeCountDTO taskTimeCountDTO) {
		try {
			logger.info("---------: {}", taskTimeCountDTO.getSpeed());
			Long count = taskService.strategyTimeCount(taskTimeCountDTO);
			return ResponseEntity.ok(new CommonResponseDTO<Long>(count));
		} catch (WxxRuntimeException e) {
			logger.error("-- strategyTimeCount Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Long>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- strategyTimeCount Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Long>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping(value = "/vpndesTimeCount")
	public ResponseEntity<CommonResponseDTO<Long>> vpndesTimeCount(@RequestBody TaskTimeCountDTO taskTimeCountDTO) {
		try {
			Long count = taskService.vpndesTimeCount(taskTimeCountDTO);
			return ResponseEntity.ok(new CommonResponseDTO<Long>(count));
		} catch (WxxRuntimeException e) {
			logger.error("-- vpndesTimeCount Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Long>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- vpndesTimeCount Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Long>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping(value = "/alg/list")
	public ResponseEntity<CommonResponseDTO<List<AlgDTO>>> getAlgList() {
		try {
			List<AlgDTO> algs = taskService.getAlgList();
			return ResponseEntity.ok(new CommonResponseDTO<List<AlgDTO>>(algs));
		} catch (WxxRuntimeException e) {
			logger.error("-- getAlgList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<AlgDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- getAlgList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<AlgDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping(value = "/algId/list")
	public ResponseEntity<CommonResponseDTO<List<AlgDTO>>> findAlgsByAlgId(@RequestParam("algId") Long algId) {
		try {
			List<AlgDTO> algs = taskService.getAlgListByAlgId(algId);
			return ResponseEntity.ok(new CommonResponseDTO<List<AlgDTO>>(algs));
		} catch (WxxRuntimeException e) {
			logger.error("-- getAlgList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<AlgDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- getAlgList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<AlgDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping(value = "/status/list")
	public ResponseEntity<CommonResponseDTO<List<Map<String, Object>>>> findTaskStatus() {
		try {
			List<Map<String, Object>> statusList = taskService.findTaskStatus();
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(statusList));
		} catch (WxxRuntimeException e) {
			logger.error("-- findTaskStatus Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findTaskStatus Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping(value = "/node/list")
	public ResponseEntity<CommonResponseDTO<List<Map<String, Object>>>> findNodeType() {
		try {
			List<Map<String, Object>> nodeList = taskService.findNodeType();
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(nodeList));
		} catch (WxxRuntimeException e) {
			logger.error("-- findNodeType Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findNodeType Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
