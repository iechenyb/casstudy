package com.cyb.job.service;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cyb.job.ApiCategory;
import com.cyb.job.ApiInvokingParam;
import com.cyb.job.bean.ApiScheduler;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月21日
 */
@Service
public class SchedulerApiSevice {
	Log log = LogFactory.getLog(SchedulerApiSevice.class);
	
	
	
	public static List<ApiScheduler> lst= new ArrayList<ApiScheduler>();
	public void init() {
		ApiScheduler shecl = new ApiScheduler();
		shecl.setClassPath("com.cyb.job.task.QutoesStateTask");
		shecl.setCorn("0/10 * * * * ?");
		shecl.setIsRestart("0");
		shecl.setIsVaild("1");
		shecl.setJobGroupName("testJobGroup");
		shecl.setJobName("firstjob");
		shecl.setTriggerGroupName("testTriggerGroupName");
		shecl.setTriggerName("testTriggerName-first");
		
		lst.add(shecl);
		shecl = new ApiScheduler();
		shecl.setClassPath("com.cyb.job.task.OtherAsapJob");
		shecl.setCorn("0/20 * * * * ?");
		shecl.setIsRestart("0");
		shecl.setIsVaild("1");
		shecl.setJobGroupName("testJobGroup");
		shecl.setJobName("secondjob");
		shecl.setTriggerGroupName("testTriggerGroupName");
		shecl.setTriggerName("testTriggerName-second");
		lst.add(shecl);
		
	}

	public synchronized List<ApiScheduler> getSchedulerList() {
		if(CollectionUtils.isEmpty(lst)){
			init();
		}
		return lst;
	}

	public void updateRestartTime(String jobName) {
		
	}

	public List<ApiCategory> getApiCategoryList(String jobName) {
		return null;
	}

	public void updateLastRunTime(String jobName) {
		
	}

	public List<ApiInvokingParam> getApiInvokingParamList(String jobName) {
		return null;
	}
}
