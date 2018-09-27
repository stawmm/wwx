package com.xgw.wwx.web.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.StrategyDTO;
import com.xgw.wwx.dto.jna.DesDTO;
import com.xgw.wwx.dto.jna.DesSliceLocationDTO;
import com.xgw.wwx.dto.jna.MaskDTO;
import com.xgw.wwx.dto.jna.MaskSliceLocationDTO;
import com.xgw.wwx.service.FileService;
import com.xgw.wwx.service.JnaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/jna/open")
@Api(value = "JnaController", tags = "切片接口")
public class JnaController {

	private static final Logger logger = LoggerFactory.getLogger(JnaController.class);

	@Autowired
	private JnaService jnaService;

	@Autowired
	private FileService fileService;

	@PostMapping("/des")
	@ApiOperation(value = "/des", notes = "DES切片")
	public ResponseEntity<CommonResponseDTO<DesSliceLocationDTO>> desSlice(HttpServletRequest request, @RequestBody @ApiParam(name = "desDTO", value = "DES对象", required = true) DesDTO desDTO) {
		try {
			DesSliceLocationDTO response = jnaService.desSlice(desDTO.getJmtype(), desDTO.getCardnum(), desDTO.getSpeed(), desDTO.getSunzinum(), desDTO.getSliceLoc());
			return ResponseEntity.ok(new CommonResponseDTO<DesSliceLocationDTO>(response));
		} catch (WxxRuntimeException e) {
			logger.error("-- desSlice Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<DesSliceLocationDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- desSlice Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<DesSliceLocationDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/des/next")
	@ApiOperation(value = "/des/next", notes = "DES切片")
	public ResponseEntity<CommonResponseDTO<DesSliceLocationDTO>> desSliceNext(HttpServletRequest request, @RequestBody @ApiParam(name = "desDTO", value = "DES对象", required = true) DesDTO desDTO) {
		try {
			String mapKey = "des_slice_" + desDTO.getId();
			DesSliceLocationDTO response = jnaService.desSliceNext(mapKey, desDTO.getJmtype(), desDTO.getCardnum(), desDTO.getSpeed(), desDTO.getSunzinum(), desDTO.getSliceLoc());
			return ResponseEntity.ok(new CommonResponseDTO<DesSliceLocationDTO>(response));
		} catch (WxxRuntimeException e) {
			logger.error("-- desSliceNext Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<DesSliceLocationDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- desSliceNext Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<DesSliceLocationDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/mask")
	@ApiOperation(value = "/mask", notes = "MASK切片")
	public ResponseEntity<CommonResponseDTO<MaskSliceLocationDTO>> maskSlice(HttpServletRequest request, @RequestBody @ApiParam(name = "maskDTO", value = "MASK对象", required = true) MaskDTO maskDTO) {
		try {
			MaskSliceLocationDTO response = jnaService.maskSlice(maskDTO.getJmtype(), maskDTO.getCardnum(), maskDTO.getSpeed(), maskDTO.getSunzinum(), maskDTO.getSliceLoc());
			return ResponseEntity.ok(new CommonResponseDTO<MaskSliceLocationDTO>(response));
		} catch (WxxRuntimeException e) {
			logger.error("-- maskSlice Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<MaskSliceLocationDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- maskSlice Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<MaskSliceLocationDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/mask/next")
	@ApiOperation(value = "/mask/next", notes = "MASK切片")
	public ResponseEntity<CommonResponseDTO<MaskSliceLocationDTO>> maskSliceNext(HttpServletRequest request, @RequestBody @ApiParam(name = "maskDTO", value = "MASK对象", required = true) MaskDTO maskDTO) {
		try {
			String mapKey = "mask_slice_" + maskDTO.getId();
			MaskSliceLocationDTO response = jnaService.maskSliceNext(mapKey, maskDTO.getJmtype(), maskDTO.getCardnum(), maskDTO.getSpeed(), maskDTO.getSunzinum(), maskDTO.getSliceLoc());
			return ResponseEntity.ok(new CommonResponseDTO<MaskSliceLocationDTO>(response));
		} catch (WxxRuntimeException e) {
			logger.error("-- maskSliceNext Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<MaskSliceLocationDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- maskSliceNext Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<MaskSliceLocationDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/preprocess")
	@ApiOperation(value = "/preprocess", notes = "切片文件预处理")
	public ResponseEntity<CommonResponseDTO<Integer>> preprocess(@RequestParam("fileName") String fileName) {
		try {
			Integer result = jnaService.maskfileExpansion(fileName);
			return ResponseEntity.ok(new CommonResponseDTO<Integer>(result));
		} catch (WxxRuntimeException e) {
			logger.error("-- preprocess Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Integer>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- preprocess Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Integer>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/file")
	@ApiOperation(value = "/file", notes = "生成预编译文件")
	public ResponseEntity<CommonResponseDTO<String>> writeToFile(@RequestBody List<StrategyDTO> strategys) {
		try {
			File file = new File(FileUtils.getTempDirectory() + "/test.hcmask");
			fileService.writeStrategyToFile(file, strategys);
			return ResponseEntity.ok(new CommonResponseDTO<String>(file.getPath()));
		} catch (WxxRuntimeException e) {
			logger.error("-- preprocess Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<String>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- preprocess Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<String>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/path")
	@ApiOperation(value = "/path", notes = "生成预编译文件")
	public ResponseEntity<CommonResponseDTO<Map<String, Object>>> healthCheck() {
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("path", System.getProperty("java.library.path"));
			return ResponseEntity.ok(new CommonResponseDTO<Map<String, Object>>(dataMap));
		} catch (WxxRuntimeException e) {
			return ResponseEntity.ok(new CommonResponseDTO<Map<String, Object>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new CommonResponseDTO<Map<String, Object>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
