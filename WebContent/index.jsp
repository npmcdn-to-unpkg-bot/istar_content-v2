<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.services.UserService"%>
<%@page import="com.istarindia.apps.dao.IstarUser"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head><% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

if(request.getSession().getAttribute("user")!=null) {
	String url1 = "/content/"+ ((IstarUser)session.getAttribute("user")).getUserType().toLowerCase()+"/dashboard.jsp";
	response.sendRedirect(url1);
}	

%>

<title>Login/Registration | iStar Skill Development</title>

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
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/headers/header-v6.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/brand-buttons/brand-buttons-inversed.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/red.css">

<!-- CSS Page Style -->
	<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/page_log_reg_v2.css">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>
<body >
	<!--=== Content Part ===-->
	<div class="container">
		<!--Reg Block-->
		<div class="reg-block">
			<div class="reg-block-header">
				<h2>Sign In</h2>
				<p>Problem Signing In? Click <a class="color-green" href="<%=baseURL%>forgot_password.jsp">here</a> to reset password.</p>
			</div>
			<form action="<%=baseURL %>auth/login" name="login_form">
			
			<% if(null != request.getAttribute("msg")) { %>
			<div class="alert alert-danger fade in">
				<strong>Oh snap!</strong> <%=request.getAttribute("msg") %>
			</div>
			<% } %>
			
			<div class="input-group margin-bottom-20">
				<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
				<input type="text" class="form-control" placeholder="Email" name="email">
			</div>
			
			<div class="input-group margin-bottom-20">
				<span class="input-group-addon"><i class="fa fa-lock"></i></span>
				<input type="password" class="form-control" placeholder="Password"  name="password">
			</div>
			
			<hr>

			<!-- <div class="checkbox">
				<label>
					<input type="checkbox" name="remember">
					<p>Always stay signed in</p>
				</label>
			</div> -->

			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<button type="submit" class="btn-u btn-block">Log In</button>
				</div>
			</div>
			
			</form>
		</div>
		
	</div>
	
	<% 
		List<TaskLog> logs = new ArrayList<>();
		if(request.getParameterMap().containsKey("jsession_id")) { 
		String jsession_id = request.getParameter("jsession_id").toString();
		TaskLog example = new TaskLog();
		example.setSessionID(jsession_id);
		logs = new TaskLogDAO().findByExample(example);
	%>
	
	<div class="modal fade" id="session-log-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="    width: 60%;">
			<div class=" modal-content">
				<div class="modal-header">Session log</div>
				
				<div class="modal-body">
					<ol>
					<% for (TaskLog log : logs) { %>
					<li>
					<strong><%=log.getTitle() %></strong> - <%=log.getComments() %>
					</li>
					
					<% } %>
					</ol>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Dismiss</button>
				</div>
			</div>
		</div>
	</div>
	
	<% } %>
	
	
	
	
	<!-- JS Global Compulsory -->
	<script src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!-- JS Implementing Plugins -->
	<script src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script src="<%=baseURL%>assets/plugins/backstretch/jquery.backstretch.min.js"></script>

	<!-- JS Customization -->
	<script src="<%=baseURL%>assets/js/custom.js"></script>

	<!-- JS Page Level -->
	<script src="<%=baseURL%>assets/js/app.js"></script>
	<script>
		if (!window.console) window.console = {};
		if (!window.console.log) window.console.log = function () { };
		
		jQuery(document).ready(function() {
			App.init();
			
			<% if(request.getParameterMap().containsKey("jsession_id") && logs.size() > 0) { %>
			
				$("#session-log-modal").modal('show');
			
			<% } %>
			
		});
	</script>
	<script>
	$.backstretch([
	   			"<%=baseURL%>assets/img/bg/19.jpg",
	   			"<%=baseURL%>assets/img/bg/18.jpg",
	   			], {
	   				fade: 1000,
	   				duration: 7000
	   			});
		$(".forms-wrapper").backstretch([
			"<%=baseURL%>assets/img/bg/6.jpg",
			"<%=baseURL%>assets/img/bg/5.jpg",
			"<%=baseURL%>assets/img/bg/7.jpg",
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
