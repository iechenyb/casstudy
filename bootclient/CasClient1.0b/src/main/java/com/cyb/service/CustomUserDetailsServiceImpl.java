package com.cyb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("开始查询数据库，验证用户名的正确性！"+username);
		return new User(username,  bCryptPasswordEncoder().encode(username), getAuthorities(username));
	}

	public Collection<? extends GrantedAuthority> getAuthorities(String username) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		// 根据自定义逻辑来返回用户权限，如果用户权限返回空或者和拦截路径对应权限不同，验证不通过ROLE_
		if (username.equals("admin")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_ADMIN");
			list.add(au);
		} else if (username.equals("user")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_USER");
			list.add(au);
		}else if (username.equals("other")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_OTHER");
			list.add(au);
			//没有任何角色
		}else{// any
			GrantedAuthority au1 = new SimpleGrantedAuthority("ROLE_USER");
			list.add(au1);
			GrantedAuthority au2 = new SimpleGrantedAuthority("ROLE_ADMIN");
			list.add(au2);
		}
		System.out.println("初始化角色信息！"+list.size()+","+list.get(0).getAuthority());
		return list;
	}
}
