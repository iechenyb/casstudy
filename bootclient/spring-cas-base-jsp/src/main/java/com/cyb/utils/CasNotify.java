package com.cyb.utils;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年7月31日
 */
public class CasNotify {
	Log log = LogFactory.getLog(CasNotify.class);
	@SuppressWarnings("unused")
	public static void main1111111(String[] args) {
		String msg = "<samlp:LogoutRequest  xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" "
				+ " ID=\"LR-2-xLwgvikF1qbbkjHdJKdtvzIZvDWY9zms5cj\" "
				+ " Version=\"2.0\" IssueInstant=\"2018-07-30T13:28:35Z\">"
				+ " <saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">@NOT_USED@</saml:NameID>"
				+ " <samlp:SessionIndex>ST-2-yrId1gIlsOeFZfKZgCDM-http://cas.kiiik.com:8088"
				+ " </samlp:SessionIndex>"
				+ " </samlp:LogoutRequest>";
		    final String token = XmlUtils.getTextForElement(msg, "SessionIndex");//ST-2-yrId1gIlsOeFZfKZgCDM-http://cas.kiiik.com:8088
	        if (CommonUtils.isNotBlank(token)) {
	            final HttpSession session = null;//this.sessionMappingStorage.removeSessionByMappingId(token);
	            if (session != null) {
	                final String sessionID = session.getId();
	                System.out.println("Invalidating session [{}] for token [{}]"+sessionID+","+token);
	                try {
	                    session.invalidate();
	                } catch (final IllegalStateException e) {
	                   System.out.println("Error invalidating session.");
	                }
	                //this.logoutStrategy.logout(request);
	            }
	        }
	}
}
