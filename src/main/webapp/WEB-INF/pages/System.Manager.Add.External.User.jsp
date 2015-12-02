<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	<form method='POST'> 
	<h2 align="justify">Approve External User</h2>
    <table width="700" border="0">
  <tbody>
  	<tr>
      <td>Enter SSN: </td>
      <td><input id ="ssn" name="ssn" type="text" class="form-control"></td>
    </tr>
    
    <tr>
      <td>Type of Account: </td>
      <td>
      	<select id="accounttype" name="accounttype">	
      		<option value="Saving Account">Saving Account</option>  	
      		<option value="Checking Account">Checking Account</option>  	
      		<option value="Loan Account">Loan Account</option>  	
      		<option value="Credit Card">Credit Card</option>
      	</select>
      </td>
    </tr>
    <tr>
      <td><font size='4' color="white"></font><input type="submit" name="Approve" value="Approve" class="submit_btn"></td>
      <td></td>
      <td><font size='4' color="white"></font><input type="submit" name="Reject" value="Reject" class="submit_btn"></td>
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
  url = url.replace("/WEB-INF/pages/System.Manager.Add.External.User.jsp",
      "/logoutusers");
  response.setHeader("Refresh", "300; URL =" + url);
  response.setHeader("Cache-Control","no-cache"); 
  response.setHeader("Pragma","no-cache"); 
  response.setDateHeader ("Expires", -1);
%>