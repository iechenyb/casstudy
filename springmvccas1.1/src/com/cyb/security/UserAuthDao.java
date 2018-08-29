package com.cyb.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年7月25日
 */
public interface UserAuthDao {
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 角色-资源对应信息 key=uri value=role<br>
	 *创建时间: 2017年7月15日hj12
	 *@return
	 */
	Map<String,String> roleResources(String username);
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 将角色进行组装<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roles
	 *@return
	 */
	public Map<String,ConfigAttribute> roleConfigAttribute(String username);
	public Collection<? extends GrantedAuthority> getAuthorities(String username);
	Map<String,String> roleResources();
}
