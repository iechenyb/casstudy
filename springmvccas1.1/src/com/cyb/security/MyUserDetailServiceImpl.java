package com.cyb.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 
 * 获得对象的方式：
 * WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * 或在JSP中：
 * <sec:authentication property="principal.username"/>
 * 
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可
 * 权限验证类
 */
//@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
	
	// 登录验证
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.err.println("-----------MyUserDetailServiceImpl loadUserByUsername ----------- "+username);
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(username);
		// 封装成spring security的user
		User userdetail = new User(
				username, 
				"11",
				true, 
				true, 
				true,
				true, 
				grantedAuths	//用户的权限
			);
		return userdetail;
	}

	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(String username) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		if(username.equals("admin")){
			authSet.add(new SimpleGrantedAuthority("ROLE_USER"));
			authSet.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}else{
			authSet.add(new SimpleGrantedAuthority("ROLE_USER"));	
		}
		return authSet;
	}
}