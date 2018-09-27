package com.xgw.wwx.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.dto.common.CommonResponseDTO;

@RestController
@RequestMapping("/health")
public class HealthController {

	@GetMapping("/open/check")
	public ResponseEntity<CommonResponseDTO<Boolean>> healthCheck() {
		try {
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (WxxRuntimeException e) {
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
