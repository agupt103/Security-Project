<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<script type="text/javascript" src="<c:url value="/js/keyboard.js " />"></script>
<link href="<c:url value="/css/keyboard.css" />" rel="stylesheet"  type="text/css" >
<sec:csrfMetaTags/>
<title>Edit PII</title>

<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
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
	<div id=creditAndDebit align="center">
		<form:form name='creditAndDebitForm'
			action="${pageContext.servletContext.contextPath}/editPII/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
				
		<h2 align="justify">Edit Personal Information</h2>
		<c:if test="${not empty emptyFields}">
			<div class="msg"><c:out value="${emptyFields}" /></div>
		</c:if>
		<c:if test="${not empty phoneNum}">
			<div class="msg"><c:out value="${phoneNum}" /></div>
		</c:if>
		<c:if test="${not empty wrongOtp}">
			<div class="msg"><c:out value="${wrongOtp}" /></div>
		</c:if>
			<table>
				<tr>
					<td><a href="./Home">Home</a></td>
					<td><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></td>
				</tr>
			</table>
			<br/>
			<br/>
			<br/>
			<table width="500" border="0">
				<tbody>
					<tr>
						<td>Select the field for update: </td>
						<td>
							<select name="PII">
								<option value="address">Address</option>
								<option value="email">Email Id</option>
								<option value="phoneNum">Phone Number</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Current Information: </td>
						<td><input type="text" name="curInfo"></td>
					</tr>
					<tr>
						<td>New Information:</td>
						<td><input type="text" name="newInfo"></td>
					</tr>
					<tr>
						<td>Confirm New Information: </td>
						<td><input type="text" name="cnfrmNewInfo"></td>
					</tr>
				<tr>
				<td></td>
					<td>
					<img id="otp_id" name="otpCaptcha123" src="captcha.jpg" hidden="true">
					<a href="javascript:;"
						title="Send OTP in email" name="otpButton"
						onclick="document.getElementById('otp_id').src = 'editPII?' + 'otpButton';  return false">
							Send Otp in Email
					</a></td>
				</tr>
		
				<tr>
					<td>OTP :</td>
					<td><input type='text' name = 'otpCode' class='keyboardInput'></td>
				</tr>
					<tr><td></td></tr>
					<tr>
					<td></td>
					<td><input name="submit" type="submit"
						value="submit" /></td>
		  
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/editPII.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>