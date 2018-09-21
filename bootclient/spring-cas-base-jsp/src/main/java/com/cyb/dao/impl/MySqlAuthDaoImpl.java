package com.cyb.dao.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.cyb.dao.UserAuthDao;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年7月25日
 */
@Repository
public class MySqlAuthDaoImpl implements UserAuthDao{
	Log log = LogFactory.getLog(MySqlAuthDaoImpl.class);
	@Autowired
	JdbcTemplate jdbc;

	public static Map<String,Object>  authData = new HashMap<>();
	public static String RolesResources="roles-resources";
	public static String RoleConfig = "roleConfig";
	public static String Authorities = "Authorities";
    
	//废弃不用
	@Override
	public Map<String, String> roleResources(String username) {
		return null;
	}

	//废弃不用
	public Map<String,ConfigAttribute> roleConfigAttribute(String username){
    	return null;
    }
    //list<SimpleGrantedAuthority> 根据用户名查询用户的角色信息
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(String username) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		String sql = " SELECT b.name FROM T_USER_ROLE  a ,t_role b,t_user c where a.roLEID  = b.id and c.id = a.userID and c.account='"+username+"'";
		List<Map<String,Object>> data = jdbc.queryForList(sql);
		if(!CollectionUtils.isEmpty(data)){
			for(Map<String,Object> role:data){
				list.add(new SimpleGrantedAuthority(role.get("NAME").toString()));
			}
		}
		System.out.println(username+"的角色列表 "+list.size()+"-->"+list);
		return list;
	}

     //map<url,role> //做个缓存1分钟更新一次
	@Override
	public Map<String, String> roleResources() {
		System.out.println("加载系统角色-资源信息！");
		Map<String, String> ret = new HashMap<>();
			String sql = "SELECT b.name,b.note,c.url "+
		  " FROM T_ROLE_RESOURCE  a,t_ROLE b,t_RESOURCE c "+
		  " where a.RESOURCEID  =c.id and b.id=a.roleid ";
		List<Map<String,Object>> data = jdbc.queryForList(sql);
		if(!CollectionUtils.isEmpty(data)){
			for(Map<String,Object> resource :data){
				ret.put(resource.get("URL").toString(), resource.get("NAME").toString());
			}
		}
		return ret;
	}


}
