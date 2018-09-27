package com.xgw.wwx.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xgw.wwx.dto.db.StrategyDTO;
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
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.helper.AuthHelper;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.UserDTO;
import com.xgw.wwx.service.HzLoggerService;
import com.xgw.wwx.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	HzLoggerService hzLoggerService;

	@GetMapping("/find/{id}")
	public ResponseEntity<CommonResponseDTO<UserDTO>> getUserById(@PathVariable("id") Long id) {
		try {
			UserDTO userDTO = userService.getUserById(id);
			return ResponseEntity.ok(new CommonResponseDTO<UserDTO>(userDTO));
		} catch (WxxRuntimeException e) {
			logger.error("-- getUserById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<UserDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- getUserById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<UserDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/find/list")
	public ResponseEntity<CommonResponseDTO<PageInfo<UserDTO>>> findUsers(HttpServletRequest request, UserDTO userDTO) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("searchWord", userDTO.getSearchWord());
			params.put("userName", AuthHelper.getUserName(request));
			// 分页查询
			PageInfo<UserDTO> pageInfo = userService.findUsersByPage(userDTO.getPageSize(), userDTO.getPageNum(), params);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<UserDTO>>(pageInfo));
		} catch (WxxRuntimeException e) {
			logger.error("-- findUsers Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<UserDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findUsers Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<UserDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}


	@GetMapping("/list")
	public ResponseEntity<CommonResponseDTO<List<UserDTO>>> findUserList() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<UserDTO> users = userService.findUsers(params);
			return ResponseEntity.ok(new CommonResponseDTO<List<UserDTO>>(users));
		} catch (WxxRuntimeException e) {
			logger.error("-- findUserList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<UserDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findUserList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<UserDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/create")
	public ResponseEntity<CommonResponseDTO<Boolean>> createUser(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			userDTO.setCreateUser(userName);
			userService.createUser(userDTO);
			hzLoggerService.createSuccessLogger(userName, "创建用户", "用户模块", "用户“" + userName + "”创建用户“" + userDTO.getUserName() + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- createUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "创建用户", "用户模块", e.getCode(), "用户“" + userName + "”创建用户“" + userDTO.getUserName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- createUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "创建用户", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”创建用户“" + userDTO.getUserName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<CommonResponseDTO<Boolean>> updateUser(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			userDTO.setUpdateUser(userName);
			userService.updateUser(userDTO);

			hzLoggerService.createSuccessLogger(userName, "更新用户", "用户模块", "用户“" + userName + "”更新用户“" + userDTO.getUserName() + "”成功！");

			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- updateUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "更新用户", "用户模块", e.getCode(), "用户“" + userName + "”更新用户“" + userDTO.getUserName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- updateUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "更新用户", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”更新用户“" + userDTO.getUserName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteUser(HttpServletRequest request, @PathVariable("id") Long id) {
		String userName = AuthHelper.getUserName(request);
		UserDTO userDTO = null;
		try {
			userDTO = userService.getUserById(id);
			logger.info("-- {} , delete user userId:{} --", userName, id);
			userService.deleteUser(id);
			hzLoggerService.createSuccessLogger(userName, "删除用户", "用户模块", "用户“" + userName + "”删除用户“" + userDTO.getUserName() + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除用户", "用户模块", e.getCode(), "用户“" + userName + "”删除用户“" + userDTO.getUserName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deleteUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除用户", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”删除用户“" + userDTO.getUserName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/deleteUserAll")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteUserAll(HttpServletRequest request, @PathVariable("id") Long id) {
		String userName = AuthHelper.getUserName(request);
		UserDTO userDTO = null;
		// chooseIdbox  前台传过来的选择框
		String items = request.getParameter("chooseIdbox");
		List<String> delList = new ArrayList<String>();
		try {
			userDTO = userService.getUserById(id);
			String[] strs = items.split(",");
			for (String str : strs) {
				delList.add(str);
			}
			userService.deleteUserAll(delList);
			hzLoggerService.createSuccessLogger(userName, "删除用户", "用户模块", "用户“" + userName + "”删除用户“" + userDTO.getUserName() + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除用户", "用户模块", e.getCode(), "用户“" + userName + "”删除用户“" + userDTO.getUserName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deleteUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除用户", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”删除用户“" + userDTO.getUserName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/enable/{id}")
	public ResponseEntity<CommonResponseDTO<Boolean>> enableUser(HttpServletRequest request, @PathVariable("id") Long id) {
		String userName = AuthHelper.getUserName(request);
		UserDTO userDTO = null;
		try {
			userDTO = userService.getUserById(id);
			logger.info("-- {} , enable user userId:{} --", userName, id);
			userService.enableUser(id);

			hzLoggerService.createSuccessLogger(userName, "启用用户", "用户模块", "用户“" + userName + "”启用用户“" + userDTO.getUserName() + "”成功！");

			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- enableUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "启用用户", "用户模块", e.getCode(), "用户“" + userName + "”启用用户“" + userDTO.getUserName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- enableUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "启用用户", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”启用用户“" + userDTO.getUserName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/disable/{id}")
	public ResponseEntity<CommonResponseDTO<Boolean>> disableUser(HttpServletRequest request, @PathVariable("id") Long id) {
		String userName = AuthHelper.getUserName(request);
		UserDTO userDTO = null;
		try {
			userDTO = userService.getUserById(id);
			logger.info("-- {} , disable user userId:{} --", userName, id);
			userService.disabledUser(id);

			hzLoggerService.createSuccessLogger(userName, "禁用用户", "用户模块", "用户“" + userName + "”禁用用户“" + userDTO.getUserName() + "”成功！");

			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- disableUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "禁用用户", "用户模块", e.getCode(), "用户“" + userName + "”禁用用户“" + userDTO.getUserName() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- disableUser Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "禁用用户", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”禁用用户“" + userDTO.getUserName() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/resetPwd/{id}")
	public ResponseEntity<CommonResponseDTO<Boolean>> resetUserPwd(HttpServletRequest request, @PathVariable("id") Long id) {
		String userName = AuthHelper.getUserName(request);
		UserDTO userDTO = null;
		try {
			userDTO = userService.getUserById(id);
			logger.info("-- {} , resetUserPwd userId:{} --", userName, id);
			userService.resetPassWord(id);

			hzLoggerService.createSuccessLogger(userName, "重置密码", "用户模块", "用户“" + userDTO.getUserName() + "”重置密码成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- resetUserPwd Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "重置密码", "用户模块", e.getCode(), "用户“" + userDTO.getUserName() + "”重置密码失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- resetUserPwd Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "重置密码", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userDTO.getUserName() + "”重置密码失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/updatePwd")
	public ResponseEntity<CommonResponseDTO<Boolean>> updatePwd(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		String userName = AuthHelper.getUserName(request);
		try {

			userService.updatePwd(userDTO);
			hzLoggerService.createSuccessLogger(userName, "修改密码", "用户模块", "用户“" + userDTO.getUserName() + "”修改密码成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- updatePwd Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "修改密码", "用户模块", e.getCode(), "用户“" + userName + "”修改密码失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- updatePwd Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "修改密码", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”修改密码失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
