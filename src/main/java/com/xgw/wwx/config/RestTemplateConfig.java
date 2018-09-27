package com.xgw.wwx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate配置类
 */
@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		//设置请求头

		/* HttpHeaders headers = new HttpHeaders();
		 MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		 headers.setContentType(type);
		 headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		 JSONObject jsonObj = JSONObject.parseObject(paras);
		 HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
		 String result = restTemplate.postForObject(url, formEntity, String.class);*/

		return new RestTemplate(factory);
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);// 单位为ms
		factory.setConnectTimeout(5000);// 单位为ms
		return factory;
	}

}
