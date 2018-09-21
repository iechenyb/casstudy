package com.cyb.job.bean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月21日
 *CREATE TABLE `api_scheduler` (
  `job_name` varchar(128) NOT NULL COMMENT '任务名称',
  `trigger_name` varchar(128) NOT NULL COMMENT '触发器名称',
  `job_group_name` varchar(128) NOT NULL COMMENT '任务组',
  `trigger_group_name` varchar(128) NOT NULL COMMENT '触发器组',
  `is_vaild` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效 1 有效  0无效',
  `class_path` varchar(128) NOT NULL COMMENT '类路径',
  `is_restart` varchar(1) NOT NULL DEFAULT '0' COMMENT '是否重启该任务 1 重启  0不重启',
  `corn` varchar(256) NOT NULL COMMENT 'corn表达式',
  `restart_time` datetime DEFAULT NULL COMMENT '重启时间',
  `last_run_time` datetime DEFAULT NULL COMMENT '最后一次运行时间',
  PRIMARY KEY (`job_name`,`trigger_name`),
  UNIQUE KEY `job_name` (`job_name`),
  UNIQUE KEY `trigger_name` (`trigger_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class ApiScheduler {
	Log log = LogFactory.getLog(ApiScheduler.class);
	private String triggerName;
	private String triggerGroupName;
	private String isVaild;
	private String isRestart;
	private String jobName;
	private String jobGroupName;
	private String classPath;
	private String corn;
	public String getCorn() {
		return corn;
	}
	public void setCorn(String corn) {
		this.corn = corn;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroupName() {
		return triggerGroupName;
	}
	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}
	public String getIsVaild() {
		return isVaild;
	}
	public void setIsVaild(String isVaild) {
		this.isVaild = isVaild;
	}
	public String getIsRestart() {
		return isRestart;
	}
	public void setIsRestart(String isRestart) {
		this.isRestart = isRestart;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	
}
