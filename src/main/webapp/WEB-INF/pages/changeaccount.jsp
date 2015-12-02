<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,authentication.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Manage Users</title>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"  
type="text/javascript" charset="utf-8"></script>  
<script type="text/javascript">
   function a() {
	   document.getElementById("hiddenUser").value = $(this).closest('tr').attr('id'); // table row ID 
       
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
<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
<a
href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</div>
<form:form action="${pageContext.servletContext.contextPath}/reqchangeaccount/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
	<h2 align= "center">Change Account Type</h2>
	<label> Your Account Type is :</label>
	<label><c:out value="${account}"/></label>
	<label> Your Account Number is :</label>
	<label><c:out value="${accountnumber}"/></label>
	<input type=hidden value="${accountnumber}" name="accountnumber">
	<c:if test="${account != null && account=='Checking Account'}">:
	<input type= submit name =accountchange value='Change to Saving Account'>
	</c:if>
	<c:if test="${account != null && account=='Saving Account'}">:
	<input type= submit name = accountchange value='Change to Checking Account'>
	</c:if>
	<label> Select Manager </label>
	
    <input type= submit name=submit value="Request Permission"> 
    <br>
 
	</form:form>
	
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> 

</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/changeaccount.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>