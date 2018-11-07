package com.xgw.wwx.dto.jna;

public class MaskSliceLocationDTO {

	private byte vaild = 1;//是否有效

	private byte finish = 0;//是否完成

	private String filePath;//文件路径

	private Long fileLoc = 0l;//mask文件内容位置偏移

	private String currentMask = "0x00";//将要切片的掩码

	private int offsetLoc = 0;//表示掩码切分位

	private String output;//切片文件输出路径

	private String[] cutFileName;//切片文件名

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
