package com.cyb.job.task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月21日
 */
public class OtherAsapJob implements Job{
	Log log = LogFactory.getLog(getClass());
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
        	log.info("任务开始执行");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}