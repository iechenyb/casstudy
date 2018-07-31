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
//@Repository
public class MySqlAuthDaoImpl implements UserAuthDao{
	Log log = LogFactory.getLog(MySqlAuthDaoImpl.class);
	@Autowired
	JdbcTemplate jdbc;

	public static Map<String,Object>  authData = new HashMap<>();
	public static String RolesResources="roles-resources";
	public static String RoleConfig = "roleConfig";
	public static String Authorities = "Authorities";
    
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> roleResources(String username) {
		String sql = "select tr.url ,tr.name,trr.roleid,a.name "+
		" from t_resource tr,  T_ROLE_RESOURCE trr "+
		" left join t_role a  on a.id=trr.roleid"+
		" where  trr.resourceid  = tr.id "+
		" and trr.roleid in("+
		" select ro.id from t_user u ,t_role ro,t_user_role ur"+ 
		" where  u.id=ur.userid  and account='"+username+"'"+
		"and ur.roleid=ro.id )";
		authData.put(username+"#"+RolesResources, null);
		authData.put(username+"#"+RoleConfig, null);
		authData.put(username+"#"+Authorities, null);
		jdbc.queryForList(sql);
		//将信息组装，放到map中
		return (Map<String, String>) authData.get(username+"#"+RolesResources);
	}


	public Map<String,ConfigAttribute> roleConfigAttribute(String username){
    	@SuppressWarnings("unchecked")
		Map<String,ConfigAttribute> auths = (Map<String, ConfigAttribute>) authData.get(username+"#"+RoleConfig);
    	return auths;
    }
    //list<SimpleGrantedAuthority>
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

     //map<url,role>
	@Override
	public Map<String, String> roleResources() {
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
