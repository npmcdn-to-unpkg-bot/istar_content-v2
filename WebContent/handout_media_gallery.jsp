<%@page import="com.istarindia.apps.services.MediaService"%><%@page
	import="com.istarindia.apps.services.task.MediaUploadHelper"%>
<%@page import="java.nio.file.*"%><%@page import="java.io.*"%>
<%@page import="com.istarindia.cms.lessons.CMSSlide"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.services.LessonService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
<%@ page import="com.istarindia.apps.dao.*"%><%@ page import="com.istarindia.apps.*"%>

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

<!-- CSS Theme -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">


</head>

<body>

	<div class="wrapper">
		<jsp:include page="content_admin/includes/header.jsp"></jsp:include>
		
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Backgrounds</h1>
			</div>
		</div>
		
		<div class="container-fluid height-1000 content">
			<div class="panel panel-orange" style="margin-left: 100px; margin-right: 100px">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-tasks"></i> Upload new background
					</h3>
				</div>
				<div class="panel-body">
					<form action="<%=baseURL%>handout_media_controller" class="sky-form" method="post"
						enctype="multipart/form-data">
						
						<fieldset>
							<section>
								<p style="color: red;">
									[<b>NOTE</b>: Please ensure to use none other than
									alphabets/numbers/underscores in the file name]
								</p>
								<label for="file" class="input input-file">
									<div class="button">
										<input type="file" id="file" name="file"
											onchange="DisplayFilePath()">Browse
									</div> <input type="text" id="formfield" readonly>
								</label>
							</section>
						</fieldset>
						<footer>
							<button type="submit" class="btn-u" style="float: right;">Submit</button>
						</footer>
					</form>
				</div>
			</div>
			
			<br>
			
			<div class="text-center margin-bottom-50">
				<h2 class="title-v2 title-center">Background Image Gallery</h2>
			</div>
			
			<div class="col-md-12">
				<%
					
					ArrayList<Image> list = (ArrayList<Image>) request.getAttribute("list");
					for (Image image : list) {
				
				%>

				<div class="col-sm-3 sm-margin-bottom-30" style="margin-top:2%">
					
					<a href="<%=image.getUrl()%>" rel="gallery3"
						class="fancybox img-hover-v1" title="Image 1"><span><%=image.getTitle() %></span>
					</a>
				</div>
				
				<%
					}
				%>
				
			</div>
			</div></div>
			<jsp:include page="content_admin/includes/footer.jsp"></jsp:include>


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
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">

			<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
			<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
				type="text/javascript" charset="utf-8"></script>
			<script type="text/javascript"
				src="//cdn.tinymce.com/4/tinymce.min.js"></script>
			<script type="text/javascript"
				src="<%=baseURL%>assets/plugins/fancybox/source/jquery.fancybox.pack.js"></script>

			<script type="text/javascript"
				src="<%=baseURL%>assets/js/plugins/fancy-box.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>
			<!-- JS Page Level -->
			<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>

			<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
			<script type="text/javascript">

			
			$(document).ready(function() {
					App.init();
					FancyBox.initFancybox();
					
				})
				
				function DisplayFilePath(){
                    var filepath=$(":file").val();
                    var filename=filepath.substr(filepath.lastIndexOf('\\')+1,filepath.length);
                document.getElementById("formfield").value = filename;
                }
			</script>
</body>
</html>
