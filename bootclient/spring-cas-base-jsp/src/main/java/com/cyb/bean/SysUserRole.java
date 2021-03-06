package com.cyb.bean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年7月27日
 */
@Entity
@Table(name="t_user_role")
public class SysUserRole {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String roleid;
    private String userid;//desc 是关键字，勿用 描述
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
