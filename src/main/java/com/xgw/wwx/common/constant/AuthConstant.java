package com.xgw.wwx.common.constant;

import java.util.Arrays;
import java.util.List;

public class AuthConstant {

	public static final String AUTH_WEB_URL = "http://localhost:8096/auth";

	public static final String USER_NAME = "userName";

	public static final String PASS_WORD = "passWord";

	public static final String AUTH_USER = "AuthUser";

	public static final List<String> EXCLUDE_URLS = Arrays.asList(new String[] { "/open", "/file", "/login","/open", "/swagger", "/v2", "/images", "/configuration", "/doc.html", "/webjars", "/docs.html", "/druid", "/api", "/sql" });

}
