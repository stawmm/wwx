package com.xgw.wwx.service;

import java.util.Map;

import com.xgw.wwx.dto.request.BaseRespDTO;

public interface RequestService {

	public <T extends BaseRespDTO> T doHttpGet(String url, Class<T> t);

	public <T extends BaseRespDTO> T doHttpGet(String url, Map<String, String> params, Class<T> t);

	public <T extends BaseRespDTO> T doHttpPostJson(String url, String jsonBody, Class<T> t);

	public <T extends BaseRespDTO> T doHttpPostForm(String url, String urlParam, Class<T> t);

}
