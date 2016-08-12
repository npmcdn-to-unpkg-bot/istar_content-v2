<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@ page import="java.util.*"%>
<%@page import="com.istarindia.apps.cmsutils.TableUtils"%><%@page
	import="com.istarindia.apps.dao.*"%><%@page
	import="com.istarindia.apps.dao.IstarUser"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.istarindia.apps.cmsutils.reports.*"%>
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
<link rel="stylesheet"
	href="http://htmlstream.com/preview/unify-v1.9.5/assets/css/pages/profile.css">
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
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="container-fluid content profile">
			<div class="row">
				<!--Left Sidebar-->
				<div class="col-md-6 md-margin-bottom-40">
					<%
						HashMap<String, String> conditions = new HashMap();
						conditions.put("content_creator_id",
								((IstarUser) request.getSession().getAttribute("user")).getId().toString());
					%>
					<%=(new ReportUtils())
					.getReport(199, conditions, ((IstarUser) request.getSession().getAttribute("user")), "LESSON")
					.toString()%>


				</div>

				<div class="col-md-6 md-margin-bottom-40">
						<div class="panel panel-profile">
							<div class="panel-heading overflow-h">
								<h2 class="panel-title heading-sm pull-left">
									<i class="fa fa-send"></i> User Activity for the day
								</h2></div>
							<div id="scrollbar3"
								class="panel-body no-padding mCustomScrollbar _mCS_4 mCS-autoHide"
								data-mcs-theme="minimal-dark"
								style="position: relative; overflow: visible;">
								<div id="mCSB_4"
									class="mCustomScrollBox mCS-minimal-dark mCSB_vertical mCSB_outside"
									tabindex="0">
									<div id="mCSB_4_container" class="mCSB_container" >
										<div
											class="alert-blocks alert-blocks-pending alert-dismissable">
											<button aria-hidden="true" data-dismiss="alert" class="close"
												type="button">×</button>
											<img class="rounded-x mCS_img_loaded"
												src="assets/img/testimonials/img3.jpg" alt="">
											<div class="overflow-h">
												<strong class="color-yellow">Pending... <small
													class="pull-right"><em>Just now</em></small></strong>
												<p>Your friend request has been sent.</p>
											</div>
										</div>
										</div></div></div></div></div>
		</div>
		<jsp:include page="includes/footer.jsp"></jsp:include>
	</div>


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
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/js/plugins/style-switcher.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatables/jquery.dataTables.min.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatables/dataTables.colVis.min.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>


	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/data.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();
		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
