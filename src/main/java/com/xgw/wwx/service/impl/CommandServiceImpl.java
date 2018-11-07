package com.xgw.wwx.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xgw.wwx.common.util.CmdUtil;
import com.xgw.wwx.dto.db.CarryDTO;
import com.xgw.wwx.service.CommandService;

@Service
public class CommandServiceImpl implements CommandService {

	@Value("${wwx.file.carry.cmd}")
	private String carryCmd;

	@Value("${wwx.strategy.time.cmd}")
	private String strategyTimeCmd;

	@Value("${wwx.vpndes.time.cmd}")
	private String vpndesTimeCmd;

	@Override
	public CarryDTO getFileCarryMessage(String filePath, Long algId) {
		CarryDTO carryDTO = new CarryDTO();
		String command = String.format(carryCmd, filePath);
		if (null != algId) {
			command = command + " " + algId;
		}
		List<String> results = CmdUtil.execute(command, CmdUtil.getCurrentDirectoryFile());
		if (null != results && !results.isEmpty()) {
			for (int index = 0; index < results.size(); index++) {
				String strLine = results.get(index);
				//判断strLine 字符串是否  不为空且长度不为0且不由空白符  截取的key 、 value 和 carryDTO对象里面的字段对应
				if(StringUtils.isNotBlank(strLine)) {
					String[] lineArray = strLine.split(":");
					if (lineArray.length == 2) {
						String key = lineArray[0].trim();
						String value = lineArray[1].trim();
						if ("t.is_raw_file_encrypted".equals(key)) {
							carryDTO.setRawFileEncrypted("True".equals(value));
						} else if ("t.hash_type".equals(key)) {
							carryDTO.setHashType(value);
						} else if ("t.rawfile_type".equals(key)) {
							carryDTO.setRawFileType(value);
						} else if ("t.rawfile_subversion".equals(key)) {
							carryDTO.setRawFileSubversion(value);
						} else if ("t.hash_mode".equals(key)) {
							carryDTO.setHashModel(NumberUtils.toLong(value));
						} else if ("t.hash_val".equals(key)) {
							carryDTO.setHashValue(value);
						} else if ("result".equals(key)) {
							if(StringUtils.isNotBlank(value) && "success".equals(value)) {
								carryDTO.setResult(true);
							}else {
								carryDTO.setResult(false);
							}
						}
					}
				}	
			}
			// TODO 待修改
			/*if (StringUtils.isNotBlank(carryDTO.getHashType()) || 100000 != carryDTO.getHashModel().longValue()) {
				carryDTO.setResult(true);
			} else {
				carryDTO.setResult(false);
			}*/
		}
		return carryDTO;
	}

	@Override
	public Long getStrategyTimeCount(String filePath, Long speed) {
		String command = String.format(strategyTimeCmd, filePath, speed);
		List<String> results = CmdUtil.execute(command, CmdUtil.getCurrentDirectoryFile());
		if (null != results && !results.isEmpty()) {
			for (String result : results) {
				if (StringUtils.startsWith(result, "count:")) {
					String[] array = result.split(":");
					if (NumberUtils.isDigits(array[1])) {
						return NumberUtils.toLong(array[1]);
					}
				}
			}
		}
		return 0l;
	}

	@Override
	public Long getVpndesTimeCount(Long speed) {
		String command = String.format(vpndesTimeCmd, speed);
		List<String> results = CmdUtil.execute(command, CmdUtil.getCurrentDirectoryFile());
		if (null != results && !results.isEmpty()) {
			for (String result : results) {
				if (StringUtils.startsWith(result, "count:")) {
					String[] array = result.split(":");
					if (NumberUtils.isDigits(array[1])) {
						return NumberUtils.toLong(array[1]);
					}
				}
			}
		}
		return 0l;
	}

}
