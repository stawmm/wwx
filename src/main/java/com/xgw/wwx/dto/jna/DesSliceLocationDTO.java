package com.xgw.wwx.dto.jna;

public class DesSliceLocationDTO {

	private byte vaild = 1;//是否有效

	private byte finish = 0;//是否完成

	private String desLoc;//切片定位

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

	public String[] getCutFileName() {
		return cutFileName;
	}

	public void setCutFileName(String[] cutFileName) {
		this.cutFileName = cutFileName;
	}

}
