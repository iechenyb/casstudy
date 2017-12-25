package com.cyb.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月22日
 */
@Component
public class CasInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
	Log log = LogFactory.getLog(CasInvocationSecurityMetadataSourceService.class);
	//private final TSysMenuDao tSysMenuDao;
	private  HashSet<Pattern> patterns=null;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

		@Autowired//TSysMenuDao tSysMenuDao,FilterStatic filterStatic
	    public void MyInvocationSecurityMetadataSourceService() {
	        //this.tSysMenuDao = tSysMenuDao;
	        patterns = new HashSet<>();
	        //可通过配置过滤路径，这里就省略不写了，写法与AcmCasProperties一致
	       /* for (String filter:filterStatic.getStaticFilters()){
	           String regex= filter.replace("**","*").replace("*",".*");
	          
	        }*/
	        patterns.add(Pattern.compile("/lhmj/*"));
	    }

	/**
	 * 查找url对应的角色
	 */
	public Collection<ConfigAttribute> loadResourceDefine(String url) {
		Collection<ConfigAttribute> array = new ArrayList<>();
		ConfigAttribute cfg;
		SecurityConfig x;
		/*SysMenu permission = tSysMenuDao.findMeneRoles(url);
		if (permission != null) {
			for (String role : permission.getRoles().split(",")) {
				cfg = new SecurityConfig(role);
				// 此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为CasAccessDecisionManager类的decide的第三个参数。
				array.add(cfg);
			}
			return array;
		}*/
		return null;

	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		String url = request.getRequestURI();
		url = url.replaceFirst(request.getContextPath(), "");
		logger.info(url);

		// 将请求的url与配置文件中不需要访问控制的url进行匹配
		Iterator<Pattern> patternIterator = patterns.iterator();
		while (patternIterator.hasNext()) {
			Pattern pattern = patternIterator.next();
			Matcher matcher = pattern.matcher(url);
			if (matcher.find())
				return null;
		}
		return loadResourceDefine(url);
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> aClass) {
		return true;
	}
}
