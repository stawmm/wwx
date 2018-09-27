package com.xgw.wwx.web.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.service.ftp.FtpService;

@RestController
@RequestMapping("/file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FtpService ftpService;

	@PostMapping(value = "/upload/file")
	public ResponseEntity<CommonResponseDTO<Boolean>> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			File tempFile = new File(FileUtils.getTempDirectory() + "/" + file.getOriginalFilename());
			file.transferTo(tempFile);
			// FTP
			boolean result = ftpService.uploadFile("/", tempFile.getName(), new FileInputStream(tempFile));
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(result));
		} catch (WxxRuntimeException e) {
			logger.error("-- uploadImg Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- uploadImg Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping(value = "/upload/files")
	public ResponseEntity<CommonResponseDTO<Boolean>> uploadFiles(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		try {
			for (MultipartFile file : files) {
				File tempFile = new File(FileUtils.getTempDirectory() + "/" + file.getOriginalFilename());
				file.transferTo(tempFile);
			}
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (WxxRuntimeException e) {
			logger.error("-- uploadFiles Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- uploadFiles Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
