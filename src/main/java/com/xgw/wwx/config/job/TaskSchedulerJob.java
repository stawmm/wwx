package com.xgw.wwx.config.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xgw.wwx.common.em.DeviceStatusType;
import com.xgw.wwx.common.em.LoggerLevelType;
import com.xgw.wwx.common.em.LoggerStatusType;
import com.xgw.wwx.common.em.ResultStatusType;
import com.xgw.wwx.common.em.TaskStatusType;
import com.xgw.wwx.common.em.TempStatusType;
import com.xgw.wwx.common.util.GsonUtil;
import com.xgw.wwx.config.task.DeviceInfoTask;
import com.xgw.wwx.config.task.IpTask;
import com.xgw.wwx.dto.common.DeviceTaskDTO;
import com.xgw.wwx.dto.common.IpResultDTO;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.db.LoggerDTO;
import com.xgw.wwx.dto.db.SubTaskDTO;
import com.xgw.wwx.dto.db.TaskDTO;
import com.xgw.wwx.service.DeviceService;
import com.xgw.wwx.service.HzLoggerService;
import com.xgw.wwx.service.TaskService;

/**
 * Created by EX-ZHONGJUN001 on 2018/4/4.
 */
public class TaskSchedulerJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerJob.class);

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HzLoggerService hzLoggerService;

	/**
	 * 20个固定线程异步获取节点信息
	 */
	private ExecutorService executor = Executors.newFixedThreadPool(20);

	/**
	 * ip检查线程池
	 */
	private ExecutorService ipExecutor = Executors.newCachedThreadPool();

	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// 获取设备列表
		List<DeviceDTO> devices = deviceService.findAllDevices();
		if (null != devices && !devices.isEmpty()) {
			try {
				// 检查IP
				Collection<Callable<IpResultDTO>> ipTasks = new LinkedList<Callable<IpResultDTO>>();
				for (int i = 0; i < devices.size(); i++) {
					DeviceDTO deviceDTO = devices.get(i);
					ipTasks.add(new IpTask(deviceDTO.getIp(), deviceDTO.getPort(), deviceDTO));
				}
				Collection<Future<IpResultDTO>> ipResults = ipExecutor.invokeAll(ipTasks, 5, TimeUnit.SECONDS);
				logger.debug("-- IpResultDTO ipResults size:{} --", ipResults.size());
				List<DeviceDTO> successDevices = new ArrayList<DeviceDTO>();
				for (Future<IpResultDTO> ipResult : ipResults) {
					DeviceDTO deviceDTO = ipResult.get().getDeviceDTO();
					if (ipResult.get().getResult()) {
						logger.debug("-- add success device[ip={},port={}]  --", deviceDTO.getIp(), deviceDTO.getPort());
						successDevices.add(deviceDTO);
					} else {
						processConnect(deviceDTO);
					}
				}

				// 温度检查
				logger.debug("-- success device result size:{} --", successDevices.size());
				Collection<Future<DeviceTaskDTO>> results = null;
				if (!successDevices.isEmpty()) {
					// 异步获取所有设备的状态信息
					Collection<Callable<DeviceTaskDTO>> tasks = new LinkedList<Callable<DeviceTaskDTO>>();
					for (int i = 0; i < successDevices.size(); i++) {
						DeviceDTO deviceDTO = successDevices.get(i);
						tasks.add(new DeviceInfoTask(deviceDTO));
					}
					results = executor.invokeAll(tasks, 10, TimeUnit.SECONDS);
				}

				// 处理设备
				if (null != results) {
					logger.debug("-- deviceTaskDTO result size:{} --", results.size());
				}
				if (null != results && !results.isEmpty()) {
					for (Future<DeviceTaskDTO> result : results) {
						DeviceTaskDTO deviceTaskDTO = result.get();
						logger.debug("-- deviceTaskDTO result:{} --", deviceTaskDTO);
						if (null != deviceTaskDTO) {
							// 设备信息
							DeviceDTO deviceDTO = deviceTaskDTO.getDeviceDTO();
							Long deviceId = deviceDTO.getId();
							logger.debug("-- device deviceId:{} --", deviceId);
							logger.debug("-- device[ip={},port={}] connect result:{} --", deviceDTO.getIp(), deviceDTO.getPort(), deviceTaskDTO.isResult());
							if (deviceTaskDTO.isResult()) {
								// 查看设备是否报警 (温度error)
								boolean isAlert = (deviceTaskDTO.getTemp() == TempStatusType.ERROR.getStatus());
								// 查看温度是否超出阈值
								boolean isThreshold = (deviceTaskDTO.getTemp() == TempStatusType.THRESHOLD.getStatus());
								logger.debug("-- device[ip={},port={}] temp[isAlert={},isThreshold={}] --", deviceDTO.getIp(), deviceDTO.getPort(), isAlert, isThreshold);
								if (isAlert) {
									// 设置设备不可用
									logger.debug("-- device[ip={},port={}] updateDeviceStatus: DISABLE --", deviceDTO.getIp(), deviceDTO.getPort());
									deviceService.updateDeviceStatus(deviceId, DeviceStatusType.DISABLE.getStatus());

									// 任务停止(远程stop,子任务状态改变)
									SubTaskDTO subTaskDTO = taskService.getDeviceSubTask(deviceId);
									if (null != subTaskDTO) {
										// 远程停止
										if (TaskStatusType.RUNNING.getStatus() == subTaskDTO.getStatus().intValue()) {
											logger.debug("-- device[ip={},port={}] stopSubTask --", deviceDTO.getIp(), deviceDTO.getPort());
											taskService.stopSubTask(deviceDTO, subTaskDTO.getId());
										}
										// 更新子任务状态
										logger.debug("-- device[ip={},port={}] updateSubTaskStatus:FAULT --", deviceDTO.getIp(), deviceDTO.getPort());
										subTaskDTO.setStatus(TaskStatusType.FAULT.getStatus());
										taskService.updateSubTask(subTaskDTO);
									}
									// 下轮循环
									continue;
								} else {
									// 报警次数设置为0
									if (null != deviceDTO.getFaultTimes() && deviceDTO.getFaultTimes().intValue() >= 5) {
										// 添加设备恢复日志
										logger.debug("-- device[ip={},port={}] create recovery --", deviceDTO.getIp(), deviceDTO.getPort());
										// 设备报警信息
										LoggerDTO loggerDTO = new LoggerDTO();
										loggerDTO.setUserName("system");
										loggerDTO.setCreateUser("system");
										loggerDTO.setActionMsg("系统设备恢复");
										loggerDTO.setModeType("设备告警");
										loggerDTO.setSuccessMsg(String.format("设备恢复(%s)", deviceDTO.getIp()));
										loggerDTO.setLevel(LoggerLevelType.INFO.getLevel());
										loggerDTO.setStatus(LoggerStatusType.SUCCESS.getStatus());
										hzLoggerService.createLogger(loggerDTO);
									}
									logger.debug("-- device[ip={},port={}] set fault times: 0 --", deviceDTO.getIp(), deviceDTO.getPort());
									deviceService.updateDeviceErrorTimes(deviceId, 0);

									logger.debug(" ********************* ");
									// 设置可用
									if (null != deviceDTO.getStatus() && deviceDTO.getStatus().intValue() == DeviceStatusType.DISABLE.getStatus()) {
										logger.debug("-- device[ip={},port={}] updateDeviceStatus: ENABLE --", deviceDTO.getIp(), deviceDTO.getPort());
										deviceService.updateDeviceStatus(deviceId, DeviceStatusType.ENABLE.getStatus());
										// 下轮循环
										continue;
									}
								}

								// 设备是不是有子任务
								SubTaskDTO subTaskDTO = taskService.getDeviceSubTask(deviceId);
								logger.debug("-- device[ip={},port={}] hasSubTask={} --", deviceDTO.getIp(), deviceDTO.getPort(), (null != subTaskDTO));
								int status = ResultStatusType.RUNNING.getStatus();
								if (null != subTaskDTO) {
									// 查看远程子任务的状态
									status = taskService.getSubTaskStatus(deviceDTO, subTaskDTO);
									logger.debug("-- device[ip={},port={}] getSubTaskStatus={} --", deviceDTO.getIp(), deviceDTO.getPort(), status);
									// 更新子任务状态
									if (status == ResultStatusType.FAILED.getStatus()) {
										// 获取主任务
										TaskDTO taskDTO = taskService.getTaskById(subTaskDTO.getTaskId());
										// 子任务故障
										subTaskDTO.setStatus(TaskStatusType.FAULT.getStatus());
										taskService.updateSubTask(subTaskDTO);
										logger.debug("-- device alert updateSubTask end --");
										// 设备释放
										deviceDTO.setTaskId(null);
										deviceService.updateDevice(deviceDTO);

										// 更新主任状态日志
										LoggerDTO loggerDTO = new LoggerDTO();
										loggerDTO.setUserName("system");
										loggerDTO.setCreateUser("system");
										loggerDTO.setActionMsg("任务获取状态失败");
										loggerDTO.setModeType("任务模块");
										loggerDTO.setSuccessMsg(taskDTO.getName() + " 任务获取状态失败");
										loggerDTO.setLevel(LoggerLevelType.ALERT.getLevel());
										loggerDTO.setStatus(LoggerStatusType.SUCCESS.getStatus());
										hzLoggerService.createLogger(loggerDTO);

										continue;
									}

									/*
									 * 11，表示切片跑完只破解出key1, 12，表示切片跑完只破解出key2。 如果切片跑完key1和key2都破解出，则状态为2（hit）
									 */

									// 命中key11或者key12
									boolean hitflag = false;
									if (status == ResultStatusType.HIT_KEY11.getStatus() || status == ResultStatusType.HIT_KEY12.getStatus()) {
										// 获取命中密码
										List<String> pwds = taskService.hitResultList(deviceDTO, subTaskDTO.getId());
										logger.debug("-- hitResultList pwds size:{} --", pwds.size());
										logger.debug("-- hitResultList pwds pwds:{} --", pwds);
										// 获取主任务
										TaskDTO taskDTO = taskService.getTaskById(subTaskDTO.getTaskId());
										if (StringUtils.isNotBlank(taskDTO.getHitPwd())) {
											List<String> hitpwds = GsonUtil.GsonToBean(taskDTO.getHitPwd(), List.class);
											logger.debug("-- hitResultList db hitpwds:{} --", hitpwds);
											// key11命中
											if (status == ResultStatusType.HIT_KEY11.getStatus()) {
												// 判断key12是否命中
												if (StringUtils.isNotBlank(hitpwds.get(1))) {
													hitflag = true;
												}
												hitpwds.set(0, pwds.get(0));
												hitpwds.set(2, pwds.get(2));
											} else {
												// 判断key11是否命中
												if (StringUtils.isNotBlank(hitpwds.get(0))) {
													hitflag = true;
												}
												hitpwds.set(1, pwds.get(1));
												hitpwds.set(2, pwds.get(2));
											}
											//
											logger.debug("-- hitResult hitflag:{}, hitpwds:{} --", hitflag, hitpwds);

											// key11或key12命中,更是数据库
											taskDTO.setHitPwd(GsonUtil.GsonString(hitpwds));
											taskService.updateTask(taskDTO);
										} else {
											// 没有值, 第一次命中
											taskDTO.setHitPwd(GsonUtil.GsonString(pwds));
											taskService.updateTask(taskDTO);
										}
										// 任务状态不需要改变, 继续运行中
										if (hitflag) {
											// 命中
											status = ResultStatusType.HIT.getStatus();
										} else {
											// 继续运行
											status = ResultStatusType.FINISH.getStatus();
										}
									}
									// 命中
									if (status == ResultStatusType.HIT.getStatus()) {
										logger.debug("-- device[ip={},port={}] updateSubTaskStatus: HIT --", deviceDTO.getIp(), deviceDTO.getPort());
										subTaskDTO.setStatus(TaskStatusType.HIT.getStatus());
										taskService.updateSubTask(subTaskDTO);
									} else if (status == ResultStatusType.FINISH.getStatus()) {

										logger.debug("-- device[ip={},port={}] updateSubTaskStatus: FINISH --", deviceDTO.getIp(), deviceDTO.getPort());
										subTaskDTO.setStatus(TaskStatusType.FINISH.getStatus());
										taskService.updateSubTask(subTaskDTO);
									}

									// 子任务完成时(命中或者运行)
									if (status == ResultStatusType.HIT.getStatus() || status == ResultStatusType.FINISH.getStatus()) {

										// 子任务解绑
										logger.debug("-- device[ip={},port={}] delete device subTask relation --", deviceDTO.getIp(), deviceDTO.getPort());
										deviceDTO.setTaskId(null);
										deviceService.updateDevice(deviceDTO);

										// 子任务如果是命中
										if (status == ResultStatusType.HIT.getStatus()) {
											// 主任务状态更新命中
											logger.debug("-- device[ip={},port={}] update mainTask: HIT --", deviceDTO.getIp(), deviceDTO.getPort());
											TaskDTO taskDTO = taskService.getTaskById(subTaskDTO.getTaskId());
											if (null != taskDTO) {
												taskDTO.setStatus(TaskStatusType.HIT.getStatus());
												taskDTO.setFinishTime(new Date());
												taskDTO.setRemainTime(0l);
												taskService.updateTask(taskDTO);

												// 更新主任状态日志
												LoggerDTO loggerDTO = new LoggerDTO();
												loggerDTO.setUserName("system");
												loggerDTO.setCreateUser("system");
												loggerDTO.setActionMsg("任务命中");
												loggerDTO.setModeType("任务模块");
												loggerDTO.setSuccessMsg(taskDTO.getName() + " 任务命中");
												loggerDTO.setLevel(LoggerLevelType.INFO.getLevel());
												loggerDTO.setStatus(LoggerStatusType.SUCCESS.getStatus());
												hzLoggerService.createLogger(loggerDTO);

											}

											// 主任ID找到运行中的其他子任务
											logger.debug("-- device[ip={},port={}] mainTask other subTask --", deviceDTO.getIp(), deviceDTO.getPort());
											List<SubTaskDTO> otherSubTasks = taskService.findRunSubTasks(subTaskDTO.getTaskId());
											if (null != otherSubTasks && !otherSubTasks.isEmpty()) {
												for (SubTaskDTO subTask : otherSubTasks) {
													// 远程停止任务
													taskService.stopSubTask(deviceDTO, subTask.getId());
													// 其他子任务状态更新完完成
													subTask.setStatus(TaskStatusType.FINISH.getStatus());
													taskService.updateSubTask(subTask);
													// 找到子任务对应设备，解绑关系
													DeviceDTO subDevice = deviceService.getDeviceByTaskId(subTask.getId());
													subDevice.setTaskId(null);
													deviceService.updateDevice(subDevice);
												}
											}

											// 保存破解信息
											if (!hitflag) {
												String passWord = taskService.hitSubTaskResult(deviceDTO, subTaskDTO.getId());
												logger.debug("-- device[ip={},port={}] hitSubTaskResult:{} --", deviceDTO.getIp(), deviceDTO.getPort(), passWord);
												if (StringUtils.isNotBlank(passWord)) {
													taskService.updateTaskPwd(passWord, subTaskDTO.getTaskId());
												}
											}

										} else {
											// 子任务完成,更新主任务
											TaskDTO taskDTO = taskService.getTaskById(subTaskDTO.getTaskId());
											if (null != taskDTO) {
												taskService.updateMainTaskStatus(taskDTO, subTaskDTO);
											}
										}

									}
								} else {
									status = ResultStatusType.IDLE.getStatus();
								}

								// 子任务完成或名中 ，无任务，分配子任务
								if (status == ResultStatusType.IDLE.getStatus() || status == ResultStatusType.HIT.getStatus() || status == ResultStatusType.FINISH.getStatus()) {
									// 判断温度是不是超过阈值
									if (isThreshold) {
										// 不在分配任务
										logger.debug("-- device[ip={},port={}] isThreshold:{} --", deviceDTO.getIp(), deviceDTO.getPort(), isThreshold);
										continue;
									}
									// 获取子任务
									logger.debug("-- device[ip={},port={}] getsubTask --", deviceDTO.getIp(), deviceDTO.getPort());
									SubTaskDTO newSubTaskDTO = taskService.getSubTask(deviceDTO);
									if (null != newSubTaskDTO) {
										// 下发远程子任务
										logger.debug("-- device[ip={},port={}] createSubTask --", deviceDTO.getIp(), deviceDTO.getPort());
										boolean success = taskService.createSubTask(deviceDTO, newSubTaskDTO, subTaskDTO);
										if (success) {
											// 更新子任务
											newSubTaskDTO.setDeviceId(deviceId);
											newSubTaskDTO.setStatus(TaskStatusType.RUNNING.getStatus());
											taskService.updateSubTask(newSubTaskDTO);

											// 绑定设备关系
											logger.debug("-- device[ip={},port={}] create device subTask:{} relatsion --", deviceDTO.getIp(), deviceDTO.getPort(), newSubTaskDTO.getId());
											deviceDTO.setTaskId(newSubTaskDTO.getId());
											deviceService.updateDevice(deviceDTO);

											// 更新主任务状态运行中
											TaskDTO taskDTO = taskService.getTaskById(newSubTaskDTO.getTaskId());
											if (null != taskDTO) {
												taskDTO.setStatus(TaskStatusType.RUNNING.getStatus());
												if (null == taskDTO.getSubmitTime()) {
													// 设置主任务提交时间
													taskDTO.setSubmitTime(new Date());
												}
												taskService.updateTask(taskDTO);
											}
										} else {
											logger.debug("-- device[ip={},port={}] createSubTask error ---");
											newSubTaskDTO.setDeviceId(deviceId);
											newSubTaskDTO.setStatus(TaskStatusType.FAULT.getStatus());
											taskService.updateSubTask(newSubTaskDTO);
										}
									}
								}
							} else {
								processConnect(deviceDTO);
							}
						} else {
							logger.debug("-- device task result empty --");
						}
					}
				}
			} catch (Exception e) {
				logger.error("-- executor invokeAll error --", e);
			}
		}
	}

	private void processConnect(DeviceDTO deviceDTO) {
		Long deviceId = deviceDTO.getId();
		logger.debug("-- ip check error[ip={},port={}]  --", deviceDTO.getIp(), deviceDTO.getPort());
		Integer errorTimes = deviceDTO.getFaultTimes();
		errorTimes = null != errorTimes ? errorTimes : 0;
		logger.debug("-- device[ip={},port={}] error times:{} --", deviceDTO.getIp(), deviceDTO.getPort(), errorTimes);

		// 如果errorTimes==5告警
		if (errorTimes.intValue() == 5) {
			logger.debug("-- device[ip={},port={}] create alert --", deviceDTO.getIp(), deviceDTO.getPort());
			// 设备报警信息
			LoggerDTO loggerDTO = new LoggerDTO();
			loggerDTO.setUserName("system");
			loggerDTO.setCreateUser("system");
			loggerDTO.setActionMsg("系统设备告警");
			loggerDTO.setModeType("设备告警");
			loggerDTO.setErrorCode("DEVICE_CONNECT_ERROR");
			loggerDTO.setErrorMsg(String.format("设备故障(%s)", deviceDTO.getIp()));
			loggerDTO.setLevel(LoggerLevelType.ALERT.getLevel());
			loggerDTO.setStatus(LoggerStatusType.FAILED.getStatus());
			hzLoggerService.createLogger(loggerDTO);
		}

		if (errorTimes.intValue() >= 5) {
			// 设备标记为故障
			logger.debug("-- device alert deviceId:{} --", deviceId);
			deviceService.updateDeviceStatus(deviceId, DeviceStatusType.DISABLE.getStatus());

			// 如果有任务, 标记为失败
			SubTaskDTO subTaskDTO = taskService.getDeviceSubTask(deviceId);
			logger.debug("-- device alert subTaskDTO:{} --", subTaskDTO);
			if (null != subTaskDTO) {
				// 更新子任务状态
				logger.debug("-- device alert updateSubTask start, status:{} --", subTaskDTO.getStatus());
				subTaskDTO.setStatus(4);
				taskService.updateSubTask(subTaskDTO);
				logger.debug("-- device alert updateSubTask end --");

				// 解绑关系
				deviceDTO.setTaskId(null);
				deviceService.updateDevice(deviceDTO);
			}
		}

		// 更新错误次数
		errorTimes = errorTimes.intValue() + 1;
		if (errorTimes.intValue() > 5) {
			errorTimes = 6;
		}
		deviceService.updateDeviceErrorTimes(deviceId, errorTimes);

	}

}
