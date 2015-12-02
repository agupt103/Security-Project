<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" charset="UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Add Internal User</title>
<script type = "text/javascript" >
   	history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) 
    {history.pushState(null, null, window.location.href);});
    document.addEventListener("contextmenu", function(e)
    {e.preventDefault();}, false);
</script>
</head>	
<body oncopy="return false" oncut="return false" onpaste="return false">	
<noscript>
	<h2>JavaScript is disabled! Why you want to do so? Please enable JavaScript in your web browser and Refresh!</h2>
			<style type="text/css">	#main-content { display:none; } </style>
</noscript>

	<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/Home">Home</a></div>
	<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></div>
	
<form action="addinternaluser" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			<table border="3" align="center">
				<h2 align="center">	<u>Add Internal User</u>	</h2>
				<div><c:if test="${not empty firstname}"><div class="msg">${firstname}</div></c:if></div>
				<div><c:if test="${not empty lastname}"><div class="msg">${lastname}</div></c:if></div>
				<div><c:if test="${not empty ssn}"><div class="msg">${ssn}</div></c:if></div>
				<div><c:if test="${not empty email}"><div class="msg">${email}</div></c:if></div>
				<div><c:if test="${not empty phonenumber}"><div class="msg">${phonenumber}</div></c:if></div>
				<div><c:if test="${not empty username}"><div class="msg">${username}</div></c:if></div>
				<div><c:if test="${not empty zip}"><div class="msg">${zip}</div></c:if></div>
				<div><c:if test="${not empty required}"><div class="msg">${required}</div></c:if></div>
				<div><c:if test="${not empty present}"><div class="msg">${present}</div></c:if></div>
				<div><c:if test="${not empty successful}"><div class="msg">${successful}</div></c:if></div>
				<tbody>
					<tr>
						<td>First Name: *</td>
						<td><input type="text" name ="firstname" maxlength="45"></td>
					</tr>
					<tr>
						<td>Middle Name: </td>
						<td><input type="text" name ="middlename" maxlength="45"></td>
					</tr>
					<tr>
						<td>Last Name: </td>
						<td><input type="text" name ="lastname" maxlength="45"></td>
					</tr>
					<tr>
						<td>Username: *</td>
						<td><input type="text" name ="username" maxlength="45"> </td>
					</tr> 
					<tr>
						<td>Designation: </td>
						<td>
							<select name="designation">
								<option value="EMPLOYEE">Employee</option>
								<option value="MANAGER">Manager</option>
							</select>
						</td>
					<tr>
					<tr>
						<td>SSN: *</td>
						<td><input type="text" name ="ssn" maxlength="45"></td>
						<td>(xxx-xx-xxxx)</td>
					</tr>
					<tr>
						<td>Email Address: *</td>
						<td><input type="text" name ="email" maxlength="45"></td>
					</tr>
					<tr>
						<td>Phone Number: *</td>
						<td><input type="text" name ="phonenumber" maxlength="45"></td>
						<td>(1xxxxxxxxxx) </td>
					</tr>
					<tr>
						<td>Date of Birth: </td>
						<td><input type="date" name ="dateofbirth" maxlength="45"></td>
						<td>(mm/dd/yyyy)</td>
					</tr>
					<tr>
      					<td>Address: </td>
      					<td><textarea name ="address" maxlength="45"></textarea></td>
    				</tr>
   					<tr>
      					<td>State: </td>
      					<td><select name ="state">
							<option value="AL">Alabama</option>
							<option value="AK">Alaska</option>
							<option value="AZ">Arizona</option>
							<option value="AR">Arkansas</option>
							<option value="CA">California</option>
							<option value="CO">Colorado</option>
							<option value="CT">Connecticut</option>
							<option value="DE">Delaware</option>
							<option value="DC">District Of Columbia</option>
							<option value="FL">Florida</option>
							<option value="GA">Georgia</option>
							<option value="HI">Hawaii</option>
							<option value="ID">Idaho</option>
							<option value="IL">Illinois</option>
							<option value="IN">Indiana</option>
							<option value="IA">Iowa</option>
							<option value="KS">Kansas</option>
							<option value="KY">Kentucky</option>
							<option value="LA">Louisiana</option>
							<option value="ME">Maine</option>
							<option value="MD">Maryland</option>
							<option value="MA">Massachusetts</option>
							<option value="MI">Michigan</option>
							<option value="MN">Minnesota</option>
							<option value="MS">Mississippi</option>
							<option value="MO">Missouri</option>
							<option value="MT">Montana</option>
							<option value="NE">Nebraska</option>
							<option value="NV">Nevada</option>
							<option value="NH">New Hampshire</option>
							<option value="NJ">New Jersey</option>
							<option value="NM">New Mexico</option>
							<option value="NY">New York</option>
							<option value="NC">North Carolina</option>
							<option value="ND">North Dakota</option>
							<option value="OH">Ohio</option>
							<option value="OK">Oklahoma</option>
							<option value="OR">Oregon</option>
							<option value="PA">Pennsylvania</option>
							<option value="RI">Rhode Island</option>
							<option value="SC">South Carolina</option>
							<option value="SD">South Dakota</option>
							<option value="TN">Tennessee</option>
							<option value="TX">Texas</option>
							<option value="UT">Utah</option>
							<option value="VT">Vermont</option>
							<option value="VA">Virginia</option>
							<option value="WA">Washington</option>
							<option value="WV">West Virginia</option>
							<option value="WI">Wisconsin</option>
							<option value="WY">Wyoming</option>
							</select>				
						</td>
				    </tr>
    				<tr>
      					<td>Zip: *</td>
     					 <td><input type="text" name ="zip" maxlength="45"></td>
    				</tr>					
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
      					<td></td>
      					<td><button type="submit">Add Internal User</button></td>
    				</tr>
										
				</tbody>
			</table>				
		</form>
	</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/addInternalUser.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>