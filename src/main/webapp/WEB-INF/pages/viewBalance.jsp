<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.user.info.AccountDetails"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page isELIgnored='true' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>View Balance</title>
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
	
<table align = "center">
	<tr>
		<td><a href="./Home">Home</a></td>
		<td><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></td>
	</tr>
</table>
<br/>

<h2 align = "center">View Balance</h2>
<table border="1" align = "center">
	<tr>
		<th>Account Number</th>
		<th>Account Type</th>
		<th>Balance</th>
	</tr>
<c:forEach var="details" items="${accountDetails}" >
	<tr>
		<td><c:out value="${details.accountNumber}"/></td>
		<td><c:out value="${details.accountType}"/></td>
		<td><c:out value="${details.balance}"/></td>
	</tr>
</c:forEach>		

</table>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/viewBalance.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>