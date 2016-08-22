<%@page import="org.ocpsoft.prettytime.PrettyTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ page import="java.text.*"%>
<%@page import="com.istarindia.apps.cmsutils.TableUtils"%><%@page import="com.istarindia.apps.dao.*"%><%@page import="com.istarindia.apps.dao.IstarUser"%>
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
<link rel='stylesheet' type='text/css' href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600&amp;subset=cyrillic,latin'>

<!-- CSS Global Compulsory -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/animate.css">
<link rel="stylesheet" href="http://htmlstream.com/preview/unify-v1.9.5/assets/plugins/scrollbar/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">

<link rel="stylesheet" href="http://htmlstream.com/preview/unify-v1.9.5/assets/css/pages/profile.css">
<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

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
								<i class="fa fa-send"></i> Task Notifications
							</h2>
							<a href="#"><i class="fa fa-cog pull-right"></i></a>
						</div>
						<div id="scrollbar3" class="panel-body no-padding mCustomScrollbar" data-mcs-theme="minimal-dark" style="height: 800px">

							<%
								DBUTILS db = new DBUTILS();
								String sql = "select * from task_log  ORDER BY created_at desc LIMIT 100";
								List<HashMap<String, Object>> items = db.executeQuery(sql);

								for (HashMap<String, Object> row : items) {
									try {
										//2016-08-19 11:13:30.320678
										SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
										PrettyTime p = new PrettyTime();
										String typeStatus = row.get("changed_status").toString();
										String desc = row.get("comments").toString();
										if (desc.length() > 100) {
											desc = desc.substring(0, 250);
										}
										int userID = Integer.parseInt(row.get("actor_id").toString());
										String name = new IstarUserDAO().findById(userID).getName();
							%>

							<div class="alert-blocks alert-blocks-pending alert-dismissable">
								<img class="rounded-x" src="http://htmlstream.com/preview/unify-v1.9.5/assets/img/testimonials/img3.jpg" alt="">
								<div class="overflow-h">
									<strong class="color-yellow"><%=name %> &nbsp;&nbsp;&nbsp;&nbsp;<%=typeStatus%>
									<small class="pull-right"><em><%=p.format(ft.parse(row.get("created_at").toString()))%></em></small></strong>
									<p><%=desc%></p>
								</div>
							</div>
							<%
								} catch (Exception e) {
									}
								}
							%>
						</div>
					</div>
				</div>
			</div>
			<jsp:include page="includes/footer.jsp"></jsp:include>
		</div>


		<!-- JS Global Compulsory -->
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
		<!-- JS Implementing Plugins -->
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
		<!-- JS Customization -->
		<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
		<!-- JS Page Level -->
		<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
		<script type="text/javascript" src="<%=baseURL%>assets/js/plugins/style-switcher.js"></script>
		<script src="<%=baseURL%>assets/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="<%=baseURL%>assets/plugins/datatables/dataTables.colVis.min.js"></script>

		<script src="<%=baseURL%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
		<script src="<%=baseURL%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<script src="<%=baseURL%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>
		<script src="http://htmlstream.com/preview/unify-v1.9.5/assets/plugins/scrollbar/js/jquery.mCustomScrollbar.concat.min.js"></script>

		<script src="https://code.highcharts.com/highcharts.js"></script>
		<script src="https://code.highcharts.com/modules/data.js"></script>
		<script src="https://code.highcharts.com/modules/exporting.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				App.init();
				App.initScrollBar();

			});
		</script>
		<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
</body>
</html>
