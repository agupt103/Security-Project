<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <meta http-equiv="Pragma" content="no-cache">
	 <meta http-equiv="Cache-Control" content="no-cache">
	 <meta http-equiv="Expires" content="-1">
</head>


<body oncopy="return false" oncut="return false" onpaste="return false">
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	</div>
	
		<form method="POST">
		<h2 align="center">
				<u>Transactions</u>
			</h2>
			<c:if test="${not empty success_msg}">
				<div class="msg" align="center">${success_msg}</div>
			</c:if><br/>
		<table align="center">
			<tr><td><label>UserName</label></td>
			<td><input type="text" name="username" maxlength="45"></td>
			</tr>
    		<tr><td><label>Transaction Amount</label></td>
    		<td><input type="text" name="transamount" maxlength="10"/>
			</td></tr>		
    		<tr><td><label>Source Account</label></td>
    		<td><input type="text" name="sourceacc" maxlength="20"/>
			</td></tr>	
    		<tr><td><label>Destination Account</label></td>
    		<td><input type="text" name="destacc" maxlength="20"/>
			</td></tr>
			<tr><td></td><td><input type="submit" value="Create" name="submit"/>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /></td></tr>
		</table>
				
		</form>
	</body>
	
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/create_transactions.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>