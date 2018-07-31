package com.cyb.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cyb.dao.UserAuthDao;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年7月25日
 */
@Component
public class MemAuthDaoImpl implements UserAuthDao {
	Log log = LogFactory.getLog(MemAuthDaoImpl.class);
    /**
     * 读取用户角色资源信息
     */
	public Map<String, String> roleResources() {
		Map<String, String> auths = new HashMap<String, String>();
		// 管理员权限
		auths.put("/user/userConfig", "ROLE_USER");
		auths.put("/user/adminConfig", "ROLE_ADMIN");
		return auths;
	}
}
