<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp" ng-controller="pageController">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 	<base href="<%=path%>" />
    <title>{{title}}</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/amazeui.min.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/amazeui.page.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/blacknav.css">
</head>
<body>
<input type="hidden" id="basePath" value="<%=basePath%>"></input>
<input type="hidden" id="path" value="<%=path%>"></input>
<app></app>
</body>
<script type="text/javascript" src="<%=basePath%>static/js/jquery.min.js"></script>
 <script type="text/javascript" src="<%=basePath%>static/js/amazeui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/angular.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/amazeui.page.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/angular-animate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/angular-ui-router.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/pub/pub.js"></script>
<script type="text/javascript" src="<%=basePath%>static/component/footer/index.js"></script>
<script type="text/javascript" src="<%=basePath%>static/component/header/index.js"></script>
<script type="text/javascript" src="<%=basePath%>static/component/top/index.js"></script>
<script type="text/javascript" src="<%=basePath%>static/component/common.js"></script>
<script type="text/javascript" src="index.js"></script> 
</html>