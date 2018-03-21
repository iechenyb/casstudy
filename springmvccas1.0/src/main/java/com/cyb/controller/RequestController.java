package com.cyb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyb.utils.RequestUtils;
/**
 * 
    http://localhost:8088/mvc-2/infor.do
 */
@Controller("req")
public class RequestController {
	
	@RequestMapping("/infor")
	public String infor(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("romoteIp:" + RequestUtils.getRemoteHost(request) + ",");
		sb.append("is ajax request:" + RequestUtils.isAjaxRequest(request) + ",");
		sb.append("is req from mobile:" + RequestUtils.JudgeIsMoblie(request)+",");
		sb.append("sessionId:"+request.getSession().getId()+",");
		sb.append("username:"+request.getSession().getAttribute("userName")+",");
		System.out.println("请求信息："+sb.toString());
		return sb.toString();
	}
	
	@RequestMapping("/mylogin")
	public String login(HttpServletRequest request,String name) {
		request.getSession().setAttribute("userName", name);
		request.getSession().setAttribute("userName2", name);
		request.getSession().setAttribute("userName3", name);
		return "login success ,your name is "+name;
	}
	
	
	
	
}
