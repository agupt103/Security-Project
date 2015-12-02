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

<script type="text/javascript"
	src="<c:url value="/js/bootstrap.min.js " />"></script>
<script type="text/javascript"
	src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" />"></script>

<link href="<c:url value="/css/globalalter.css" />" rel="stylesheet"
	type="text/css">
<link href="<c:url value="/css/bootstrap-responsive.min.css" />"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet"
	type="text/css">

<link
	href="<c:url value="http://teamtreehouse.com/assets/favicon.ico" />"
	rel="shortcut icon">
<link
	href="<c:url value="http://teamtreehouse.com/assets/favicon.ico" />"
	rel="icon">
<script type="text/javascript">
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
		<h2 align="center">UNLOCK ACCOUNT</h2>
		<strong> <c:if test="${not empty lock}">
				<div class="msg">${lock}</div>
			</c:if> <c:if test="${not empty emptyFields}">
				<div class="msg">${emptyFields}</div>
			</c:if> <c:if test="${not empty wrongOtp}">
				<div class="msg">${wrongOtp}</div>
			</c:if> <c:if test="${not empty incorrectFields}">
				<div class="msg">${incorrectFields}</div>
			</c:if> <c:if test="${not empty alreadypresent}">
				<div class="msg">${alreadypresent}</div>
			</c:if> <c:if test="${not empty success}">
				<div class="msg">${success}</div>
			</c:if></strong>
		<form name="form"
			action="${pageContext.servletContext.contextPath}/unlockaccount?${_csrf.parameterName}=${_csrf.token}"
			method='POST'>
			<table border="0">
				<tbody>
					<tr>
						<td>User name:</td>
						<td><c:out value="${user}" /></td>
					</tr>
					<tr>
						<td>Enter SSN</td>
						<td><input type="text" name="ssn"></td>
					</tr>
					<tr>
						<td>Enter email address</td>
						<td><input type="text" name="email"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td><input class="btn btn-primary" name="submit" type="submit" value="submit" /></td>
					</tr>


				</tbody>
			</table>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>
	</div>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/unlockaccount.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>