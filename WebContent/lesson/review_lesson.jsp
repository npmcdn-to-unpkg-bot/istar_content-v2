<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.services.LessonService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@ page import="java.util.*"%>
<%@ page import="com.istarindia.apps.dao.*"%>

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
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">

<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.css">

<!-- CSS Theme -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/default.css"
	id="style_color">
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
				<h1 class="pull-left">Review Lesson</h1>
				<!-- <ul class="pull-right breadcrumb">
					<li><a href="/">Home</a></li>
					<li><a href="">Content Admin </a></li>
					<li class="active">Edit Lesson</li>
				</ul> -->
			</div>
			<%
				Lesson lesson = (Lesson) request.getAttribute("lesson");
			%>
		</div>
		<div class="container-fluid height-1000"
			style="padding: 0px !important">
			<div class="row">
				<div class="col-md-6">
					<form action="/content/review_lesson" id="sky-form4"
						class="sky-form">
						<input type="hidden" name="lesson_id" value="<%=lesson.getId()%>" />
						<input type="hidden" name="cmsession_id"
							value="<%=lesson.getCmsession().getId()%>" />
						<fieldset>

							<section>
								<label>Title of Lesson</label> <label class="input"> <input
									readonly="readonly" value="<%=lesson.getTitle()%>" type="text"
									name="title" placeholder="Title of Lesson"> <b
									class="tooltip tooltip-bottom-right">The title of the
										lesson</b>
								</label>
							</section>

							<section>
								<label>Duration of Lesson</label> <label class="input">
									<input readonly="readonly" value="<%=lesson.getDuration()%>"
									type="number" name="duration" placeholder="Duration of Lesson">
									<b class="tooltip tooltip-bottom-right">The duration of the
										lesson</b>
								</label>
							</section>
							<section>
								<label> Tags</label> <label class="input"> <input
									readonly="readonly" data-role="tagsinput"
									value="<%=lesson.getTags()%>" type="text" name="Tags"
									class="tagcontainer" placeholder="Tags of Lesson"> <b
									class="tooltip tooltip-bottom-right">The tags of the lesson</b>
								</label>
							</section>

							<section>
								<label> Review Comments</label> <label class="input"> <textarea
										rows="3" name="review_notes" placeholder=" Please enter text"
										id="teacher_notes"></textarea>
								</label>
							</section>
						</fieldset>

						<footer>
							<button type="submit" class="btn-u">Add Review Comments</button>
						</footer>
					</form>


				</div>


				<div class="col-md-6">
					<%
						if (LessonService.isEmptyLesson(lesson)) {
					%>
					<div class="alert alert-warning fade in text-center">
						<h4>Please Create a Presentation/Assessment/Game</h4>
						<p>
							<%-- 							<a class="btn-u btn-u-xs btn-u-red" href="/content/create_ppt?lesson_id=<%=lesson.getId() %>">Create a Presentation</a> <a class="btn-u btn-u-xs btn-u-sea" href="#">Create a Assessment</a> <a class="btn-u btn-u-xs btn-u-orange" href="#" style="margin-top: 20px">Create a Game</a>
 --%>
						</p>
					</div>
					<%
						} else {
							LessonUtils utils = new LessonUtils();
							out.println(utils.getReviewForm(lesson));

							// Create Two block 
							// Top one has to be add new SLide  
							//Botton one is list of slides
						}
					%>

					<div class="col-sm-5">
						<div class="panel panel-profile profile">
							<div class="panel-heading overflow-h">
								<h2 class="panel-title heading-sm pull-left">
									<i class="fa fa-comments-o"></i> Review Comments
								</h2>
								
							</div>
							<div id="scrollbar4"
								class="panel-body no-padding mCustomScrollbar"
								data-mcs-theme="minimal-dark">
								
								<% 
								TaskLogDAO dao = new TaskLogDAO();
								List<TaskLog> items = dao.findByProperty("taskId", Integer.parseInt(request.getParameter("task_id")));
								for(TaskLog log : items) {
								
									IstarUser user = (new IstarUserDAO()).findById(log.getActorId());
									
									%>
								<div class="comment">
									<img src="https://cdn2.iconfinder.com/data/icons/lil-faces/233/lil-face-4-512.png" alt="">
									<div class="overflow-h">
										<strong><%=user.getName() %></strong>
										<p><%=log.getComments() %></p>
										
									</div>
								</div>
								<% } %>

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
	<!-- JS Customization -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"
		type="text/javascript" charset="utf-8"></script>

	<script
		src="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/js/plugins/validation.js"></script>

	<script type="text/javascript">
		jQuery(document)
				.ready(
						function() {
							App.init();
							Validation.lessonValidation();
							tinymce
									.init({
										selector : 'textarea',
										height : 100,
										plugins : [
												'advlist autolink lists link image charmap print preview anchor',
												'searchreplace visualblocks code fullscreen',
												'insertdatetime media table contextmenu paste code' ],
										toolbar : 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
										content_css : [
												'//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
												'//www.tinymce.com/css/codepen.min.css' ],
										setup : function(editor) {
											editor
													.on(
															'change',
															function() {
																console
																		.debug(tinyMCE.activeEditor
																				.getContent());

																var iframeInner = $(
																		'#prv')
																		.contents()
																		.find(
																				'#data_slide_paragraph')
																		.html(
																				tinyMCE.activeEditor
																						.getContent());
																tinyMCE
																		.triggerSave();
															});
										}
									});
						});

		function openWin(url) {
			myWindow = window.open(url, "", "width=412, height=659"); // Opens a new window

			return false;
		}
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
