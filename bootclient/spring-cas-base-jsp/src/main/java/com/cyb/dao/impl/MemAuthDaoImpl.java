package com.cyb.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cyb.dao.UserAuthDao;

/**
 * 作者 : iechenyb<br>
 * 类描述: 固定权限设定<br>
 * 创建时间: 2018年7月25日
 */
public class MemAuthDaoImpl implements UserAuthDao {
	Log log = LogFactory.getLog(MemAuthDaoImpl.class);
    /**
     * 读取用户角色资源信息
     */
	public Map<String, String> roleResources(String username) {
		Map<String, String> auths = new HashMap<String, String>();
		//管理员权限
		auths.put("/user/userConfig", "ROLE_USER");
		auths.put("/user/adminConfig", "ROLE_ADMIN");
		return auths;
	}
	String[] roles = new String[]{"ROLE_ADMIN","ROLE_USER"};
	
	public Map<String,ConfigAttribute> roleConfigAttribute(String username){
    	Map<String,ConfigAttribute> auths = new HashMap<String,ConfigAttribute>();
    	for(int i=0;i<this.roles.length;i++){
    		ConfigAttribute configAttribute = new SecurityConfig(this.roles[i]);// 资源标识
    		auths.put(this.roles[i], configAttribute);
    	}
    	return auths;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(String username) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		// 根据自定义逻辑来返回用户权限，如果用户权限返回空或者和拦截路径对应权限不同，验证不通过ROLE_
		if (username.equals("admin")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_ADMIN");
			list.add(au);
		} else if (username.equals("user")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_USER");
			list.add(au);
		} else if (username.equals("other")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_OTHER");
			list.add(au);
			// 没有任何角色
		} else {// any
			GrantedAuthority au1 = new SimpleGrantedAuthority("ROLE_USER");
			list.add(au1);
			GrantedAuthority au2 = new SimpleGrantedAuthority("ROLE_ADMIN");
			list.add(au2);
		}
		return list;
	}

	@Override
	public Map<String, String> roleResources() {
		// TODO Auto-generated method stub
		return null;
	}
}
