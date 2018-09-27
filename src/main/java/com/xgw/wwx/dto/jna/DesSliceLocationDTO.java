package com.xgw.wwx.dto.jna;

public class DesSliceLocationDTO {

	private byte vaild = 1;

	private byte finish = 0;

	private String desLoc;

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
