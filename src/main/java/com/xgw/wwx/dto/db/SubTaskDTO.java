package com.xgw.wwx.dto.db;

import java.util.List;

public class SubTaskDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 任务Id
	 */
	private Long taskId;

	/**
	 * 设备ID
	 */
	private Long deviceId;

	/**
	 * 板卡类型
	 */
	private Integer cardType;

	/**
	 * 算法类型
	 */
	private Long algId;

	/**
	 * 特征串
	 */
	private String crackInfo;

	/**
	 * 破解模式
	 */
	private Integer crackMode;

	/**
	 * 选用卡数
	 */
	private Integer cardNum;

	/**
	 * 提串切片文件
	 */
	private String cutInfoNames;
	
	/**
	 * 子任务预估时间
	 * 
	 */
	private Long timeCount;

	/**
	 * 提串切片文件
	 */
	private List<String> cutInfos;

	private Integer status;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Long getAlgId() {
		return algId;
	}

	public void setAlgId(Long algId) {
		this.algId = algId;
	}

	public String getCrackInfo() {
		return crackInfo;
	}

	public void setCrackInfo(String crackInfo) {
		this.crackInfo = crackInfo;
	}

	public Integer getCrackMode() {
		return crackMode;
	}

	public void setCrackMode(Integer crackMode) {
		this.crackMode = crackMode;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public String getCutInfoNames() {
		return cutInfoNames;
	}

	public void setCutInfoNames(String cutInfoNames) {
		this.cutInfoNames = cutInfoNames;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<String> getCutInfos() {
		return cutInfos;
	}

	public void setCutInfos(List<String> cutInfos) {
		this.cutInfos = cutInfos;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getTimeCount() {
		return timeCount;
	}

	public void setTimeCount(Long timeCount) {
		this.timeCount = timeCount;
	}
	
}
