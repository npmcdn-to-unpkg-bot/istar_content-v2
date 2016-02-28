<%@page import="com.istarindia.apps.services.MediaService"%>
<%@page import="com.istarindia.cms.lessons.CMSSlide"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.services.LessonService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@ page import="java.util.*"%>
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
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">

<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.css">
	<link rel="stylesheet" href="<%=baseURL%>assets/plugins/fancybox/source/jquery.fancybox.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/reveal/css/reveal.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/reveal/css/theme/black.css" id="style_color">
<link rel="stylesheet" href="//www.tinymce.com/css/codepen.min.css" id="style_color">
<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="/content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Gallery</h1>

			</div>

		</div>
		<div class="container-fluid content">
			<div class="text-center margin-bottom-50">
				<h2 class="title-v2 title-center">Gallery</h2>
			</div>

			<div class="row  margin-bottom-30">
				<% 
				MediaService service = new MediaService();
				List<Image> images = service.getAllPublishedImages();
				for(Image image : images ) {
				%>
				<div class="col-sm-2 sm-margin-bottom-30">
					<a href="<%=image.getThumbnailUrl() %>" rel="gallery3" class="fancybox img-hover-v1" title="Image 1"> 
					<span><img class="img-responsive" src="<%=image.getThumbnailUrl() %>" alt=""></span>
					</a>
				</div>
				<% } %>
			</div>
		</div>
	</div>
	<!-- JS Global Compulsory -->
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
	<!-- JS Customization -->
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/fancybox/source/jquery.fancybox.pack.js"></script>

	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js" type="text/javascript" charset="utf-8"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>	<script type="text/javascript" src="<%=baseURL%>assets/js/plugins/fancy-box.js"></script>
	
	<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>

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
