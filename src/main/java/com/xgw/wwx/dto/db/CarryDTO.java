package com.xgw.wwx.dto.db;

import java.util.List;

public class CarryDTO {

	private boolean result;

	private String hashValue;

	private Long hashModel;

	private String hashType;

	private String rawFileType;

	private String rawFileSubversion;

	private boolean isRawFileEncrypted;

	private String errorMsg;

	private String fileName;

	private String carryPath;

	private List<AlgDTO> algs;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getHashValue() {
		return hashValue;
	}

	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}

	public Long getHashModel() {
		return hashModel;
	}

	public void setHashModel(Long hashModel) {
		this.hashModel = hashModel;
	}

	public String getHashType() {
		return hashType;
	}

	public void setHashType(String hashType) {
		this.hashType = hashType;
	}

	public String getRawFileType() {
		return rawFileType;
	}

	public void setRawFileType(String rawFileType) {
		this.rawFileType = rawFileType;
	}

	public String getRawFileSubversion() {
		return rawFileSubversion;
	}

	public void setRawFileSubversion(String rawFileSubversion) {
		this.rawFileSubversion = rawFileSubversion;
	}

	public boolean isRawFileEncrypted() {
		return isRawFileEncrypted;
	}

	public void setRawFileEncrypted(boolean isRawFileEncrypted) {
		this.isRawFileEncrypted = isRawFileEncrypted;
	}

	public List<AlgDTO> getAlgs() {
		return algs;
	}

	public void setAlgs(List<AlgDTO> algs) {
		this.algs = algs;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCarryPath() {
		return carryPath;
	}

	public void setCarryPath(String carryPath) {
		this.carryPath = carryPath;
	}

}
