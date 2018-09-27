package com.xgw.wwx.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.helper.AuthHelper;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.request.CardStatusRespDTO;
import com.xgw.wwx.service.DeviceService;
import com.xgw.wwx.service.HzLoggerService;

@RestController
@RequestMapping("/device")
public class DeviceController {

	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private HzLoggerService hzLoggerService;

	@GetMapping("/find/{id}")
	public ResponseEntity<CommonResponseDTO<DeviceDTO>> getDeviceById(@PathVariable("id") Long id) {
		try {
			DeviceDTO deviceDTO = deviceService.getDeviceById(id);
			return ResponseEntity.ok(new CommonResponseDTO<DeviceDTO>(deviceDTO));
		} catch (WxxRuntimeException e) {
			logger.error("-- getDeviceById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<DeviceDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- getDeviceById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<DeviceDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/list")
	public ResponseEntity<CommonResponseDTO<List<DeviceDTO>>> findDeviceList() {
		try {
			List<DeviceDTO> devices = deviceService.findAllDevices();
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(devices));
		} catch (WxxRuntimeException e) {
			logger.error("-- findDeviceList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findDeviceList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}
	
	@GetMapping("/alive/list")
	public ResponseEntity<CommonResponseDTO<List<DeviceDTO>>> findAliveDeviceList() {
		try {
			List<DeviceDTO> devices = deviceService.findAliveDevices();
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(devices));
		} catch (WxxRuntimeException e) {
			logger.error("-- findAliveDevices Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findAliveDevices Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/dict/list")
	public ResponseEntity<CommonResponseDTO<List<DeviceDTO>>> findDeviceListByDictId(@RequestParam("dictId") Long dictId) {
		try {
			List<DeviceDTO> devices = deviceService.findDevicesByDictId(dictId);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(devices));
		} catch (WxxRuntimeException e) {
			logger.error("-- findDeviceListByDictId Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findDeviceListByDictId Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/nodeType/list")
	public ResponseEntity<CommonResponseDTO<List<DeviceDTO>>> findDevicesByNodeType(@RequestParam("nodeType") String nodeType) {
		try {
			List<DeviceDTO> devices = deviceService.findDevicesByNodeType(nodeType);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(devices));
		} catch (WxxRuntimeException e) {
			logger.error("-- findDevicesByNodeType Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findDevicesByNodeType Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}
	
	@GetMapping("/nodeType/alive/list")
	public ResponseEntity<CommonResponseDTO<List<DeviceDTO>>> findAliveDevicesByNodeType(@RequestParam("nodeType") String nodeType) {
		try {
			List<DeviceDTO> devices = deviceService.findAliveDevicesByNodeType(nodeType);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(devices));
		} catch (WxxRuntimeException e) {
			logger.error("-- findAliveDevicesByNodeType Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findAliveDevicesByNodeType Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}
	
	

	@PostMapping("/create")
	public ResponseEntity<CommonResponseDTO<Boolean>> registerDevice(HttpServletRequest request, @RequestBody DeviceDTO deviceDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			deviceDTO.setCreateUser(userName);
			deviceService.createDevice(deviceDTO);
			
			hzLoggerService.createSuccessLogger(userName, "添加设备", "设备模块", "用户“" + userName + "”添加设备“"+deviceDTO.getName()+"”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- registerDevice Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "添加设备", "设备模块",e.getCode(), "用户“" + userName + "”添加设备“"+deviceDTO.getName()+"”失败！错误："+e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- registerDevice Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "添加设备", "设备模块",BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”添加设备“"+deviceDTO.getName()+"”失败！错误："+BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/batch/create")
	public ResponseEntity<CommonResponseDTO<Boolean>> searchDevice(HttpServletRequest request, @RequestBody List<DeviceDTO> devices) {
		try {
			String userName = AuthHelper.getUserName(request);
			for (DeviceDTO deviceDTO : devices) {
				deviceDTO.setCreateUser(userName);
				deviceService.createDevice(deviceDTO);
			}
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- searchDevice Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- searchDevice Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<CommonResponseDTO<Boolean>> updateDevice(HttpServletRequest request, @RequestBody DeviceDTO deviceDTO) {
		String updateUser = AuthHelper.getUserName(request);
		try {
			deviceDTO.setUpdateUser(updateUser);
			deviceService.updateDevice(deviceDTO);
			hzLoggerService.createSuccessLogger(updateUser, "更新设备", "设备模块", "用户“" + updateUser + "”更新设备“"+deviceDTO.getName()+"”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- updateDevice Exception error --", e);
			hzLoggerService.createFailedLogger(updateUser, "更新设备", "设备模块",e.getCode(), "用户“" + updateUser + "”添加设备“"+deviceDTO.getName()+"”失败！错误："+e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- updateDevice Exception error --", e);
			hzLoggerService.createFailedLogger(updateUser, "更新设备", "设备模块",BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + updateUser + "”添加设备“"+deviceDTO.getName()+"”失败！错误："+BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteDevice(HttpServletRequest request,@PathVariable("id") Long id) {
		String userName = AuthHelper.getUserName(request);
		DeviceDTO deviceDTO = null;
		try {
			deviceDTO = deviceService.getDeviceById(id);

			deviceService.deleteDevice(id);
			hzLoggerService.createSuccessLogger(userName, "刪除设备", "设备模块", "用户“" + userName + "”刪除设备“"+deviceDTO.getName()+"”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteDevice Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "刪除设备", "设备模块",e.getCode(), "用户“" + userName + "”添加设备“"+deviceDTO.getName()+"”失败！错误："+e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deleteDevice Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "刪除设备", "设备模块",BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”添加设备“"+deviceDTO.getName()+"”失败！错误："+BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/find/list")
	public ResponseEntity<CommonResponseDTO<PageInfo<DeviceDTO>>> findDevices(HttpServletRequest request, DeviceDTO deviceDTO) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("searchWord", deviceDTO.getSearchWord());
			Integer pageNum = deviceDTO.getPageNum();
			Integer pageSize = deviceDTO.getPageSize();
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<DeviceDTO>>(deviceService.findDevicesByPage(pageSize, pageNum, params)));
		} catch (WxxRuntimeException e) {
			logger.error("-- findDevices Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<DeviceDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findDevices Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<DeviceDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/find/search/list")
	public ResponseEntity<CommonResponseDTO<List<DeviceDTO>>> findSearchDevices(HttpServletRequest request, @RequestParam("ips") String ips) {
		try {
			String[] ipArray = ips.split("-");
			String startIp = ipArray[0];
			String endIp = ipArray[1];
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(deviceService.findSearchDevices(startIp, endIp)));
		} catch (WxxRuntimeException e) {
			logger.error("-- findSearchDevices Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findSearchDevices Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DeviceDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/card/status/{id}")
	public ResponseEntity<CommonResponseDTO<List<CardStatusRespDTO>>> deviceCardStatus(HttpServletRequest request, @PathVariable("id") Long id) {
		try {
			List<CardStatusRespDTO> statusList = deviceService.getDeviceCardStatus(id);
			return ResponseEntity.ok(new CommonResponseDTO<List<CardStatusRespDTO>>(statusList));
		} catch (WxxRuntimeException e) {
			logger.error("-- deviceCardStatus Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<CardStatusRespDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deviceCardStatus Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<CardStatusRespDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
