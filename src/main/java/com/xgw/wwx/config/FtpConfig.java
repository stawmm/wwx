package com.xgw.wwx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/ftp.properties")
public class FtpConfig {

}
