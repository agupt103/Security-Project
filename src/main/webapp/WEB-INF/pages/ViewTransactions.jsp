<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Insert title here</title>
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
	
<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	</div>
	<br></br>
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
	</div>
<table border="3" align="center">
		<h2 align="center">
			<u>Requests</u>
		</h2>
		<tr>
			<th>UserName</th>
			<th>Transaction ID</th>
			<th>Transaction Amount</th>
			<th>Source Account</th>
			<th>Destination Account</th>
			<th>Date and Time</th>
			<th>Transfer Type</th>
			<th>Status</th>
		</tr>
		<c:forEach var="view" items="${requestView}">
			<tr>
				<td><c:out value="${view.userName}" /></td>
				<td><c:out value="${view.transactionID}" /></td>
				<td><c:out value="${view.transactionAmount}" /></td>
				<td><c:out value="${view.sourceAccount}" /></td>
				<td><c:out value="${view.destAccount}" /></td>
				<td><c:out value="${view.dateandTime}" /></td>
				<td><c:out value="${view.transferType}" /></td>
				<td><c:out value="${view.status}" /></td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/ViewTransactions.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>