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
import com.xgw.wwx.dto.db.StrategyDTO;
import com.xgw.wwx.service.HzLoggerService;
import com.xgw.wwx.service.StrategyService;

@RestController
@RequestMapping("/strategy")
public class StrategyController {

	private static final Logger logger = LoggerFactory.getLogger(StrategyController.class);

	@Autowired
	private StrategyService strategyService;

	@Autowired
	HzLoggerService hzLoggerService;

	@GetMapping("/find/{id}")
	public ResponseEntity<CommonResponseDTO<StrategyDTO>> getStrategyById(@PathVariable("id") Long id) {
		try {
			logger.info("-- info message, id={} --", id);
			StrategyDTO strategyDTO = strategyService.getStrategyById(id);
			return ResponseEntity.ok(new CommonResponseDTO<StrategyDTO>(strategyDTO));
		} catch (WxxRuntimeException e) {
			logger.error("-- getStrategyById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<StrategyDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- getStrategyById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<StrategyDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}


	@GetMapping("/find/list")
	public ResponseEntity<CommonResponseDTO<PageInfo<StrategyDTO>>> findStrategysByPage(HttpServletRequest request, StrategyDTO strategyDTO) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("searchWord", strategyDTO.getSearchWord());
			// 分页查询
			PageInfo<StrategyDTO> pageInfo = strategyService.findStrategysByPage(strategyDTO.getPageSize(), strategyDTO.getPageNum(), params);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<StrategyDTO>>(pageInfo));
		} catch (WxxRuntimeException e) {
			logger.error("-- findStrategysByPage Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<StrategyDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findStrategysByPage Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<StrategyDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/list")
	public ResponseEntity<CommonResponseDTO<List<StrategyDTO>>> findStrategyList(HttpServletRequest request) {
		try {
			String userName = AuthHelper.getUserName(request);
			List<StrategyDTO> strategys = strategyService.findStrategys(userName);
			return ResponseEntity.ok(new CommonResponseDTO<List<StrategyDTO>>(strategys));
		} catch (WxxRuntimeException e) {
			logger.error("-- findStrategyList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<StrategyDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findStrategyList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<StrategyDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/taskId/list")
	public ResponseEntity<CommonResponseDTO<List<StrategyDTO>>> findStrategysByTaskId(@RequestParam("taskId") Long taskId) {
		try {
			List<StrategyDTO> strategys = strategyService.findStrategysByTaskId(taskId);
			return ResponseEntity.ok(new CommonResponseDTO<List<StrategyDTO>>(strategys));
		} catch (WxxRuntimeException e) {
			logger.error("-- findStrategysByTaskId Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<StrategyDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findStrategysByTaskId Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<StrategyDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/create")
	public ResponseEntity<CommonResponseDTO<StrategyDTO>> createStrategy(HttpServletRequest request, @RequestBody StrategyDTO strategyDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			strategyDTO.setCreateUser(userName);
			strategyService.createStrategy(strategyDTO);
			hzLoggerService.createSuccessLogger(userName, "创建字典", "字典模块", "用户“" + userName + "”创建字典“" + strategyDTO.getName() + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<StrategyDTO>(strategyDTO));
		} catch (WxxRuntimeException e) {
			logger.error("-- createStrategy Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "创建字典", "字典模块", e.getCode(), "用户“" + userName + "”创建字典“" + strategyDTO.getName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<StrategyDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- createStrategy Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "创建字典", "字典模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”创建字典“" + strategyDTO.getName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<StrategyDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<CommonResponseDTO<Boolean>> updateStrategy(HttpServletRequest request, @RequestBody StrategyDTO strategyDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			strategyDTO.setUpdateUser(userName);
			strategyService.updateStrategy(strategyDTO);

			hzLoggerService.createSuccessLogger(userName, "更新字典", "字典模块", "用户“" + userName + "”更新字典“" + strategyDTO.getName() + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- updateStrategy Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "更新字典", "字典模块", e.getCode(), "用户“" + userName + "”更新字典“" + strategyDTO.getName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- updateStrategy Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "更新字典", "字典模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”更新字典“" + strategyDTO.getName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteStrategy(HttpServletRequest request, @PathVariable("id") Long id) {
		String userName = AuthHelper.getUserName(request);
		StrategyDTO strategyDTO = null;
		try {
			strategyDTO = strategyService.getStrategyById(id);
			logger.info("-- {} , delete strategy userId:{} --", userName, id);
			strategyService.deleteStrategy(id);

			hzLoggerService.createSuccessLogger(userName, "删除字典", "字典模块", "用户“" + userName + "”删除字典“" + strategyDTO.getName() + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteStrategy Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除字典", "字典模块", e.getCode(), "用户“" + userName + "”删除字典“" + strategyDTO.getName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deleteStrategy Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除字典", "字典模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”删除字典“" + strategyDTO.getName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/batchDeleteStrategy")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteStrategyAll(HttpServletRequest request,@RequestParam("ids") int[] ids) {
		String userName = AuthHelper.getUserName(request);
	/*	Long strategyId = Long.parseLong(request.getParameter("id"));
		StrategyDTO strategyDTO = null;*/
		try {
//			strategyDTO = strategyService.getStrategyById(strategyId);
			strategyService.deleteStrategyAll(ids);
			hzLoggerService.createSuccessLogger(userName, "删除策略", "策略模块", "用户“" + userName + "”删除策略“"   + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteStrategy Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除策略", "策略模块", e.getCode(), "用户“" + userName + "”删除策略“"  + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e){
			logger.error("-- deleteStrategy Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除策略", "策略模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”删除策略“"  + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}
	
	@GetMapping(value = "/type/list")
	public ResponseEntity<CommonResponseDTO<List<Map<String, Object>>>> findStrategyType() {
		try {
			List<Map<String, Object>> strategyTypes = strategyService.findStrategyType();
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(strategyTypes));
		} catch (WxxRuntimeException e) {
			logger.error("-- findStrategyType Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findStrategyType Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<Map<String, Object>>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}
	
}
