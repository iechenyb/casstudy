package com.cyb.config.cas;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月22日
 */
@Component
public class AcmCasProperties {
	Log log = LogFactory.getLog(AcmCasProperties.class);
	
	@Value("${cas.server.host.url}")
	private String casServerPrefix;

	@Value("${cas.server.host.login_url}")
	private String casServerLoginUrl;

	@Value("${cas.server.host.logout_url}")
	private String casServerLogoutUrl;

	@Value("${app.server.host.url}")
	private String appServicePrefix;

	@Value("${app.login.url}")
	private String appServiceLoginUrl;

	@Value("${app.logout.url}")
	private String appServiceLogoutUrl;

	public String getCasServerPrefix() {
	    return casServerPrefix;
	}

	public void setCasServerPrefix(String casServerPrefix) {
	    this.casServerPrefix = casServerPrefix;
	}

	public String getCasServerLoginUrl() {
	    return casServerLoginUrl;
	}

	public void setCasServerLoginUrl(String casServerLoginUrl) {
	    this.casServerLoginUrl = casServerLoginUrl;
	}

	public String getCasServerLogoutUrl() {
	    return casServerLogoutUrl;
	}

	public void setCasServerLogoutUrl(String casServerLogoutUrl) {
	    this.casServerLogoutUrl = casServerLogoutUrl;
	}

	public String getAppServicePrefix() {
	    return appServicePrefix;
	}

	public void setAppServicePrefix(String appServicePrefix) {
	    this.appServicePrefix = appServicePrefix;
	}

	public String getAppServiceLoginUrl() {
	    return appServiceLoginUrl;
	}

	public void setAppServiceLoginUrl(String appServiceLoginUrl) {
	    this.appServiceLoginUrl = appServiceLoginUrl;
	}

	public String getAppServiceLogoutUrl() {
	    return appServiceLogoutUrl;
	}

	public void setAppServiceLogoutUrl(String appServiceLogoutUrl) {
	    this.appServiceLogoutUrl = appServiceLogoutUrl;
	}
}
