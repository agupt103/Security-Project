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
<link rel="stylesheet" type="text/css" media="all"
	href="css/global.css">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" language="javascript" charset="utf-8"
	src="js/bootstrap.min.js"></script>
</head>
<script type = "text/javascript" >
    history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) {
    history.pushState(null, null, window.location.href);
    });
    document.addEventListener("contextmenu", function(e){
        e.preventDefault();
    }, false);
    </script>

<body oncopy="return false" oncut="return false" onpaste="return false">
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
				<div class="span4">
					<h2>SunDevils Credit Cards</h2>
					<p>
						<strong>SunDevils Cash Rewards Card</strong>
					</p>
					<p>
						<strong>SunDevils Travel Rewards Card</strong>
					</p>
					<p>
						<strong>SunDevils Better Balance Card</strong>
					</p>
					<p>
						<strong>SunDevils Student Card</strong>
					</p>
				</div>

				<div class="span4">
					<h2>SunDevils Loans</h2>
					<p>
						<strong>Mortgage</strong>
					</p>
					<p>
						<strong>Refinance</strong>
					</p>
					<p>
						<strong>Home Equity</strong>
					</p>
					<p>
						<strong>Auto Loans</strong>
					</p>

				</div>

				<div class="span4">
					<h2>Sundevils Investments</h2>
					<p>
						<strong>Mutual funds</strong>
					</p>
					<p>
						<strong>Retirement</strong>
					</p>
					<p>
						<strong>Stock market</strong>
					</p>
					<p>
						<strong>Investment Products</strong>
					</p>

				</div>
			</div>
		</div>
	</div>
</body>
</html>