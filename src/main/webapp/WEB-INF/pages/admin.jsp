<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
	<form>
	<h2 align="justify">Hello <c:out value="${sessionScope.Admin}"/></h2>
		<table>

			<tbody>
				<tr>
					<td><a
						href="${pageContext.request.contextPath}/authorizationRequest">View
							Requests</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/viewlogs">View
							Logs</a></td>
				</tr>

				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/addi">Add
							Internal User</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/addg">Add
							Government Agency</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/showInternal">Delete
							Accounts</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/modifyinternal">Modify
							Internal User</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/showInternal">Delete
							Internal User</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/piirequest">PII
							Access</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/unlockinternal">Unlock
							Internal User</a></td>
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
	url = url.replace("/WEB-INF/pages/admin.jsp", "/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>