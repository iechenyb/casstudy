<%@page import="org.apache.jasper.tagplugins.jstl.core.Import"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> --%>
<%@ page
	import="org.springframework.security.cas.authentication.*"%>
<%@ page import="org.springframework.security.core.userdetails.*"%>
<%
  String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/";  
   HttpServletRequest req = (HttpServletRequest) request;
 // 从Cas服务器获取登录账户的用户名（2种方式）
    CasAuthenticationToken token = (CasAuthenticationToken)req.getUserPrincipal();
	User user = (User) token.getPrincipal();
 %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=request.getContextPath()%>-<%=request.getServerPort()%></title>
</head>
<body>
用户名：<%=token.getName()%>&nbsp;&nbsp;&nbsp;&nbsp;角色信息：<%=user.getAuthorities().toString()%><br><br>
<a href="<%=basePath%>user/infor">Restful查看用户信息</a><br><br>
<a href="<%=basePath%>service/list">服务列表</a><br><br>
<a href="<%=basePath%>logout?service=<%=basePath%>user/infor">退出</a><br>
</body>
</html>