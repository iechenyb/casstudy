<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags'%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  <sec:authentication property ="principal" var ="authentication"/>
      <a href="<%=basePath%>api/add.html">增加页面</a>&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="<%=basePath%>/api/query.html">查询页面</a>&nbsp;&nbsp;&nbsp;&nbsp;
      <%-- <a href="<%=basePath%>exit.html">退出系统</a><br> --%>
      <a href="<%=basePath%>logout.html?service=<%=basePath%>">退出</a><br>
      <hr>
      <sec:authorize ifAllGranted ="ROLE_ADD">  <a href="<%=basePath%>api/add.html">add</a> </sec:authorize>
      <sec:authorize ifAllGranted ="ROLE_DELETE"> <a href="<%=basePath%>api/delete.html">删除可见</a></sec:authorize>
      <sec:authorize ifAllGranted ="ROLE_UPDATE"><a href="<%=basePath%>api/update.html">更新可见</a> </sec:authorize>
      <sec:authorize ifAllGranted ="ROLE_QUERY"><a href="<%=basePath%>api/query.html">查询可见</a> </sec:authorize>
      <hr>
      <sec:authorize access="hasRole('ROLE_ADD')">
        <a href="<%=basePath%>api/add.html">增加可见</a>
      </sec:authorize>
      <sec:authorize access="hasRole('ROLE_DELETE')">
        <a href="<%=basePath%>api/delete.html">删除可见</a>
      </sec:authorize>
      <sec:authorize access="hasRole('ROLE_UPDATE')">
        <a href="<%=basePath%>api/update.html">更新可见</a>
      </sec:authorize>
      <sec:authorize access="hasRole('ROLE_QUERY')">
        <a href="<%=basePath%>api/query.html">查询可见</a>
      </sec:authorize>
       <hr>
   <!-- 需要拥有所有的权限 -->
   <sec:authorize ifAllGranted="ROLE_ADD">
      <a href="<%=basePath%>api/add.html">增加可见</a>
      <sec:authorize ifAnyGranted="ROLE_ADD,ROLE_UPDATE">增加和修改用户可见(满足一个角色即可)</sec:authorize>
   </sec:authorize>
   <!-- 只需拥有其中任意一个权限 -->
   <hr>
   <!-- 不允许拥有指定的任意权限 -->
   <sec:authorize ifNotGranted="ROLE_ADD">
      <a href="<%=basePath%>api/add.html">非增加可见</a>
   </sec:authorize>
              用户名：${authentication.username } <br/> 
      <hr>
    <!-- 拥有访问指定url的权限才显示其中包含的内容 -->
   <sec:authorize url="api/add.html">
      <a href="<%=basePath%>api/add.html">add-url</a>
   </sec:authorize>
   <sec:authorize url="api/delete.html">
      <a href="<%=basePath%>api/delete.html">delete</a>
   </sec:authorize>
   <sec:authorize url="api/update.html">
      <a href="<%=basePath%>api/update.html">update</a>
   </sec:authorize>
   <sec:authorize url="api/query.html">
      <a href="<%=basePath%>api/query.html">query</a>
   </sec:authorize>
   <hr>
    <sec:authorize access="isFullyAuthenticated()" var="isFullyAuthenticated">
      	只有通过登录界面进行登录的用户才能看到1。<br/>
   </sec:authorize>
   		上述权限的鉴定结果是：${isFullyAuthenticated }<br/>
   <%if((Boolean)pageContext.getAttribute("isFullyAuthenticated")) {%>
      	只有通过登录界面进行登录的用户才能看到2。
   <%}%>
   <sec:authentication property="principal.username"/>
   <sec:authentication property="name"/>
   <!-- 将获取到的用户名以属性名username存放在session中 -->
   <sec:authentication property="principal.username" scope="session" var="username"/>
   ${username }
  </body>
</html>
