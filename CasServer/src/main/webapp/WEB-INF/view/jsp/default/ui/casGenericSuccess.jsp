<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:directive.include file="includes/top.jsp"/>
<div id="msg" class="success">
    <h2>${requestScope.credential.username},<spring:message code="screen.success.header"/></h2>
    <p><spring:message code="screen.success.success"/></p>
    <p><spring:message code="screen.success.security"/></p>
</div>
<table width="100%" border=	11>
	<tr>
		<td width=20%></td>
		<td width=50%><a href="http://ocalhost:8088/casclientc/index.jsp">授信应用C</a></td>
		<td><a href="http://localhost:8088/casclientd/index.jsp" target="_blank">授信应用D</a></td>
	<tr>
</table>
<jsp:directive.include file="includes/webs.jsp"/>
<jsp:directive.include file="includes/bottom.jsp"/>

