<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = application.getRealPath(request.getRequestURI());
	String basePath = "http://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
<meta charset="UTF-8">
<title>首页</title>
<style type="text/css"></style>
</head>
<body>
	<a href="<%=basePath%>sec/admin" target="_self">管理员权限</a>
	<a href="<%=basePath%>sec/user" target="_self">用户权限</a>
	<a href="<%=basePath%>sec/any" target="_self">管理员或者用户权限</a>
	<a href="<%=basePath%>sec/all" target="_self">匿名访问(用户登录信息)</a>
	<a href="<%=basePath%>logout" target="_self">退出系统</a>
</body>
</html>
