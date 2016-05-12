<%@page import="com.istarindia.apps.services.task.MediaUploadHelper"%>
<%@page import="com.istarindia.apps.services.LessonService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@ page import="java.util.*"%>
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
				<h1 class="pull-left">Create New Trainer</h1>
			</div>
		</div>
		<div class="container-fluid height-1000"
			style="padding: 0px !important">
			<form action="/content/create_trainer" id="sky-form1"
				class="sky-form" method="GET">
				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<fieldset>
								<section class="col-md-6">
									<label> Name* </label> <label class="input"> <input
										type="text" name="name" placeholder="Enter you name">
									</label>
								</section>
								<section class="col-md-6">

									<label> Gender* </label> <label class="input"> <select
										name="gender" class="input">
											<option value="male">Male</option>
											<option value="female">Female</option>
									</select>
								</section>
							</fieldset>
						</div>
						<div class="row">
							<fieldset>
								<section class="col-md-6">
									<label> Email* </label> <label class="input"> <input
										type="email" name="email" placeholder="E-mail ID">
									</label>
								</section>
								<section class="col-md-6">
									<label> Password* </label> <label class="input"> <input
										type="password" name="password" placeholder="Password">
									</label>
								</section>
							</fieldset>
						</div>

						</fieldset>
						<footer>
							<button type="submit" class="btn-u">Create user</button>
							<label id="err" style="display: block; color: #ee9393"></label>
						</footer>
					</div>
					
					<div>
					<header>Select Assessments</header>
						<div class="row">
							<fieldset>
							<%
							VacancyDAO dao  = new VacancyDAO();
							List<Vacancy> vacancies = dao.findAll();
							for (Vacancy vacancy : vacancies){	
							%>
							<section>
									<input type="checkbox" name="vacancy_id" value="<%=vacancy.getId() %>>"> 
									<%=vacancy.getRecruiterCompany().getName() %> - <%=vacancy.getProfileTitle() %>
							</section>
							<% 
							}
							%>
							</fieldset>
						</div>

					</div>

				</div>
			</form>
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
