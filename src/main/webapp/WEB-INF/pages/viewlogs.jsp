<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,authentication.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Review External Users</title>
<script type="text/javascript">
    history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) {
    history.pushState(null, null, window.location.href);
    });
    document.addEventListener("contextmenu", function(e){
        e.preventDefault();
    }, false);
    </script>
</head>
<body oncopy="return false" oncut="return false" onpaste="return false">
	
<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
	
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	</div>
	<form method="POST"
		action="${pageContext.servletContext.contextPath}/logsaccess"
		name="form">

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<h2 align="center">Access Logs</h2>
		<table align="center">
			<tr>
				<td><a href="viewfile">${file1}</a></td>
			</tr>
			<tr>
				<td><input type = 'submit' value = 'submit' align ="middle"></td>
			</tr>
		</table>
	</form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/viewlogs.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>