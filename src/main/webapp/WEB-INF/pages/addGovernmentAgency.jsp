<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="-1">
	<title>Add Government Agency</title>
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

<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/Home">Home</a></div>
<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></div>
	
		<form action="addgovernmentagency" method='POST'><input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			<table border="3" align="center">
			<h2 align="center"><u>Add Government Agency</u></h2>
			<div><c:if test="${not empty agencyname}"><div class="msg">${agencyname}</div></c:if></div>
			<div><c:if test="${not empty agencyid}"><div class="msg">${agencyid}</div></c:if></div>
			<div><c:if test="${not empty agencyemail}"><div class="msg">${agencyemail}</div></c:if></div>
			<div><c:if test="${not empty required}"><div class="msg">${required}</div></c:if></div>
			<div><c:if test="${not empty invalid}"><div class="msg">${invalid}</div></c:if></div>
				<tbody>
					<tr>
						<td>Agency Name: *</td>
						<td><input type="text" name ="agencyname" maxlength="45"></td>
					</tr>
					<tr>
						<td>Agency ID: *</td>
						<td><input type="text" name ="agencyid" maxlength="45"></td>
					</tr>					
					<tr>
						<td>Agency Email Address: *</td>
						<td><input type="text" name ="agencyemail" maxlength="45"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
      					<td></td>
      					<td><button type="submit">Add Government Agency</button></td>
    				</tr>										
				</tbody>
			</table>			
		</form>
	</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/addGovernmentAgency.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>