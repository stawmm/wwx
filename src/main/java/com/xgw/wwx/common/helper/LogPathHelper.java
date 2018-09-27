package com.xgw.wwx.common.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class LogPathHelper {

	public static List<String> excludePaths = new ArrayList<String>();

	static {
		excludePaths.add("/wwx/logs/find/list");
		excludePaths.add("/wwx/system/dictmap/list");
		excludePaths.add("/wwx/login/userNameCheck");
	}

	public static boolean isExclude(String path) {
		for (String excludePath : excludePaths) {
			if (StringUtils.startsWith(path, excludePath)) {
				return true;
			}
		}
		return false;
	}

}
