package com.xgw.wwx.dto.db;

public class AlgDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 设备Id
	 */
	private Long deviceId;

	/**
	 * 节点类型
	 */
	private Integer nodeType;

	/**
	 * 板卡类型
	 */
	private Integer cardType;

	/**
	 * 算法类型
	 */
	private Long algId;

	/**
	 * 算法类型
	 */
	private Integer algType;

	/**
	 * 算法类型
	 */
	private String algTypeName;

	/**
	 * 算法名称
	 */
	private String algName;

	/**
	 * 速率
	 */
	private Long speed;

	/**
	 * 算核
	 */
	private Integer coreNum;

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getAlgType() {
		return algType;
	}

	public void setAlgType(Integer algType) {
		this.algType = algType;
	}

	public String getAlgName() {
		return algName;
	}

	public void setAlgName(String algName) {
		this.algName = algName;
	}

	public Long getSpeed() {
		return speed;
	}

	public void setSpeed(Long speed) {
		this.speed = speed;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public Long getAlgId() {
		return algId;
	}

	public void setAlgId(Long algId) {
		this.algId = algId;
	}

	public String getAlgTypeName() {
		return algTypeName;
	}

	public void setAlgTypeName(String algTypeName) {
		this.algTypeName = algTypeName;
	}

	public Integer getCoreNum() {
		return coreNum;
	}

	public void setCoreNum(Integer coreNum) {
		this.coreNum = coreNum;
	}

}
