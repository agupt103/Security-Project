<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Spring MVC Multiple File Upload</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script>
$(document).ready(function() {
    //add more file components if Add is clicked
    $('#addFile').click(function() {
        var fileIndex = $('#fileTable tr').children().length - 1;
        $('#fileTable').append(
                '<tr><td>'+
                '   <input type="file" name="files['+ fileIndex +']" />'+
                '</td></tr>');
    });
     
});
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
	<br>
	<br>
	<div align="center">
		<h2>Instructions:</h2>
		<h3>For Individual Customer: Upload scanned copy of Passport or
			Driver License or DMV Identification Cards</h3>
		<h3>For Merchant/Organization: Upload scanned copy of Business
			License</h3>
		<form:form method="POST" modelAttribute="uploadForm"
			enctype="multipart/form-data"
			action="${pageContext.servletContext.contextPath}/upload/?${_csrf.parameterName}=${_csrf.token}">
<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<p>Select files to upload. inputs.</p>

			<table id="fileTable">
				<tr>
					<td><input name="files[0]" type="file" /></td>
				</tr>

			</table>
			<br />
			<input type="submit" value="Upload" />
			

		</form:form>
		
		<br />
	</div>

</body>
</html>