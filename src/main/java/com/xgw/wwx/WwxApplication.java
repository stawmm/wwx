package com.xgw.wwx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
@MapperScan({"com.xgw.wwx.mapper"})  
@EnableSwagger2
@EnableCaching
public class WwxApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WwxApplication.class, args);
	}
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(WwxApplication.class);
	}
}
