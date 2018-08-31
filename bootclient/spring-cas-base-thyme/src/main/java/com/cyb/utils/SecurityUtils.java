package com.cyb.utils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月21日
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Component
public class SecurityUtils {
    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager;
    @Autowired
    BCryptPasswordEncoder bcrypt;
    
   /* @Autowired
    private TokenBasedRememberMeServices tokenBasedRememberMeServices;*/
    /**
     * 判断当前用户是否已经登陆
     * @return 登陆状态返回 true, 否则返回 false
     */
    public  boolean isLogin() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !"anonymousUser".equals(username);
    }
    /**
     * 取得登陆用户的 ID, 如果没有登陆则返回 -1
     * @return 登陆用户的 ID
     */
    public  String getLoginUserId() {
    		try{
	    		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    		if (isLogin()) {
	        		UserDetails userDetails = (UserDetails) principle;
	            	return userDetails.getUsername();
	    		}
    		}catch(Exception e){
    			return "";
    		}
        return "";
    }
    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return 登陆后需要访问的页面的 URL
     */
    public String login(String username, String password) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String defaultTargetUrl = "/"; // 默认登陆成功的页面
        String redirectUrl = "/login?error=1"; // 默认为登陆错误页面
        try {
        	//输入原始密码
            Authentication token = new UsernamePasswordAuthenticationToken(username, password);
            token = authenticationManager.authenticate(token); // 登陆
            SecurityContextHolder.getContext().setAuthentication(token);
            //tokenBasedRememberMeServices.onLoginSuccess(request, response, token); // 使用 remember me
            // 重定向到登陆前的页面
            SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
            redirectUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : defaultTargetUrl;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return redirectUrl;
    }
}
