<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Modify Internal User</title>
<script type = "text/javascript" >
   	history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) 
    {history.pushState(null, null, window.location.href);});
    document.addEventListener("contextmenu", function(e)
    {e.preventDefault();}, false);
</script>
</head>
<body oncopy="return false" oncut="return false" onpaste="return false">
<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/Home">Home</a></div>
<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></div>


<noscript>
	<h2>JavaScript is disabled! Why you want to do so? Please enable JavaScript in your web browser and Refresh!</h2>
	<style type="text/css">#main-content { display:none; }</style>
</noscript>

<form action="modifyinternaluser" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	<table border="3" align="center">
		<h2 align="center">
			<u>Modify Internal User</u>	
		</h2>
		<div><c:if test="${not empty required}"><div class="msg">${required}</div></c:if></div>	
		<div><c:if test="${not empty newinfo}"><div class="msg">${newinfo}</div></c:if></div>	
		<div><c:if test="${not empty incorrect}"><div class="msg">${incorrect}</div></c:if></div>	
		<div><c:if test="${not empty notpresent}"><div class="msg">${notpresent}</div></c:if></div>		
		<tbody>
			<tr>
				<td>Enter Username: *</td>
				<td><input type="text" name ="username" maxlength="45"></td>
			</tr>
			<tr>
				<td>Select the field for update: </td>
				<td>
					<select name="column">
						<option value="address">Address</option>
						<option value="email">Email Id</option>
						<option value="phonenumber">Phone Number</option>
					</select>
				</td>
			</tr>
					
			<tr>
				<td>New Information: *</td>
				<td><input type="text" name = "newinfo" maxlength="45"></td>
				<td>(1XXXXXXXXXX) - If selected field for update is phone number.</td>
			</tr>			
			<tr>
				<td></td>
				<td><button type="submit">Modify Internal Accounts</button></td>
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
	url = url.replace("/WEB-INF/pages/modifyInternalAccount.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>