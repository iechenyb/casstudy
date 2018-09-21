<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/";  
    HttpServletRequest req = (HttpServletRequest) request;
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=request.getContextPath()%>-<%=request.getServerPort()%></title>
</head>
<body>
	<a href="index.jsp" target="_self">进入管理页面</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="<%=basePath%>logout?service=<%=basePath%>index">退出</a><br>
	<table border="1" width="50%">
		<thead>
			<tr>
				<td width="20%">系统服务名</td>
				<td>系统地址入口</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>BMS</td>
				<td><a target="_blank" href="http://bms.kiiik.com:8081/bms/index">业务管理系统(BMS)</a></td>
			</tr>
			<tr>
				<td>HR</td>
				<td><a target="_blank" href="http://hr.kiiik.com:8082/hr/index">人力资源管理系统</a></td>
			</tr>
			<tr>
				<td>OA</td>
				<td> <a target="_blank" href="http://oa.kiiik.cn:8083/oa/index">OA办公平台</a></td>
			</tr>
			<!-- <tr>
				<td>中央认证服务</td>
				<td><a target="_blank" href="http://cas.kiiik.com:8088/cas/login">中央认证服务-登录</a></td>
			</tr>
			<tr>
				<td>中央认证服务</td>
				<td><a target="_blank" href="http://cas.kiiik.com:8088/cas/logout">中央认证服务-退出</a></td>
			</tr> -->
		</tbody>
	</table>
</body>
</html>