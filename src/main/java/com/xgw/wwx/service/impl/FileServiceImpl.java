package com.xgw.wwx.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xgw.wwx.common.em.StrategyEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.util.FileUtil;
import com.xgw.wwx.dto.db.StrategyDTO;
import com.xgw.wwx.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	private static final String HC_MASK = ".hcmask";

	private static final String NEW_LINE = System.getProperty("line.separator");

	@Value("${wwx.hcmask.task.dir}")
	private String hcMackPath;

	@Override
	public File createTempFile() {
		String tempPath = System.getProperty("java.io.tmpdir");
		Long timeFlag = System.currentTimeMillis();
		String filePath = tempPath + "/hcmask_" + timeFlag + HC_MASK;
		return createFile(filePath);
	}

	@Override
	public File createTaskFile() {
		Long timeFlag = System.currentTimeMillis();
		String filePath = hcMackPath + "/hcmask_" + timeFlag + HC_MASK;
		File file = new File(filePath);
		FileUtil.createFile(file);
		return file;
	}

	private File createFile(String filePath) {
		if (StringUtils.isNotBlank(filePath)) {
			return new File(filePath);
		}
		return null;
	}

	@Override
	public void writeStrategyToFile(File hcmaskFile, List<StrategyDTO> strategys) {
		FileWriter fileWriter = null;
		try {
			if (null != strategys && !strategys.isEmpty()) {
				fileWriter = new FileWriter(hcmaskFile, true);
				int size = strategys.size();
				for (int i = 0; i < size; i++) {
					StrategyDTO strategyDTO = strategys.get(i);
					String express = "";
					if (StrategyEnum.RANDOM_LENGTH.getType() == strategyDTO.getType().intValue()) {
						Long startLength = strategyDTO.getStartLength();
						Long endLength = strategyDTO.getEndLength();
						if (null != startLength && null != endLength) {
							String strategyExpress = strategyDTO.getExpress();
							for (long l = startLength; l <= endLength; l++) {
								for (long m = 0; m < l; m++) {
									express = express + strategyExpress;
								}
								express = express + NEW_LINE;
							}
						}
					} else {
						express = strategyDTO.getExpress() + NEW_LINE;
					}
					fileWriter.write(express);
				}
			}
		} catch (IOException e) {
			throw new WxxRuntimeException("WRITE_FILE_ERROR", "书写策略文件出错");
		} finally {
			if (null != fileWriter) {
				IOUtils.closeQuietly(fileWriter);
			}
		}
	}

	// public static void main(String[] args) {
	// List<StrategyDTO> strategys = new ArrayList<StrategyDTO>();
	// StrategyDTO strategy1 = new StrategyDTO();
	// strategy1.setType(1);
	// strategy1.setExpress("?d?d?d?d?d?d");
	//
	// StrategyDTO strategy2 = new StrategyDTO();
	// strategy2.setType(2);
	// strategy2.setStartLength(11l);
	// strategy2.setEndLength(10l);
	// strategy2.setExpress("?3");
	//
	//
	// StrategyDTO strategy3 = new StrategyDTO();
	// strategy3.setType(3);
	// strategy3.setExpress("?d?1?2?5?8?d");
	//
	//
	// strategys.add(strategy1);
	// strategys.add(strategy2);
	// strategys.add(strategy3);
	//
	// File hcmaskFile = new File("D:\\test1.txt");
	// FileUtil.createFile(hcmaskFile);
	//
	// FileWriter fileWriter = null;
	// try {
	// if (null != strategys && !strategys.isEmpty()) {
	// fileWriter = new FileWriter(hcmaskFile, true);
	// int size = strategys.size();
	// for (int i = 0; i < size; i++) {
	// StrategyDTO strategyDTO = strategys.get(i);
	// String express = "";
	// if (StrategyEnum.RANDOM_LENGTH.getType() ==
	// strategyDTO.getType().intValue()) {
	// Long startLength = strategyDTO.getStartLength();
	// Long endLength = strategyDTO.getEndLength();
	// String strategyExpress = strategyDTO.getExpress();
	// for (long l = startLength; l <= endLength; l++) {
	// for(long m=0;m<l;m++){
	// express = express + strategyExpress;
	// }
	// express = express + NEW_LINE;
	// }
	// } else {
	// express = strategyDTO.getExpress() + NEW_LINE;
	// }
	// fileWriter.write(express);
	// }
	// }
	// } catch (IOException e) {
	// throw new WxxRuntimeException("WRITE_FILE_ERROR", "书写策略文件出错");
	// } finally {
	// if (null != fileWriter) {
	// IOUtils.closeQuietly(fileWriter);
	// }
	// }
	//
	// }

}
