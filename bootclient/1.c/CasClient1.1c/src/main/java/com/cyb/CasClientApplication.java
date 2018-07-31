package com.cyb;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.config.AcmCasProperties;
import com.cyb.config.CasAccessDecisionManager;
import com.cyb.config.CasFilterSecurityInterceptor;

/**
 * 
 * @author DHUser 默认用名user 默认密码见后台
 *         http://www.cnblogs.com/question-sky/p/7068511.html
 *         https://www.cnblogs.com/lihuidu/p/6495247.html
 *         http://192.168.16.211:8080/app0 互信应用1
 *         http://192.168.16.211:8081/app1 互信信用2
 */
@SpringBootApplication
@Controller
public class CasClientApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(CasClientApplication.class);
	}

	@Resource
	private CasAccessDecisionManager casAccessDecisionManager;

	@Bean
	public CasFilterSecurityInterceptor getXXX() {
		CasFilterSecurityInterceptor x = new CasFilterSecurityInterceptor();
		System.out.println("CasAccessDecisionManager:"+casAccessDecisionManager);
		x.setCasAccessDecisionManager(casAccessDecisionManager);
		return x;
	}

	@GetMapping("/")
	public ModelAndView home(HttpServletRequest req) {
	/*	String username1 = req.getUserPrincipal().toString();
		String username2 = req.getRemoteUser();*/
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		return view;
	}
	
	@GetMapping("/service/list")
	public ModelAndView serviceList(HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		view.setViewName("list");
		return view;
	}
	@Resource
    private AcmCasProperties acmCasProperties;
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		System.out.println("用户执行退出...");
		session.invalidate();
		String logoutUrl = acmCasProperties.getCasServerPrefix()+acmCasProperties.getCasServerLogoutUrl();
		return "redirect:"+logoutUrl+"?service="+acmCasProperties.getAppServicePrefix();
	}

	public static void main(String[] args) {
		SpringApplication.run(CasClientApplication.class, args);
	}

}
