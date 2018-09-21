package com.cyb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cyb.dao.UserAuthDao;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月13日
 */
@Service
public class AcmCasUserDetailService 
implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
	private static final Logger USER_SERVICE_LOGGER = LoggerFactory.getLogger(AcmCasUserDetailService.class);

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	UserAuthDao userAuthDao;
	
	public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
		USER_SERVICE_LOGGER.info("回调登录验证-1！！！\n校验成功的登录名为: " + token.getName());
		Map<String, Object> attr = token.getAssertion().getPrincipal()
				.getAttributes();
		//将当前用户的权限信息放到map中。
		return new User(token.getName(), bCryptPasswordEncoder().encode(token.getName()), getAuthorities(token.getName()));
	}

	public Collection<? extends GrantedAuthority> getAuthorities(String username) {
		return userAuthDao.getAuthorities(username);
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        return auths;
    }

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("回调登录验证-2！！！");
		return new User(username, bCryptPasswordEncoder().encode(username), getAuthorities(username));
	}
}
