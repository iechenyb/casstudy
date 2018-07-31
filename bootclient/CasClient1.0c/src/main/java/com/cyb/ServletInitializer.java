package com.cyb;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
//@Configuration
public class ServletInitializer extends SpringBootServletInitializer {
	//spring-boot提供的解决方案：生成tomcat服务器能管理的war包，而非内嵌的tomcat直接生成的jar包
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CasClientApplication.class);
	}

}
