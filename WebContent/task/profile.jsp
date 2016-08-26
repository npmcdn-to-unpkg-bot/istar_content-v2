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
	
	IstarUser user =  (new IstarUserDAO()).findById(Integer.parseInt(request.getParameter("id")));
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
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">
	<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/shortcode_timeline2.css">


<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>
<% 
String imageURL = user.getImageUrl();
										if(imageURL == null) {
											imageURL = user.getUserType().toLowerCase() + ".png" ; 
										}
										%>
<body>

	<div class="wrapper">
		<jsp:include page="../content_creator/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid">
				<h1 class="pull-left"><%=user.getName() %>'s Profile</h1>
			</div>
		</div>
		<div class="container-fluid content profile">
			<div class="row">
				<!--Left Sidebar-->
				<div class="col-md-3 md-margin-bottom-40">
					<img style="width: 100%" class="img-responsive profile-img margin-bottom-20" src="<%=baseURL%>img/user_images/<%=imageURL %>" alt="<%=user.getName() %>">

					

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
					<div class="profile-body">
						<!--Timeline-->
						<ul class="timeline-v2">
							<li>
								<time class="cbp_tmtime" datetime=""><span>4/1/08</span> <span>January</span></time>
								<i class="cbp_tmicon rounded-x hidden-xs"></i>
								<div class="cbp_tmlabel">
									<h2>Our first step</h2>
									<div class="row">
										<div class="col-md-4">
											<img class="img-responsive" src="assets/img/main/img18.jpg" alt="">
											<div class="md-margin-bottom-20"></div>
										</div>
										<div class="col-md-8">
											<p>Winter purslane courgette pumpkin quandong komatsuna fennel green bean cucumber watercress. Pea sprouts wattle seed rutabaga okra yarrow cress avocado grape.</p>
											<p>Cabbage lentil cucumber chickpea sorrel gram garbanzo plantain lotus root bok choy squash cress potato.</p>
										</div>
									</div>
								</div>
							</li>
						</ul>
						<!--End Timeline-->
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
 