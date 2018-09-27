package com.xgw.wwx.common.util;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.dto.request.BaseRespDTO;

public class HttpUtil {

	private static final String CHARSET_UTF8 = "UTF-8";

	private static final String APPLICATION_JSON = "application/json";

	private static final String APPLICATION_FORM = "application/x-www-form-urlencoded";

	private static RequestConfig requestConfig = null;

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	static {
		// 设置请求和传输超时时间
		requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
	}

	public static <T extends BaseRespDTO> T doHttpGet(String url, Class<T> t) {
		logger.info("-- HttpUtil doHttpGet url:{} --", url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
			logger.info("-- HttpUtil doHttpGet result:{} --", result);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return GsonUtil.GsonToBean(result, t);
			} else {
				logger.error("-- response message --", result);
				throw new WxxRuntimeException("HTTP_STATUS_ERROER", "请求状态码异常");
			}
		} catch (WxxRuntimeException e) {
			logger.error("-- WxxRuntimeException --", e);
			throw e;
		} catch (Exception e) {
			logger.error("HTTP_ERROER,请求异常", e);
			throw new WxxRuntimeException("HTTP_ERROER", "请求异常");
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				httpClient.close();
			} catch (Exception ex) {
				logger.error("HTTP_CLOSE_ERROER,请求资源关闭异常", ex);
				throw new WxxRuntimeException("HTTP_CLOSE_ERROER", "请求资源关闭异常");
			}
		}
	}

	public static <T extends BaseRespDTO> T doHttpGet(String url, Map<String, String> params, Class<T> t) {
		logger.info("-- HttpUtil doHttpGet url:{} --", url);
		logger.info("-- HttpUtil doHttpGet params:{} --", GsonUtil.GsonString(params));
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			URI uri = getRequestUri(url, params);
			HttpGet httpGet = new HttpGet(uri);
			response = httpClient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
			logger.info("-- HttpUtil doHttpGet result:{} --", result);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return GsonUtil.GsonToBean(result, t);
			} else {
				logger.error("-- response message --", result);
				throw new WxxRuntimeException("HTTP_STATUS_ERROER", "请求状态码异常");
			}
		} catch (WxxRuntimeException e) {
			logger.error("-- WxxRuntimeException --", e);
			throw e;
		} catch (Exception e) {
			logger.error("HTTP_ERROER,请求异常", e);
			throw new WxxRuntimeException("HTTP_ERROER", "请求异常");
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				httpClient.close();
			} catch (Exception ex) {
				logger.error("HTTP_CLOSE_ERROER,请求资源关闭异常", ex);
				throw new WxxRuntimeException("HTTP_CLOSE_ERROER", "请求资源关闭异常");
			}
		}
	}

	public static <T extends BaseRespDTO> T doHttpPostJson(String url, String jsonBody, Class<T> t) {
		logger.info("-- HttpUtil doHttpPostJson url:{} --", url);
		logger.info("-- HttpUtil doHttpPostJson jsonBody:{} --", jsonBody);

		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			if (StringUtils.isNotBlank(jsonBody)) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonBody, CHARSET_UTF8);
				entity.setContentEncoding(CHARSET_UTF8);
				entity.setContentType(APPLICATION_JSON);
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			String result = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
			logger.info("-- HttpUtil doHttpPostJson result:{} --", result);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return GsonUtil.GsonToBean(result, t);
			} else {
				logger.error("-- response message --", result);
				throw new WxxRuntimeException("HTTP_STATUS_ERROER", "请求状态码异常");
			}
		} catch (WxxRuntimeException e) {
			logger.error("-- WxxRuntimeException --", e);
			throw e;
		} catch (Exception e) {
			logger.error("HTTP_ERROER,请求异常", e);
			throw new WxxRuntimeException("HTTP_ERROER", "请求异常");
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				httpClient.close();
			} catch (Exception ex) {
				logger.error("HTTP_CLOSE_ERROER,请求资源关闭异常", ex);
				throw new WxxRuntimeException("HTTP_CLOSE_ERROER", "请求资源关闭异常");
			}
		}
	}

	public static <T> T doHttpPost(String url, String jsonBody, Class<T> t) {
		logger.info("-- HttpUtil doHttpPostJson url:{} --", url);
		logger.info("-- HttpUtil doHttpPostJson jsonBody:{} --", jsonBody);

		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			if (StringUtils.isNotBlank(jsonBody)) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonBody, CHARSET_UTF8);
				entity.setContentEncoding(CHARSET_UTF8);
				entity.setContentType(APPLICATION_JSON);
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			String result = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
			logger.info("-- HttpUtil doHttpPostJson result:{} --", result);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return GsonUtil.GsonToBean(result, t);
			} else {
				logger.error("-- response message --", result);
				throw new WxxRuntimeException("HTTP_STATUS_ERROER", "请求状态码异常");
			}
		} catch (WxxRuntimeException e) {
			logger.error("-- WxxRuntimeException --", e);
			throw e;
		} catch (Exception e) {
			logger.error("HTTP_ERROER,请求异常", e);
			throw new WxxRuntimeException("HTTP_ERROER", "请求异常");
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				httpClient.close();
			} catch (Exception ex) {
				logger.error("HTTP_CLOSE_ERROER,请求资源关闭异常", ex);
				throw new WxxRuntimeException("HTTP_CLOSE_ERROER", "请求资源关闭异常");
			}
		}
	}

	public static <T extends BaseRespDTO> T doHttpPostForm(String url, String urlParam, Class<T> t) {
		logger.info("-- HttpUtil doHttpPostForm url:{} --", url);
		logger.info("-- HttpUtil doHttpPostForm urlParam:{} --", urlParam);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			if (StringUtils.isNotBlank(urlParam)) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(urlParam, CHARSET_UTF8);
				entity.setContentEncoding(CHARSET_UTF8);
				entity.setContentType(APPLICATION_FORM);
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			String result = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
			logger.info("-- HttpUtil doHttpPostForm result:{} --", result);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return GsonUtil.GsonToBean(result, t);
			} else {
				logger.error("-- response message --", result);
				throw new WxxRuntimeException("HTTP_STATUS_ERROER", "请求状态码异常");
			}
		} catch (WxxRuntimeException e) {
			logger.error("-- WxxRuntimeException --", e);
			throw e;
		} catch (Exception e) {
			logger.error("HTTP_ERROER,请求异常", e);
			throw new WxxRuntimeException("HTTP_ERROER", "请求异常");
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				httpClient.close();
			} catch (Exception ex) {
				logger.error("HTTP_CLOSE_ERROER,请求资源关闭异常", ex);
				throw new WxxRuntimeException("HTTP_CLOSE_ERROER", "请求资源关闭异常");
			}
		}
	}

	private static URI getRequestUri(String url, Map<String, String> params) throws Exception {
		URIBuilder builder = new URIBuilder(url);
		if (null != params && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				builder.addParameter(entry.getKey(), entry.getValue());
			}
		}
		return builder.build();
	}

}
