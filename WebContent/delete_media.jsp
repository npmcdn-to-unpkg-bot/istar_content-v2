<%@page import="com.istarindia.apps.services.MediaService"%><%@page
	import="com.istarindia.apps.services.task.MediaUploadHelper"%>

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
<style type="text/css">
.jstree-anchor{
height:    auto   !important;
 white-space:  normal  !important;}
 </style>
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
</head>

<body>

	<div class="wrapper">
		<jsp:include page="content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<div class="text-center margin-bottom-50">
				<h2 class="title-v2 title-center">Gallery</h2>
			</div>
			</div>
		</div>
		<div class="container-fluid height-1000 content">
			
			<button style="margin-bottom: 1%;" onClick="getSessions();" type="submit" class="btn-u">Filter on Session</button>
			<input type="hidden" id="selected_items" name="selected_items" />
			<label id="err" style="display: block;color:#ff0000"></label>
			
			<div id="html1" class="col-md-4"style="padding-right : 4% ;background-color: aliceblue; ">
				<%=(new MediaUploadHelper().getMediaTree((IstarUser) request.getSession().getAttribute("user")))%>
			</div>
			
			<div class="col-md-8" style="padding-left : 2%  ;background-color: #f2f2f2">
				<form action="/content/media_upload" name="" method="GET" data-parsley-validate="" novalidate="" class="sky-form">
				<button style="margin-bottom: 1%;" type="submit" class="btn-u">Filter on Session</button>
				
				<%
					MediaService service = new MediaService();
					String sess = new String();
					if(request.getParameterMap().containsKey("sessionids")) {
						sess = request.getParameter("sessionids");
					} else {
						sess = "show_all";
					}
					List<Image> images = service.getAllPublishedImages(sess);
					for (Image image : images) {
				%>
				<div class="col-sm-2 sm-margin-bottom-30" style="margin-top: 2%;  word-wrap: break-word;">
					<label class="checkbox">
						<input type="checkbox" name="delfile" 	value="<%=image.getUrl()%>" class="imageToDelete">  <i></i>
						
						<a href="<%=image.getUrl()%>" rel="gallery3" class="fancybox img-hover-v1" title="Image <%=image.getId()%>">
							<span> <%=image.getTitle().replaceAll("_", " ")%> </span>
						</a>
						
					</label>
					
				</div>
				<%
					}
				%>
				
				</form>
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

			function getSessions() {
				var selectedElmsIds = $('#html1').jstree("get_selected");
				if (selectedElmsIds == ""){
					event.preventDefault();
					document.getElementById("err").innerHTML = "Please select at least one session";
					return false; 
					}
				$('#selected_items').val(selectedElmsIds);
				window.location = '<%=baseURL%>delete_media.jsp?sessionids='+selectedElmsIds;
			}

			$('input.imageToDelete').on('change', function() {
					$('input.imageToDelete').not(this).prop('checked', false);
			});
			
			$(document).ready(function() {
					App.init();
					FancyBox.initFancybox();

					$('#html1').jstree({
						"core" : {
							"multiple": true,
							"themes" : {
								"variant" : "large"
							}
						},
						"checkbox" : {
							"keep_selected_style" : false,
							"three_state" : false,
						},
						"plugins" : [ "checkbox"]
					});
				})
			</script>
</body>
</html>
