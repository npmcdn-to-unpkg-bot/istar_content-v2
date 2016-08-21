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
<link rel="shortcut icon" href="favicon.ico">

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
<style>
* {
  padding: 0;
  margin: 0;
}
.fit {
  max-width: 100%;
  max-height: 100%;
}
.center {
  display: block;
  margin: auto;
}
</style>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" language="JavaScript">
function set_body_height()
{
    var wh = $(window).height();
    $('body').attr('style', 'height:' + wh + 'px;background-color: #C62828;');
}
$(document).ready(function() {
    set_body_height();
    $(window).bind('resize', function() { set_body_height(); });
    
});
</script>
</head>
<body style="height:674px; ">

		<img id="the_pic" class="center fit" src="<%=baseURL %>assets/error.png">  
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
	<!--[if lt IE 9]>
  <script src="assets/plugins/respond.js"></script>
  <script src="assets/plugins/html5shiv.js"></script>
  <script src="assets/plugins/placeholder-IE-fixes.js"></script>
  <![endif]-->
</body>
</html>