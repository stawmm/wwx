package com.xgw.wwx.dto.db;

public class TaskFileDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 任务Id
	 */
	private Long taskId;

	/**
	 * 破解文件名称
	 */
	private String fileName;

	/**
	 * 破解文件路径
	 */
	private String filePath;

	/**
	 * 算法名称
	 */
	private String algName;

	/**
	 * 算法id
	 */
	private Long algId;

	/**
	 * 特征串
	 */
	private String specialChar;

	/**
	 * 提串文件名称
	 */
	private String specialFileName;

	/**
	 * 提串文件路径
	 */
	private String specialFilePath;

	private Integer status;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAlgName() {
		return algName;
	}

	public void setAlgName(String algName) {
		this.algName = algName;
	}

	public Long getAlgId() {
		return algId;
	}

	public void setAlgId(Long algId) {
		this.algId = algId;
	}

	public String getSpecialChar() {
		return specialChar;
	}

	public void setSpecialChar(String specialChar) {
		this.specialChar = specialChar;
	}

	public String getSpecialFileName() {
		return specialFileName;
	}

	public void setSpecialFileName(String specialFileName) {
		this.specialFileName = specialFileName;
	}

	public String getSpecialFilePath() {
		return specialFilePath;
	}

	public void setSpecialFilePath(String specialFilePath) {
		this.specialFilePath = specialFilePath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
