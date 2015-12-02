
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Request for PII</title>
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
<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></div>

<form action="${pageContext.servletContext.contextPath}/requestPII/?${_csrf.parameterName}=${_csrf.token}" method="POST">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<table border="3" align="center">
		<h2 align="center"><u>Government Homepage</u></h2>
		
	<%-- <div>
		<c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
		</c:if>
		<c:if test="${not empty ExsistingUser}">
			<div class="msg">${ExsistingUser}</div>
		</c:if>
		<c:if test="${not empty Successful}">
			<div class="msg">${Successful}</div>
		</c:if>
	</div> --%>
		<tbody>
				<tr>
					<th scope="col">&nbsp;</th>
					<th scope="col">&nbsp;</th>
				</tr>
				<tr>
					<td>Please request type:</td>
					<td><select id="requesttype" name="requesttype" >
							<option value="ssn">SSN</option>
							<option value="passportnumber">Passport Number</option>
							<option value="accountnumber">Account Number</option>
					</select></td>
				</tr>
				<tr>
					<td>Enter Details:</td>
					<td><input id="requestdetails" name="requestdetails" type="text" maxlength="45"></td>
				</tr>				
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" name="submit"></td>
				</tr>
			</tbody>
		</table>		
	</form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/RequestPII.jsp","/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>