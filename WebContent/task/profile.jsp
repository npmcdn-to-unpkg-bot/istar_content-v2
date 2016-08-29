 <%@page import="org.omg.CosNaming.IstringHelper"%>
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
	
	IstarUser user = new IstarUser();
	if(request.getParameterMap().containsKey("id")) {
		user = (new IstarUserDAO()).findById(Integer.parseInt(request.getParameter("id")));
	} else {
		user = (IstarUser)request.getSession().getAttribute("user");
	}
 
	String imageURL = user.getImageUrl();
	if(imageURL == null) {
		imageURL = user.getUserType().toLowerCase() + ".png" ; 
	}
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
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/scrollbar/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">
	<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/shortcode_timeline2.css">


<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="../content_creator/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid">
				<h1 class="pull-left"><%=user.getName() %>'s Profile</h1>
			</div>
		</div>
		<div class="container-fluid content profile custom-container">
			<div class="row">
				<!--Left Sidebar-->
				<div class="col-md-3 md-margin-bottom-40">
					<img style="width: 100%" class="img-responsive profile-img margin-bottom-20" src="/video/img/user_images/<%=imageURL %>" alt="<%=user.getName() %>">

					<div class="panel-heading-v2 overflow-h">
						<h2 class="heading-xs pull-left"><i class="fa fa-bar-chart-o"></i> Work Progress</h2>
						<a href="#"><i class="fa fa-cog pull-right"></i></a>
					</div>
					<% // Get list of Tasks for this user 
					String sql = "select lesson.title, task.id, (count(*)*100/55) as perc  from task, slide, lesson, presentaion where actor_id="+user.getId()+" and status='DRAFT' and "
							+" task.item_id=lesson.id and lesson.id=presentaion.lesson_id and "
							+" slide.presentation_id= presentaion.id"
							+" group by task.id, lesson.title";
							
							DBUTILS db = new DBUTILS();
							List<HashMap<String, Object>> data = db.executeQuery(sql);
					for(HashMap<String, Object> row : data) {
							
					%>
					<h3 class="heading-xs"><%=row.get("title") %><span class="pull-right"><%=row.get("perc") %> % </span></h3>
					<div class="progress progress-u progress-xxs">
						<div style="width: <%=row.get("perc") %>%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="92" role="progressbar" class="progress-bar progress-bar-u">
						</div>
					</div>
					<% } %>

					<hr>

					<!--Notification-->
					
					
					<!--End Notification-->

					<div class="margin-bottom-50"></div>

					<!--Datepicker-->
					<form action="#" id="sky-form2" class="sky-form">
						<div id="inline-start"></div>
					</form>
					<!--End Datepicker-->
				</div>
				<!--End Left Sidebar-->

				<!-- Profile Content -->
				<div class="col-md-9">
					<div class="panel panel-sea" style="margin: 10px; border: 1px solid #1ABC9C;">
						<div class="panel-heading overflow-h">
							<h2 class="panel-title heading-sm pull-left">
								<i class="fa fa-send"></i> Task Notifications
							</h2>
						</div>
						<div id="scrollbar3" class="panel-body no-padding mCustomScrollbar" data-mcs-theme="minimal-dark" style="height: 64vh;">
							<%
								String sql1 = "select * from task_log where actor_id="+user.getId()+" ORDER BY created_at desc LIMIT 700";
								List<HashMap<String, Object>> items = db.executeQuery(sql1);

								for (HashMap<String, Object> row : items) {
									try {
										SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
										PrettyTime p = new PrettyTime();
										String typeStatus = row.get("changed_status").toString();
										String desc = row.get("comments").toString();
										String title = row.get("title").toString();
										
										if (desc.length() > 100) {
											desc = desc.substring(0, 250);
										}
										if(desc.contains("New text")) {
											desc = desc.split("New text")[0];
										}
										
										int userID = Integer.parseInt(row.get("actor_id").toString());
										IstarUser istarUser = new IstarUserDAO().findById(userID);
										String name = istarUser.getName();
										String imageURL1 = istarUser.getImageUrl();
										if(imageURL1 == null) {
											imageURL1 = istarUser.getUserType().toLowerCase() + ".png" ; 
										}
							%>

							<div class="alert-blocks alert-blocks-pending alert-dismissable">
								<img  src="/video/img/user_images/<%=imageURL %>" alt="<%=name %>">
								<div class="overflow-h">
									
									<strong class="color-yellow"><a href='<%=baseURL%>task/profile.jsp?id=<%=userID %>'><%=name %></a> &nbsp;&nbsp;&nbsp;&nbsp;<%=typeStatus%>
									
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
				<!-- End Profile Content -->
			</div>
		</div><!--/container-->
		<!--=== End Profile ===-->

		<!--=== Footer Version 1 ===-->
		
		<!--=== End Footer Version 1 ===-->
	</div><!--/wrapper-->
		<jsp:include page="../content_creator/includes/footer.jsp"></jsp:include>
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
	<script src="<%=baseURL%>assets/plugins/scrollbar/js/jquery.mCustomScrollbar.concat.min.js"></script>

	<script src="<%=baseURL%>assets/plugins/highcharts/highcharts.js"></script>
	<script src="<%=baseURL%>assets/plugins/highcharts/data.js"></script>
	<script src="<%=baseURL%>assets/plugins/highcharts/exporting.js"></script>
		
		<script type="text/javascript">
			jQuery(document).ready(function() {
				App.init();
				App.initScrollBar();
			});
			
		</script>
		
</body>
</html>
 