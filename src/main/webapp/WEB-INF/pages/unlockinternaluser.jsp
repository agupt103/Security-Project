<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
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
	
    <div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
	</div>
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	</div>
	<h2 align="center">
				<u>Unlock User Account</u>
			</h2>
	<c:if test="${not empty unlock_msg}">
				<div class="msg" align="center">${unlock_msg}</div>
			</c:if><br/>
	
	<c:if test="${request_results != null && request_results.size() != 0}">
	<table border="1" align="center">
		<th>UserName</th>
        <th>RequestID</th>
		<c:forEach items="${request_results}" var="req">
			<tr>
				<td><c:out value="${req.username}" /></td>
				<td><c:out value="${req.requestid}"></c:out>
				<td><form method="POST">
				<input type="hidden" name="username" value="${req.username }"/>
				<input type="submit" value="Unlock" name="submit"/>
				<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
				</form></td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
		<form method="POST">
		<div style="text-align: center">
			<br/><br/><input type="submit" value="Refresh" name="refresh"/>
		</div>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		</form>
	</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/unlockinternaluser.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>