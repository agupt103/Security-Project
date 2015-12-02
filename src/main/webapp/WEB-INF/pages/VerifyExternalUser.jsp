<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Insert title here</title>
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
	<div>
		<c:if test="${not empty norequests}">
			<div class="msg">${norequests}</div>
		</c:if>
	</div>
	<div>
		<c:if test="${requests != null && requests.size() != 0}">
			<div>
				<table border="1">
					<th>RequestID</th>
					<th>RequestType</th>
					<th>ModifiedColumn</th>
					<th>SSN/OldValue</th>
					<th>AccountType/NewValue</th>
					<th>Decision</th>


					<c:forEach items="${requests}" var="request" varStatus="myIndex">


						<tr>
							<td><c:out value="${request.requestid}" />
							<td><c:out value="${request.requesttype}" />
							<td><c:out value="${request.modifiedcolumn}" />
							<td><c:out value="${request.oldvalue}" />
							<td><c:out value="${request.newvalue}" />
							<td><c:out value="${request.approvalstatus}"/>
							<td><c:if
									test="${request.requesttype == 'modify' || request.requesttype == 'REQUEST_CARD' }">
									<form name="form"
										action="${pageContext.servletContext.contextPath}/VerifyExternalUser"
										method='POST'>
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<div>
											<button name="approve" type="submit"
												value="${request.requestid}"
												style="margin-left: 50px; float: left;">approve</button>
											<button name="decline" type="submit"
												value="${request.requestid}"
												style="margin-left: 50px; float: left;">decline</button>
										</div>
									</form>
								</c:if> <c:if test="${request.requesttype == 'ADD_ACCOUNT'}">
									<div>
										<form name="form"
											action="${pageContext.servletContext.contextPath}/verifyExternalUser"
											method='POST'>
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
											<button name="review" type="submit"
												value="${request.oldvalue}?${request.newvalue}?${request.requestid}"
												style="margin-left: 50px; float: left;">review</button>
										</form>
									</div>
								</c:if>	</td>
						</tr>

					</c:forEach>


				</table>
			</div>
		</c:if>

		<!--<div style="text-align:center">
	<h3 style="color:red">Verify Request</h3>
</div>
<div style="width:300px;margin-left:auto;margin-right:auto" >
	<div style="margin-bottom:20px">
    <label style="width:50%;float:left">
    	Request ID
     </label>
    <input type="text" id="request_id" style="width:50%"/>
	</div>
    <div style="margin-bottom:20px">
    <label style="width:50%;float:left">
    	Request Type
     </label>
    <input type="text" id="request_type" style="width:50%"/>
	</div>
    <div style="margin-bottom:20px">
       <label style="width:50%;float:left">
    	Description
       </label>
    <input type="text" id="description" style="width:50%"/>
	</div>
    <div>
    <button style="margin-left:50px;float:left;">
    approve
    </button>
    <button style="margin-left:50px;float:left;">
    decline
    </button>
	</div>
</div>-->
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/VerifyExternalUser.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>