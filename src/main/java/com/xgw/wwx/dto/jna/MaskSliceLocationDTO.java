package com.xgw.wwx.dto.jna;

public class MaskSliceLocationDTO {

	private byte vaild = 1;

	private byte finish = 0;

	private String filePath;

	private Long fileLoc = 0l;

	private String currentMask = "0x00";

	private int offsetLoc = 0;

	private String output;

	private String[] cutFileName;

	public byte getVaild() {
		return vaild;
	}

	public void setVaild(byte vaild) {
		this.vaild = vaild;
	}

	public byte getFinish() {
		return finish;
	}

	public void setFinish(byte finish) {
		this.finish = finish;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public int getOffsetLoc() {
		return offsetLoc;
	}

	public void setOffsetLoc(int offsetLoc) {
		this.offsetLoc = offsetLoc;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String[] getCutFileName() {
		return cutFileName;
	}

	public void setCutFileName(String[] cutFileName) {
		this.cutFileName = cutFileName;
	}

}
