<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Sun Devils Bank Home Page</title>

<script type="text/javascript" src="<c:url value="/js/bootstrap.min.js " />"></script>
<script type="text/javascript" src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" />"></script>

<link href="<c:url value="/css/globalalter.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/bootstrap-responsive.min.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" type="text/css">

<link href="<c:url value="http://teamtreehouse.com/assets/favicon.ico" />" rel="shortcut icon">
<link href="<c:url value="http://teamtreehouse.com/assets/favicon.ico" />" rel="icon">
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
	<nav id="navigation">
		<div class="container">
			<ul class="navlinks">
				<li><a href="${pageContext.servletContext.contextPath}/">Home</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/aboutus">About Us</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/projects">Projects</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/team">The Team</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/contact">Contact Us</a></li>
			</ul>
		</div>
	</nav>

	<header id="heading">
		<div class="container text-center">
			<h1>Sun Devils Bank</h1>
			<h4>Secure Banking Website by Group#1</h4>


		</div>
	</header>

	<div id="login-box" align="center">
		<h2 align="center">LOGIN FORM</h2>
		<strong><c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
		</c:if>
		<c:if test="${not empty wrongCaptcha}">
			<div class="msg">${wrongCaptcha}</div>
		</c:if>
		<c:if test="${not empty wrongCredentials}">
			<div class="msg">${wrongCredentials}</div>
		</c:if>
		<c:if test="${not empty loggedIn}">
			<div class="msg">${loggedIn}</div>
		</c:if></strong>
		<form:form name='loginForm'
			action="${pageContext.servletContext.contextPath}/login/?${_csrf.parameterName}=${_csrf.token}"
			method='POST' autocomplete='none'>

			<table>
				<tr>
					<td>Username:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td>Image :</td>
					<td>
						<div>
							<img id="captcha_id" name="imgCaptcha123" src="captcha.jpg">
						</div>
					</td>


					<td align="left"><a href="javascript:;"
						title="change captcha text" name="imgCaptcha"
						onclick="document.getElementById('captcha_id').src = '<%=request.getContextPath()%>/login?' + 'imgCaptcha';  return false">
					</a></td>
				</tr>
				<tr>
					<td>Enter Captcha:</td>
					<td><input type="text" name='captcha' /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				
				<tr>
					<td></td>
					<td><input class="btn btn-primary" align = "center" name="submit" type="submit" value="submit" /></td>
				</tr>
				<tr>
					<td></td>
					<td><a href="${pageContext.servletContext.contextPath}/forgotusername">Forgot username</a></td>
				</tr>
				<tr>
					<td></td>
					<td><a href="${pageContext.servletContext.contextPath}/forgotpassword">Forgot password</a></td>
				</tr>
			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form>
	</div>
</body>
</html>
</html>
<%
	request.getSession().setAttribute("isUserLoggedIn", "Not Set");
	request.getSession().setAttribute("Username", "username");
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>