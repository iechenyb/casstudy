package com.cyb.base;

import org.springframework.context.ApplicationContext;

public class JunitBase {
	public ApplicationContext fac=null;
	public ApplicationContext ac = null;
	public JunitBase(){
		/*fac =new FileSystemXmlApplicationContext(	
						"classpath:applicationContext_base.xml",
						"classpath:applicationContext_druid.xml",
						"classpath:applicationContext_druid_1.xml",
						"classpath:applicationContext_mail.xml",
						"classpath:applicationContext_quatarz.xml",
						"classpath:applicationContext_websocket.xml"
				);*/
		/*ac = new ClassPathXmlApplicationContext(
						"classpath:applicationContext_base.xml",
						"classpath:applicationContext_druid.xml",
						"classpath:applicationContext_atomikos.xml",
						"classpath:applicationContext_studyaop.xml",
						"classpath:applicationContext_mail.xml",
						"classpath:applicationContext_quatarz.xml",
						"classpath:applicationContext_websocket.xml"
						);*/// 从classpath中加载
	 //repositoryService =  (RepositoryService) ac.getBean("repositoryService");
	 //executionService =  (ExecutionService) ac.getBean("executionService");
	}
}
