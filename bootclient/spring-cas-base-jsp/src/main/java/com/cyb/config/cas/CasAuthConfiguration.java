package com.cyb.config.cas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月22日
 */
@Configuration
public class CasAuthConfiguration {
	Log log = LogFactory.getLog(CasAuthConfiguration.class);
	@Resource
    private AcmCasProperties acmCasProperties;

    /**
     * 设置客户端service的属性
     * <p>
     * 主要设置请求cas服务端后的回调路径,一般为主页地址，不可为登录地址
     * acmCasProperties.getAppServicePrefix() + "/" 与 acmCasProperties.getAppServicePrefix() + "" 完全不同，需要注意。！！！！
     * </p>
     * 
     * @return
     */
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        // 设置回调的service路径，此为主页路径
        serviceProperties.setService(acmCasProperties.getAppServicePrefix() + "/");//登出地址 必须放开权限，否则被拦截，或者放在所有的拦截器之前使用。
        // 对所有的未拥有ticket的访问均需要验证
        serviceProperties.setAuthenticateAllArtifacts(true);
        return serviceProperties;
    }

  

    /**
     * 单点注销，接受cas服务端发出的注销session请求
     * 
     * @see SingleLogout(SLO) Front or Back Channel
     * 
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter outFilter = new SingleSignOutFilter();
        // 设置cas服务端路径前缀，应用于front channel的注销请求
        outFilter.setCasServerUrlPrefix(acmCasProperties.getCasServerPrefix());
        outFilter.setIgnoreInitConfiguration(true);
        return outFilter;
    }
    
    @Bean
    public FilterRegistrationBean singleSignOutFilterBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(singleSignOutFilter());
//        filterRegistrationBean.addInitParameter("targetFilterLifecycle","true")
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setName("singleFilter");
        System.out.println("================================singleFilter执行");
        return filterRegistrationBean;
    }
    private static boolean casEnabled  = true;
    /**
	 * 用于实现单点登出功能
	 */
	@Bean
	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
		ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
		listener.setEnabled(casEnabled);
		listener.setListener(new SingleSignOutHttpSessionListener());
		listener.setOrder(1);
		return listener;
	}

    /**
     * 单点请求cas客户端退出Filter类
     * 
     * 请求/logout，转发至cas服务端进行注销
     */
    @Bean
    public LogoutFilter logoutFilter() {
    	System.out.println("初始化登出过滤器...");
        // 设置回调地址，以免注销后页面不再跳转
        StringBuilder logoutRedirectPath = new StringBuilder();
        logoutRedirectPath.append(acmCasProperties.getCasServerPrefix())
                .append(acmCasProperties.getCasServerLogoutUrl()).append("?service=")
                .append(acmCasProperties.getAppServicePrefix());
        LogoutFilter logoutFilter = new LogoutFilter(logoutRedirectPath.toString(), new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl(acmCasProperties.getAppServiceLogoutUrl());
        logoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher("/**/logout", "GET"));
        return logoutFilter;
    }
    /** 
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前 
     */
    @Bean
    public FilterRegistrationBean logOutFilter() { 
      FilterRegistrationBean filterRegistration = new FilterRegistrationBean(); 
      LogoutFilter logoutFilter = new LogoutFilter(acmCasProperties.getCasServerPrefix() + "/logout?service=" + acmCasProperties.getAppServicePrefix(),new SecurityContextLogoutHandler()); 
      filterRegistration.setFilter(logoutFilter); 
      filterRegistration.setEnabled(casEnabled);
      if(acmCasProperties.getSignOutFilters().length()>0){ 
    	ArrayList<String> xs = new ArrayList<String>(2);
      	xs.add(acmCasProperties.getSignOutFilters());
        filterRegistration.setUrlPatterns(xs); 
      }else{
        filterRegistration.addUrlPatterns("/**/logout");
      }//casServerUrlPrefix
      filterRegistration.addInitParameter("casServerUrlPrefix", acmCasProperties.getCasServerPrefix()); 
      filterRegistration.addInitParameter("serverName", acmCasProperties.getAppServicePrefix()); 
      filterRegistration.setOrder(2); 
      return filterRegistration; 
    } 
    


    /**
     * 创建cas校验类
     * 
     * <p>
     * <b>Notes:</b> TicketValidator、AuthenticationUserDetailService属性必须设置;
     * serviceProperties属性主要应用于ticketValidator用于去cas服务端检验ticket
     * </p>
     * 
     * @return
     */
    @Bean("casProvider")
    public CasAuthenticationProvider casAuthenticationProvider(
            AuthenticationUserDetailsService<CasAssertionAuthenticationToken> userDetailsService) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setKey("casProvider");
        provider.setTicketValidator(cas20ServiceTicketValidator());
        provider.setAuthenticationUserDetailsService(userDetailsService);
        provider.setServiceProperties(serviceProperties());//可以重新定义一个参数
        return provider;
    }
    
   

    /**
     * ==============================================================
     * ==============================================================
     */

    /**
     * 认证的入口，即跳转至服务端的cas地址
     * 
     * <p>
     * <b>Note:</b>浏览器访问不可直接填客户端的login请求,若如此则会返回Error页面，无法被此入口拦截
     * </p>
     */
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        ServiceProperties serviceProperties = new ServiceProperties();
        // 设置回调的service路径，此为主页路径
        serviceProperties.setService(acmCasProperties.getAppServicePrefix() + "/index");//登出地址 必须放开权限，否则被拦截，或者放在所有的拦截器之前使用。
        // 对所有的未拥有ticket的访问均需要验证
        serviceProperties.setAuthenticateAllArtifacts(true);
        entryPoint.setServiceProperties(serviceProperties());//单独定义一个
        entryPoint.setLoginUrl(acmCasProperties.getCasServerPrefix() + acmCasProperties.getCasServerLoginUrl());
        return entryPoint;
    }
    /**	
     * 配置ticket校验器
     * 
     * @return
     */
    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
    	Cas20ServiceTicketValidator x  = new Cas20ServiceTicketValidator(acmCasProperties.getCasServerPrefix());
        // 配置上服务端的校验ticket地址
        return x;
    }
    /** 
     * 该过滤器负责对Ticket的校验工作 
     */
 /*  @Bean
    public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() { 
      
      Cas20ProxyReceivingTicketValidationFilter cas20ProxyReceivingTicketValidationFilter = new Cas20ProxyReceivingTicketValidationFilter(); 
      cas20ProxyReceivingTicketValidationFilter.setTicketValidator(cas20ServiceTicketValidator()); 
      cas20ProxyReceivingTicketValidationFilter.setServerName(acmCasProperties.getAppServicePrefix()); 
     
      FilterRegistrationBean filterRegistration = new FilterRegistrationBean(); 
      filterRegistration.setFilter(cas20ProxyReceivingTicketValidationFilter); 
      filterRegistration.setEnabled(casEnabled); 
      if(autoconfig.getValidateFilters().size()>0) 
        filterRegistration.setUrlPatterns(autoconfig.getValidateFilters()); 
      else
      filterRegistration.addUrlPatterns("/*"); 
      filterRegistration.addInitParameter("casServerUrlPrefix", acmCasProperties.getCasServerPrefix()); 
      filterRegistration.addInitParameter("serverName", acmCasProperties.getAppServicePrefix()); 
      filterRegistration.setOrder(5); 
      return filterRegistration; 
    } */

    
    /** 
     * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名 
     * 加上当前方法，jsp页面无法获取用户的名称等信息。！！！！！！！
     */
    /*@Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter() { 
      FilterRegistrationBean filterRegistration = new FilterRegistrationBean(); 
      filterRegistration.setFilter(new HttpServletRequestWrapperFilter()); 
      filterRegistration.setEnabled(true); 
      if(autoconfig.getRequestWrapperFilters().size()>0) 
        filterRegistration.setUrlPatterns(autoconfig.getRequestWrapperFilters()); 
      else
      filterRegistration.addUrlPatterns("/*"); 
      filterRegistration.setOrder(6); 
      return filterRegistration; 
    }*/

    
    /** 
     * 该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。 
     比如AssertionHolder.getAssertion().getPrincipal().getName()。 
     这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息 
     */
    /*@Bean
    public FilterRegistrationBean assertionThreadLocalFilter() { 
      FilterRegistrationBean filterRegistration = new FilterRegistrationBean(); 
      filterRegistration.setFilter(new AssertionThreadLocalFilter()); 
      filterRegistration.setEnabled(true); 
      if(autoconfig.getAssertionFilters().size()>0) 
        filterRegistration.setUrlPatterns(autoconfig.getAssertionFilters()); 
      else
        filterRegistration.addUrlPatterns("/*"); 
      filterRegistration.setOrder(7); 
      return filterRegistration; 
    } */
   /* @Bean
	public FilterRegistrationBean ValidationFilterRegistrationBean(){
		FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
		authenticationFilter.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("casServerUrlPrefix", "http://cas.kiiik.com:8088/cas");
		initParameters.put("serverName", "http://aaa.kiiik.com:8081/aaa");
		authenticationFilter.setInitParameters(initParameters);
		authenticationFilter.setOrder(1);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");// 设置匹配的url
		authenticationFilter.setUrlPatterns(urlPatterns);
		return authenticationFilter;
	}
	
	@Bean
	public FilterRegistrationBean casHttpServletRequestWrapperFilter(){
		FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
		authenticationFilter.setFilter(new HttpServletRequestWrapperFilter());
		authenticationFilter.setOrder(3);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");// 设置匹配的url
		authenticationFilter.setUrlPatterns(urlPatterns);
		return authenticationFilter;
	}
	
	@Bean
	public FilterRegistrationBean casAssertionThreadLocalFilter(){
		FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
		authenticationFilter.setFilter(new AssertionThreadLocalFilter());
		authenticationFilter.setOrder(4);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");// 设置匹配的url
		authenticationFilter.setUrlPatterns(urlPatterns);
		return authenticationFilter;
	}*/
}
