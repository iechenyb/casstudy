<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   HttpServletRequest request2 = (HttpServletRequest) request;
 // 从Cas服务器获取登录账户的用户名（2种方式）
 Object username1 = request2.getUserPrincipal();
 String username2 = request2.getRemoteUser();
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试c</title>
</head>
<body>
<h2>用户信息，casclientc!<%=username1%>--<%=username2%></h2>
<a href="exit.jsp">退出</a>
</body>
</html>
