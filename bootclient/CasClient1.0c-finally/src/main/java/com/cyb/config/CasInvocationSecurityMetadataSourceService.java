package com.cyb.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	@SuppressWarnings("unused")
	private Map<String,String> auths = null;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
     public CasInvocationSecurityMetadataSourceService(){
    	 System.out.println("CasInvocationSecurityMetadataSourceService构造器执行1！");
    	 patterns = new HashSet<>();
    	 patterns.add(Pattern.compile("/lhmj/*"));
     }
		@Autowired//TSysMenuDao tSysMenuDao,FilterStatic filterStatic
	    public void MyInvocationSecurityMetadataSourceService() {
			System.out.println("MyInvocationSecurityMetadataSourceService构造器执行2！");
	        //this.tSysMenuDao = tSysMenuDao;
	        patterns = new HashSet<>();
	        //可通过配置过滤路径，这里就省略不写了，写法与AcmCasProperties一致
	       /* for (String filter:filterStatic.getStaticFilters()){
	           String regex= filter.replace("**","*").replace("*",".*");	          
	        }*/
	        patterns.add(Pattern.compile("/lhmj/*"));
	    }
		/**
	     * 
	     *作者 : iechenyb<br>
	     *方法描述: 优化，角色对象只创建一个<br>
	     *创建时间: 2017年7月15日hj12
	     *@return
	     */
	    public Map<String,ConfigAttribute> roleResources2(){
	    	String[] roles = new String[]{"ROLE_ADMIN","ROLE_USER"};
	    	Map<String,ConfigAttribute> auths = new HashMap<String,ConfigAttribute>();
	    	for(int i=0;i<roles.length;i++){
	    		ConfigAttribute configAttribute = new SecurityConfig(roles[i]);// 资源标识
	    		auths.put(roles[i], configAttribute);
	    	}
	    	return auths;
	    }
	    public Map<String,String> roleResources(){
	    	Map<String,String> auths = new HashMap<String,String>();
	    	//管理员权限
	    	auths.put("/user/userConfig", "ROLE_USER");
	    	auths.put("/user/adminConfig", "ROLE_ADMIN");
	    	this.auths = auths;
	    	return auths;
	    }
	/**
	 * 查找url对应的角色
	 */
	public void loadResourceDefine(String url_) {
		System.out.println("根据url查找加载权限资源文件！");
		/*Collection<ConfigAttribute> array = new ArrayList<>();
		ConfigAttribute configAttribute;
		SecurityConfig x;
		SysMenu permission = tSysMenuDao.findMeneRoles(url);
		if (permission != null) {
			for (String role : permission.getRoles().split(",")) {
				configAttribute = new SecurityConfig(role);
				// 此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为CasAccessDecisionManager类的decide的第三个参数。
				array.add(cfg);
			}
			return array;
		}*/
		 if (resourceMap == null) {
	            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
	            Map<String,String> auths = roleResources();
	            /*for(String url:auths.keySet()){
	            	  Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
	                  ConfigAttribute configAttribute = new SecurityConfig(auths.get(url));// 资源标识
	                  configAttributes.add(configAttribute);//创建个数为uri的个数，浪费内存
	                  resourceMap.put(url, configAttributes);
	            }*/
	            Map<String,ConfigAttribute> auths2 = roleResources2();
	            for(String url:auths.keySet()){
	          	  	Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
	                configAttributes.add(auths2.get(auths.get(url)));//只用一个地址
	                resourceMap.put(url, configAttributes);
	          }
		 }
	}
    @Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    	 if (resourceMap == null) loadResourceDefine(null);
		// object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		String url = request.getRequestURI();
		url = url.replaceFirst(request.getContextPath(), "");
		logger.info("获取角色对应的url："+url+",access="+resourceMap.containsKey(url));
		/*// 将请求的url与配置文件中不需要访问控制的url进行匹配
		Iterator<Pattern> patternIterator = patterns.iterator();
		while (patternIterator.hasNext()) {
			Pattern pattern = patternIterator.next();
			Matcher matcher = pattern.matcher(url);
			if (matcher.find())
				return null;
		}*/
		return resourceMap.get(url);
		//return loadResourceDefine(url);//找不到时，从数据库里再加载
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> aClass) {
		return true;
	}
}
