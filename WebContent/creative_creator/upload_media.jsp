<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.dao.*"%><%@page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<title>Upload media | iStar CMS</title>

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

<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-skins/dark.css">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/global.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container">
				<h1 class="pull-left">Upload media</h1>
				<ul class="pull-right breadcrumb">
					<li><a href="upload_media.jsp">Upload</a></li>
					<li><a href="dashboard.jsp">Dashboard</a></li>
				</ul>
			</div>
			<!--/container-->
			<div class="container content">
				<div class="row">
					<!-- Begin Sidebar Menu -->

					<!-- End Sidebar Menu -->

					<!-- Begin Content -->
					<div class="col-md-4">
						<!-- General Unify Forms -->
						<form action="#" class="sky-form">
							<header>Select Folder</header>

							<fieldset>
								<section>
									<label class="label">Text input</label> <label class="input">
										<input type="text">
									</label>
								</section>

								<section>
									<label class="label">File input</label> <label for="file"
										class="input input-file">
										<div class="button">
											<input type="file" id="file"
												onchange="this.parentNode.nextSibling.value = this.value">Browse
										</div> <input type="text" readonly="">
									</label>
								</section>
							</fieldset>

						</form>
						<div class="margin-bottom-60"></div>

					</div>
					<!-- End Content -->
					<div class="col-md-4">
						<!-- General Unify Forms -->
						<form action="#" class="sky-form">
							<header>Select Session</header>

							<fieldset>

								<div class="panel panel-grey margin-bottom-40">
									<div class="panel-body">
										<input type="hidden" name="selected_items" />


										<div id="html1">
											<ul>
												<li id="none" data-jstree='{"opened":true}'>All Courses
													<ul>
														<%
															CourseDAO dao = new CourseDAO();
															for (Course course : (List<Course>)dao.findAll()) {
														%>
														<li id="course_<%=course.getId()%>"
															data-jstree='{"opened":true}'><%=course.getCourseName()%>
															<ul>
																<%
																	for (Module module : course.getModules()) {
																%>
																<li id="module_<%=module.getId()%>"
																	data-jstree='{"opened":true}'><%=module.getModuleName()%>

																	<%
																		for (Cmsession session1 : module.getCmsessions()) {
																	%>
																<li id="session_<%=session1.getId()%>"
																	data-jstree='{"opened":true}'><%=session1.getTitle()%>

																	<%
																		}
																	%></li>
																<%
																	}
																%>
															</ul></li>
														<%
															}
														%>
													</ul>
												</li>
											</ul>
										</div>

									</div>
							</fieldset>

						</form>
						<!-- General Unify Forms -->

						<div class="margin-bottom-60"></div>

					</div>
					<div class="col-md-4">
						<!-- General Unify Forms -->
						<form action="#" class="sky-form">
							<header>Media details</header>

							<fieldset>
								<section>
									<label class="label">Title</label> <label class="input">
										<input type="text">
									</label>
								</section>

								<section>
									<label class="label">Tags</label> <label class="input">
										<input type="text">
									</label>
								</section>

								<section>
									<label class="label">File input</label> <label for="file"
										class="input input-file">
										<div class="button">
											<input type="file" id="file"
												onchange="this.parentNode.nextSibling.value = this.value">Browse
										</div> <input type="text" readonly="">
									</label>
								</section>
							</fieldset>

							<footer>
								<button type="submit" class="btn-u">Upload</button>
							</footer>
						</form>
						<!-- General Unify Forms -->

						<div class="margin-bottom-60"></div>

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
	<script type="text/javascript" src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>

	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/plugins/style-switcher.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();
			StyleSwitcher.initStyleSwitcher();
			$('#html1').jstree({
				"core" : {
					"themes" : {
						"variant" : "large"
					}
				},
				"checkbox" : {
					"keep_selected_style" : false
				},
				"plugins" : [ "checkbox" ]
			});
			var selectedElmsIds = $('#html1').jstree("get_selected");
		});
	
</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
