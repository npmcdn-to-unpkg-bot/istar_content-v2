<%@page import="com.istarindia.apps.services.LessonService"%>
<%@page import="com.istarindia.apps.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
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
<title>New Lesson | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicon -->
<link rel="shortcut icon" href="<%=baseURL%>assets/img/talentify_logo_fav_48x48.png" />

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
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.css">

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
				<h1 class="pull-left">Edit Handout</h1>
				<!-- <ul class="pull-right breadcrumb">
					<li><a href="/">Home</a></li>
					<li><a href="">Content Admin </a></li>
					<li class="active">Create Lesson</li>
				</ul> -->
			</div>

		</div>
		<div class="container-fluid height-1000"
			style="padding: 0px !important">
			<div class="row">
				<form action="/content/update_handout" id="sky-form4" class="sky-form" onsubmit="myFunction()" method="POST">
					
					<%
					int handout_id = Integer.parseInt(request.getParameter("lesson_id")); 
					HandoutsDAO dao = new HandoutsDAO();Handouts handout = new Handouts();
					if(!dao.findByLessonId(handout_id).isEmpty()) {
						handout = dao.findByLessonId(handout_id).get(0);	
						%>
						<input type="hidden" id="handout_id" name="handout_id" value="<%=handout_id%>"/>
						<input type="hidden" id="handout_id" name="is_edit" value="true"/>
						<% 
					}else {
						
						%>
						<input type="hidden" id="handout_id" name="is_edit" value="false"/>
						<input type="hidden" id="handout_id" name="lessson_id" value="<%=request.getParameter("lesson_id")%>"/>
						<%
					}
					
					%>
					
					
					<div class="col-md-6">
					<div class="alert alert-warning fade in text-center">
						<fieldset>
							<section>
								<label>Title of Handout</label> 
								<label class="input" > <input
									value="<%=handout.getTitle() %>" type="text" name="title" placeholder="Title of Lesson">
									<b class="tooltip tooltip-bottom-right">The title of the
										Handout*</b>
								</label>
							</section>
							<section>
								<label>Handouts*</label>
								 <label class="textarea"> 
								 <textarea rows="10" id="handouts_text1" name="handouts_text" placeholder="Handouts"> <%=handout.getDataBloB() %></textarea>
							</section>
							

						</fieldset>
						<footer>
							<button type="submit" class="btn-u"  >Update Handout</button>
							<label id="err" style="display: block;color:#ee9393"></label>
							
							
						</footer>
	
					</div>
				</div>
				
			</form>

		</div>
	</div>
	</div>
	</div>
	<jsp:include page="../content_admin/includes/footer.jsp"></jsp:include>
	</div>


	<!-- JS Global Compulsory -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>
	<!-- JS Customization -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="<%=baseURL %>assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/ckeditor/ckeditor.js"></script>
	<script src="<%=baseURL%>assets/plugins/ckeditor/plugins/imagebrowser/plugin.js"></script>

	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
		type="text/javascript" charset="utf-8"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/js/plugins/validation.js"></script>
	<script type="text/javascript">

		function myFunction() {
			var selectedElmsIds = $('#html1').jstree("get_selected");
			if (selectedElmsIds == ""){
				event.preventDefault();
				document.getElementById("err").innerHTML = "Please select the session and try again";
				return false; 
				}
			else {
				document.getElementById("err").innerHTML = "";
			}
			$('#selected_items').val(selectedElmsIds);
			console.log(selectedElmsIds);

		}
		
		jQuery(document).ready(function() {
			App.init();
			$('#html1').jstree({
				"core" : {
					"multiple" : false,
					"themes" : {
						"variant" : "large"
					}
				},
				"checkbox" : {
					"keep_selected_style" : false,
					"three_state" : false
				},
				"plugins" : [ "checkbox" ]
			});
			
			$('#selected_items').val("aaaa");
			
			try {
				CKEDITOR.replace('handouts_text1', { 
					"extraPlugins" : 'imagebrowser',
					"imageBrowser_listUrl" : "/content/GalleryJsonController"
				});
			} catch (err) {
				console.log("CKEditor faced issue while initializing-" + err);
			}
			
			
		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
