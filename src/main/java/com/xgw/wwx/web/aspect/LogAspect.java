/*package com.xgw.wwx.web.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xgw.wwx.common.em.ModeType;
import com.xgw.wwx.common.em.UserType;
import com.xgw.wwx.common.helper.AuthHelper;
import com.xgw.wwx.common.helper.LogPathHelper;
import com.xgw.wwx.common.util.GsonUtil;
import com.xgw.wwx.dto.db.LogDTO;
import com.xgw.wwx.dto.db.UserDTO;
import com.xgw.wwx.service.CacheService;
import com.xgw.wwx.service.LogService;

@Component
@Aspect
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Autowired
	private LogService logService;

	@Autowired
	private CacheService cacheService;

	// 定义切点Pointcut
	@Pointcut("execution(* com.xgw.wwx.web.controller.*Controller.*(..))")
	public void excuteService() {

	}

	@Around("excuteService()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		LogDTO logDTO = null;
		boolean flag = true;
		try {
			String userName = AuthHelper.getUserName(request);
			String uri = request.getRequestURI();
			if (StringUtils.isNotBlank(userName) && cacheService.isHit(userName) && !LogPathHelper.isExclude(uri)) {
				logDTO = getLogDTO(request);
				logService.createLog(logDTO);
			}
		} catch (Exception e) {
			flag = false;
			logger.error("-- LogAspect createLog error --", e);
		}
		// 处理业务方法
		Object result = pjp.proceed();
		if (flag && null != logDTO) {
			logDTO.setStatus(1);
			logService.updateLog(logDTO);
		}
		if (null != logDTO) {
			// logger.info("-- RequestMessage: {} -- " + logDTO.toString());
		}
		return result;
	}

	public LogDTO getLogDTO(HttpServletRequest request) {
		LogDTO logDTO = new LogDTO();
		String userName = AuthHelper.getUserName(request);
		logDTO.setCreateUser(userName);
		logDTO.setUpdateUser(userName);
		logDTO.setUserName(userName);
		String userStr = cacheService.get(userName);
		UserDTO user = GsonUtil.GsonToBean(userStr, UserDTO.class);
		UserType userType = UserType.parse(user.getUserType());
		if (null != userType) {
			logDTO.setUserType(userType.getMessage());
		}
		String uri = request.getRequestURI();
		String method = request.getMethod();
		if ("DELETE".equals(method.toUpperCase())) {
			logDTO.setLevel("WARN");
		} else {
			logDTO.setLevel("INFO");
		}
		String queryString = request.getQueryString();
		logDTO.setStatus(0);
		logDTO.setUrl(uri);
		logDTO.setMethod(method);
		logDTO.setParams(queryString);
		logDTO.setModeType(ModeType.parse(uri).getDesc());
		return logDTO;
	}

}
*/