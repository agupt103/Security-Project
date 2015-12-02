<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<title>Apply Online for Bank Accounts</title>
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
		<a href="${pageContext.servletContext.contextPath}/login">Back</a></div>
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
	<form name='openform'
		action="${pageContext.servletContext.contextPath}/openAccountForm/?${_csrf.parameterName}=${_csrf.token}"
		method='POST'>
		<h2 align="justify">Bank Account Opening Form</h2>
		<table width="700" border="0">
			<tbody>
				<tr>
					<td>Type of External Users:*</td>
					<td><label> <input type="radio" name="usertype"
							value="USER" id="usertype_0" checked>Individual Customer
					</label></td>
					<td><label> <input type="radio" name="usertype"
							value="MERCHANT" id="usertype_1">Merchant/Organization
					</label></td>
				</tr>
				<tr>
					<td>Prefix:</td>
					<td><select id="prefix" name="prefix">
							<option value="NA">N/A</option>
							<option value="Mr.">Mr.</option>
							<option value="Mrs.">Mrs.</option>
							<option value="Miss">Miss</option>
							<option value="Dr.">Dr.</option>
					</select></td>
				</tr>
				<tr>
					<td>First Name:*</td>
					<td><input id="firstname" name="firstname" type="text" maxlength="20"
						class="form-control"></td>
				</tr>
				<tr>
					<td>Middle Name:</td>
					<td><input id="middlename" name="middlename" type="text" maxlength="20"
						class="form-control"></td>
				</tr>
				<tr>
					<td>Last Name:*</td>
					<td><input id="lastname" name="lastname" type="text" maxlength="20"></td>
				</tr>
				<tr>
					<td>Gender:</td>
					<td><select id="gender" name="gender">
							<option value="None"></option>
							<option value="Male">Male</option>
							<option value="Female">Female</option>
					</select></td>
				</tr>
				<tr>
					<td>Type of Account:*</td>
					<td><select id="accounttype" name="accounttype">
							<option value="Saving Account">Saving Account</option>
							<option value="Checking Account">Checking Account</option>

					</select></td>
				</tr>
				<tr>
					<td>Address:</td>
					<td><textarea id="address" name="address" maxlength="40"></textarea></td>
				</tr>
				<tr>
					<td>State:</td>
					<td><select id="state" name="state">
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
					</select></td>
				</tr>
				<tr>
					<td>Zip:*</td>
					<td><input id="zip" name="zip" type="text" maxlength="20"></td>
				</tr>
				<tr>
					<td>SSN:*</td>
					<td><input id="ssn" name="ssn" type="text" maxlength="20"></td>
				</tr>
				<tr>
					<td>Passport:</td>
					<td><input id="passportnumber" name="passportnumber"
						type="text" maxlength="20"></td>
				</tr>
				<tr>
					<td>Email:*</td>
					<td><input id="email" name="email" type="email" maxlength="40"></td>
				</tr>
				<tr>
					<td>Mobile No:*</td>
					<td><input id="phonenumber" name="phonenumber" type="tel" maxlength="20"></td>
				</tr>
				<tr>
					<td>Date of Birth:</td>
					<td><input id="dateofbirth" name="dateofbirth" type="date" maxlength="20"></td>
				</tr>

				<tr>
					<td>Business License:</td>
					<td><input id="businesslicence" name="businesslicence" maxlength="20"
						type="text"></td>
				</tr>
				<tr>
					<td></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td></td>
					<td><input id="checkagreement" name="checkagreement"
						type="checkbox"> * I understand the agreement with Sun Devils Bank</td>
				</tr>
				<tr>
					<td></td>
					<td><font size='4' color="white"></font><input type="submit"
						name="submit" value="Continue" class="submit_btn"></td>
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
  url = url.replace("/WEB-INF/pages/Account.Opening.Form.jsp",
      "/logoutusers");
  response.setHeader("Refresh", "300; URL =" + url);
  response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>