package com.cyb.config;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月22日
 */
@Component
public class CasAccessDecisionManager implements AccessDecisionManager {
	Log log = LogFactory.getLog(CasAccessDecisionManager.class);

	/**
	 * @param authentication
	 *            当前用户权限信息
	 * @param o
	 *            请求信息
	 * @param configAttributes
	 *            当前访问的url对应的角色
	 */
	@Override
	public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		System.out.println("进入decide权限核查逻辑！。。。");
		// 没有角色要求则返回
		if (null == configAttributes || configAttributes.size() <= 0) {
			return;
		}
		// 比较当前用户角色和当前访问的url对应的角色，是否拥有对应权限
		ConfigAttribute c;
		String needRole;
		for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext();) {
			c = iter.next();
			needRole = c.getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {// authentication
																			// 为在注释1
																			// 中循环添加到
																			// GrantedAuthority
																			// 对象中的权限信息集合
				if (( needRole.trim()).equals(ga.getAuthority())) {//"ROLE_" +
					return;
				}
			}
		}
		throw new AccessDeniedException("no right");
	}

	@Override
	public boolean supports(ConfigAttribute configAttribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return true;
	}
}
