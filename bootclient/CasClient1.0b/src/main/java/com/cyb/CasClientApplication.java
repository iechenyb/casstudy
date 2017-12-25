package com.cyb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @author DHUser
 * 默认用名user
 * 默认密码见后台
 * http://www.cnblogs.com/question-sky/p/7068511.html
 * https://www.cnblogs.com/lihuidu/p/6495247.html
 */
@SpringBootApplication
@Controller
public class CasClientApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{

	@RequestMapping("/")
	public String home(HttpServletRequest req){
		 String username1 = req.getUserPrincipal().toString();
		 String username2 = req.getRemoteUser();
		return "首页"+username1+","+username2;
	}
	@RequestMapping("logout")
	public String logout(HttpSession session){
		System.out.println("用户执行退出...");
		session.invalidate();
		return "redirect:http://localhost:8087/cas/logout?service=http://localhost:8087/cas/login";
	}

	public static void main(String[] args) {
		SpringApplication.run(CasClientApplication.class, args);
	}
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setContextPath("");
		
	}
}
