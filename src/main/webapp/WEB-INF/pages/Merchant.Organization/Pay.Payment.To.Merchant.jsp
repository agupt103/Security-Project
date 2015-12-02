<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<%@ page import="java.util.*,soroosh.AccountInfo"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	<noscript>
  <meta http-equiv="refresh" content="0; url=${pageContext.servletContext.contextPath}/logoutusers" />
  Javascript Disabled
</noscript>

	<form:form
		action="${pageContext.servletContext.contextPath}/merchantpayersubmitacceptpayment/?${_csrf.parameterName}=${_csrf.token}"
		method="post">

		<table>
			<tr>
				<td>Payment request from:</td>
				<td>${requester_merchant}</td>
			</tr>
			<tr>
				<td>Amount:</td>
				<td>${request_payment_amount}</td>
			</tr>
			<tr></tr>
			<tr>
				<td>Select the bank account:</td>
				<td><select name="accountNumber">
						<c:forEach items="${bankaccounts}" var="bankaccount">
							<option value="${bankaccount.getAccountNumber()}">
								${bankaccount.getAccountNumber()}
								(${bankaccount.getAccountType()}) - B:
								${bankaccount.getBalance()}</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<input type="submit" name="makepayment" value="Make Payment" />
	</form:form>


	<br />
	<br />
	<br />
	<a href="${pageContext.servletContext.contextPath}/merchanthome">Home</a>&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/Merchant.Organization/Pay.Payment.To.Merchant.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>