<%@page import="com.istarindia.apps.dao.*"%><%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="dist/themes/default/style.min.css" />


<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/orange.css" id="style_color">
<link rel="stylesheet" href="//static.jstree.com/3.2.1/assets/dist/themes/default/style.min.css" />

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container">
				<h1 class="pull-left">Content Admin Dashboard</h1>
				<ul class="pull-right breadcrumb">
					<li><a href="index.html">Home</a></li>
					<li><a href="">Content Admin </a></li>
					<li class="active">Dashboard</li>
				</ul>
			</div>
			<!--/container-->
		</div>
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<div class="panel panel-orange" style="margin-left: 100px; margin-right: 100px">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-tasks"></i> Create Course Syllabus
					</h3>
				</div>
				<div class="panel-body">
					<form action="<%=baseURL %>course_upload" class="sky-form" method="post" enctype="multipart/form-data">

						<fieldset>
							<section>
								<label class="label">File input</label> <label for="file" class="input input-file">
									<div class="button">
										<input name="file" type="file" id="file" onchange="this.parentNode.nextSibling.value = this.value">Browse
									</div> <input type="text" readonly="">
								</label>
							</section>
						</fieldset>
						<footer>
							<button type="submit" class="btn-u">Submit</button>
						</footer>
					</form>
				</div>
			</div>


			<div class="panel panel-orange" style="margin-left: 100px; margin-right: 100px">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-tasks"></i> Course Tree & Assignment
					</h3>
				</div>
				<div class="panel-body">
					<div class="tab-v1">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#home" data-toggle="tab" aria-expanded="true">Assign Content Creators</a></li>
							<li class=""><a href="#profile" data-toggle="tab" aria-expanded="false">Assign Content Reviewer</a></li>

						</ul>
						<div class="tab-content">
							<div class="tab-pane fade active in" id="home">
								<div class="row">
									<form class="form-inline" role="form" class="sky-form" action="<%=baseURL %>assign_creator" method="POST">
										<div class="form-group">
											<label class="sr-only" for="exampleInputEmail2">Please choose Content Creator</label> <select name=creator_id>
												<option value="0">Choose name</option>
												<option value="1">Alexandra</option>
												<option value="2">Alice</option>
												<option value="3">Anastasia</option>
												<option value="4">Avelina</option>
											</select>
										</div>
										<button type="submit" class="btn-u btn-u-default">Sign in</button>
										<div id="jstree2" class="demo">
											<ul>
												<li>All Courses <% CourseDAO dao = new CourseDAO();
														List<Course> courseList = 	dao.findAll();			
														for (Course course : courseList) {
																	%>
													<ul>

														<li><input type="checkbox" class="course_selector" name="course_<%=course.getId() %>"> 
														<label for="tall"><%=course.getCourseName() %></label>

															<ul>
																<% for (Module module : course.getModules()) { %>
																<li><input type="checkbox" class="module_selector" name="module_<%=module.getId() %>">
																<label for="tall"><%=module.getModuleName() %></label>
																	<ul>

																		<% for (Cmsession iStarSession : module.getCmsessions()) { %>
																		<li><input type="checkbox" class="iStarSession_selector" name="iStarSession_<%=iStarSession.getId() %>">
																<label for="tall"><%=iStarSession.getTitle() %></label>

																			<ul>
																				<% for (Lesson lesson : iStarSession.getLessons()) { %>
																				<li><input type="checkbox" class="iStarLesson_selector" name="iStarSession_<%=lesson.getId() %>">
																<label for="tall"><%=iStarSession.getTitle() %></label>
																			</ul></li>


																		<% } %>
																	</ul> <% } %>
															</ul></li>
													</ul> <% } %></li>
											</ul>
										</div>
									</form>
								</div>
							</div>
							<div class="tab-pane fade" id="profile">
								<div id="jstree3" class="demo">
									<ul>
										<li data-jstree='{ "opened" : true }'>All Courses <%
													for (Course course : courseList) {
																%>
											<ul>
												<li data-jstree='{ "opened" : true }'><%=course.getCourseName() %>
													<ul>
														<% for (Module module : course.getModules()) { %>
														<li data-jstree='{ "opened" : true }'><%=module.getModuleName() %>
															<ul>

																<% for (Cmsession iStarSession : module.getCmsessions()) { %>
																<li data-jstree='{ "opened" : true }'><%=iStarSession.getTitle() %>

																	<ul>
																		<% for (Lesson lesson : iStarSession.getLessons()) { %>
																		<li data-jstree='{ "opened" : true }'><%=lesson.getTitle() %> <% } %>
																	</ul></li>


																<% } %>
															</ul> <% } %>
													</ul></li>
											</ul> <% } %></li>
									</ul>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>

			<div class="container-fluid height-1000" style="padding: 0px !important"></div>
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
		<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();

			$('input[type="checkbox"]').change(function(e) {

				  var checked = $(this).prop("checked"),
				      container = $(this).parent(),
				      siblings = container.siblings();

				  container.find('input[type="checkbox"]').prop({
				    indeterminate: false,
				    checked: checked
				  });

				  function checkSiblings(el) {

				    var parent = el.parent().parent(),
				        all = true;

				    el.siblings().each(function() {
				      return all = ($(this).children('input[type="checkbox"]').prop("checked") === checked);
				    });

				    if (all && checked) {

				      parent.children('input[type="checkbox"]').prop({
				        indeterminate: false,
				        checked: checked
				      });

				      checkSiblings(parent);

				    } else if (all && !checked) {

				      parent.children('input[type="checkbox"]').prop("checked", checked);
				      parent.children('input[type="checkbox"]').prop("indeterminate", (parent.find('input[type="checkbox"]:checked').length > 0));
				      checkSiblings(parent);

				    } else {

				      el.parents("li").children('input[type="checkbox"]').prop({
				        indeterminate: true,
				        checked: false
				      });

				    }

				  }

				  checkSiblings(container);
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
