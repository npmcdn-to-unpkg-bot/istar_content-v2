<%@page import="com.istarindia.apps.dao.*"%><%@page import="java.util.*"%>
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
<title>Content Admin Dashboard | iStar CMS</title>

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
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="dist/themes/default/style.min.css" />


<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/orange.css" id="style_color">
<link rel="stylesheet" href="//static.jstree.com/3.2.1/assets/dist/themes/default/style.min.css" />

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container">
				<h1 class="pull-left">Content Admin Dashboard</h1>
				<ul class="pull-right breadcrumb">
					<li><a href="index.html">Home</a></li>
					<li><a href="">Content Admin </a></li>
					<li class="active">Dashboard</li>
				</ul>
			</div>
			<!--/container-->
		</div>
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<form action="/content/course_upload" class="sky-form" method="post" enctype="multipart/form-data">
				<header>General Unify Forms</header>

				<fieldset>
					<section>
						<label class="label">File input</label> <label for="file" class="input input-file">
							<div class="button">
								<input name="file" type="file" id="file" onchange="this.parentNode.nextSibling.value = this.value">Browse
							</div> <input type="text" readonly="">
						</label>
					</section>
				</fieldset>
				<footer>
					<button type="submit" class="btn-u">Submit</button>
				</footer>
			</form>

			<div id="jstree2" class="demo">
				<ul>
					<li data-jstree='{ "opened" : true }'>All Courses <% CourseDAO dao = new CourseDAO();
					List<Course> courseList = 	dao.findAll();			
					for (Course course : courseList) {
								%>

						<ul>
							<li data-jstree='{ "opened" : true }'><%=course.getCourseName() %>
								<ul>
									<% for (Module module : course.getModules()) { %>
									<li data-jstree='{ "opened" : true }'><%=module.getModuleName() %>
									<ul>
									
									<% for (Cmsession iStarSession : module.getCmsessions()) { %>
										<li data-jstree='{ "opened" : true }'><%=iStarSession.getTitle() %></li>
										<% } %>
									</ul>


									<% } %>
								</ul></li>
						</ul> <% } %></li>
				</ul>
			</div>
		</div>

		<div class="container-fluid height-1000" style="padding: 0px !important"></div>
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
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jstree/jstree.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();

			$('#jstree2').jstree();
			
		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
