package com.xgw.wwx.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.LoggerDTO;

public interface HzLoggerService {

    public LoggerDTO getLoggerById(Long id);

    public List<LoggerDTO> findLoggers(LoggerDTO loggerDTO);

    public PageInfo<LoggerDTO> findLoggersByPage(LoggerDTO loggerDTO);

    public void createLogger(LoggerDTO loggerDTO);

    public void updateLogger(LoggerDTO loggerDTO);

    public void deleteLogger(Long id);

    public void clearLogger();

    public void createSuccessLogger(String userName, String actionMsg, String modeType, String successMsg);

    public void createSuccessLogger(String userName, String actionMsg, String modeType, String successMsg, String memo);

    public void createFailedLogger(String userName, String actionMsg, String modeType, String errorCode, String errorMsg);

    public void createFailedLogger(String userName, String actionMsg, String modeType, String errorCode, String errorMsg, String memo);

    public void createAlertLogger(String userName, String actionMsg, String modeType, String errorCode, String errorMsg);

    public void createAlertLogger(String userName, String actionMsg, String modeType, String errorCode, String errorMsg, String memo);

    public List<LoggerDTO> findLoggersByLevel(String level);

    public List<LoggerDTO> findAlertLoggers();

	public void updateAlertLoggers();

}
