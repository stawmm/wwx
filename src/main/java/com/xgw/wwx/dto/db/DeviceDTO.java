package com.xgw.wwx.dto.db;

import java.util.List;

public class DeviceDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;

	private String mac;

	private int port;

	private String name;

	private String desc;

	private Integer nodeType;

	private Integer cardNum;

	private Integer algNum;

	private Integer dictNum;

	private Integer status;

	private Integer roleType;

	private Integer faultTimes;

	private Long taskId;

	private List<CardDTO> cardList;

	private List<DictDTO> dictList;

	private List<AlgDTO> algList;

	private List<Long> dictIds;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Long> getDictIds() {
		return dictIds;
	}

	public void setDictIds(List<Long> dictIds) {
		this.dictIds = dictIds;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public Integer getAlgNum() {
		return algNum;
	}

	public void setAlgNum(Integer algNum) {
		this.algNum = algNum;
	}

	public Integer getDictNum() {
		return dictNum;
	}

	public void setDictNum(Integer dictNum) {
		this.dictNum = dictNum;
	}

	public List<CardDTO> getCardList() {
		return cardList;
	}

	public void setCardList(List<CardDTO> cardList) {
		this.cardList = cardList;
	}

	public List<DictDTO> getDictList() {
		return dictList;
	}

	public void setDictList(List<DictDTO> dictList) {
		this.dictList = dictList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getFaultTimes() {
		return faultTimes;
	}

	public void setFaultTimes(Integer faultTimes) {
		this.faultTimes = faultTimes;
	}

	public List<AlgDTO> getAlgList() {
		return algList;
	}

	public void setAlgList(List<AlgDTO> algList) {
		this.algList = algList;
	}

}
