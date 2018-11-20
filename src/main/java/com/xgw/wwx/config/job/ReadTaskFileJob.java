package com.xgw.wwx.config.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ReadTaskFileJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ReadTaskFileJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("-- ReadTaskFileJob execute start --" + new Date());






    }
}
