package com.cyb.config;

import javax.annotation.Resource;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.cyb.config.cas.AcmCasProperties;
/**
 *作者 : iechenyb<br>
 *类描述:hasrole不生效原因 prePostEnabled=true
 * http://blog.csdn.net/sinat_28454173/article/details/52312828 <br>
 *创建时间: 2017年12月13日
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter { /**
     * 自定义动态权限过滤器
     */
    @Resource
    private  MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    
   /* @Resource
    private final FilterStatic filterStatic;*/
    

    /**
     * 自定义过滤规则及其安全配置
     */
    @SuppressWarnings("unused")
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // HeadersConfigurer
        http.headers().frameOptions().disable();
        // CsrfConfigurer
        http.csrf().disable();
        //ExpressionInterceptUrlRegistry
        http.authorizeRequests()
        .antMatchers("/list.jsp","/service/list","/","/logout").permitAll()
        .anyRequest()
        .authenticated()
        .anyRequest().fullyAuthenticated();
        // acm cas策略
        // 对logout请求放行
        http.logout().permitAll();
        // 入口
        CasAuthenticationEntryPoint entryPoint = getApplicationContext().getBean(CasAuthenticationEntryPoint.class);
       /* CasAuthenticationFilter casAuthenticationFilter = getApplicationContext()
                    .getBean(CasAuthenticationFilter.class);*/
        SingleSignOutFilter singleSignOutFilter = getApplicationContext().getBean(SingleSignOutFilter.class);
        LogoutFilter logoutFilter = getApplicationContext().getBean(LogoutFilter.class);
            /**
             * 执行顺序为
             * LogoutFilter-->SingleSignOutFilter-->CasAuthenticationFilter-->
             * ExceptionTranslationFilter
             */
            http.exceptionHandling()
            .authenticationEntryPoint(entryPoint)
            .and()
            .addFilter(casAuthenticationFilter())
            .addFilterBefore(logoutFilter, LogoutFilter.class);
            //.addFilterBefore(singleSignOutFilter, SingleSignOutFilter.class);
            // addFilter
            http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
    @Autowired
    AcmCasProperties acm;
    
    @Autowired
    ServiceProperties serp ;
    /**CAS认证过滤器*/
   	@Bean
   	public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
   		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
   		casAuthenticationFilter.setServiceProperties(serp);
   		casAuthenticationFilter.setAuthenticationManager(authenticationManager());
   		casAuthenticationFilter.setFilterProcessesUrl(acm.getCasServerPrefix()+acm.getCasServerLoginUrl());
   		return casAuthenticationFilter;
   	}

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            //放入cas凭证校验器
            AuthenticationProvider authenticationProvider = (AuthenticationProvider) getApplicationContext()
                    .getBean("casProvider");
            auth.authenticationProvider(authenticationProvider);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.securityInterceptor(myFilterSecurityInterceptor);
        // 静态文静过滤
        web.ignoring().antMatchers("/service/**").antMatchers("**/*.jsp");
        web.privilegeEvaluator(customWebInvocationPrivilegeEvaluator());
    }
    
    @Bean
    @Primary//<sec:authorize url="/api/update"> 生效解决方案！
    public DefaultWebInvocationPrivilegeEvaluator customWebInvocationPrivilegeEvaluator() {
        return new DefaultWebInvocationPrivilegeEvaluator(myFilterSecurityInterceptor);
    }

	/**
     * cas filter类
     * 
     * 针对/login请求的校验
     * 
     * @return
     */
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(ServiceProperties properties,
            AcmCasProperties acmCasProperties) throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setServiceProperties(properties);
        casAuthenticationFilter.setFilterProcessesUrl(acmCasProperties.getAppServiceLoginUrl());
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter
                .setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/service/list"));
        return casAuthenticationFilter;
    }}
