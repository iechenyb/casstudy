package com.cyb.demo;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.utils.SecurityUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月19日
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	Log log = LogFactory.getLog(UserController.class);

	/*
	 * @Autowired private AuthenticationManager authenticationManager ;
	 */

	@Autowired
	BCryptPasswordEncoder bcrypt;
	@Autowired
	SecurityUtils utils;

	@GetMapping("login")
	@ResponseBody
	public String getP(String username) {
		utils.login(username, username);// 模拟自动登录
		System.out.println("查看权限和角色");
		return "login success!";
	}

	@ResponseBody
	@GetMapping("infor")
	public String getInfor(HttpServletRequest req) {
		Object username = req.getUserPrincipal();
		CasAuthenticationToken ap = (CasAuthenticationToken) req.getUserPrincipal();
		log.info("用户名：" + username + "," + ap.getName());
		User user = (User) ap.getPrincipal();
		Object [] roles = user.getAuthorities().toArray();
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<roles.length;i++){
			sb.append(roles[i]+",");
		}
		log.info(user.getAuthorities().size() + "-" + user.getUsername() + "-" + user.getPassword());
		/*
		 * if(ap!=null){ Map<String,Object> tmp =ap.getPrincipal();
		 * if(tmp.size()>0){ //输出到后台console String
		 * userid=tmp.get("userid").toString(); String
		 * username1=tmp.get("username").toString(); String
		 * email=tmp.get("email").toString();
		 * log.info("更多属性"+userid+","+username1+","+email); } }
		 */
		return "用户名： " + req.getUserPrincipal().getName()+",角色信息："+sb.toString();
	}

	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("admin")
	public String admin(HttpServletRequest req) {
		return "admin  infor is" + req.getUserPrincipal().toString();
	}

	@ResponseBody
	@PreAuthorize("hasRole('USER')")
	@GetMapping("user") 
	public String user(HttpServletRequest req) {
		return "user infor is " + req.getUserPrincipal().toString();
	}

	@ResponseBody
	@GetMapping("other")
	public String other(HttpServletRequest req) {
		return "other  infor is  " + req.getUserPrincipal().toString();
	}
	//测试过滤器配置权限加载数据库
	@ResponseBody
	@GetMapping("userConfig")
	public String userConfig(HttpServletRequest req) {
		return "userConfig  infor is  " + req.getUserPrincipal().toString();
	}
	
	@ResponseBody
	@GetMapping("adminConfig")
	public String adminConfig(HttpServletRequest req) {
		return "adminConfig  infor is  " + req.getUserPrincipal().toString();
	}
	//测试过滤器配置权限加载数据库
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 调用系统的http://ip:port/context/logout，当前方法被舍弃！<br>
     *创建时间: 2017年7月15日hj12
     *@param session
     *@param req
     *@param response
     *@return
     */
	/*@GetMapping("logout")
	public String logout(HttpSession session, HttpServletRequest req, HttpServletResponse response) {
		System.out.println("用户执行退出...");
		session.invalidate();
		// 获取上下文中的Authentication 凭证信息
		// Authentication auth =
		// SecurityContextHolder.getContext().getAuthentication();
		// SecurityContextImpl securityContextImpl = (SecurityContextImpl) req
		// .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		// Authentication auth =
		// SecurityContextHolder.getContext().getAuthentication();
		
		 * Authentication auth = securityContextImpl.getAuthentication(); if
		 * (auth != null) { System.out.println("exit infor:" + auth.getName());
		 * new SecurityContextLogoutHandler().logout(req, response, auth); }
		 * else { System.out.println("无授权信息！"); }
		 
		// 一般是销毁session和清除Authentication 凭证信息，比如SecurityContextLogoutHandler
		
		 * for (LogoutHandler handler : handlers) { handler.logout(req,
		 * response, auth); }
		 
		response.addHeader("location", "http://localhost:8088/cas/logout");
		return "redirect:http://localhost:8088/cas/logout?service=http://localhost:8080/casclientboot";
	}*/

}
