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
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=request.getContextPath()%>-<%=request.getServerPort()%></title>
</head>
<body>
用户名:<sec:authentication property="name" />, 欢迎来到主页!<br>
 拥有权限:<sec:authentication property="principal.authorities" /><br>
 是否可用:<sec:authentication property="principal.enabled" /><br>
 未被锁定:<sec:authentication property="principal.accountNonLocked" /><br> 
<hr>
用户名：<%=token.getName()%>&nbsp;&nbsp;&nbsp;&nbsp;角色信息：<%=user.getAuthorities().toString()%>&nbsp;&nbsp;&nbsp;&nbsp;
<hr><br><a href="<%=basePath%>user/infor">Restful查看用户信息</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="<%=basePath%>service/list">服务列表</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="<%=basePath%>logout?service=<%=basePath%>user/infor">退出</a><br>
	<table border="1" width="50%">
		<thead>
			<tr>
				<td width="20%" colspan="2">系统所有菜单</td>
			</tr>
			<tr>
				<td width="20%">系统菜单</td>
				<td>接口</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>增加数据</td>
				<td><a target="_blank" href="<%=basePath%>api/add">增加数据</a></td>
			</tr>
			<tr>
				<td>删除数据</td>
				<td><a target="_blank" href="<%=basePath%>api/delete">删除数据</a></td>
			</tr>
			<tr>
				<td>更新数据</td>
				<td><a target="_blank" href="<%=basePath%>api/update">更新数据</a></td>
			</tr>
			<tr>
				<td>查询数据</td>
				<td><a target="_blank" href="<%=basePath%>api/query">查询数据</a></td>
			</tr>
		</tbody>
	</table>
	<hr>
	<table border="1" width="50%">
		<thead>
			<tr>
				<td width="20%" colspan="2">当前用户菜单</td>
			</tr>
			<tr>
				<td width="20%">系统菜单</td>
				<td>接口</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>增加数据(role)</td>
				<td>
					<sec:authorize access="hasRole('ROLE_ADD')">
						<a target="_blank" href="<%=basePath%>api/add">增加数据</a>
					</sec:authorize>&nbsp;
				</td>
			</tr>
			<tr>
				<td>删除数据(role)</td>
				<td>
					<sec:authorize access="hasRole('ROLE_DELETE')">
						<a target="_blank" href="<%=basePath%>api/delete">删除数据</a>
					</sec:authorize>&nbsp;
				</td>
			</tr>
			<tr>
				<td>更新数据(url)</td>
				<td>
					<sec:authorize url="/api/update">
						<a target="_blank" href="<%=basePath%>api/update">更新数据</a>
					</sec:authorize>&nbsp;
				</td>
			</tr>
			<tr>
				<td>查询数据(url)</td>
				<td>
					<sec:authorize url="/api/query">
						<a target="_blank" href="<%=basePath%>api/query">查询数据</a>
					</sec:authorize>&nbsp;
				</td>
			</tr>
		</tbody>
	</table>
	
	<!--从标签源码可以知道，authorize标签判断顺序是：
	 access->url->ifNotGranted->ifAllGranted->ifAnyGranted 
	 但他们的关系是“与”: 即只要其中任何一个属性不满足则该标签中间的内容将不会显示给用户,举个例子: -->
	<%-- <sec:authorize  ifAllGranted="ROLE_ADD" ifAnyGranted="ROLE_QUERY"
	  ifNotGranted="ROLE_UDATE">满足才会显示给用户 </sec:authorize> --%>
</body>
</html>