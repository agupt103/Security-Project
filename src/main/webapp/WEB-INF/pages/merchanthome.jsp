<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Insert title here</title>
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
	
	<form>
	<h2 align="justify">Hello <c:out value="${sessionScope.Merchant}"/></h2>
		<table width="500" border="0">
			<tbody>
				<tr>
					<th scope="col">&nbsp;</th>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/viewBal">View Balance</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/debitAndCredit">Debit and Credit Funds</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/changeaccount">Change Account Type</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/addRecepient">Add A Recipient</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/internalTransfer">Internal Funds Transfer</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/externalTransfer">External Funds Transfer</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/merchantstartpayment">Make a Payment Request</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/merchantshowpayments">View Payment Lists</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/reqModify">Permission to Modify Personal Information</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/authorizationRequest">Allow View Access</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/downloadStatement" target="_blank">View Statement</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></td>
				</tr>
			</tbody>
		</table>

	</form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/merchanthome.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>