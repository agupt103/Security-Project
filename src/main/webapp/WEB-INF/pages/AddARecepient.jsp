<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Insert title here</title>
<script type="text/javascript">
	function getData(e) {
		e.preventDefault();
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
	<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	</div>
	
	<form name="form"
		action="${pageContext.servletContext.contextPath}/addRecepient"
		method='POST'>
		<h2 align="center">Add a Recipient</h2>
		<div style="text-align: center">
		<c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
		</c:if>
		<c:if test="${not empty accountNumDismatch}">
			<div class="msg">${accountNumDismatch}</div>
		</c:if>
		<c:if test="${not empty wrongOtp}">
			<div class="msg">${wrongOtp}</div>
		</c:if>
		<c:if test="${not empty ExsistingUser}">
			<div class="msg">${ExsistingUser}</div>
		</c:if>
		<c:if test="${not empty dulicateuser}">
			<div class="msg">${dulicateuser}</div>
		</c:if>
		<c:if test="${not empty invaliduser}">
			<div class="msg">${invaliduser}</div>
		</c:if>

		<c:if test="${not empty Successfull}">
			<div class="msg">${Successfull}</div>
		</c:if>
		<c:if test="${not empty wrongDebit}">
			<div class="msg">${wrongDebit}</div>
		</c:if>
		<c:if test="${not empty sameuser}">
			<div class="msg">${sameuser}</div>
		</c:if>
		
	</div>
		<table width="500" border="0" align = "center">
			<tbody>
				<tr>
					<th scope="col">&nbsp;</th>
					<th scope="col">&nbsp;</th>
				</tr>
				<tr>
					<td>First Name of the Recipient:</td>
					<td><input id="firstname" name="firstname" type="text"
						class="form-control"></td>
				</tr>
				<tr>
					<td>Last Name of the Recipient:</td>
					<td><input id="lastname" name="lastname" type="text"
						class="form-control"></td>
				</tr>
				<tr>
					<td>Enter Account Number:</td>
					<td><input id="accountnumber" name="accountnumber" type="text"></td>
				</tr>
				<tr>
					<td>Confirm Account Number:</td>
					<td><input id="confirmaccountnumber"
						name="confirmaccountnumber" type="text"></td>
				</tr>
				<tr>
					<td>Enter Recepient's Registered Email:</td>
					<td><input id="email" name="email" type="email"></td>
				</tr>
				<tr>
					<td>Enter your debit card number for authentication</td>
					<td><input id="debitcard" name="debitcard" type="text"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><img id="otp_id" name="otpCaptcha123" src="captcha.jpg"
						hidden="true"> <a href="javascript:;"
						title="Send OTP in email" name="otpButton"
						onclick="document.getElementById('otp_id').src = '${pageContext.servletContext.contextPath}/addRecepient?' + 'otpButton';  return false">
							Send Otp in Email </a></td>
					<td></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>OTP:</td>
					<td><input id="otpCode" name="otpCode" type="text" class='keyboardInput'></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<!-- <td><button  type="submit" >Add Recipient</button></td>-->
					<td><font size='4' color="white"><input type="submit"
							value="Add A Recepient" id="add" name="add"></td>
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
	url = url.replace("/WEB-INF/pages/AddARecepient.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>