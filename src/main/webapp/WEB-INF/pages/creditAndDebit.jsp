<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page isELIgnored='true'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Insert title here</title>

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
	<div id=creditAndDebit align="center">
		<table>
			<tr>
				<td><a
					href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></td>
			</tr>
		</table>
		<h2>Credit And Debit Transactions</h2>
		<c:if test="${not empty emptyFields}">
			<div class="msg">
				<c:out value="${emptyFields}" />
			</div>
		</c:if>
		<c:if test="${not empty insuffFunds}">
			<div class="msg">
				<c:out value="${insuffFunds}" />
			</div>
		</c:if>
		<form:form name='creditAndDebitForm'
			action="${pageContext.servletContext.contextPath}/creditAndDebitFull/?${_csrf.parameterName}=${_csrf.token}"
			method='POST'>
			<br />

			<table>
				<tr>
					<td><input type="radio" name="transaction" value="credit"
						checked>Credit</td>
					<td><input type="radio" name="transaction" value="debit">Debit</td>
				</tr>
				<tr>
					<td>Select The account:</td>
					<td><select name="transactions">
							<c:forEach var="account" items="${accountNumbers}">
								<option><c:out value="${account}" /></option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>Enter the Amount:</td>
					<td><input type='text' name='amount'></td>
				</tr>
				<tr>
					<td></td>
					<td><input name="submit" type="submit" value="submit" /></td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form>
	</div>

</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/creditAndDebit.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>