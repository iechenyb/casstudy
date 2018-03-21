package com.cyb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * http://localhost:8088/mvc-2/toLogin.do http://localhost:8088/mvc-2/login.do
 * 加密，安全测试。
 */
@RestController()
@RequestMapping("sec")
public class SecurityController {

	//只有管理员可以访问
	@RequestMapping("/any")
	public String any() {
		return "user or admin ";
	}
	//只有管理员可以访问
	@RequestMapping("/admin")
	public String login() {
		return "admin";
	}
	//只有普通用户可以访问
	@RequestMapping("/user")
	public String user() {
		return "user";
	}
	//任何人都可以访问
	@RequestMapping("/all")
	public String all(HttpServletRequest req) {
		// 从Cas服务器获取登录账户的用户名（2种方式）
		CasAuthenticationToken token = (CasAuthenticationToken) req.getUserPrincipal();
		String infor = "cur username is "+token.getName()+",roles is "+token.getAuthorities().toString();
		return infor;
	}

}
