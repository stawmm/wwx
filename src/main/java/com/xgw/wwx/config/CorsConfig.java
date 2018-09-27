package com.xgw.wwx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Web开发经常会遇到跨域问题，解决方案有：jsonp，iframe，CORS等等。
 * pring Boot使用CORS解决跨域问题
 * CORS 与 JSONP 相比：
 * 1、 JSONP 只能实现 GET 请求，而 CORS 支持所有类型的 HTTP 请求。
 * 2、 使用 CORS，开发者可以使用普通的 XMLHttpRequest 发起请求和获得数据，比起 JSONP 有更好的 错误处理。
 * 3、 JSONP 主要被老的浏览器支持，它们往往不支持 CORS，而绝大多数现代浏览器都已经支持了 CORS。
 *
 *
 *
 * 局配置跨域问题
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

        // 设置了可以被跨域访问的路径和可以被哪些主机跨域访问
		registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowCredentials(false).maxAge(3600);
	}

}
