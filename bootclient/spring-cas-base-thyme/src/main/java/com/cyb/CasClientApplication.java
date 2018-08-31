package com.cyb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.bean.SysUser;
import com.cyb.config.MyAccessDecisionManager;
import com.cyb.config.MyFilterSecurityInterceptor;
import com.cyb.config.cas.AcmCasProperties;

@SpringBootApplication
@Controller
public class CasClientApplication {

	@Resource
	private MyAccessDecisionManager myAccessDecisionManager;

	@Bean
	public MyFilterSecurityInterceptor createMyFilterSecurityInterceptor() {
		MyFilterSecurityInterceptor x = new MyFilterSecurityInterceptor();
		System.out.println("CasAccessDecisionManager:" + myAccessDecisionManager);
		x.setCasAccessDecisionManager(myAccessDecisionManager);
		return x;
	}

	@GetMapping("/home")
	public String hello(Map<String, Object> map) {
		SysUser user = new SysUser(1, "aaa", "bbb", 1);
		map.put("user", user);

		List<SysUser> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			SysUser u = new SysUser(i, "fei" + (i + 2),  "爱好：篮球" + (i + 2), i);
			list.add(u);
		}
		map.put("userList", list);
		return "home";
	}

	@GetMapping("/")
	public ModelAndView home(HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		return view;
	}

	@RequestMapping("jsp")
	public ModelAndView jspPage() {
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		view.addObject("name", "chenyb");
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
		String logoutUrl = acmCasProperties.getCasServerPrefix() + acmCasProperties.getCasServerLogoutUrl();
		return "redirect:" + logoutUrl + "?service=" + acmCasProperties.getAppServicePrefix();
	}

	// java -Dspring.profiles.active=stock -jar casclientboot.jar
	public static void main(String[] args) {
		System.out.println("准备启动...");
		new SpringApplicationBuilder(CasClientApplication.class).web(true).run(args);
		// SpringApplication.run(CasClientApplication.class, args);
	}

	/*
	 * @Bean//jar包专用 public EmbeddedServletContainerCustomizer
	 * embeddedServletContainerCustomizer() { return new
	 * EmbeddedServletContainerCustomizer() { //jar包使用
	 * 
	 * @Override public void customize(ConfigurableEmbeddedServletContainer
	 * container) { ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN,
	 * "/exception/403.jsp"); ErrorPage error404Page = new
	 * ErrorPage(HttpStatus.NOT_FOUND, "/exception/404.jsp"); ErrorPage
	 * error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
	 * "/exception/500.jsp"); container.addErrorPages(error403Page,
	 * error404Page, error500Page); } }; }
	 */

}
