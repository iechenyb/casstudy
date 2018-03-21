<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basePath = "http://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>post模拟</title>
</head>
<body style='margin-left: 10%; margin-right: 10%'>
	<form enctype="multipart/form-data" action="post.do" method="post">
		<table>
			<tr>
				<td><label for="txtname">账号：</label></td>
				<td><input type="text" id="txtname" name="id" /></td>
			</tr>
			<tr>
				<td><label for="txtpswd">密码：</label></td>
				<td><input type="password" id="txtpswd" name="login_pswd" /></td>
			</tr>
			<tr>
				<td colspan=2><input type="reset" /> <input type="submit" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>