package com.cyb.dao;

import java.util.Map;

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
	Map<String,String> roleResources();
}
