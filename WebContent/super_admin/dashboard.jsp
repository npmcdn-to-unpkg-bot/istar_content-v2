<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<%
	String url = request.getRequestURL().toString();
	String basePath = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
%>
<title>Super Admin Dahsboard | Istar Skill Development</title>
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
<link rel="stylesheet" href="<%=basePath%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=basePath%>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=basePath%>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/scrollbar/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">

<!-- CSS Page Style -->
<link rel="stylesheet" href="<%=basePath%>assets/css/pages/profile.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=basePath%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=basePath%>assets/css/theme-colors/red.css">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=basePath%>assets/css/custom.css">
</head>

<body>
	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="container content profile">
			<div class="row">
				<!--Left Sidebar-->
				<div class="col-md-3 md-margin-bottom-40">
					<img class="img-responsive profile-img margin-bottom-20" src="<%=basePath%>assets/img/team/img32-md.jpg" alt="">

					<ul class="list-group sidebar-nav-v1 margin-bottom-40" id="sidebar-nav-1">
						<li class="list-group-item active"><a href="page_profile.html"><i class="fa fa-bar-chart-o"></i> Overall</a></li>
						<li class="list-group-item"><a href="users.jsp"><i class="fa fa-user"></i> Users</a></li>
						<li class="list-group-item"><a href="organizations.jsp"><i class="fa fa-group"></i> Organization(s)</a></li>
						<li class="list-group-item"><a href="content_snapshot.jsp"><i class="fa fa-cubes"></i> Content</a></li>
						<li class="list-group-item"><a href="creative_snapshot.jsp"><i class="fa fa-comments"></i> Creative</a></li>

					</ul>

				</div>
				<!--End Left Sidebar-->

				<!-- Profile Content -->
				<div class="col-md-9">
					<div class="profile-body">
						<!--Service Block v3-->
						<div class="row margin-bottom-10">
							<div class="col-sm-6 sm-margin-bottom-20">
								<div class="service-block-v3 service-block-u">
									<i class="icon-users"></i> <span class="service-heading">Total Users</span> <span class="counter">52,147</span>
									<div class="clearfix margin-bottom-10"></div>
								</div>
							</div>
							<div class="col-sm-6 sm-margin-bottom-20">
								<div class="service-block-v3 service-block-blue">
									<i class="icon-users"></i> <span class="service-heading">Total Organization</span> <span class="counter">52,147</span>
									<div class="clearfix margin-bottom-10"></div>
								</div>
							</div>
							<div class="col-sm-6 sm-margin-bottom-20">
								<div class="service-block-v3 service-block-green">
									<i class="icon-users"></i> <span class="service-heading">Content</span> <span class="counter">52,147</span>
									<div class="clearfix margin-bottom-10"></div>
								</div>
							</div><div class="col-sm-6 sm-margin-bottom-20">
								<div class="service-block-v3 service-block-orange">
									<i class="icon-users"></i> <span class="service-heading">Creative</span> <span class="counter">52,147</span>
									<div class="clearfix margin-bottom-10"></div>
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- JS Global Compulsory -->
	<script type="text/javascript" src="<%=basePath%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=basePath%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/smoothScroll.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/counter/waypoints.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/counter/jquery.counterup.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/sky-forms-pro/skyforms/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/scrollbar/js/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=basePath%>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=basePath%>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/plugins/datepicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/plugins/style-switcher.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();
			App.initCounter();
			App.initScrollBar();
			Datepicker.initDatepicker();
			StyleSwitcher.initStyleSwitcher();
		});
	</script>
	<!--[if lt IE 9]>
	<script src="<%=basePath%>assets/plugins/respond.js"></script>
	<script src="<%=basePath%>assets/plugins/html5shiv.js"></script>
	<script src="<%=basePath%>assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
