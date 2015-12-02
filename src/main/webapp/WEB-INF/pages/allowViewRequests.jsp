<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="<c:url value="/js/keyboard.js " />"></script>
<link href="<c:url value="/css/keyboard.css" />" rel="stylesheet"
	type="text/css">
<sec:csrfMetaTags />
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Request Page</title>
<style>
.buttonHolder{ text-align: center; }
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
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
function check_User(val){
 var element=document.getElementById('adminID');
 if(val=='ADMIN')
	 {
   element.style.display='block';
   document.getElementById('adID').innerHTML = "Admin ID: ";
	 }
 else  
	 {
   element.style.display='none';
   document.getElementById('adID').innerHTML = "";
	 }
}

</script>

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
	<h1 align="center">Sun Devils Bank</h1>
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	</div>
	<br></br>
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
	</div>
	<div id="login-box" align="center">

		<h3>Access Request</h3>
		<c:if test="${not empty Validity}">
			<div class="msg">${Validity}</div>
		</c:if>
		<c:if test="${not empty Status}">
			<div class="msg">${Status}</div>
		</c:if>
		<c:if test="${not empty AdminStatus}">
			<div class="msg">${AdminStatus}</div>
		</c:if>
		<form:form name='loginForm'
			action="${pageContext.servletContext.contextPath}/updateAllow/?${_csrf.parameterName}=${_csrf.token}"
			method='POST'>

			<table>
				<tr>
					<td>Customer ID:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
				<td id="adID"></td>
				<td><input type="text" name="adminID" id="adminID" style='display:none;'/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>User Type:</td>
					<td><select name="userType" onchange='check_User(this.value);'>
							<option value="USER">USER</option>
							<option value="ADMIN">ADMIN</option>
					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
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
	<form:form name='loginForm'
			action="${pageContext.servletContext.contextPath}/updateAllow/?${_csrf.parameterName}=${_csrf.token}"
			method='POST'>
	<table border="3" align="center">
		<h2 align="center">
		<c:if test="${not empty Select}">
			<div class="msg">${Select}</div>
		</c:if>
		<c:if test="${not empty SelectView}">
			<div class="msg">${SelectView}</div>
		</c:if>
			<u>Requests</u>
		</h2>
		<tr>
		    <th>Select to delete</th>
		    <th>Select to View</th>
			<th>Request ID</th>
			<th>Request To</th>
			<th>Request From</th>
			<th>Request For</th>
			<th>Request Type</th>
			<th>Request Time</th>
			<th>Request Status</th>
		</tr>
		<c:forEach var="view" items="${requestDetails}">
			<tr>
			
			<c:if test="${view.rqstStatus == 'Approve'}">
			<td><input type="checkbox" value="${view.requstID}" name="check"></td>
			<td><INPUT TYPE="radio" name="radio" value="${view.rqstFor}"/></td>
			<td><c:out value="${view.requstID}" /></td>
			<c:set var="User" value="${view.rqstFor}"/>
			<%session.setAttribute("User",(String)pageContext.getAttribute("User"));
			%>
			    <c:set var="User" value="${view.requstID}"/>
				<td><c:out value="${view.rqstTo}" /></td>
				<td><c:out value="${view.rqstFrom}" /></td>
				<td><c:out value="${view.rqstFor}" /></td>
				<td><c:out value="${view.rqstType}" /></td>
				<td><c:out value="${view.rqstTime}" /></td>
				<td><c:out value="${view.rqstStatus}" /></td>
		</c:if>
		<c:if test="${view.rqstStatus == 'Reject'}">
			<td><input type="checkbox" value="${view.requstID}" name="check"></td>
			<td></td>
			<td><c:out value="${view.requstID}" /></td>
			<c:set var="User" value="${view.rqstFor}"/>
			    <c:set var="User" value="${view.requstID}"/>
				<td><c:out value="${view.rqstTo}" /></td>
				<td><c:out value="${view.rqstFrom}" /></td>
				<td><c:out value="${view.rqstFor}" /></td>
				<td><c:out value="${view.rqstType}" /></td>
				<td><c:out value="${view.rqstTime}" /></td>
				<td><c:out value="${view.rqstStatus}" /></td>
		</c:if>
		<c:if test="${view.rqstStatus == 'Pending'}">
		    <td></td>
		    <td></td>
			<td><c:out value="${view.requstID}" /></td>
				<td><c:out value="${view.rqstTo}" /></td>
				<td><c:out value="${view.rqstFrom}" /></td>
				<td><c:out value="${view.rqstFor}" /></td>
				<td><c:out value="${view.rqstType}" /></td>
				<td><c:out value="${view.rqstTime}" /></td>
				<td><c:out value="${view.rqstStatus}" /></td>
		</c:if>
				
			
		</c:forEach>
	</table>
	</br>
	</br>
	<div class="buttonHolder">
	<input name="submitDelete" type="submit" value="Delete"/>
	</div>
	</br>
	
	<div class="buttonHolder">
	<input name="submitView" type="submit" value="View"/>
	</div>
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
	</form:form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/allowViewRequests.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
	
%>
