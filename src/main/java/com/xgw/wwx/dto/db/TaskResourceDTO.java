package com.xgw.wwx.dto.db;

public class TaskResourceDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 任务Id
	 */
	private Long taskId;

	/**
	 * 节点类型
	 */
	private Integer nodeType;

	/**
	 * 节点类型
	 */
	private String nodeName;

	/**
	 * 选用数量
	 */
	private Integer nodeNum;

	/**
	 * 总量
	 */
	private Integer totalNum;

	private Integer status;
	
	private Integer cardNum;

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Integer getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(Integer nodeNum) {
		this.nodeNum = nodeNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

}
