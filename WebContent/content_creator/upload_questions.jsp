<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.dao.*"%><%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<title>Question Upload | iStar CMS</title>

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
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">


<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">





<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>

<body>
	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid">
				<h1 class="pull-left">
					<a href="<%=baseURL %>content_creator/dashboard.jsp">Content Creator Dashboard</a>
				</h1>

			</div>
			<!--/container-->
		</div>
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<div class="panel panel-orange" style="margin-left: 100px; margin-right: 100px">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-tasks"></i> Create Assessment
					</h3>
				</div>
				<div class="panel-body">
					<form action="<%=baseURL%>question_upload" class="sky-form" method="post" enctype="multipart/form-data">

						<fieldset>
							<section>
								<p>File input &nbsp;&nbsp;&nbsp;&nbsp; (Download sample question set from <a href="../assets/excel/cms course structure.xls" style="color: RED">here</a> )
								</p>
								<label for="file" class="input input-file">
									<div class="button"><input type="file" id="file" name="file" onchange="this.parentNode.nextSibling.value = this.value">Browse</div><input type="text" readonly>
								</label>
								
							</section>
						</fieldset>
						<footer>
							<button type="submit" class="btn-u">Submit</button>
							
							
						</footer>
					</form>
				</div>
			</div>

			</form>
			<!-- JS Global Compulsory -->
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
			<script type="text/javascript" src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>

			<script type="text/javascript" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
			<!-- JS Implementing Plugins -->
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>

			<!-- JS Customization -->
			<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
			<!-- JS Page Level -->
			<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
			
			<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
</body>
</html>
