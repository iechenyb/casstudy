package com.cyb.demo;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.utils.SecurityUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月19日
 */
@Controller
@RequestMapping("api")
public class ApiController {
	
	Log log = LogFactory.getLog(ApiController.class);
	
	@GetMapping("query")
	@ResponseBody
	public String getP(String username) {
		System.out.println("查看权限和角色");
		return "信息列表!";
	}

	@ResponseBody
	@GetMapping("add")
	public String add(String name) {
		return "添加信息成功！";
	}

	@ResponseBody
	@GetMapping("delete")
	public String delete(HttpServletRequest req) {
		return "删除信息成功";
	}

	@ResponseBody
	@GetMapping("update") 
	public String update(String name) {
		return "更新信息成功！";
	}
}
