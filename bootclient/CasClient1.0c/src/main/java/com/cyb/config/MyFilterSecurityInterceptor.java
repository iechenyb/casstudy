package com.cyb.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import com.cyb.dao.UserAuthDao;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>AbstractSecurityInterceptor
 * 创建时间: 2017年12月22日
 */
public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor  implements Filter {
	Log log = LogFactory.getLog(MyFilterSecurityInterceptor.class);
	
	public void setCasAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
		super.setAccessDecisionManager(myAccessDecisionManager);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
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

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}
	@Autowired
	UserAuthDao userAuthDao;
	
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		MyInvocationSecurityMetadataSourceService mdss = new MyInvocationSecurityMetadataSourceService();
		mdss.setUserAuthDao(userAuthDao);
		return mdss;
	}
}
