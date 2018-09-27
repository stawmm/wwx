package com.xgw.wwx.dto.db;

public class TaskVpndesDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long taskId;

	private String fileName;

	private String filePath;

	private Integer vaild;

	private Integer finish;

	private String desLoc;

	private String output;

	private String cutFileName;

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

	public Integer getVaild() {
		return vaild;
	}

	public void setVaild(Integer vaild) {
		this.vaild = vaild;
	}

	public Integer getFinish() {
		return finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}

	public String getDesLoc() {
		return desLoc;
	}

	public void setDesLoc(String desLoc) {
		this.desLoc = desLoc;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getCutFileName() {
		return cutFileName;
	}

	public void setCutFileName(String cutFileName) {
		this.cutFileName = cutFileName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
