package com.xgw.wwx.startup;

import com.xgw.wwx.config.job.ReadTaskFileJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xgw.wwx.config.job.HistoryTaskJob;
import com.xgw.wwx.config.job.TaskSchedulerJob;

/**
 * Created by EX-ZHONGJUN001 on 2018/4/4.
 */
@Component
public class ScheduleRunner implements CommandLineRunner {

	@Autowired
	private Scheduler scheduler;

	@Value("${wwx.task.schedule.cron}")
	private String taskScheduleCron;
	
	@Override
	public void run(String... strings) throws Exception {
		// 调度任务
		JobDetail taskJob = JobBuilder.newJob(TaskSchedulerJob.class).withIdentity("taskSchedulerJob", "tasktaskSchedulerGroup").build();
		CronScheduleBuilder taskJobCron = CronScheduleBuilder.cronSchedule(taskScheduleCron).withMisfireHandlingInstructionDoNothing();
		CronTrigger taskJobTrigger = TriggerBuilder.newTrigger().withIdentity("taskSchedulerTriger").withSchedule(taskJobCron).build();
		
		// 历史任务
		JobDetail historyJob = JobBuilder.newJob(HistoryTaskJob.class).withIdentity("historyJob", "historGroup").build();
		CronScheduleBuilder historyCron = CronScheduleBuilder.cronSchedule("0 0/15 * * * ?").withMisfireHandlingInstructionDoNothing();
		CronTrigger historyTrigger = TriggerBuilder.newTrigger().withIdentity("historyTrigger").withSchedule(historyCron).build();

		//读取任务文件
		JobDetail readJob = JobBuilder.newJob(ReadTaskFileJob.class).withIdentity("readTaskFileJob", "readTaskFileGroup").build();
		CronScheduleBuilder readJobCron = CronScheduleBuilder.cronSchedule("0/20 * * * * ?").withMisfireHandlingInstructionDoNothing();
		CronTrigger readJobTrigger = TriggerBuilder.newTrigger().withIdentity("readJobTrigger").withSchedule(readJobCron).build();

		scheduler.scheduleJob(taskJob, taskJobTrigger);
		scheduler.scheduleJob(historyJob, historyTrigger);
		scheduler.scheduleJob(readJob, readJobTrigger);
	}
}
