package com.xgw.wwx.common.constant;

import org.apache.commons.lang3.StringUtils;

public class WebUrlConstant {

	/************* 节点相关信息 ************/
	public static final String URL_NODE_REGISTER = "/node/register/{serverip}";
	public static final String URL_NODE_DELETE = "/node/delete/";
	public static final String URL_NODE_INFO = "/node/getinfo/";
	public static final String URL_NODE_CARD_STATUS = "/node/getcardstatus/{nodeType}";

	/************* 任务相关信息 ************/
	public static final String URL_TASK_CREATE = "/task/create/";
	public static final String URL_TASK_ADD_INFO = "/task/addcutinfo/";
	public static final String URL_TASK_STOP = "/task/stop/{type}";
	public static final String URL_TASK_STOP_FPGA = "/task/stop/fpga/{taskId}";
	public static final String URL_TASK_STOP_GPU = "/task/stop/gpu";
	public static final String URL_TASK_RUN = "/task/run/{type}";
	public static final String URL_TASK_STATUS = "/task/getstatus/{nodeType}/{taskId}";
	public static final String URL_TASK_STATUS_NUMBER = "/task/getstatus/{nodeType}/{taskId}/{cardNum}";
	public static final String URL_TASK_SUCCESS_PWD = "/task/getresult/{nodeType}/{taskId}";

	/** 程序升级相关信息 **/
	public static final String UPDATE = "/update/";
	public static final String URL_UPGRADE_FGPA = "/fpga/update/{path}";
	public static final String URL_UPGRADE_GPU = "/fpga/update/{path}";
	public static final String URL_UPGRADE_DICT = "/dict/update/{path}";

	/** 请求url参数 **/
	public static final String REQUEST_URL = "http://%s:%s";

	/** 请求结果头 **/
	public static final String REQUEST_SUCCESS = "success";
	public static final String REQUEST_FAILED = "failed";

	/**
	 * 获取请求的url
	 * 
	 * @param ip
	 * @param port
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getRequestUrl(String path, String ip, int port, String... params) {
		String url = String.format(REQUEST_URL, ip, port) + path;
		for (String param : params) {
			if (StringUtils.isNotBlank(param)) {
				url = url.replaceFirst("\\{[\\w]+\\}", param);
			}
		}
		return url;
	}

}
