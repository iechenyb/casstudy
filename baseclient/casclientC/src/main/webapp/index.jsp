<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.jasig.cas.client.util.*"%>
<%@ page
	import="org.jasig.cas.client.validation.*,java.security.Principal"%>
<%@ page import="java.util.*,org.jasig.cas.client.authentication.*"%>
<%
	HttpServletRequest request2 = (HttpServletRequest) request;
	// 从Cas服务器获取登录账户的用户名（2种方式）
	Principal username1 = request2.getUserPrincipal();
	String username2 = request2.getRemoteUser();
	String str = "";
	Assertion assertion = AssertionHolder.getAssertion();
	if (assertion != null) {
		AttributePrincipal attributePrincipal = assertion.getPrincipal();
		if (attributePrincipal != null) {
			String name = attributePrincipal.getName();
			Map<String, Object> map = attributePrincipal.getAttributes();
			if (map != null) {
				Object id = map.get("id");
				Object account = map.get("username");
				str = str + ",id=" + id;
				str = str + ",account=" + account;
			}
		}
	}
	String sstr="";
	//获取session
	session = request2.getSession();
	// 获取session中所有的键值
	Enumeration<?> enumeration = session.getAttributeNames();
	// 遍历enumeration中的
	while (enumeration.hasMoreElements()) {
		// 获取session键值
		String name = enumeration.nextElement().toString();
		// 根据键值取session中的值
		Object value = session.getAttribute(name);
		// 打印结果
		sstr=sstr+("<B>" + name + "</B>=" + value + "<br>/n");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试c</title>
</head>
<body>
	<h2>
		Hello World，casclientc!<%=username1%>--<%=username2%></h2>
	<a href="exit.jsp">退出</a>
</body>
</html>
