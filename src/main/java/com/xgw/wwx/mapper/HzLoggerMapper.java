package com.xgw.wwx.mapper;

import java.util.List;

import com.xgw.wwx.dto.db.LoggerDTO;

public interface HzLoggerMapper {

    public LoggerDTO getLoggerById(Long id);

    public List<LoggerDTO> findLoggers(LoggerDTO loggerDTO);

    public void createLogger(LoggerDTO loggerDTO);

    public void updateLogger(LoggerDTO loggerDTO);

    public void deleteLogger(Long id);

    public void clearLogger();

    public List<LoggerDTO> findLoggersByLevel(String level);

    public List<LoggerDTO> findAlertLoggers();

	public void updateAlertLoggers();
	
}
