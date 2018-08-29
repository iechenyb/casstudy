package com.cyb.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>AbstractSecurityInterceptor
 * 创建时间: 2017年12月22日
 */
public class MyFilterSecurityInterceptor
extends AbstractSecurityInterceptor//FilterSecurityInterceptor  
implements Filter {
	Log log = LogFactory.getLog(MyFilterSecurityInterceptor.class);
	//accessDecisionManager
	MyAccessDecisionManager accessDecisionManager;
	MyInvocationSecurityMetadataSourceService securityMetadataSource;
	
	public MyAccessDecisionManager getAccessDecisionManager() {
		return accessDecisionManager;
	}

	public void setAccessDecisionManager(MyAccessDecisionManager accessDecisionManager) {
		System.out.println("decision:"+accessDecisionManager);
		this.accessDecisionManager = accessDecisionManager;
	}

	public UserAuthDao getUserAuthDao() {
		return userAuthDao;
	}

	public void setUserAuthDao(UserAuthDao userAuthDao) {
		this.userAuthDao = userAuthDao;
	}

	public MyInvocationSecurityMetadataSourceService getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	UserAuthDao userAuthDao = new MemAuthDaoImpl();
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//super.setAuthenticationManager(newManager);
	}


	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
		invoke(fi);
	}
	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		// fi里面有一个被拦截的url
		// 里面调用CasInvocationSecurityMetadataSource的getAttributes(Object
		// object)这个方法获取fi对应的所有权限
		// 再调用CasAccessDecisionManager的decide方法来校验用户的权限是否足够
		//在“beforeInvocation”该方法中，
        //1、会调用ConfigAttributeDefinition attr = this.obtainObjectDefinitionSource().getAttributes(object); 来获取访问该url的权限；ps：object就是fi。
        //2、会调用Authentication authenticated = authenticateIfRequired();来获取该用户拥有的权限。
        //3、调用accessDecisionManager的decide(authenticated, object, attr);来决定该用户有没有访问该url的权限。如果有，则继续；如果没有则抛出异常。
        //4、调用Authentication runAs = this.runAsManager.buildRunAs(authenticated, object, attr);去尝试run as a different user。
        //    如果不为空，则把它放入上下文中： SecurityContextHolder.getContext().setAuthentication(runAs); 
        //5、返回token
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			log.info("执行拦截器！");
			// 执行下一个拦截器
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	@Override
	public void destroy() {

	}
	public void setSecurityMetadataSource(
			MyInvocationSecurityMetadataSourceService newSource) {
		System.out.println("MyInvocationSecurityMetadataSourceService:"+newSource);
        this.securityMetadataSource = newSource;
    }
	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}
	
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		//securityMetadataSource.setUserAuthDao(userAuthDao);
		return securityMetadataSource;
	}
}
