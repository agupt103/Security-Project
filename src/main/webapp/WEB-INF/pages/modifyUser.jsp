<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,authentication.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Manage Users</title>
<style>
table {
	color: #333;
	font-family: Helvetica, Arial, sans-serif;
	width: 640px;
	border-collapse: collapse;
	border-spacing: 0;
}

td, th {
	border: 1px solid transparent; /* No more visible border */
	height: 30px;
	transition: all 0.3s; /* Simple transition for hover effect */
}

th {
	background: #DFDFDF; /* Darken header a bit */
	font-weight: bold;
}

td {
	background: #FAFAFA;
	text-align: center;
}

/* Cells in even rows (2,4,6...) are one color */
tr:nth-child(even) td {
	background: #F1F1F1;
}

/* Cells in odd rows (1,3,5...) are another (excludes header cells)  */
tr:nth-child(odd) td {
	background: #FEFEFE;
}

tr td:hover {
	background: #666;
	color: #FFF;
} /* Hover cell effect! */
</style>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	function a() {
		document.getElementById("hiddenUser").value = $(this).closest('tr')
				.attr('id'); // table row ID 

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
	<c:if test="${singleUser != null && singleUser.size() != 0}">:
	
    <table>



			<c:forEach items="${singleUser}" var="user">
				<form:form name='Search'
					action="${pageContext.servletContext.contextPath}/modifyUs/?${_csrf.parameterName}=${_csrf.token}"
					method='POST'>
					<tr id="${user.username}">
						<td><label> User Name : </label></td>
						<td><input type="text" value="${user.username} " name="Name"
							readonly /></td>
					</tr>
					<tr id="${user.firstName}">

						<td><label> First Name : </label></td>
						<td><input type=text value="${user.firstName}"
							name="firstName" required/></td>
					</tr>
					<tr id="${user.lastName}">
						<td><label> Last Name : </label></td>
						<td><input type=text value="${user.lastName}" name="lastName" required/></td>
					</tr>
					<tr id="${user.email}">
						<td><label> Email : </label></td>
						<td><input type=email value="${user.email}" name="email" required/></td>
					</tr>
					<tr id="${user.phonenumber}">
						<td><label> Phone Number : </label></td>
						<td><input type=number value="${user.phonenumber}"
							name="phonenumber" required/></td>
					</tr>
					<tr id="${user.passport}">
						<td><label> Passport Number : </label></td>
						<td><input type=text value="${user.passport}" name="passport" required/></td>
					</tr>
					<tr id="${user.address}">
						<td><label> Address : </label></td>
						<td><input type=text value="${user.address}" name="address" required /></td>
					</tr>

					<tr id="${user.state}">
						<td><label> State : </label></td>
						<td><input type=text value="${user.state}" name="state" required/></td>
					</tr>
					<tr id="${user.zip}">
						<td><label> Zip : </label></td>
						<td><input type=number value="${user.zip}" name="zip" required/></td>
					</tr>
					<tr>
						<td><label> Business License: </label></td>
						<td><input type=text value="${user.businessLicense}"
							name="businessLicense" required /></td>
					</tr>
					<tr>
						<td><label> Date of Birth: </label></td>
						<td><input type=date value="${user.dateOfBirth}"
							name="dateOfBirth" required/></td>
					</tr>
					<tr>
						<td><input type=submit name="save" value=save></td>
					</tr>


				</form:form>

			</c:forEach>


		</table>
	</c:if>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />

</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/modifyUser.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>