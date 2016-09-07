<%@page import="com.istarindia.apps.services.task.MediaUploadHelper"%>
<%@page import="com.istarindia.apps.services.LessonService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
<%@ page import="com.istarindia.apps.dao.*"%>

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
<title>Create Media Task | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="shortcut icon" href="<%=baseURL%>assets/img/talentify_logo_fav_48x48.png" />
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
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="../content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Create Task</h1>
			</div>
		</div>
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<form action="/content/create_task" id="sky-form1" class="sky-form" method="GET" onsubmit="myFunction()">
			<input type="hidden" id="selected_items" name="selected_items" />
			<div class="row">
				<div class="col-md-6">
						<fieldset>
						<section>
								<label> Media Title* </label> <label class="input"> <input
									type="text" name="title" placeholder="Title of Media">
								</label>
							</section>
							<section>
							
							
								<label> Task Type</label> <label class="input"> 
									<select name="media_type" class="input">
									<option value ="IMAGE"> Image
									</option>
									<option value ="VIDEO"> Video
									</option>
								</select>
									
								</label>
							</section>
							
							<section>
								<label > Task Description* </label> <label class="input">
								 <TEXTAREA NAME="description" ROWS="5" cols="75"></TEXTAREA>
								</label>
							</section>

						</fieldset>
						<footer>
							<button type="submit" class="btn-u">Create Task</button>
								<label id="err" style="display: block;color:#ee9393"></label>
						</footer>
					

					
				</div>
				
					<div class="col-md-4">
						<!-- General Unify Forms -->
						<header>Select Session</header>

						<fieldset>

							<div class="panel panel-grey margin-bottom-40" style="border:0">
								<div class="panel-body">


									<div id="html1">
										<%=(new MediaUploadHelper().getMediaTree((IstarUser)request.getSession().getAttribute("user"))) %>
									</div>

								</div>
							</div>
						</fieldset>
						<div class="margin-bottom-60"></div>

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
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->	<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>
	
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/smoothScroll.js"></script>
	<!-- JS Customization -->
	<!-- JS Implementing Plugins -->
	<script src="<%=baseURL %>assets/plugins/sky-forms-pro/skyforms/js/jquery-ui.min.js"></script>
	<script src="<%=baseURL %>assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/js/plugins/validation.js"></script>
	<script type="text/javascript">
				function myFunction() {
					var selectedElmsIds = $('#html1').jstree("get_selected");
					if (selectedElmsIds == ""){
						event.preventDefault();
						document.getElementById("err").innerHTML = "Please select the session";
						return false; 
						}
					$('#selected_items').val(selectedElmsIds);
				}
				jQuery(document).ready(function() {
					App.init();
					Validation.createMediaTaskValidation();

					$('#html1').jstree({
						"core" : {
							"multiple": false,
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

					try {

						$('#selected_items1').val("aaaa");
					 tinymce.init({
						  selector: 'textarea',
						  height: 100,
						  plugins: [
						    'advlist autolink lists link image charmap print preview anchor',
						    'searchreplace visualblocks code fullscreen',
						    'insertdatetime media table contextmenu paste code'
						  ],
						  toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
						  content_css: [
						    '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
						    '//www.tinymce.com/css/codepen.min.css'
						  ]
						}); 
				}
				 catch (err) {
					// TODO: handle exception
				}});
			</script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js" type="text/javascript" charset="utf-8"></script>

	<script type="text/javascript" src="<%=baseURL %>assets/js/custom.js"></script>
	<script src="<%=baseURL %>assets/plugins/tagz/bootstrap-tagsinput.js" type="text/javascript" charset="utf-8"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/app.js"></script>

	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
