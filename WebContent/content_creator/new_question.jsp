<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@ page import="java.util.*"%>
<%@page import="com.istarindia.apps.cmsutils.TableUtils"%><%@page import="com.istarindia.apps.dao.*"%><%@page import="com.istarindia.apps.dao.IstarUser"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.istarindia.apps.cmsutils.reports.*"%>

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
<title>Create New Lesson | iStar CMS</title>

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
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">

<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css" />

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid height-1000" style="padding: 0px !important">
				<div class="col-md-12" style="padding: 26px;">
					<form action="#" class="sky-form row">
						<header>Create Question</header>
							<section class="col-md-6">
								<label class="label">Type of Question</label> <label class="select"> <select name="type_of_question">
										<option value="0">Choose name</option>
										<option value="1">Alexandra</option>
										<option value="2">Alice</option>
										<option value="3">Anastasia</option>
										<option value="4">Avelina</option>
								</select> <i></i>
								</label>
							</section>
							<section  class="col-md-6">
								<label class="label">Difficulty Level</label>
								<label class="input">
									<input type="number" name="difficulty_level">
								</label>
							</section>
						
							<section  class="col-md-6">
								<label class="label">Question Text</label> <label class="textarea"> <textarea rows="3"></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is the wyiswig for creating the question
								</div>
							</section>
						
							<section  class="col-md-6">
								<label class="label">Option Text 1</label> <label class="textarea"> <textarea rows="3"></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is the wyiswig for creating the question
								</div>
							</section>
						
							<section  class="col-md-6">
								<label class="label">Option Text 2</label> <label class="textarea"> <textarea rows="3"></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is the wyiswig for creating the question
								</div>
							</section>
						
							<section  class="col-md-6">
								<label class="label">Option Text 3</label> <label class="textarea"> <textarea rows="3"></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is the wyiswig for creating the question
								</div>
							</section>
						
							<section  class="col-md-6">
								<label class="label">Option Text 4</label> <label class="textarea"> <textarea rows="3"></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is the wyiswig for creating the question
								</div>
							</section>
						
							<section  class="col-md-6">
								<label class="label">Option Text 5</label> <label class="textarea"> <textarea rows="3"></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is the wyiswig for creating the question
								</div>
							</section>
						</fieldset>
						<footer>
							<button type="submit" class="btn-u">Submit</button>
						</footer>
					</form>
				</div>
			</div>
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
		<script src="<%=baseURL%>assets/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="<%=baseURL%>assets/plugins/datatables/dataTables.colVis.min.js"></script>
		<script src="<%=baseURL%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
		<script src="<%=baseURL%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>	<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>
		
		<script src="<%=baseURL%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>
		<script type="text/javascript">jQuery(document).ready(function() {
			App.init();
			tinymce.init({
				selector : 'textarea',
				height : 100,
				plugins : [
						'advlist autolink lists link image charmap print preview anchor',
						'searchreplace visualblocks code fullscreen',
						'insertdatetime media table contextmenu paste code' ],
				toolbar : 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
				content_css : [
						'//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
						'//www.tinymce.com/css/codepen.min.css' ], 
						setup: function (editor) {
					        editor.on('change', function () {
					        	console.debug(tinyMCE.activeEditor.getContent());

					        	var iframeInner = $('#prv').contents().find('#data_slide_paragraph').html(tinyMCE.activeEditor.getContent());
					        	tinyMCE.triggerSave();
					        });
					    }
			});
		});
	</script>
		<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
</body>
</html>
