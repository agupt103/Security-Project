<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<form method="POST">
		<h2>Payment Request</h2>
		<br> <br> <label>Requested by:</label> <label>Load
			name from db </label> <br> <br> <label>Amount:</label> <label>Load
			amount db</label> <br> <br>
		<div class="buttons">
			<button name="accept" value="Accept">Accept</button>
			<button name="reject" value="reject">Reject</button>
		</div>
	</form>


	<br />
	<br />
	<br />
	<a href="${pageContext.servletContext.contextPath}/customerhome">Home</a>&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/Individual.Customers/User.Authorize.Transactions.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>