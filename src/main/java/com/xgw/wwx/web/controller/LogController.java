package com.xgw.wwx.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.LoggerDTO;
import com.xgw.wwx.service.HzLoggerService;

@RestController
@RequestMapping("/logs")
public class LogController {

	private static final Logger logger = LoggerFactory.getLogger(LogController.class);

	@Autowired
	private HzLoggerService hzLoggerService;

	@GetMapping("/find/list")
	public ResponseEntity<CommonResponseDTO<PageInfo<LoggerDTO>>> findLogs(HttpServletRequest request, LoggerDTO loggerDTO) {
		try {
			PageInfo<LoggerDTO> pageInfo = hzLoggerService.findLoggersByPage(loggerDTO);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<LoggerDTO>>(pageInfo));
		} catch (WxxRuntimeException e) {
			logger.error("-- findLogs Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<LoggerDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findLogs Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<LoggerDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/clear")
	public ResponseEntity<CommonResponseDTO<Boolean>> clearLog(HttpServletRequest request) {
		try {
			hzLoggerService.clearLogger();
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- clearLog Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- clearLog Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/alert/list")
	public ResponseEntity<CommonResponseDTO<List<LoggerDTO>>> findAlertLogs() {
		try {
			List<LoggerDTO> pageInfo = hzLoggerService.findAlertLoggers();
			return ResponseEntity.ok(new CommonResponseDTO<List<LoggerDTO>>(pageInfo));
		} catch (WxxRuntimeException e) {
			logger.error("-- findAlertLogs Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<LoggerDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findAlertLogs Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<LoggerDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}
	
	@GetMapping("/alert/view")
	public ResponseEntity<CommonResponseDTO<Boolean>> viewAlertLogs() {
		try {
			hzLoggerService.updateAlertLoggers();
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (WxxRuntimeException e) {
			logger.error("-- viewAlertLogs Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- viewAlertLogs Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
