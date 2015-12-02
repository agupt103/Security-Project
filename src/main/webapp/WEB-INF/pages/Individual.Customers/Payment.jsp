<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<link href="<c:url value="/css/keyboard.css" />" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="<c:url value="/js/keyboard.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/sjcl.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/aes.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/codecBase32.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/codecBytes.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/codecHex.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/sha512.js"/>"></script>

<script type="text/javascript">
	history.pushState(null, null, window.location.href);
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, window.location.href);
	});
	document.addEventListener("contextmenu", function(e) {
		e.preventDefault();
	}, false);
</script>

<title>Payment</title>

<script type="text/javascript">
	window.onload = function() {
		document.getElementById('theForm').onsubmit = function() {
			var txt1 = document.getElementById('merchantName');
			txt1.value = "";
			var txt2 = document.getElementById('accountNumber');
			txt2.value = "";
			var txt3 = document.getElementById('amount');
			txt3.value = "";
			var txt4 = document.getElementById('otpText');
			txt4.value = "";
			var txt5 = document.getElementById('signature');
			txt5.value = "";
		};
	};
</script>

<script>
	function getMerchantName() {
		var xhttp = new XMLHttpRequest();
		var signature = document.getElementById("merchantName").value;
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				document.getElementById("merchantNameE").value = xhttp.responseText;
			}
		}
		var requestTXT = "getallpaymentfields/?value=" + signature;
		xhttp.open("GET", requestTXT, true);
		xhttp.send();
	}
</script>

<script>
	function getAccountNumber() {
		var xhttp = new XMLHttpRequest();
		var signature = document.getElementById("accountNumber").value;
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				document.getElementById("accountNumberE").value = xhttp.responseText;
			}
		}
		var requestTXT = "getallpaymentfields/?value=" + signature;
		xhttp.open("GET", requestTXT, true);
		xhttp.send();
	}
</script>

<script>
	function getAmount() {
		var xhttp = new XMLHttpRequest();
		var signature = document.getElementById("amount").value;
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				document.getElementById("amountE").value = xhttp.responseText;
			}
		}
		var requestTXT = "getallpaymentfields/?value=" + signature;
		xhttp.open("GET", requestTXT, true);
		xhttp.send();
	}
</script>

<script>
	function getOTP() {
		var xhttp = new XMLHttpRequest();
		var signature = document.getElementById("otpText").value;
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				document.getElementById("otpTextE").value = xhttp.responseText;
			}
		}
		var requestTXT = "getallpaymentfields/?value=" + signature;
		xhttp.open("GET", requestTXT, true);
		xhttp.send();
	}
</script>

<script>
	function getSignature() {
		var xhttp = new XMLHttpRequest();
		var signature = document.getElementById("signature").value;
		document.getElementById("signatureE").value = document.getElementById("signature").value;	
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {}
		}
		var requestTXT = "getallpaymentfields/?value=" + signature;
	}
</script>

</head>
<body>
<noscript>
  <meta http-equiv="refresh" content="0; url=${pageContext.servletContext.contextPath}/logoutusers" />
  Javascript Disabled
</noscript>
<div style="text-align: center"><a href="${pageContext.servletContext.contextPath}/Home">Home</a>&nbsp;&nbsp;&nbsp;
	<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></div>
	<form:form id='theForm'
		action="${pageContext.servletContext.contextPath}/readuserpaymentform/?${_csrf.parameterName}=${_csrf.token}"
		method="post">
	<h2 align = "center">Make Payments</h2>
		<table align="center">
			<tr>
				<td>Select the merchant:</td>
				<td><select name="merchantName" id="merchantName"
					onchange="getMerchantName()">
						<option value="choose">choose</option>
						<c:forEach items="${merchants}" var="merchant">
							<option value="${merchant}">${merchant}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Select the bank account:</td>
				<td><select name="accountNumber" id="accountNumber"
					onchange="getAccountNumber()">
						<option value="choose">choose</option>
						<c:forEach items="${bankaccounts}" var="bankaccount">
							<option value="${bankaccount.getAccountNumber()}">
								${bankaccount.getAccountNumber()}
								(${bankaccount.getAccountType()}) - B:
								${bankaccount.getBalance()}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Enter amount:</td>
				<td><input type="text" name="amount" id="amount"
					onchange="getAmount()" /></td>
			</tr>
			<tr>
				<td>OTP:</td>
				<td><input type="text" name="otpText" id="otpText"
					class="keyboardInput" onchange="getOTP()" /></td>
				<td><img id="otp_id" name="otpCaptcha123" src="refresh.jpg"
					hidden="true"> <a href="javascript:;"
					title="Send OTP in email" name="otpButton"
					onclick="document.getElementById('otp_id').src = '${pageContext.servletContext.contextPath}' + '/otpforpayment';  return false">
						Send OTP in Email </a></td>
			</tr>
			<tr>
				<td>Signature:</td>
				<td><input type="text" name="signature" id="signature"
					onchange="getSignature()" /></td>
			</tr>
		</table>


		<input type="hidden" id="merchantNameE" name="merchantNameE" value="" />
		<input type="hidden" id="accountNumberE" name="accountNumberE"
			value="" />
		<input type="hidden" id="amountE" name="amountE" value="" />
		<input type="hidden" id="otpTextE" name="otpTextE" value="" />
		<input type="hidden" id="signatureE" name="signatureE" value="" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
			<br/>
		<div style="text-align: center"><input type="submit" name="makepayment" value="Make Payment" align="middle" /></div>
	</form:form>

	<br />
	<br />
	<br />
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/Individual.Customers/Payment.jsp", "/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>