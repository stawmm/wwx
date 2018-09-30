package com.xgw.wwx.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.ProgramRuntimeException;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.helper.AuthHelper;
import com.xgw.wwx.common.util.FileUtil;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.service.HzLoggerService;
import com.xgw.wwx.service.ProgramService;

@RestController
@RequestMapping("/program/upgrade")
public class ProgramController {

	private static final Logger logger = LoggerFactory.getLogger(ProgramController.class);

	@Autowired
	private ProgramService programService;

	@Value("${wwx.fpga.upgrade.dir}")
	private String fpgaUpgradePath;

	@Value("${wwx.gpu.upgrade.dir}")
	private String gpuUpgradePath;

	@Autowired
	private HzLoggerService hzLoggerService;

	@PostMapping("/gpu")
	public ResponseEntity<CommonResponseDTO<Boolean>> gpuUpgrade(@RequestParam("deviceIds") String deviceIds, @RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		String userName = AuthHelper.getUserName(request);
		try {
			if (StringUtils.isBlank(deviceIds)) {
				throw new ProgramRuntimeException("UPGRADE_DEVICE_IS_EMPTY", "设备列表为空");
			}
			if (null == files || files.length == 0) {
				throw new ProgramRuntimeException("UPGRADE_FILE_IS_EMPTY", "上传文件为空");
			}
			List<String> fileList = new ArrayList<String>();
			for (MultipartFile file : files) {
				File uploadFile = new File(gpuUpgradePath + "/" + file.getOriginalFilename());
				FileUtil.createFile(uploadFile);
				file.transferTo(uploadFile);
				fileList.add(file.getOriginalFilename());
			}
			List<Long> deviceList = new ArrayList<Long>();
			if (StringUtils.isNotBlank(deviceIds)) {
				String[] deviceArray = deviceIds.split(",");
				for (String deviceId : deviceArray) {
					deviceList.add(NumberUtils.toLong(deviceId));
				}
			}
			programService.gpuUpgrade(fileList, deviceList);
			// gpu程序升级
			hzLoggerService.createSuccessLogger(userName, "GPU设备升级", "系统升级", "用户“" + userName + "”升级GPU设备成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (ProgramRuntimeException e) {
			logger.error("-- gpuUpgrade ProgramRuntimeException error --", e);
			hzLoggerService.createFailedLogger(userName, "GPU设备升级", "系统升级", e.getCode(), "用户“" + userName + "”升级GPU设备失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (WxxRuntimeException e) {
			logger.error("-- gpuUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "GPU设备升级", "系统升级", e.getCode(), "用户“" + userName + "”升级GPU设备失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- gpuUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "GPU设备升级", "系统升级", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”升级GPU设备失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/fpga")
	public ResponseEntity<CommonResponseDTO<Boolean>> fpgaUpgrade(@RequestParam("deviceIds") String deviceIds, @RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		String userName = AuthHelper.getUserName(request);
		try {
			if (StringUtils.isBlank(deviceIds)) {
				throw new ProgramRuntimeException("UPGRADE_DEVICE_IS_EMPTY", "设备列表为空");
			}
			if (null == files || files.length == 0) {
				throw new ProgramRuntimeException("UPGRADE_FILE_IS_EMPTY", "上传文件为空");
			}
			List<Long> deviceList = new ArrayList<Long>();
			if (StringUtils.isNotBlank(deviceIds)) {
				String[] deviceArray = deviceIds.split(",");
				for (String deviceId : deviceArray) {
					deviceList.add(NumberUtils.toLong(deviceId));
				}
			}
			List<String> fileList = new ArrayList<String>();
			for (MultipartFile file : files) {
				File uploadFile = new File(fpgaUpgradePath + "/" + file.getOriginalFilename());
				FileUtil.createFile(uploadFile);
				file.transferTo(uploadFile);
				fileList.add(file.getOriginalFilename());
			}
			programService.fpgaUpgrade(fileList, deviceList);
			hzLoggerService.createSuccessLogger(userName, "FPGA设备升级", "系统升级", "用户“" + userName + "”升级FPGA设备成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (ProgramRuntimeException e) {
			logger.error("-- fpgaUpgrade ProgramRuntimeException error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA设备升级", "系统升级", e.getCode(), "用户“" + userName + "”升级FPGA设备失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (WxxRuntimeException e) {
			logger.error("-- fpgaUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA设备升级", "系统升级", e.getCode(), "用户“" + userName + "”升级FPGA设备失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- fpgaUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA设备升级", "系统升级", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”升级FPGA设备失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/fpgaOne")
	public ResponseEntity<CommonResponseDTO<Boolean>> fpgaOneUpgrade(@RequestParam("deviceIds") String deviceIds, @RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		String userName = AuthHelper.getUserName(request);
		try {
			if (StringUtils.isBlank(deviceIds)) {
				throw new ProgramRuntimeException("UPGRADE_DEVICE_IS_EMPTY", "设备列表为空");
			}
			if (null == files || files.length == 0) {
				throw new ProgramRuntimeException("UPGRADE_FILE_IS_EMPTY", "上传文件为空");
			}
			List<Long> deviceList = new ArrayList<Long>();
			if (StringUtils.isNotBlank(deviceIds)) {
				String[] deviceArray = deviceIds.split(",");
				for (String deviceId : deviceArray) {
					deviceList.add(NumberUtils.toLong(deviceId));
				}
			}
			List<String> fileList = new ArrayList<String>();
			for (MultipartFile file : files) {
				File uploadFile = new File(fpgaUpgradePath + "/" + file.getOriginalFilename());
				FileUtil.createFile(uploadFile);
				file.transferTo(uploadFile);
				fileList.add(file.getOriginalFilename());
			}
			programService.fpgaOneUpgrade(fileList, deviceList);
			hzLoggerService.createSuccessLogger(userName, "FPGA一代算法升级", "系统升级", "用户“" + userName + "”升级FPGA一代算法成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (ProgramRuntimeException e) {
			logger.error("-- fpgaUpgrade ProgramRuntimeException error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA一代算法升级", "系统升级", e.getCode(), "用户“" + userName + "”升级FPGA一代算法失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (WxxRuntimeException e) {
			logger.error("-- fpgaUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA一代算法升级", "系统升级", e.getCode(), "用户“" + userName + "”升级FPGA一代算法失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- fpgaUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA一代算法升级", "系统升级", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”升级FPGA一代算法失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/fpgaTwo")
	public ResponseEntity<CommonResponseDTO<Boolean>> fpgaTwoUpgrade(@RequestParam("deviceIds") String deviceIds, @RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		String userName = AuthHelper.getUserName(request);
		try {
			if (StringUtils.isBlank(deviceIds)) {
				throw new ProgramRuntimeException("UPGRADE_DEVICE_IS_EMPTY", "设备列表为空");
			}
			if (null == files || files.length == 0) {
				throw new ProgramRuntimeException("UPGRADE_FILE_IS_EMPTY", "上传文件为空");
			}
			List<Long> deviceList = new ArrayList<Long>();
			if (StringUtils.isNotBlank(deviceIds)) {
				String[] deviceArray = deviceIds.split(",");
				for (String deviceId : deviceArray) {
					deviceList.add(NumberUtils.toLong(deviceId));
				}
			}
			List<String> fileList = new ArrayList<String>();
			for (MultipartFile file : files) {
				File uploadFile = new File(fpgaUpgradePath + "/" + file.getOriginalFilename());
				FileUtil.createFile(uploadFile);
				file.transferTo(uploadFile);
				fileList.add(file.getOriginalFilename());
			}
			programService.fpgaTwoUpgrade(fileList, deviceList);
			hzLoggerService.createSuccessLogger(userName, "FPGA二代算法升级", "系统升级", "用户“" + userName + "”升级FPGA二代算法成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (ProgramRuntimeException e) {
			logger.error("-- fpgaUpgrade ProgramRuntimeException error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA二代算法升级", "系统升级", e.getCode(), "用户“" + userName + "”升级FPGA二代算法失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (WxxRuntimeException e) {
			logger.error("-- fpgaUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA二代算法升级", "系统升级", e.getCode(), "用户“" + userName + "”升级FPGA二代算法失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- fpgaUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "FPGA二代算法升级", "系统升级", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”升级FPGA一代算法失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/dict")
	public ResponseEntity<CommonResponseDTO<Boolean>> dictUpgrade(@RequestParam("deviceIds") String deviceIds, @RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		String userName = AuthHelper.getUserName(request);
		try {
			List<String> fileList = new ArrayList<String>();
			List<Long> deviceList = new ArrayList<Long>();
			if (StringUtils.isNotBlank(deviceIds)) {
				String[] deviceArray = deviceIds.split(",");
				for (String deviceId : deviceArray) {
					deviceList.add(NumberUtils.toLong(deviceId));
				}
			}
			for (MultipartFile file : files) {
				File uploadFile = new File(FileUtils.getTempDirectory() + "/" + file.getOriginalFilename());
				FileUtil.createFile(uploadFile);
				file.transferTo(uploadFile);
				fileList.add(file.getOriginalFilename());
			}
			if (deviceList.isEmpty()) {
				throw new ProgramRuntimeException("UPGRADE_DEVICE_IS_EMPTY", "设备列表为空");
			}
			if (fileList.isEmpty()) {
				throw new ProgramRuntimeException("UPGRADE_FILE_IS_EMPTY", "上传文件为空");
			}
			programService.dictUpgrade(fileList, deviceList);
			hzLoggerService.createSuccessLogger(userName, "字典升级", "系统升级", "用户“" + userName + "”升级字典成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (ProgramRuntimeException e) {
			logger.error("-- dictUpgrade ProgramRuntimeException error --", e);
			hzLoggerService.createFailedLogger(userName, "字典升级", "系统升级", e.getCode(), "用户“" + userName + "”升级字典失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (WxxRuntimeException e) {
			logger.error("-- dictUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "字典升级", "系统升级", e.getCode(), "用户“" + userName + "”升级字典失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- dictUpgrade Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "字典升级", "系统升级", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”升级字典失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		 }
	}

}
