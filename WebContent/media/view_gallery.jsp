<%@page import="org.apache.catalina.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title>Gallery | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico">

<!-- Web Fonts -->
<link rel='stylesheet' type='text/css' href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600&amp;subset=cyrillic,latin'>

<!-- CSS Global Compulsory -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/global.css">



<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container">
				<h1 class="pull-left">Show gallery</h1>
			</div>
			<!--/container-->
		</div>
		TODO
	<jsp:include page="includes/footer.jsp"></jsp:include>
	</div>


	<!-- JS Global Compulsory -->
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/smoothScroll.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/js/plugins/style-switcher.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();
		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>