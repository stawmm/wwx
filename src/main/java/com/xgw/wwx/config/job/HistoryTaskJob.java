package com.xgw.wwx.config.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xgw.wwx.dto.db.TaskDTO;
import com.xgw.wwx.service.TaskService;

public class HistoryTaskJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(HistoryTaskJob.class);

	@Autowired
	private TaskService taskService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("-- HistoryTaskJob execute start --");
		// 已完成的任务
		List<TaskDTO> tasks = taskService.findCompeleteTasks();
		if (null != tasks && !tasks.isEmpty()) {
			logger.debug("-- HistoryTaskJob complete tasks size:{} --", tasks.size());
			for (TaskDTO task : tasks) {
				updateTask(task);
			}
		} else {
			logger.debug("-- HistoryTaskJob complete tasks is empty --");
		}
		logger.debug("-- HistoryTaskJob execute end --");
	}

	public void updateTask(TaskDTO task) {
		try {
			Date finishTime = task.getFinishTime();
			logger.debug("-- HistoryTaskJob complete task id={}, finishTime:{} --", task.getId(), finishTime);
			if (null != finishTime) {
				Date tempDate = DateUtils.addDays(finishTime, 1);
				if (tempDate.before(new Date())) {
					taskService.setTaskToHistory(task.getId());
					logger.debug("-- HistoryTaskJob taskId:{}, taskName:{} set histroy --", task.getId(), task.getName());
				}
			}
		} catch (Exception e) {
			logger.error(" -- HistoryTaskJob updateTask error -- ", e);
		}
	}

}
