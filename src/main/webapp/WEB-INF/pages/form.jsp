<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<html>
<head>
<script type="text/javascript" src="<c:url value="/js/keyboard.js " />"></script>
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<link href="<c:url value="/css/keyboard.css" />" rel="stylesheet"
	type="text/css">
<title>Login Page</title>
<script type = "text/javascript" >
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
	<noscript>
  <meta http-equiv="refresh" content="0; url=${pageContext.servletContext.contextPath}/logoutusers" />
  Javascript Disabled
</noscript>

	<h1 align="center">Sun Devils Bank</h1>

	<c:if test="${userId != null}">
		<p>
			Welcome ${userType} : ${userId}
		</p>
	</c:if>

	<form:form action="${pageContext.servletContext.contextPath}/readForm/?${_csrf.parameterName}=${_csrf.token}" method="post">
		My user id: <input type="text" name="userId"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" name="admin" value="this is admin"/>
		<input type="submit" name="user" value="this is user"/>
	</form:form>
	
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/form.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>