<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	HttpServletRequest request2 = (HttpServletRequest) request;
	request2.getSession().invalidate();
	response.sendRedirect(application
	          .getInitParameter("casServerLogoutUrl") + 
	          "?service="+application
	          .getInitParameter("casServerLoginUrl"));
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>退出测试</title>
</head>
<body>
</body>
</html>
