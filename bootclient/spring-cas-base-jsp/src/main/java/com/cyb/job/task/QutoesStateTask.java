package com.cyb.job.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyb.job.QuartzManager;


@Component
public class QutoesStateTask implements Job{
	Log log = LogFactory.getLog(getClass());
	@Autowired
	QuartzManager quartzManager;
	public static long count = 0;
    public void execute(JobExecutionContext context) throws JobExecutionException{
    	try {
    		log.info(quartzManager+"任务开始执行");
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
    }
}