<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,authentication.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Manage Users</title>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"  
type="text/javascript" charset="utf-8"></script>  
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
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
<a
href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</div>
<form:form name='SearchForm'
			action="${pageContext.servletContext.contextPath}/modifyUs/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
	<h1>
<c:if test="${not empty status}">
			<div class="msg"><c:out value="${status}" /></div>
</c:if>

</h1>
<h2 align= "center">Search User</h2>
	<table align="center">
	<tr><td><select name=searchcat>
	<option value=Name> Name </option>
	<option value=UserName> User Name </option>
	<option value=AccountNumber> Account Number </option>
	
	
	</select>
	</td>
	<td>
	<input type= text name = username maxlength=45></td></tr>
    <tr><td></td><td><input type= submit name=submit value=search></td></tr>
    <br>
    <br> 
	<c:if test="${users != null && users.size() != 0}">:
	<table>
		<th>User Name</th>
		<th>Email</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Address</th>
		<th> Phone Number</th>
		<th>Passport Number</th>
		<th>State </th>
		<th>Zip</th>
		<th>Business License</th>

		<c:forEach items="${users}" var="user">
		<form:form name='SearchForm'
			action="${pageContext.servletContext.contextPath}/modifyUs/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
			<tr id="${user.username}">
				<td><input type=text value="${user.username}" disabled/>
				</td>
				<td><input type=text value="${user.email}" disabled />
				</td>
				<td><input type=text value="${user.firstName}" disabled />
				</td>
				<td><input type=text  value="${user.lastName}" disabled /></td>
				<td><input type=text  value="${user.address}" disabled /></td>
				<td><input type=text  value="${user.phonenumber}" disabled /></td>
				<td><input type=text  value="${user.passport}" disabled /></td>
				<td><input type=text  value="${user.state}" disabled /></td>
				<td><input type=text  value="${user.zip}" disabled /></td>
				<td><input type=text  value="${user.businessLicense}" disabled /></td>
				
			</tr>
			<tr>
			
				<td><input type= hidden name= hiddenUser id='hiddenUser' value="${user.username}" /></td>
				<td><input type= hidden name= hiddenUserNumber id='hiddenUserNumber' value="${user.username}" /></td>
			</tr>
			</form:form>
		</c:forEach>
	</table>
	</c:if>
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> 
</form:form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/modifyUsersemployee.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>