package com.xgw.wwx.web.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.dto.common.CommonResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<CommonResponseDTO<Map<String, Object>>> exceptionHandler(HttpServletRequest request, Exception e) {
		logger.error("-- exceptionHandler exception --", e);
		Map<String, Object> errorMap = new HashMap<String, Object>();
		if (e instanceof WxxRuntimeException) {
			WxxRuntimeException ex = (WxxRuntimeException) e;
			errorMap.put("code", ex.getCode());
			errorMap.put("message", ex.getMessage());
		} else {
			errorMap.put("code", BaseCodeEnum.SYSTEM_UNKNOW_ERROR.getCode());
			errorMap.put("message", BaseCodeEnum.SYSTEM_UNKNOW_ERROR.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponseDTO<Map<String, Object>>(errorMap, false));
	}
}
