package com.xgw.wwx.service;

import java.io.File;
import java.util.List;

import com.xgw.wwx.dto.db.StrategyDTO;

public interface FileService {

	public File createTempFile();

	public File createTaskFile();

	public void writeStrategyToFile(File hcmaskFile, List<StrategyDTO> strategys);

}
