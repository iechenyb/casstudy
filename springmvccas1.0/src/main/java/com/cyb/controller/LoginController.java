package com.cyb.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 *  http://localhost:8088/mvc-2/toLogin.do
    http://localhost:8088/mvc-2/login.do
    加密，安全测试。
 */
@Controller("login")
public class LoginController {
	
	@RequestMapping("/login")
	public String login(String username,String password,HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("username:" + username + ",");
		sb.append("password:" + password + ",");
		sb.append("token:" + request.getSession().getAttribute("token") + ",");
		System.out.println("请求信息："+sb.toString());
		return sb.toString();
	}
	
	@RequestMapping("/toLogin")
	public ModelAndView	toLoginPage(String username,String password,HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String token = UUID.randomUUID().toString();
		request.getSession().setAttribute("token",token);
		view.addObject("token", token);
		view.setViewName("/login.jsp");
		return view;
	}
	
}
