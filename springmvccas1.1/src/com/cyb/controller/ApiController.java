package com.cyb.controller;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月19日
 */
@Controller
@RequestMapping("api")
public class ApiController {
	
	Log log = LogFactory.getLog(ApiController.class);
	//https://blog.csdn.net/elim168/article/details/70882360
	@RequestMapping("query")
	@ResponseBody
	public String getP(String username) throws MalformedURLException {
		/*AttributePrincipal principal = AssertionHolder.getAssertion().getPrincipal();
		String proxyTicket = principal.getProxyTicketFor("http://elim:8081/app2/getData.jsp");
		URL url = new URL("http://elim:8081/app2/getData.jsp?ticket=" + proxyTicket);
		*/return "信息列表!";
	}

	@ResponseBody
	@RequestMapping("add")
	public String add(String name) {
		return "添加信息成功！";
	}

	@ResponseBody
	@RequestMapping("delete")
	public String delete(HttpServletRequest req) {
		return "删除信息成功";
	}

	@ResponseBody
	@RequestMapping("update") 
	public String update(String name) {
		return "更新信息成功！";
	}
	
	@ResponseBody
	@RequestMapping("infor") 
	public String infor(String name) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();  
		Authentication au = (Authentication) SecurityContextHolder.getContext()  
			    .getAuthentication();
			System.out.println("用户名："+userDetails.getUsername());
			Collection<? extends GrantedAuthority> roles = au.getAuthorities();
			for(GrantedAuthority g:roles){
				System.out.println("角色："+g.getAuthority());
			}
			
		return "更新信息成功！";
	}
	
	

		
}
