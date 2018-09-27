package com.xgw.wwx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.em.LoggerLevelType;
import com.xgw.wwx.common.em.LoggerStatusType;
import com.xgw.wwx.dto.db.LoggerDTO;
import com.xgw.wwx.mapper.HzLoggerMapper;
import com.xgw.wwx.service.HzLoggerService;

@Service
public class HzLoggerServiceImpl implements HzLoggerService {

    @Autowired
    private HzLoggerMapper hzLoggerMapper;

    public LoggerDTO getLoggerById(Long id) {
        return hzLoggerMapper.getLoggerById(id);
    }

    public List<LoggerDTO> findLoggers(LoggerDTO loggerDTO) {
        return hzLoggerMapper.findLoggers(loggerDTO);
    }

    public PageInfo<LoggerDTO> findLoggersByPage(LoggerDTO loggerDTO) {
        PageHelper.startPage(loggerDTO.getPageNum(), loggerDTO.getPageSize());
        return new PageInfo<LoggerDTO>(hzLoggerMapper.findLoggers(loggerDTO));
    }

    public void createLogger(LoggerDTO loggerDTO) {
        hzLoggerMapper.createLogger(loggerDTO);
    }

    public void updateLogger(LoggerDTO loggerDTO) {
        hzLoggerMapper.updateLogger(loggerDTO);
    }

    public void deleteLogger(Long id) {
        hzLoggerMapper.deleteLogger(id);
    }

    public void clearLogger() {
        hzLoggerMapper.clearLogger();
    }

    @Override
    @Async
    public void createSuccessLogger(String userName, String actionMsg, String modeType, String successMsg) {
        createSuccessLogger(userName, actionMsg, modeType, successMsg, null);
    }

    @Override
    @Async
    public void createSuccessLogger(String userName, String actionMsg, String modeType, String successMsg, String memo) {
        LoggerDTO loggerDTO = new LoggerDTO();
        loggerDTO.setUserName(userName);
        loggerDTO.setCreateUser(userName);
        loggerDTO.setActionMsg(actionMsg);
        loggerDTO.setModeType(modeType);
        loggerDTO.setSuccessMsg(successMsg);
        loggerDTO.setMemo(memo);
        loggerDTO.setLevel(LoggerLevelType.INFO.getLevel());
        loggerDTO.setStatus(LoggerStatusType.SUCCESS.getStatus());
        hzLoggerMapper.createLogger(loggerDTO);
    }

    @Override
    @Async
    public void createFailedLogger(String userName, String actionMsg, String modeType, String errorCode, String errorMsg) {
        createFailedLogger(userName, actionMsg, modeType, errorCode, errorMsg, null);
    }

    @Override
    @Async
    public void createFailedLogger(String userName, String actionMsg, String modeType, String errorCode, String errorMsg, String memo) {
        LoggerDTO loggerDTO = new LoggerDTO();
        loggerDTO.setUserName(userName);
        loggerDTO.setCreateUser(userName);
        loggerDTO.setActionMsg(actionMsg);
        loggerDTO.setModeType(modeType);
        loggerDTO.setErrorCode(errorCode);
        loggerDTO.setErrorMsg(errorMsg);
        loggerDTO.setMemo(memo);
        loggerDTO.setLevel(LoggerLevelType.INFO.getLevel());
        loggerDTO.setStatus(LoggerStatusType.FAILED.getStatus());
        hzLoggerMapper.createLogger(loggerDTO);
    }

    @Override
    @Async
    public void createAlertLogger(String userName, String actionMsg, String modeType, String errorCode, String errorMsg) {
        createAlertLogger(userName, actionMsg, modeType, errorCode, errorMsg, null);
    }

    @Override
    @Async
    public void createAlertLogger(String userName, String actionMsg, String modeType, String errorCode, String errorMsg, String memo) {
        LoggerDTO loggerDTO = new LoggerDTO();
        loggerDTO.setUserName(userName);
        loggerDTO.setCreateUser(userName);
        loggerDTO.setActionMsg(actionMsg);
        loggerDTO.setModeType(modeType);
        loggerDTO.setErrorCode(errorCode);
        loggerDTO.setErrorMsg(errorMsg);
        loggerDTO.setMemo(memo);
        loggerDTO.setLevel(LoggerLevelType.ALERT.getLevel());
        loggerDTO.setStatus(LoggerStatusType.FAILED.getStatus());
        hzLoggerMapper.createLogger(loggerDTO);
    }

    @Override
    public List<LoggerDTO> findLoggersByLevel(String level) {
        return hzLoggerMapper.findLoggersByLevel(level);
    }

    @Override
    public List<LoggerDTO> findAlertLoggers() {
        return hzLoggerMapper.findAlertLoggers();
    }

	@Override
	public void updateAlertLoggers() {
		hzLoggerMapper.updateAlertLoggers();
	}

}
