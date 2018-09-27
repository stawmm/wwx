package com.xgw.wwx.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.HistoryDTO;
import com.xgw.wwx.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {

	private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

	@Autowired
	private HistoryService historyService;

	@GetMapping("/{id}")
	public ResponseEntity<CommonResponseDTO<HistoryDTO>> getHistoryById(@PathVariable("id") Long id) {
		try {
			logger.info("-- info message, id={} --", id);
			HistoryDTO historyDTO = historyService.getHistoryById(id);
			return ResponseEntity.ok(new CommonResponseDTO<HistoryDTO>(historyDTO));
		} catch (WxxRuntimeException e) {
			logger.error("-- getHistoryById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<HistoryDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- getHistoryById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<HistoryDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
