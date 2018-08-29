package com.cyb.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年8月27日
 */
public class UserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
	
	//@Resource
	UserAuthDao  userAuthDao = new MemAuthDaoImpl();
	
	
	public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		CasAssertionAuthenticationToken casauth = null;
		System.out.println("cas登录成功后，跳转回调！");
		if (token instanceof CasAssertionAuthenticationToken) {
			casauth = (CasAssertionAuthenticationToken) token;
			Map<String, Object> attr = casauth.getAssertion().getPrincipal()
					.getAttributes();
			String username =casauth.getName(); //attr.get("st_lgnm").toString();
			if(username == null || username=="" || username.equals("")) {
				String message = "用户：["+username +"]不存在";
				throw new UsernameNotFoundException(message);
			}
			
			
			/*Users users = wsurepos.findByStLgnm(username);
			
			//h获取用户信息
			Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
			List<Resources> resourceList = resourceDao.getUserResourceByLgnm(username);
			boolean enables = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			WavenetUser userdetail = new WavenetUser(users.getStLgnm(), users.getStLgps(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
			//org.springframework.security.core.userdetails.User user = new 
			//UserDetails userdetail = new 
			userdetail.setUsercode(users.getCdUs());
			userdetail.setResourceList(resourceList);
			Users nuser = new Users();
			nuser.setCdUs(users.getCdUs());
			nuser.setStAreaid(users.getStAreaid());
			nuser.setStName(users.getStName());
			userdetail.setUsers(nuser);*/
			return new User(token.getName(), token.getName()
					, getAuthorities(token.getName()));
			
		}
		return new User(token.getName(), token.getName()
				, getAuthorities(token.getName()));
	}
	public Collection<? extends GrantedAuthority> getAuthorities(String username) {
		return userAuthDao.getAuthorities(username);
	}
}
