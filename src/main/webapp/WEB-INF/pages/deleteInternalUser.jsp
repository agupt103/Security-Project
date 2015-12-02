<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Delete Internal User</title>
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
	<h2>JavaScript is disabled! Why you want to do so? 	Please enable JavaScript in your web browser and Refresh!</h2>
	<style type="text/css">#main-content { display:none; }</style>
</noscript>
	<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/Home">Home</a></div>
	<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></div>



<form action="delInternalUser" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		<table border="3" align="center">
			<h2 align="center">	<u>Delete Internal User</u></h2>
			<div><c:if test="${not empty nouser}"><div class="msg">${nouser}</div></c:if></div>	
			<div><c:if test="${not empty delinternal}"><div class="msg">${delinternal}</div></c:if></div>	
			<div><c:if test="${not empty notchecked}"><div class="msg">${notchecked}</div></c:if></div>				
			<tr>
				<th>Select</th>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
			</tr>
			<c:forEach var="details" items="${list}">
				<tr>
					<td><input type="checkbox" name="cblist" value="${details.username}"></td>
					<td><c:out value="${details.username}" /></td>
					<td><c:out value="${details.firstname}" /></td>
					<td><c:out value="${details.lastname}" /></td>
				</tr>
			</c:forEach>
		</table>		
		<tr>
			<td><button type="submit" id="deleteusers">Delete</button></td>
		</tr>
		</form>		
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/deleteInternalUser.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>