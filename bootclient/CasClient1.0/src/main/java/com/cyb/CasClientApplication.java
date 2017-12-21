package com.cyb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @author DHUser
 * 默认用名user
 * 默认密码见后台
 */
@SpringBootApplication
@Controller
public class CasClientApplication {

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
}
