<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ page
	import="org.springframework.security.cas.authentication.*"%>
<%@ page
	import="org.jasig.cas.client.validation.*,java.security.Principal"%>
<%@ page import="org.springframework.security.core.userdetails.*"%>
<%-- <%
	HttpServletRequest req = (HttpServletRequest) request;
	// 从Cas服务器获取登录账户的用户名（2种方式）
	CasAuthenticationToken token = (CasAuthenticationToken) req.getUserPrincipal();
	//User user = (User) token.getPrincipal();
	System.out.println("xx:"+token.getAuthorities());
%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>跳转页面</title>
</head>
<body>
	跳转页面-${ret}-${error}
</body>
</html>