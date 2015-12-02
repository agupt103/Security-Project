<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Insert title here</title>
<script type="text/javascript">
	history.pushState(null, null, window.location.href);
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, window.location.href);
	});
	document.addEventListener("contextmenu", function(e) {
		e.preventDefault();
	}, false);
</script>

</head>
<body oncopy="return false" oncut="return false" onpaste="return false">
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a> <a
			href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>

	</div>
	<form:form name='loginForm'
		action="${pageContext.servletContext.contextPath}/authorizationRequest/?${_csrf.parameterName}=${_csrf.token}"
		method='POST'>
		<table border="3" align="center">

			<h2 align="center">
				<c:if test="${not empty Select}">
					<div class="msg">${Select}</div>
				</c:if>
				<u>Requests</u>
			</h2>
			<tr>
				<th>Select</th>
				<th>Request ID</th>
				<th>Request From</th>
				<th>Request For</th>
				<th>Request Time</th>
				<th>Request Status</th>
			</tr>
			<c:forEach var="view" items="${requestApprove}">
				<tr>
					<td><input type="checkbox" value="${view.requstID}"
						name="check"></td>
					<td><c:out value="${view.requstID}" /></td>
					<td><c:out value="${view.rqstFrom}" /></td>
					<td><c:out value="${view.rqstFor}" /></td>
					<td><c:out value="${view.rqstTime}" /></td>
					<td><c:out value="${view.rqstStatus}" /></td>
				</tr>
			</c:forEach>

		</table>
		</br>
		</br>
		<div style="text-align: center">
			<select name="Type">
				<option value="Approve">Approve</option>
				<option value="Reject">Reject</option>
			</select> <br></br> <br></br> <input name="submit" type="submit"
				value="submit" />
		</div>
	</form:form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/accessRequests.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>