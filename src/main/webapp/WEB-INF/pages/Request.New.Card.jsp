<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>

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
	<div>
		<c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
		</c:if>
		<c:if test="${not empty ExsistingUser}">
			<div class="msg">${ExsistingUser}</div>
		</c:if>
		<c:if test="${not empty Successful}">
			<div class="msg">${Successful}</div>
		</c:if>
	</div>
	<form method='POST' action="${pageContext.servletContext.contextPath}/replaceCard/?${_csrf.parameterName}=${_csrf.token}" method="post">
		<table width="400" border="0" align = "center">
			<tbody>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/Home">Home</a>
					<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></td>
				</tr>
				<tr><td><h2 align="justify">Request for New Card</h2></td></tr>
				<tr>
					<td>Select the bank account:</td>
					<td><select name="accountNumber">
							<c:forEach items="${bankaccounts}" var="bankaccount">
								<option value="${bankaccount.getAccountNumber()}">
									${bankaccount.getAccountNumber()}
									(${bankaccount.getAccountType()}) </option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><font size='4' color="white"></font><input type="submit"
						name="submit" value="Send Request" class="submit_btn"></td>
				</tr>
			</tbody>
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/Request.New.Card.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>