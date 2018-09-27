package com.xgw.wwx.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.code.UserCodeEnum;
import com.xgw.wwx.common.constant.AuthConstant;
import com.xgw.wwx.common.exception.UserRuntimeException;
import com.xgw.wwx.common.util.GsonUtil;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.service.CacheService;

/**
 * AuthFilter 过滤器
 */
@WebFilter(urlPatterns = "/*", filterName = "indexFilter")
public class AuthFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	@Autowired
	private CacheService cacheService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("-- AuthFilter init --");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 设置允许跨域访问的域，*表示支持所有的来源
	/*	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization，Content-Type");*/


		String strContextPath = request.getSession().getServletContext().getContextPath();
		String uri = request.getRequestURI().substring(strContextPath.length());
		// OPTIONS
		if (StringUtils.isNotBlank(request.getMethod()) && request.getMethod().equals(RequestMethod.OPTIONS.toString())) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		// 排除的url
		for (String excludeUrl : AuthConstant.EXCLUDE_URLS) {
			if (uri.indexOf(excludeUrl) != -1) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}
		try {
		    //判断userName 是否为空 或 长度为0 或 由空白符构成
			String userName = request.getHeader("AuthUser");
			if (StringUtils.isBlank(userName) || !cacheService.isHit(userName)) {
				throw new UserRuntimeException(UserCodeEnum.USER_SESSION_TIMEOUT);
			}
			logger.debug("-- AuthFilter Auth Success, userName:{}, url:{} --", userName, uri);
		} catch (UserRuntimeException e) {
			logger.error("-- AuthFilter Auth Error, UserRuntimeException:{}, url:{} --", e.getCode(), uri);
			response.getWriter().write(GsonUtil.GsonString(new CommonResponseDTO<UserCodeEnum>(UserCodeEnum.USER_SESSION_TIMEOUT, false)));
			return;
		} catch (Exception e) {
			logger.error("-- AuthFilter Auth Error, Exception:{}, url:{} --", ExceptionUtils.getRootCauseMessage(e), uri);
			response.getWriter().write(GsonUtil.GsonString(new CommonResponseDTO<BaseCodeEnum>(BaseCodeEnum.SYSTEM_ERROR, false)));
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		logger.debug("-- AuthFilter destroy --");
	}
}
