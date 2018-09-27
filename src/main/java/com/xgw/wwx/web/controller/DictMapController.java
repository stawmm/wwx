package com.xgw.wwx.web.controller;

import java.util.List;

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
import com.xgw.wwx.dto.db.DictMapDTO;
import com.xgw.wwx.service.DictMapService;

@RestController
@RequestMapping("/system/dictmap")
public class DictMapController {

	private static final Logger logger = LoggerFactory.getLogger(DictMapController.class);

	@Autowired
	private DictMapService dictMapService;

	@GetMapping("/list/{code}")
	public ResponseEntity<CommonResponseDTO<List<DictMapDTO>>> findDictMapList(@PathVariable("code") String code) {
		try {
			List<DictMapDTO> list = dictMapService.findDictMapListByCode(code);
			return ResponseEntity.ok(new CommonResponseDTO<List<DictMapDTO>>(list));
		} catch (WxxRuntimeException e) {
			logger.error("-- findDictMapList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DictMapDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findDictMapList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DictMapDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
