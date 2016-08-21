<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@page import="com.istarindia.apps.Themes"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>

<%@page import="com.istarindia.apps.services.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
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
	href="assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet"
	href="assets/css/headers/header-default.css">
<link rel="stylesheet"
	href="assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="assets/plugins/animate.css">
<link rel="stylesheet"
	href="assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet"
	href="assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/business.style.css">
<link rel="stylesheet" href="assets/css/global.css">
<link rel="stylesheet" href="assets/css/pages/profile.css">

<link rel="stylesheet"
	href="assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"
	href="assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet"
	href="assets/plugins/tagz/bootstrap-tagsinput.css">

<!-- CSS Theme -->
<link rel="stylesheet"
	href="assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet"
	href="assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="../content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Assessment</h1>
				<!-- <ul class="pull-right breadcrumb">
					<li><a href="/">Home</a></li>
					<li><a href="">Content Admin </a></li>
					<li class="active">Edit Lesson</li>
				</ul> -->
			</div>

		</div>
		<BR />
		<div class="container-fluid height-1000"
			style="padding: 0px !important">

			<%
				int assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
				Assessment assessment = new AssessmentDAO().findById(assessment_id);
				Lesson lesson = assessment.getLesson();
			%>

			<div class="col-md-4">
				<div class="col-md-12">
					<div class=" col-md-12 ">
						<div class="panel panel-sea">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-tasks"></i>Assessment Details
								</h3>
							</div>
							<div class="panel-body">
								<form id="sky-form4" class="sky-form">


									<fieldset>
										<section>
											<label>Title: </label>
											<%=lesson.getTitle()%>

										</section>

										<section>
											<label>Duration:</label>
											<%=lesson.getDuration()%>
											Minutes
										</section>

										<section>
											<label>List of Learning Objectives:</label>
											<div class="row">
												<%
													for (LearningObjective obj : lesson.getLearningObjectives()) {
												%>
												<div class="col col-12">
													<li><%=obj.getTitle()%></li>
												</div>
												<%
													}
												%>
											</div>
										</section>
									</fieldset>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-8">
				<%
					IstarUser user = (IstarUser) request.getSession().getAttribute("user");
					ReportService service = new ReportService();
					if (service.getAssessmentReportOfUser(user.getId(), assessment) == null) {
				%>
				<div class="alert alert-warning fade in text-center">
					<h4>Proceed to start the assessment</h4>
					<p>
						<a class="btn-u btn-u-xs btn-u-sea"
							href="/content/assessment_play?assessment_id=<%=assessment_id%>">Begin</a>
					</p>
				</div>
				<%
					} else {
						int question_id = 0;
						AssessmentQuestionService assessmentQuestionService = new AssessmentQuestionService();
						Report report = service.getAssessmentReportOfUser(user.getId(), assessment);
						if(request.getParameterMap().containsKey("question_id")){
							question_id = Integer.parseInt(request.getAttribute("question_id").toString());
						}
						else {
							question_id = report.getProgress();
						}
						if (question_id > 0) {
				%>
				<div class="col-md-12">
					<div class=" col-md-12 ">
						<div class="panel panel-sea">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-tasks"></i>Questions
								</h3>
							</div>
							<div class="panel-body">
								<%
									//int question_id = Integer.parseInt(request.getParameter("question_id"));
											Question question = new QuestionDAO().findById(question_id);
								%>
								<form action="/content/assessment_play" id="sky-form4" class="sky-form">
									<input type="hidden" name="assessment_id" value="<%=assessment_id%>" /> 
									<input type="hidden" name="question_id" value="<%=question_id%>" /> 
									<input type="hidden" name="report_id" value="<%=report.getId()%>" />

									<fieldset>
										<section>
											<label>Type: </label>
											<%=question.getQuestionType()%>
										</section>

										<section>
											<label>Question: </label>
											<%=question.getQuestionText()%>
										</section>

										<section>
											<label>List of Options:</label>
											<div class="row">
												<%
													AssessmentOptionService assessmentOptionService = new AssessmentOptionService();
															ArrayList<Integer> options_list = new ArrayList<Integer>();
															options_list = assessmentOptionService.getOptionIdsForQuestion(question_id);
															AssessmentOptionDAO dao = new AssessmentOptionDAO();
															for (int i = 0; i < options_list.size(); i++) {
												%>
												<div class="col col-12">
													<label class="checkbox"><input type="checkbox"
														name="selected"
														value="<%=dao.findById(options_list.get(i)).getId()%>">
														<i></i><%=dao.findById(options_list.get(i)).getText()%></label>
												</div>
												<%
													}
												%>
											</div>
										</section>
									</fieldset>
									<footer>
										<button type="submit" class="btn-u">Submit</button>
									</footer>
								</form>

							</div>
						</div>
					</div>

				</div>
				<%
					} else {
				%>

				<div class="col-md-8">
					<div class=" col-md-12 ">
						<div class="panel panel-sea">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-tasks"></i>Congratulations!
								</h3>
							</div>
							<div class="panel-body">
								<label>Final score: </label>
								<%=report.getScore()%>
							</div>
						</div>
					</div>

				</div>
				<%
					}
					}
				%>
			</div>


		</div>
		<jsp:include page="../content_admin/includes/footer.jsp"></jsp:include>
	</div>


	<!-- JS Global Compulsory -->
	<script type="text/javascript"
		src="assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript"
		src="assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript"
		src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>
	<script type="text/javascript"
		src="assets/plugins/back-to-top.js"></script>
	<script type="text/javascript"
		src="assets/plugins/smoothScroll.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript"
		src="assets/plugins/jstree/jstree.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"
		type="text/javascript" charset="utf-8"></script>

	<script
		src="assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="assets/js/custom.js"></script>
	<script src="assets/plugins/tagz/bootstrap-tagsinput.js"
		type="text/javascript" charset="utf-8"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="assets/js/app.js"></script>
	<script type="text/javascript"
		src="assets/js/plugins/validation.js"></script>

	<script type="text/javascript">
		
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
