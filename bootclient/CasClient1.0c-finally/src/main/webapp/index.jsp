<%@page import="org.apache.jasper.tagplugins.jstl.core.Import"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> --%>
<%@ page
	import="org.springframework.security.cas.authentication.*"%>
<%@ page import="org.springframework.security.core.userdetails.*"%>
<%
   HttpServletRequest req = (HttpServletRequest) request;
 // 从Cas服务器获取登录账户的用户名（2种方式）
    CasAuthenticationToken token = (CasAuthenticationToken)req.getUserPrincipal();
	User user = (User) token.getPrincipal();
 %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>boot-cas</title>
</head>
<body>
欢迎登陆主页面：<%=token.getName()%>,<%=user.getAuthorities().toString()%>
<a href="logout?service=''http://localhost:8083/user/infor">退出</a>
</body>
</html>