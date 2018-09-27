package com.xgw.wwx.config.schedule;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * quartz 实体类
 */

@Configuration
@EnableScheduling
public class QuartzConfig {

	@Autowired
	private SpringJobFactory springJobFactory;

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setOverwriteExistingJobs(true);
		schedulerFactoryBean.setQuartzProperties(quartzProperties());
		schedulerFactoryBean.setJobFactory(springJobFactory);
		return schedulerFactoryBean;
	}

	@Bean
	public Scheduler scheduler() throws IOException {
		return schedulerFactoryBean().getScheduler();
	}

	/**
	 * 加载quartz数据源配置
	 *
	 * @return
	 * @throws IOException
	 */
	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

}
