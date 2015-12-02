<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.sql.Date"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type = "text/javascript" >
    history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) {
    history.pushState(null, null, window.location.href);
    });
    document.addEventListener("contextmenu", function(e){
        e.preventDefault();
    }, false);
    </script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Authorize Transaction</title>
<style>
body {
	text-align: center;
}

.buttons {
	padding-top: 20px;
}

label {
	display: inline-block;
	width: 140px;
	text-align: center;
}
</style>
</head>

<body oncopy="return false" oncut="return false" onpaste="return false">
<noscript>
  <meta http-equiv="refresh" content="0; url=${pageContext.servletContext.contextPath}/logoutusers" />
  Javascript Disabled
</noscript>
<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/Home">Home</a>&nbsp;&nbsp;&nbsp;
	<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></div>
	<c:choose>
		<c:when test="${payments != null && payments.size() != 0}">
			<c:set var="count" value="0" scope="page" />
			<h2 align="center">Payment Requests:</h2>
			<br>
			<br>

			<table align = "center">
				<th>Requested by</th>
				<th>amount</th>
				<th>date</th>
				<th>actions</th>
				<c:forEach items="${payments}" var="payment">
					<tr>
						<td><c:out value="${payment.username}" /></td>
						<td><c:out value="${payment.amount}" /></td>
						<td><c:out value="${payment.date.toString()}" /></td>
						<td>
							<form
								action="${pageContext.servletContext.contextPath}/payerpaymentdecision/"
								method="POST">
								<div style="text-align: center"><input type="submit" name="accept" value="Accept Payment" /> 
								<input type="submit" name="reject" value="Reject Payment" /> 
								<input type="hidden" name="iteration" value="${count}" /> 
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></div>
							</form>
						</td>
					</tr>
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<h2 align = "center">There are no payments submitted to you.</h2>
		</c:otherwise>
	</c:choose>

	<br/>
	<br/>
	<br/>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/Individual.Customers/Customer.Authorize.Payments.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>