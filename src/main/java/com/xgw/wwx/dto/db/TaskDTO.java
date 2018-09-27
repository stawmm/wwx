package com.xgw.wwx.dto.db;

import java.util.Date;
import java.util.List;

public class TaskDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 任务名称
	 */
	private String name;

	/**
	 * 任务描述
	 */
	private String desc;

	/**
	 * 任务类型
	 */
	private Integer type;

	/**
	 * 剩余时间
	 * 
	 */
	private Long remainTime;

	/**
	 * 预估时间
	 */
	private Long predictTime;

	/**
	 * 是否选择穷举
	 */
	private Boolean hasVpndes;

	/**
	 * 命中密码
	 */
	private String hitPwd;

	/**
	 * 口令总量
	 */
	private Long command;

	/**
	 * 字典口令总数
	 */
	private Long dictCommand;

	/**
	 * 字典运行的口令总数
	 */
	private Long dictRunCommand;

	/**
	 * 策略口令总数
	 */
	private Long strategyCommand;

	/**
	 * 策略运行的口令总数
	 */
	private Long strategyRunCommand;

	/**
	 * 子任务总量
	 * 
	 */
	private Integer totalCount;

	/**
	 * 运行任务
	 */
	private Integer runCount;

	/**
	 * 剩余任务
	 */
	private Integer remainCount;

	/**
	 * 提交时间
	 */
	private Date submitTime;

	/**
	 * 提交时间
	 */
	private Date finishTime;

	/**
	 * 0未同步1已同步
	 */
	private Integer hisAsync;

	/**
	 * 0排队1完成2命中3运行中
	 */
	private Integer status;

	private Long algId;

	private Integer statusId;

	private Integer nodeId;

	private Date startDate;

	private Date endDate;

	private TaskFileDTO file;

	private List<TaskFileDTO> files;

	private List<TaskResourceDTO> resources;

	private List<DictDTO> dicts;

	private List<TaskDictDTO> taskDicts;

	private List<StrategyDTO> strategys;

	private List<TaskStrategyDTO> taskStrategys;

	private List<DeviceDTO> devices;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getHisAsync() {
		return hisAsync;
	}

	public void setHisAsync(Integer hisAsync) {
		this.hisAsync = hisAsync;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getRunCount() {
		return runCount;
	}

	public void setRunCount(Integer runCount) {
		this.runCount = runCount;
	}

	public Integer getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Long getDictCommand() {
		return dictCommand;
	}

	public void setDictCommand(Long dictCommand) {
		this.dictCommand = dictCommand;
	}

	public Long getDictRunCommand() {
		return dictRunCommand;
	}

	public void setDictRunCommand(Long dictRunCommand) {
		this.dictRunCommand = dictRunCommand;
	}

	public Long getStrategyCommand() {
		return strategyCommand;
	}

	public void setStrategyCommand(Long strategyCommand) {
		this.strategyCommand = strategyCommand;
	}

	public Long getStrategyRunCommand() {
		return strategyRunCommand;
	}

	public void setStrategyRunCommand(Long strategyRunCommand) {
		this.strategyRunCommand = strategyRunCommand;
	}

	public TaskFileDTO getFile() {
		return file;
	}

	public void setFile(TaskFileDTO file) {
		this.file = file;
	}

	public List<TaskResourceDTO> getResources() {
		return resources;
	}

	public void setResources(List<TaskResourceDTO> resources) {
		this.resources = resources;
	}

	public List<DictDTO> getDicts() {
		return dicts;
	}

	public void setDicts(List<DictDTO> dicts) {
		this.dicts = dicts;
	}

	public List<TaskDictDTO> getTaskDicts() {
		return taskDicts;
	}

	public void setTaskDicts(List<TaskDictDTO> taskDicts) {
		this.taskDicts = taskDicts;
	}

	public List<StrategyDTO> getStrategys() {
		return strategys;
	}

	public void setStrategys(List<StrategyDTO> strategys) {
		this.strategys = strategys;
	}

	public List<TaskStrategyDTO> getTaskStrategys() {
		return taskStrategys;
	}

	public void setTaskStrategys(List<TaskStrategyDTO> taskStrategys) {
		this.taskStrategys = taskStrategys;
	}

	public Long getCommand() {
		return command;
	}

	public void setCommand(Long command) {
		this.command = command;
	}

	public Long getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(Long remainTime) {
		this.remainTime = remainTime;
	}

	public Long getPredictTime() {
		return predictTime;
	}

	public void setPredictTime(Long predictTime) {
		this.predictTime = predictTime;
	}

	public Boolean getHasVpndes() {
		return hasVpndes;
	}

	public void setHasVpndes(Boolean hasVpndes) {
		this.hasVpndes = hasVpndes;
	}

	public String getHitPwd() {
		return hitPwd;
	}

	public void setHitPwd(String hitPwd) {
		this.hitPwd = hitPwd;
	}

	public List<DeviceDTO> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceDTO> devices) {
		this.devices = devices;
	}

	public List<TaskFileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<TaskFileDTO> files) {
		this.files = files;
	}

	public Long getAlgId() {
		return algId;
	}

	public void setAlgId(Long algId) {
		this.algId = algId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
