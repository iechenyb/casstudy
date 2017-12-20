<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   HttpServletRequest request2 = (HttpServletRequest) request;
 // 从Cas服务器获取登录账户的用户名（2种方式）
 String username1 = request2.getUserPrincipal().toString();
 String username2 = request2.getRemoteUser();
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试a</title>
</head>
<body>
<h2>Hello World，casclienta!<%=username1%>--<%=username2%></h2>
</body>
</html>
