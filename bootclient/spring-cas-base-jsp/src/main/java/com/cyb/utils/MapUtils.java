package com.cyb.utils;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月21日
 */
public class MapUtils extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(MapUtils.class);

	@Override
	public MapUtils put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	/*public static MapUtils add(String key, Object value) {
		super.put(key, value);
		return this;
	}*/
	
	public static void main(String[] args) {
		MapUtils map = new MapUtils();
		map.put("11", "12");
		map.put("22", "23");
		//new HashMap<>().put("111", "1111").put("222","333"); 不可以连续的增加属性
		System.out.println(map);
	}

}
