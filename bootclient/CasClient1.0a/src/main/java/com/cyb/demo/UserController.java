package com.cyb.demo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.config.SpringCasAutoconfig;
import com.cyb.utils.SecurityUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月19日
 */
@Controller
@RequestMapping("user")
public class UserController {
	Log log = LogFactory.getLog(UserController.class);
	
	@Autowired  
    SpringCasAutoconfig autoconfig;  
	
/*	@Autowired
    private  AuthenticationManager authenticationManager ;*/
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	@Autowired
	SecurityUtils utils;
	@RequestMapping("login")
	@ResponseBody
	public String getP(String username){
		utils.login(username, username);//模拟自动登录
        System.out.println("查看权限和角色");
		return autoconfig.getServerName();
	}
	@ResponseBody
	@RequestMapping("infor")
	public String getInfor(HttpServletRequest req){
		return "user infor is " +req.getUserPrincipal().toString();
	}
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("admin")
	public String admin(HttpServletRequest req){
		return "admin  infor is"+req.getUserPrincipal().toString();
	}
	@ResponseBody
	@PreAuthorize("hasRole('USER')")
	@RequestMapping("user")
	public String user(HttpServletRequest req){
		return "user infor is "+req.getUserPrincipal().toString();
	}
	@ResponseBody
	@RequestMapping("other")
	public String other(HttpServletRequest req){
		return "other  infor is  "+req.getUserPrincipal().toString();
	}
	@RequestMapping("logout")
	public String logout(HttpSession session){
		System.out.println("用户执行退出...");
		session.invalidate();
		return "redirect:http://localhost:8087/cas/logout?service=http://localhost:8087/cas/login";
	}

}
