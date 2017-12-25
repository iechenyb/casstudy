package com.cyb.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * 当向session中放入数据触发
 */
public class LoginListenner implements HttpSessionAttributeListener {
	/**
     * 用于存放账号和session对应关系的map
     */
    private Map<String,HttpSession> map = new HashMap<String, HttpSession>();
 
	public void attributeAdded(HttpSessionBindingEvent event) {
		String name = event.getName();

		/*if (name.equals("loginuser")) {
			User user = (User) event.getValue();
			if (map.get(user.getName()) != null) {
				HttpSession session = map.get(user.getName());
				session.removeAttribute(user.getName());
				session.invalidate();
			}
			map.put(user.getName(), event.getSession());
		}*/

	}

	/**
	 * 当向session中移除数据触发
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) {
		String name = event.getName();

		/*if (name.equals("loginuser")) {
			User user = (User) event.getValue();
			map.remove(user.getName());

		}*/
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {

	}

	public Map<String, HttpSession> getMap() {
		return map;
	}

	public void setMap(Map<String, HttpSession> map) {
		this.map = map;
	}

}
