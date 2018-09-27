package com.xgw.wwx.dto.db;

public class TaskStrategyFileDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long taskId;

	private Integer prepare;

	private String fileName;

	private String filePath;

	private Integer vaild;

	private Integer finish;

	private Long fileLoc;

	private String currentMask;

	private Integer offsetLoc;

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

	public Long getFileLoc() {
		return fileLoc;
	}

	public void setFileLoc(Long fileLoc) {
		this.fileLoc = fileLoc;
	}

	public String getCurrentMask() {
		return currentMask;
	}

	public void setCurrentMask(String currentMask) {
		this.currentMask = currentMask;
	}

	public Integer getOffsetLoc() {
		return offsetLoc;
	}

	public void setOffsetLoc(Integer offsetLoc) {
		this.offsetLoc = offsetLoc;
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

	public Integer getPrepare() {
		return prepare;
	}

	public void setPrepare(Integer prepare) {
		this.prepare = prepare;
	}

}
