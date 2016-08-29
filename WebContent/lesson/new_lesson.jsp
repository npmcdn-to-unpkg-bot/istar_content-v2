<%@page import="com.istarindia.apps.services.LessonService"%>
<%@page import="com.istarindia.apps.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
<%@ page import="com.istarindia.apps.dao.*"%>
<%@page import="org.hibernate.Query"%>

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
<title>New Lesson | iStar CMS</title>

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
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">

<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.css">

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
		<jsp:include page="../content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Create new lesson</h1>
			</div>
		</div>
		<br>
		<div class="container-fluid height-1000"cstyle="padding: 0px !important">
			<div class="row">
				<div class="col-md-6">
					<div class="col-md-12">
						<div class="panel panel-sea">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-tasks"></i>Lesson Details
								</h3>
							</div>
							<div class="panel-body">
								<form action="/content/create_lesson" id="new_lesson_form"
									class="sky-form">
									<input type="hidden" id="selected_items" name="selected_items" />
									<fieldset>
										<section class="col col-md-6">
											<label>Title of Lesson*</label> <label class="input">
												<input value="" type="text" name="title"
												placeholder="Title of Lesson"> <b
												class="tooltip tooltip-bottom-right">The title of the
													lesson*</b>
											</label>
										</section>
										<section class="col col-md-6">
											<label>Duration of Lesson*</label> <label class="input">
												<input value="" type="number" name="duration"
												placeholder="Duration of Lesson"> <b
												class="tooltip tooltip-bottom-right">The duration of the
													lesson</b>
											</label>
										</section>
										<section class="col col-md-6">
											<label> Tags</label> <label class="input"> <input
												data-role="tagsinput" value="" type="text" name="Tags"
												class="tagcontainer" placeholder="Tags of Lesson"> <b
												class="tooltip tooltip-bottom-right">The tags of the
													lesson</b>
											</label>
										</section>
									</fieldset>
									<footer>
										<button onClick="myFunction()" type="button" class="btn-u btn"
											style="float: right">Create Lesson</button>
										<label id="err" style="color: #ee9393; font-size: large;"></label>
									</footer>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="col-md-12">
						<div class="panel panel-sea">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-tasks"></i>Select session associated with the
									lesson*
								</h3>
							</div>
							<div class="panel-body">
								<div id="course_tree">
									<ul>
										<li id="none" data-jstree='{"opened":true, "disabled":true}'>All
											Courses
											<ul>
												<%
													int course_sno = 0;
													CourseDAO dao = new CourseDAO();
													for (Course course : (List<Course>) dao.findAll()) {
														course_sno++;
												%>
												<li id="course_<%=course.getId()%>"
													data-jstree='{"opened":false, "disabled":true}'><%=course_sno%>.
													<%=course.getCourseName()%>
													<ul>
														<%
															int cmsession_sno = 0;

																String hql = "from Module as model where model.course=:sid order by order_id";
																Query q = (new IstarUserDAO()).getSession().createQuery(hql);
																q.setInteger("sid", course.getId());
																List<Module> moduleList = q.list();

																for (Module module : moduleList) {
														%>
														<li id="module_<%=module.getId()%>"
															data-jstree='{"opened":true, "disabled":true}'><%=module.getModuleName()%>
															<ul>
																<%
																	hql = "from Cmsession as model where model.module=:sid  order by order_id";
																			q = (new IstarUserDAO()).getSession().createQuery(hql);
																			q.setInteger("sid", module.getId());
																			List<Cmsession> sessionList = q.list();

																			for (Cmsession session1 : sessionList) {
																				cmsession_sno++;
																%>
																<li id="session_<%=session1.getId()%>"
																	data-jstree='{"opened":true}'><%=cmsession_sno%>.
																	<%=session1.getTitle()%></li>
																<%
																	}
																%>
															</ul></li>
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
						</div>
					</div>
				</div>
			</div>
		</div>

		<jsp:include page="../content_admin/includes/footer.jsp"></jsp:include>
	</div>
	<!-- JS Global Compulsory -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>
	<!-- JS Customization -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"
		type="text/javascript" charset="utf-8"></script>
	<script
		src="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>

	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
		type="text/javascript" charset="utf-8"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/js/plugins/validation.js"></script>
	<script type="text/javascript">
		function myFunction() {
			var selectedElmsIds = $('#course_tree').jstree("get_selected");
			if (selectedElmsIds == "") {
				event.preventDefault();
				document.getElementById("err").innerHTML = "Please select the session and try again!";
				return false;
			} else {
				document.getElementById("err").innerHTML = "";
				$('#selected_items').val(selectedElmsIds);
				$('#new_lesson_form').submit();
			}
		}

		jQuery(document).ready(function() {
			App.init();
			Validation.lessonValidation();
			$('#course_tree').jstree({
				"core" : {
					"multiple" : false,
					"themes" : {
						"variant" : "large"
					}
				},
				"checkbox" : {
					"keep_selected_style" : false,
					"three_state" : false
				},
				"plugins" : [ "checkbox" ]
			});
			$('#selected_items').val("aaaa");

		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
