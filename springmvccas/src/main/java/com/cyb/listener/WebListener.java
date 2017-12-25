package com.cyb.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * 功能描述：
 * 作者：iechenyb
 * 创建时间：2016年11月8日下午4:03:46
 */
public class WebListener implements ServletContextListener {

    public WebListener() {}

    public void contextInitialized(ServletContextEvent sce)  { 
    	try {
    		/*H2Manager.start();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    	try {
    		/*H2Manager.stop();
    		H2Manager.shutdown();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}