package com.cyb.config;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月20日
 */

import com.cyb.utils.SecurityUtils;
public class MyInterceptor implements HandlerInterceptor {
	Log log = LogFactory.getLog(MyInterceptor.class);
    @Autowired
	 SecurityUtils utils;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
		String username = req.getRemoteUser();
		log.info("username "+username+", "+utils);
		//String id = utils.getLoginUserId();!StringUtils.isEmpty(id)&&
		Object isLogin = req.getSession().getAttribute("isLogin");
		if(isLogin==null){//&&Integer.valueOf(isLogin.toString())!=1
			if(!StringUtils.isEmpty(username)){
				log.info("用户首次进入系统，进行自动登录！");
				utils.login(username, username);
				req.getSession().setAttribute("isLogin", 1);
			}
		}
		/*username = req.getUserPrincipal().toString();
		log.info("username "+username);*/
		return true;
	}
	
}
