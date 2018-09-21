package com.cyb.job;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月21日
 */
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.cyb.job.bean.ApiScheduler;
import com.cyb.job.service.SchedulerApiSevice;


@Configuration
@EnableScheduling
public class QuartzConfiguration {
	Log log = LogFactory.getLog(QuartzConfiguration.class);
	String isVaild_yes ="1";
	String isRestart_yes="1";
	@Autowired
	QuartzManager quartzManage ;
	
	@Autowired
	SchedulerApiSevice apiService ;
    //注入scheduler到spring，在quartzManege会用到
    @Bean(name = "scheduler")
    public Scheduler scheduler(QuartzJobFactory quartzJobFactory) throws Exception {

        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setJobFactory(quartzJobFactory);
        factoryBean.afterPropertiesSet();
        Scheduler scheduler = factoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }
    ScheduledExecutorService x;
    
    @Scheduled(cron = "* * * * * ?")
    public void run() throws Exception {
    	//System.out.println("任务调度主任务开始执行...");

//        ===================================服务上的算法调用===================================
         List<ApiScheduler> listScheduler = apiService.getSchedulerList();
        for(ApiScheduler api:listScheduler){
            try{

                String state = quartzManage.getJobState(api.getTriggerName(),api.getTriggerGroupName());
    //            log.info(" 任务 "+api.getJobName()+" 的状态为 "+state);
                // NONE(没有该job), NORMAL(正常), PAUSED(暂停), COMPLETE(完成), ERROR(错误), BLOCKED(阻塞)
                /*if(state.equals("NORMAL")){
                	continue;
                }*/
                //如果任务有效
                if(StringUtils.equals(isVaild_yes,api.getIsVaild())){
                    //如果任务需要重启 修改了corn后需要重启
                    if(StringUtils.equals(api.getIsRestart(),isRestart_yes)){
                        log.info(" 任务 "+api.getJobName()+" 开始重启 ");
                        //需要重启
                        quartzManage.removeJob(api.getJobName(),api.getJobGroupName(),api.getTriggerName(),api.getTriggerGroupName());
                        doJob(api.getClassPath(),api.getJobName(),api.getJobGroupName(),api.getTriggerName(),api.getTriggerGroupName(),api.getCorn());
                        //更新状态
                        apiService.updateRestartTime(api.getJobName());
                        //重启过不能在再重启
                        api.setIsRestart("0");//不需要再重启
                    }else{
                        //不需要重启
                        //判断任务是否存在  如果不存在 则添加
                        if(StringUtils.equals(state,"NONE")){
                            log.info(" 任务 "+api.getJobName()+" 开始启动 ");
                            doJob(api.getClassPath(),api.getJobName(),api.getJobGroupName(),api.getTriggerName(),api.getTriggerGroupName(),api.getCorn());
                        }else if(StringUtils.equals(state,"PAUSED")||StringUtils.equals(state,"ERROR")){
                            log.info(" 任务 "+api.getJobName()+" 重新启动 ");
                            quartzManage.removeJob(api.getJobName(),api.getJobGroupName(),api.getTriggerName(),api.getTriggerGroupName());
                            doJob(api.getClassPath(),api.getJobName(),api.getJobGroupName(),api.getTriggerName(),api.getTriggerGroupName(),api.getCorn());
                        }
                    }

                }else {
                    if(!StringUtils.equals(state,"NONE")){
                        log.info(" 任务 "+api.getJobName()+" 移除 ");
                        quartzManage.removeJob(api.getJobName(),api.getJobGroupName(),api.getTriggerName(),api.getTriggerGroupName());
                    }
                }
            }catch (Exception e){
                log.error("任务"+api.getJobName()+"调度出现异常，异常信息为："+e.getMessage());
            }
        }

//        log.info("-------------------------------------------------------------------------------------------------------------------");

//        doJob("com.aliyun.citybrain.job.OtherAsapJob","ASAP_OTHER_SCHEDULER","ASAP_OTHER_JOB_GROUP","ASAP_OTHER_TRIGGER","ASAP_OTHER_JOB_GROUP","0 0/1 * * * ?");

//        ===================================服务上的算法调用===================================
    }

    /**
     * 添加任务调度
     * @param classPath
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupNam
     * @param corn
     * @throws ClassNotFoundException
     */
    public void doJob(String classPath,String jobName,String jobGroupName,String triggerName,String triggerGroupNam,String corn) throws ClassNotFoundException {
        Class<?> jobClass = Class.forName(classPath);
        Map<String,Object> jobMap = new HashMap<>();
        quartzManage.addJob(jobName,jobGroupName,triggerName,triggerGroupNam, jobClass,corn,jobMap);
    }

}
