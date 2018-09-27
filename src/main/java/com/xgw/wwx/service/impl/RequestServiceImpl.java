package com.xgw.wwx.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.xgw.wwx.common.util.HttpUtil;
import com.xgw.wwx.dto.request.BaseRespDTO;
import com.xgw.wwx.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {

	@Override
	public <T extends BaseRespDTO> T doHttpGet(String url, Class<T> t) {
		return HttpUtil.doHttpGet(url, t);
	}

	@Override
	public <T extends BaseRespDTO> T doHttpGet(String url, Map<String, String> params, Class<T> t) {
		return HttpUtil.doHttpGet(url, params, t);
	}

	@Override
	public <T extends BaseRespDTO> T doHttpPostJson(String url, String jsonBody, Class<T> t) {
		return HttpUtil.doHttpPostJson(url, jsonBody, t);
	}

	@Override
	public <T extends BaseRespDTO> T doHttpPostForm(String url, String urlParam, Class<T> t) {
		return HttpUtil.doHttpPostJson(url, urlParam, t);
	}

}
