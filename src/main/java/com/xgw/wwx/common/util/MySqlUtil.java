package com.xgw.wwx.common.util;

import org.apache.commons.lang3.StringUtils;

public class MySqlUtil {

	/**
	 * 处理模糊查询特殊字符
	 *
	 * @param likeChar
	 * @return
	 */
	public static String replaceSpecialChar4Like(String likeChar) {
		String newStr = "";
		if (StringUtils.isNotBlank(likeChar)) {
			String[] strArr = likeChar.split("");
			for (String s : strArr) {
				if (s.equals("%")) {
					s = s.replace("%", "\\%");
				}
				if (s.equals("\\")) {
					s = s.replace("\\", "\\\\\\\\");
				}
				if (s.equals("_")) {
					s = s.replace("_", "\\_");
				}
				if (s.equals("\'")) {
					s = s.replace("\'", "\\'");
				}
				newStr = newStr + s;
			}
		}
		return newStr;
	}

}
