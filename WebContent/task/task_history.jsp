<%@page import="com.istarindia.apps.services.TaskService"%>
<%@page import="com.istarindia.apps.services.MediaService"%>
<%@page import="com.istarindia.cms.lessons.CMSSlide"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.services.LessonService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@ page import="java.util.*"%>
<%@ page import="com.istarindia.apps.dao.*"%>

<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title>Media Gallery | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico">

<!-- Web Fonts -->
<link rel='stylesheet' type='text/css'
	href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600&amp;subset=cyrillic,latin'>

<!-- CSS Global Compulsory -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/headers/header-default.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/animate.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">

<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/fancybox/source/jquery.fancybox.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/pages/shortcode_timeline2.css">

<!-- CSS Theme -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="../content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Task History</h1>
			</div>
		</div>
		<div class="container height-1000 content">
			<ul class="timeline-v2">
				<% TaskService service = new TaskService();
					ArrayList<ArrayList<String>> history = service.getHistory(request.getParameter("task_id"));
				for(ArrayList<String> row: history) {
				%>
				
				<li class="equal-height-columns">
					<div class="cbp_tmtime equal-height-column" style="height: 162px;">
						<span><%=row.get(3) %></span> <span><%=row.get(2) %></span>
					</div> <i class="cbp_tmicon rounded-x hidden-xs"></i>
					<div class="cbp_tmlabel equal-height-column" style="height: 192px;">
						<h2>Status Changed to <%=row.get(0).toLowerCase() %></h2>
						<div class="row">
							<div class="col-md-4">
								<img style="    width: 50px;" class="img-responsive" src="http://images.clipartpanda.com/task-clipart-task-md.png"
									alt="">
								<div class="md-margin-bottom-20"></div>
							</div>
							<div class="col-md-8">
								<p style="color: black"><%=row.get(1) %></p>
							</div>
						</div>
					</div>
				</li>
				<% } %>
			</ul>

		</div>
		<jsp:include page="../content_admin/includes/footer.jsp"></jsp:include>


		<!-- JS Global Compulsory -->
		<script type="text/javascript"
			src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
		<script type="text/javascript"
			src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
		<!-- JS Implementing Plugins -->
		<script type="text/javascript"
			src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
		<script type="text/javascript"
			src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
		<!-- JS Customization -->
		<script
			src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"
			type="text/javascript" charset="utf-8"></script>
		<script
			src="http://rvera.github.io/image-picker/image-picker/image-picker.js"
			type="text/javascript"></script>

		<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
		<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
			type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript"
			src="//cdn.tinymce.com/4/tinymce.min.js"></script>
		<script type="text/javascript"
			src="<%=baseURL%>assets/plugins/fancybox/source/jquery.fancybox.pack.js"></script>

		<script type="text/javascript"
			src="<%=baseURL%>assets/js/plugins/fancy-box.js"></script>

		<!-- JS Page Level -->
		<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>

		<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
		<script type="text/javascript">
$( document ).ready(function() {
	App.init();
	FancyBox.initFancybox();
	})
	</script>
</body>
</html>
