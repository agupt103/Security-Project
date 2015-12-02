<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
<title>Sun Devils Bank Home Page</title>
<link rel="shortcut icon"
	href="http://teamtreehouse.com/assets/favicon.ico">
<link rel="icon" href="http://teamtreehouse.com/assets/favicon.ico">
<link rel="stylesheet" type="text/css" media="all"
	href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="all"
	href="css/bootstrap-responsive.min.css">
<link rel="stylesheet" type="text/css" media="all" href="css/global.css">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" language="javascript" charset="utf-8"
	src="js/bootstrap.min.js"></script>
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
	<noscript>
  <meta http-equiv="refresh" content="0; url=${pageContext.servletContext.contextPath}/logoutusers" />
  Javascript Disabled
</noscript>
	<nav id="navigation">
		<div class="container">
			<ul class="navlinks">
				<li><a href="${pageContext.servletContext.contextPath}/">Home</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/aboutus">About Us</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/projects">Projects</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/team">The Team</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/contact">Contact Us</a></li>
			</ul>
		</div>
	</nav>

	<header id="heading">
		<div class="container text-center">
			<h1>Sun Devils Bank</h1>
			<h4>Secure Banking Website by Group#1</h4>


		</div>
	</header>

	<div id="main-content">
		<div class="container">
			<div class="row">
				<div class="span10">
					<h2>Start banking with Us</h2>
					<p>
						<strong>You could apply for checking, savings accounts
							and credit cards</strong>
					</p>
					<p>
						<strong>For opening any type of account, please visit our nearest store and provide all the documentation that is required.</strong>
					</p>
					<p>
						<strong>The activation of account will normally require two business days to go through all the activation process. So please bear with us.</strong>
					</p>
					<p>
						<strong>You can open your account now by clicking <a href = "${pageContext.servletContext.contextPath}/accountopening">here</a></strong>
					</p>
				</div>

			</div>
		</div>
	</div>
</body>
</html>