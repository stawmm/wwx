package com.xgw.wwx.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.code.UserCodeEnum;
import com.xgw.wwx.common.exception.UserRuntimeException;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.util.GsonUtil;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.UserDTO;
import com.xgw.wwx.service.CacheService;
import com.xgw.wwx.service.HzLoggerService;
import com.xgw.wwx.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private HzLoggerService hzLoggerService;

	@Autowired
	private UserService userService;

	@Autowired
	private CacheService cacheService;

	@PostMapping("/check")
	public ResponseEntity<CommonResponseDTO<UserDTO>> loginCheck(@RequestBody UserDTO userDTO, HttpServletResponse response, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(userDTO.getUserName())) {
				throw new UserRuntimeException(UserCodeEnum.USER_USERNAME_EMPTY);
			}
			if (StringUtils.isBlank(userDTO.getPassWord())) {
				throw new UserRuntimeException(UserCodeEnum.USER_PASSWORD_EMPTY);
			}
			UserDTO dbUser = userService.getUserByName(userDTO.getUserName());
			if (null == dbUser) {
				throw new UserRuntimeException(UserCodeEnum.USER_USERNAME_NOT_EXIST);
			}
			if (0 == dbUser.getStatus().intValue()) {
				throw new UserRuntimeException(UserCodeEnum.USER_STATUS_DISABLED);
			}
			// String passWord = PasswordUtil.md5Hex(userDTO.getPassWord(),
			// dbUser.getSalt());
			if (!StringUtils.equalsIgnoreCase(userDTO.getPassWord(), dbUser.getPassWord())) {
				throw new UserRuntimeException(UserCodeEnum.USER_PASSWORD_ERROR);
			}
			// 记录Session
			// AuthHelper.setUserInfo(request, response, userDTO);
			cacheService.put(userDTO.getUserName(), GsonUtil.GsonString(dbUser));

			// 用户登录成功日志
			hzLoggerService.createSuccessLogger(userDTO.getUserName(), "用户登录", "用户模块", "用户“" + userDTO.getUserName() + "”登录成功！");

			return ResponseEntity.ok(new CommonResponseDTO<UserDTO>(dbUser));
		} catch (UserRuntimeException e) {
			logger.error("-- loginCheck UserRuntimeException error --", e);

			// 用户登录成功日志
			hzLoggerService.createFailedLogger(userDTO.getUserName(), "用户登录", "用户模块", e.getCode(), "用户“" + userDTO.getUserName() + "”登录失败！错误：" + e.getMessage());

			return ResponseEntity.ok(new CommonResponseDTO<UserDTO>(e.getCode(), e.getMessage()));
		} catch (WxxRuntimeException e) {
			logger.error("-- loginCheck Exception error --", e);

			// 用户登录成功日志
			hzLoggerService.createFailedLogger(userDTO.getUserName(), "用户登录", "用户模块", e.getCode(), "用户“" + userDTO.getUserName() + "”登录失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<UserDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- loginCheck Exception error --", e);
			// 用户登录成功日志
			hzLoggerService.createFailedLogger(userDTO.getUserName(), "用户登录", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userDTO.getUserName() + "”登录失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<UserDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/exit/{userName}")
	public ResponseEntity<CommonResponseDTO<Boolean>> loginExit(HttpServletRequest request, @PathVariable("userName") String userName) {
		try {
			cacheService.remove(userName);
			hzLoggerService.createSuccessLogger(userName, "用户安全登出", "用户模块", "用户“" + userName + "”用户安全登出成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (WxxRuntimeException e) {
			logger.error("-- loginExit Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "用户安全登出", "用户模块", e.getCode(), "用户“" + userName + "”用户安全登出失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- loginExit Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "用户安全登出", "用户模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”用户安全登出失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/userNameCheck")
	public ResponseEntity<CommonResponseDTO<Boolean>> userNameCheck(HttpServletRequest request) {
		try {
			String headerUserName = request.getHeader("AuthUser");
			if (StringUtils.isNotBlank(headerUserName) && cacheService.isHit(headerUserName)) {
				return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
			}
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.FALSE));
		} catch (WxxRuntimeException e) {
			logger.error("-- userNameCheck Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- userNameCheck Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
