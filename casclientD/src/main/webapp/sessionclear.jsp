<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   HttpServletRequest request2 = (HttpServletRequest) request;
   request2.getSession().invalidate();
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试d</title>
</head>
<body>
<h2>Hello World，清除session测试！</h2>
</body>
</html>
