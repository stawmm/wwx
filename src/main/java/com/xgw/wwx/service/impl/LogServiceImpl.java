/*
package com.xgw.wwx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.LogDTO;
import com.xgw.wwx.mapper.LogMapper;
import com.xgw.wwx.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper;

	@Override
	public LogDTO getLogById(Long id) {
		return logMapper.getLogById(id);
	}

	@Override
	public LogDTO getLogByName(String name) {
		return logMapper.getLogByName(name);
	}

	@Override
	public PageInfo<LogDTO> findLogsByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<LogDTO> list = logMapper.findLogs(params);
		return new PageInfo<LogDTO>(list);
	}

	@Override
	public List<LogDTO> findLogs(Map<String, Object> params) {
		return logMapper.findLogs(params);
	}

	@Override
	public void createLog(LogDTO logDTO) {
		logMapper.createLog(logDTO);
	}

	@Override
	public void updateLog(LogDTO logDTO) {
		logMapper.updateLog(logDTO);
	}

	@Override
	public void deleteLog(Long id) {
		logMapper.deleteLog(id);
	}

	@Override
	public void clearLog() {
		logMapper.clearLog();
	}

	@Override
	public List<LogDTO> findAlertLogs() {
		return logMapper.findAlertLogs();
	}

}
*/
