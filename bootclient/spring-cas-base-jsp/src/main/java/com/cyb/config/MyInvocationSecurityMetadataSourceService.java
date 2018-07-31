package com.cyb.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cyb.dao.UserAuthDao;

/**
 * 作者 : iechenyb<br>
 * 类描述: 如何在当前环境下知道用户的相关信息？<br>
 * 创建时间: 2017年12月22日
 */
//@Service
public class MyInvocationSecurityMetadataSourceService 
 implements FilterInvocationSecurityMetadataSource {
	Log log = LogFactory.getLog(MyInvocationSecurityMetadataSourceService.class);
	
	UserAuthDao userAuthDao;
	
	//手动注入
	public void setUserAuthDao(UserAuthDao userAuthDao) {
		this.userAuthDao = userAuthDao;
	}

	private Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();
	
	@PostConstruct//  被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
	private void loadResourceDefine() {
		resourceMap.clear();//清空旧数据
		//读取所有的url-role信息 并不是以人为单位！
		Map<String,String> urlRolesData = userAuthDao.roleResources();
		for(String url:urlRolesData.keySet()){
      	  	Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
            configAttributes.add(new SecurityConfig(urlRolesData.get(url)));//只用一个地址
            resourceMap.put(url, configAttributes);
      }
	}
	/**
	 * 查找url对应的角色  如何获取当前用户的权限信息
	 */
	public void loadResourceDefine(String url_) {
		System.out.println("根据url查找加载权限资源文件！");
		 if (resourceMap == null) {
			 loadResourceDefine();
		 }
	}
    @Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    	if (CollectionUtils.isEmpty(resourceMap)) loadResourceDefine();//加载所有
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		String url = request.getRequestURI();
		url = url.replaceFirst(request.getContextPath(), "");
		log.info("url："+url+",access="+resourceMap.containsKey(url));
		return resourceMap.get(url);//返回空，说明不受限制，放行，可以访问。不为空，则进行权限过滤，必须存在指定的角色才能访问。
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return new ArrayList<ConfigAttribute>();
	}

	public boolean supports(Class<?> aClass) {
		return true;
	}
}
