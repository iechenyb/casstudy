package com.cyb.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年8月29日
 * http://blog.51cto.com/flyingsnail/1565428
 */
public class FilterSecurityInterceptor 
extends AbstractSecurityInterceptor 
implements Filter {
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	public void setAuthenticationManager(AuthenticationManager authm){
		super.setAuthenticationManager(authm);
	}
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		// 在“beforeInvocation”该方法中，
		// 1、会调用ConfigAttributeDefinition attr =
		// this.obtainObjectDefinitionSource().getAttributes(object);
		// 来获取访问该url的权限；ps：object就是fi。
		// 2、会调用Authentication authenticated =
		// authenticateIfRequired();来获取该用户拥有的权限。
		// 3、调用accessDecisionManager的decide(authenticated, object,
		// attr);来决定该用户有没有访问该url的权限。如果有，则继续；如果没有则抛出异常。
		// 4、调用Authentication runAs =
		// this.runAsManager.buildRunAs(authenticated, object, attr);去尝试run as a
		// different user。
		// 如果不为空，则把它放入上下文中：
		// SecurityContextHolder.getContext().setAuthentication(runAs);
		// 5、返回token
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
