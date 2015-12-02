<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>PII Requests</title>
<script type = "text/javascript" >
   	history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) 
    {history.pushState(null, null, window.location.href);});
    document.addEventListener("contextmenu", function(e)
    {e.preventDefault();}, false);
</script>
</head>
<body oncopy="return false" oncut="return false" onpaste="return false">
<noscript>
	<h2>JavaScript is disabled! Why you want to do so? Please enable JavaScript in your web browser and Refresh!</h2>
	<style type="text/css">#main-content { display:none; }</style>
</noscript>
<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
	</div>
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</div>
<form:form action="updatepiistatus" method='POST'>
 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	<table border="3" align="center">
		<h2 align="center">	<u>PII Requests</u></h2>	
		<div><c:if test="${not empty norequest}"><div class="msg">${norequest}</div></c:if></div>
		<div><c:if test="${not empty sent}"><div class="msg">${sent}</div></c:if></div>
		<tr>
				<th>Select</th>
				<th>Government Request ID</th><th></th>				
				<th>Username</th><th></th>
				<th>First Name</th><th></th>
				<th>Last Name</th><th></th>
				<th>Account Number</th><th></th>
				<th>Email</th><th></th>
				<th>Phone Number</th><th></th>
				<th>Address</th><th></th>								
		</tr>			
		<c:forEach var="list" items="${showList}" varStatus="loop">
				<tr>
					<td><input type="checkbox" name="checkList" value="${list.pid}"></td>
					<td><c:out value="${list.pid}" /></td><td></td>
					<td><c:out value="${list.username}" /></td><td></td>
					<td><c:out value="${list.firstname}" /></td><td></td>
					<td><c:out value="${list.lastname}" /></td><td></td>
					<td><c:out value="${list.accountnumber}" /></td><td></td>
					<td><c:out value="${list.email}" /></td><td></td>	
					<td><c:out value="${list.phonenumber}" /></td><td></td>
					<td><c:out value="${list.address}" /></td><td></td>			
				</tr>
		</c:forEach>			
	</table>
	</br>
	</br>
	<div style="text-align: center">
			<select name="Type"><option value="Approve">Approve</option><option value="Reject">Reject</option></select> 
			<br></br> <br></br> <input name="submit" type="submit" value="submit" />
		</div>		
</form:form>	
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/modifyUsers.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>