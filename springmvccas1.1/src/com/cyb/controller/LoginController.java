package com.cyb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		System.out.println("用户执行退出...");
		session.invalidate();
		String logoutUrl = "http://cas.kiiik.com:8088/cas/logout";
		return "redirect:"+logoutUrl+"?service=http://localhost:8088/springsecurity3/aaa.html";
	}
	/*
	@Autowired
	private AuthenticationManager myAuthenticationManager;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPage(
			@RequestParam(value = "error", required = false) String error,
			String username, String password, Model model,
			HttpServletRequest request) {
		    
			Authentication authentication = myAuthenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username,
							password));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			System.out.println("authentication="+authentication);
			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		return "index";
	}

	@RequestMapping(value = "/fail", method = RequestMethod.GET)
	public String loginFail(
			@RequestParam(value = "error", required = false) String error,
			Model model) {
		System.out.println("loginFail error=" + error);
		if (error != null) {
			return "denied";
		}
		return "index";
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String exit(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
				.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		// Authentication auth =
		// SecurityContextHolder.getContext().getAuthentication();
		Authentication auth = securityContextImpl.getAuthentication();
		if (auth != null) {
			System.out.println("exit infor:" + auth.getName());
			new SecurityContextLogoutHandler().logout(request, response, auth);
		} else {
			System.out.println("无授权信息！");
		}
		try {
			// 获得当前用户所拥有的权限
			@SuppressWarnings("unchecked")
			List<GrantedAuthority> authorities = (List<GrantedAuthority>) securityContextImpl
					.getAuthentication().getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				System.out.println("Authority"
						+ grantedAuthority.getAuthority());
			}
			WebAuthenticationDetails details = (WebAuthenticationDetails) securityContextImpl
					.getAuthentication().getDetails();
			// 获得访问地址
			System.out.println("RemoteAddress" + details.getRemoteAddress());
			// 获得sessionid
			System.out.println("SessionId" + details.getSessionId());
			// 登录密码，未加密的
			System.out.println("Credentials:"
					+ securityContextImpl.getAuthentication().getCredentials());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "login";
	}
*/}