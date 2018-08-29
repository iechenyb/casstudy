package com.cyb.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 如何在当前环境下知道用户的相关信息？<br>
 * 创建时间: 2017年12月22日
 */
//@Service 手动创建FilterInvocationSecurityMetadataSource
public class MyInvocationSecurityMetadataSourceService 
 implements FilterInvocationSecurityMetadataSource {
	Log log = LogFactory.getLog(MyInvocationSecurityMetadataSourceService.class);
	
	UserAuthDao userAuthDao = new MemAuthDaoImpl();
	
	/*//手动注入
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
            resourceMap.put(url, configAttributes);//同一个url 可以有多个角色访问
      }
	}
	*//**
	 * 查找url对应的角色  如何获取当前用户的权限信息
	 *//*
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
	}*/
	private AntPathMatcher urlMatcher = new AntPathMatcher();;
    public static Map<String, List<ConfigAttribute>> resourceMap = null;
    public MyInvocationSecurityMetadataSourceService() {
//        loadResourceDefine();
    }
    private void loadResourceDefine() {
        resourceMap = new HashMap<String, List<ConfigAttribute>>();
        
       /* Map<String, List<String>> hash = moduleAuthService.getAllModuleAuthRef();
        for(String url : hash.keySet()) {
            List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
            List<String> authList = hash.get(url);
            for(String auth : authList) {
                list.add(new SecurityConfig(auth));
            }
            resourceMap.put(url, list);
        }*/
        Map<String,String> urlRolesData = userAuthDao.roleResources();
		for(String url:urlRolesData.keySet()){
			List<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
            configAttributes.add(new SecurityConfig(urlRolesData.get(url)));//只用一个地址
            resourceMap.put(url, configAttributes);//同一个url 可以有多个角色访问
      }
    }
    // According to a URL, Find out permission configuration of this URL.
    @Override
    public List<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        if(resourceMap == null) {
            loadResourceDefine();
        }
        // guess object is a URL.
        String url = ((FilterInvocation) object).getRequestUrl();
        if(url.indexOf("?") != -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        List<ConfigAttribute> result = null;
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            System.out.println("requrl="+url+",resourceURL="+resURL);
            if (urlMatcher.match(resURL, url)) {
                result = resourceMap.get(resURL);
                System.out.println("resURL : " + resURL + " , result : " + result);
                return result;
            }
        }
        return null;
    }
    public boolean supports(Class<?> clazz) {
        return true;
    }
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
