package com.cyb.job.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.job.bean.ApiScheduler;
import com.cyb.job.service.SchedulerApiSevice;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月21日
 */
import com.kiiik.pub.bean.ResultBean;
@RestController
@RequestMapping("job")
public class JobController {
	Log log = LogFactory.getLog(JobController.class);
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 让任务失效<br>
	 *创建时间: 2017年7月15日hj12
	 *@param jobName
	 *@param cron
	 *@return
	 */
	@GetMapping("disableTask")
	public ResultBean<String> updateTask(String jobName){
		for(ApiScheduler sche:SchedulerApiSevice.lst){
			if(sche.getJobName().equals(jobName)){
				sche.setIsVaild("0");//任务无效
				sche.setIsRestart("0");//不需要启动
			}
		}
		return new ResultBean<String>("成功！");
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 重启任务<br>
	 *创建时间: 2017年7月15日hj12
	 *@param jobName
	 *@param cron
	 *@return
	 */
	@GetMapping("restartTask")
	public ResultBean<String> restartTask(String jobName){
		for(ApiScheduler sche:SchedulerApiSevice.lst){
			if(sche.getJobName().equals(jobName)){
				sche.setIsVaild("1");//任务有效
				sche.setIsRestart("1");//需要启动
			}
		}
		return new ResultBean<String>("成功！");
	}
	
	
	@GetMapping("listTask")
	public ResultBean<List<ApiScheduler>> listTask(){
		return new ResultBean<List<ApiScheduler>>(SchedulerApiSevice.lst);
	}
	
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 更新执行的时间<br>
	 *创建时间: 2017年7月15日hj12
	 *@param jobName
	 *@param cron
	 *@return
	 */
	@GetMapping("addTask")
	public ResultBean<String> addTask(){
		//往表里添加一个记录即可
		return new ResultBean<String>("插入一条记录到数据库即可！");
	}
	
	@GetMapping("updTaskCron")
	public ResultBean<String> updateTaskCron(String jobName,String cron){
		//往表里添加一个记录即可
		for(ApiScheduler sche:SchedulerApiSevice.lst){
			if(sche.getJobName().equals(jobName)){
				sche.setCorn(cron);//修改执行时间后  重启任务
				sche.setIsVaild("1");//任务有效
				sche.setIsRestart("1");//需要启动
			}
		}
		return new ResultBean<String>("成功！");
	}
}
