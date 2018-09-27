package com.xgw.wwx.common.helper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.xgw.wwx.common.constant.AuthConstant;
import com.xgw.wwx.dto.db.UserDTO;

public class AuthHelper {

	public static boolean loginUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (null != session) {
			Object userName = session.getAttribute(AuthConstant.USER_NAME);
			if (null != userName) {
				return true;
			}
		}
		String userName = request.getHeader(AuthConstant.AUTH_USER);
		if (StringUtils.isNotBlank(userName)) {
			return true;
		}
		return false;
	}

	public static String getUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (null != session) {
			Object userName = session.getAttribute(AuthConstant.USER_NAME);
			if (null != userName) {
				return userName.toString();
			}
		}
		String userName = request.getHeader(AuthConstant.AUTH_USER);
		if (StringUtils.isNotBlank(userName)) {
			return userName;
		}
		return null;
	}

	public static void setUserInfo(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {
		HttpSession session = request.getSession();
		if (null != session) {
			session.setAttribute(AuthConstant.USER_NAME, userDTO.getUserName());
			session.setAttribute(AuthConstant.PASS_WORD, userDTO.getPassWord());
			session.setMaxInactiveInterval(60 * 60);

			Cookie cookie = new Cookie("JSESSIONID", session.getId());
			// 设置cookie的有效路径
			cookie.setPath(request.getContextPath());
			response.addCookie(cookie);
		}
	}

	public static void clearUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (null != session) {
			session.removeAttribute(AuthConstant.USER_NAME);
			session.removeAttribute(AuthConstant.PASS_WORD);
		}
	}

}
