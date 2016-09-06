<%@page import="com.istarindia.apps.dao.IstarUserDAO"%><%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.services.UserService"%><%@page import="com.istarindia.apps.dao.IstarUser"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head><% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

				%><title>Login/Registration | iStar Skill Development</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicon -->
<link rel="shortcut icon" href="<%=baseURL%>assets/img/talentify_logo_fav_48x48.png" />

<!-- Web Fonts -->
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans:400,300,700&amp;subset=cyrillic,latin">

<!-- CSS Global Compulsory -->
<link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="assets/css/headers/header-v6.css">
<link rel="stylesheet" href="assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="assets/plugins/animate.css">
<link rel="stylesheet" href="assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/plugins/brand-buttons/brand-buttons-inversed.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="assets/css/theme-colors/red.css">

<!-- CSS Page Style -->
	<link rel="stylesheet" href="assets/css/pages/page_log_reg_v2.css">

<!-- CSS Customization -->
<link rel="stylesheet" href="assets/css/custom.css">
</head>
<body >
	<!--=== Content Part ===-->
	<div class="container">
		<!--Reg Block-->
		<div class="reg-block">
			<div class="reg-block-header">
				<h2>Sign In</h2>
				<!--
				<p>Problem Signing In? Click <a class="color-green" href="<%=baseURL%>forgot_password.jsp">here</a> to reset password.</p>
			 	-->
			</div>
			<form action="<%=baseURL %>auth/login" name="login_form">
				<div class="input-group margin-bottom-20">
					<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
					<input type="text" class="form-control" placeholder="Email" name="email">
				</div>
				<div class="input-group margin-bottom-20">
					<span class="input-group-addon"><i class="fa fa-lock"></i></span>
					<input type="password" class="form-control" placeholder="Password"  name="password">
				</div>
				<hr>
	
				<div class="checkbox">
					<label>
						<input type="checkbox" name="remember">
						<p>Always stay signed in</p>
					</label>
				</div>
	
				<div class="row">
					<div class="col-md-10 col-md-offset-1">
						<button type="submit" class="btn-u btn-block">Log In</button>
					</div>
				</div>
			
			</form>
		</div>
		<!--End Reg Block-->
	</div>
	<!--=== End Content Part ===-->

	<!--=== Footer v1 ===-->

	<!--=== End Footer v1 ===-->

	<!-- JS Global Compulsory -->
	<script src="assets/plugins/jquery/jquery.min.js"></script>
	<script src="assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!-- JS Implementing Plugins -->
	<script src="assets/plugins/back-to-top.js"></script>
	<script src="assets/plugins/backstretch/jquery.backstretch.min.js"></script>
	<script type="text/javascript" src="assets/js/plugins/style-switcher.js"></script>

	<!-- JS Customization -->
	<script src="assets/js/custom.js"></script>

	<!-- JS Page Level -->
	<script src="assets/js/app.js"></script>
	<script>
		jQuery(document).ready(function() {
			App.init();
			StyleSwitcher.initStyleSwitcher();
		});
	</script>
	<script>
	$.backstretch([
	   			"assets/img/bg/19.jpg",
	   			"assets/img/bg/18.jpg",
	   			], {
	   				fade: 1000,
	   				duration: 7000
	   			});
		$(".forms-wrapper").backstretch([
			"assets/img/bg/6.jpg",
			"assets/img/bg/5.jpg",
			"assets/img/bg/7.jpg",
			], {
				fade: 1000,
				duration: 7000
			});
	</script>
	<!--[if lt IE 9]>
  <script src="assets/plugins/respond.js"></script>
  <script src="assets/plugins/html5shiv.js"></script>
  <script src="assets/plugins/placeholder-IE-fixes.js"></script>
  <![endif]-->
</body>
</html>