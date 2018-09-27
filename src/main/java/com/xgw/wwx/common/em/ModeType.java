package com.xgw.wwx.common.em;

import org.apache.commons.lang3.StringUtils;

public enum ModeType {

	DEVICE("/wwx/device", "设备管理"),

	DICT("/wwx/dict", "字典管理"),

	SYSTEM("/wwx/system", "系统管理"),

	HISTORY("/wwx/history", "历史任务"),

	LOG("/wwx/logs", "日志管理"),

	PROGRAM("/wwx/program", "应用程序管理"),

	STRATEGY("/wwx/strategy", "策略管理"),

	TASK("/wwx/task", "任务管理"),

	USER("/wwx/user", "用户管理"),

	LOGIN("/wwx/login/check", "登陆"),

	LOGOUT("/wwx/login/exit", "登出"),

	;

	private String code;

	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private ModeType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static ModeType parse(String code) {
		for (ModeType _code : ModeType.values()) {
			if (StringUtils.startsWithIgnoreCase(code, _code.getCode())) {
				return _code;
			}
		}
		return ModeType.SYSTEM;
	}

}
