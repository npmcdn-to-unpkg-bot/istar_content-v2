<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@page import="com.istarindia.cms.lessons.CMSSlide"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.services.LessonService"%><%@page
	import="com.istarindia.apps.*"%><%@page
	import="com.istarindia.apps.SlideTransition"%>
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
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

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
			<div class="container-fluid">
				<h1 class="pull-left">Review Question</h1>
			</div>
		</div>

		<div class="container-fluid height-1000" style="padding: 0px !important">
			<%
				int lesson_id = Integer.parseInt(request.getParameter("lesson_id"));
				int question_id = Integer.parseInt(request.getParameter("question_id"));
				Lesson lesson = new LessonDAO().findById(lesson_id);
				Assessment assessment = lesson.getAssessment();
			%>
			<br />
			<div class="col-md-4">
				<div class="panel panel-sea">
					<div class="panel-heading">
						<h3 class="panel-title"> <i class="fa fa-tasks"> </i>Review notes </h3>
					</div>
					<div class="panel-body">
						<form action="/content/review_lesson" name="" method="GET"
							class="sky-form">
							<fieldset>
								<input type="hidden" name="lesson_id" value=<%=lesson_id%>>
								<input type="hidden" name="question_id" value=<%=question_id%>>

								<section>
									<label class="textarea"> 
										<textarea rows="3" name="review_notes" placeholder=" Please enter text"> </textarea>
									</label>
									<div class="note">
										<strong>Note:</strong> This is where we will put in the Review Notes.
									</div>
								</section>
							</fieldset>

							<footer>
								<button type="submit" class="btn-u">Submit</button>
							</footer>
						</form>
					</div>
				</div>
				<div class="panel panel-sea">

					<div class="panel-heading">
						<h3 class="panel-title"> <i class="fa fa-comments-o"></i> Review Comments </h3>
					</div>

					<div class="panel-body">

						<div class="panel panel-profile profile">

							<%
								try {
									TaskDAO TDAO = new TaskDAO();
									Task task = new Task();
									task.setItemType("LESSON");
									task.setItemId(assessment.getLesson().getId());
									task = TDAO.findByExample(task).get(0);
									TaskLogDAO dao = new TaskLogDAO();
									TaskLog sample = new TaskLog();
									sample.setTaskId(task.getId());
									sample.setItemType("QUESTION");
									sample.setItem_id(Integer.parseInt(request.getParameter("question_id")));

									List<TaskLog> items = dao.findByExample(sample);
									for (TaskLog log : items) {

										IstarUser user = (new IstarUserDAO()).findById(log.getActorId());
							%>
							<div class="comment">
								<img
									src="https://cdn2.iconfinder.com/data/icons/lil-faces/233/lil-face-4-512.png"
									alt="">
								<div class="overflow-h">
									<strong><%=user.getName()%></strong>
									<p><%=log.getComments()%></p>

								</div>
							</div>
							<%
								}
								} catch (Exception e) {

								}
							%>

						</div>
					</div>
				</div>

			</div>
			<%
				LessonUtils utils = new LessonUtils();
				Question question = new QuestionDAO().findById(question_id);
				ArrayList<LearningObjective> items = new ArrayList<LearningObjective>(question.getLearningObjectives());
			%>
			<div class=" col-md-8 ">
				<div class="panel panel-sea">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-tasks"> </i>Question Details
						</h3>
					</div>
					<div class="panel-body">
						<form action="/content/add_question" id="sky-form4"
							class="sky-form" method="POST">
							<input type="hidden" name="assessment_id"
								value=<%=assessment.getId()%>>
							<fieldset>

								<section>
									<label>List of Learning Objectives of the question</label>
									<div class="row">
										<ul>
											<%
												for (LearningObjective obj : items) {
											%>

											<div class="col col-12">
												<li><%=obj.getTitle()%></li>
											</div>
											<%
												}
											%>
										</ul>
									</div>
								</section>
								<br />
								<%
									Set<AssessmentOption> options = question.getAssessmentOptions();
								ArrayList<AssessmentOption> option_list = new ArrayList<AssessmentOption>(options);
								%>
								<div class="row">
									<section class="col col-md-4">
										<label>Question Type</label> <label class="input"> <%=question.getQuestionType()%>
										</label>
									</section>
									<section class="col col-md-4">
										<label>Difficulty Level</label> <label class="input">
											<%=question.getDifficultyLevel()%>
										</label>
									</section>
									<section class="col col-md-4">
										<label>Depth</label> <label class="input"><%=question.getSpecifier() %></label>
									</section>
								</div>
								<section>
									<label>Question Text</label>
									<%=question.getQuestionText()%>
								</section>
								<section>
								<% String[] check = new String[]{"unchecked","unchecked","unchecked","unchecked","unchecked"};
								for(int i =0; (i<5)&&(option_list.get(i).getMarkingScheme()!=null);i++){
								if (option_list.get(i).getMarkingScheme()==1){
									check[i]="checked";
								}}%>
								
									<label class="checkbox"><input type="checkbox"
										disabled="disabled" name="answers" <%=check[0]%>><i></i>Option
										1</label><%=option_list.get(0).getText()%>
								</section>
								<section>
									<label class="checkbox"><input type="checkbox"
										disabled="disabled" name="answers" <%=check[1]%>><i></i>Option
										2</label><%=option_list.get(1).getText()%>
								</section>
								<section>
									<label class="checkbox"><input type="checkbox"
										disabled="disabled" name="answers" <%=check[2]%>><i></i>Option
										3</label><%=option_list.get(2).getText()%>
								</section>
								<section>
									<label class="checkbox"><input type="checkbox"
										disabled="disabled" name="answers" <%=check[3]%>><i></i>Option
										4</label><%=option_list.get(3).getText()%>
								</section>
								<section>
									<label class="checkbox"><input type="checkbox"
										disabled="disabled" name="answers" <%=check[4]%>><i></i>Option
										5</label><%=option_list.get(4).getText()%>
								</section>
							</fieldset>
						</form>
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
	<script
		src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"
		type="text/javascript" charset="utf-8"></script>
	<script
		src="http://rvera.github.io/image-picker/image-picker/image-picker.js"
		type="text/javascript"></script>

	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
		type="text/javascript" charset="utf-8"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>

	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
	<script type="text/javascript">
		function initTextArea() {
			try {
				$("#image-picker").imagepicker()
			} catch (err) {
				// TODO: handle exception
			}
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
							editor.on('change', function() {
								console
										.debug(tinyMCE.activeEditor
												.getContent());

								var iframeInner = $('#prv').contents().find(
										'#data_slide_paragraph').html(
										tinyMCE.activeEditor.getContent());
								tinyMCE.triggerSave();
							});
						}
					});
		}

		function initHooks() {
			$(".updateble").each(
					function(index, listItem) {
						var id = $(this).attr('id');
						$('#' + id)
								.keyup(
										function() {
											console.log('new value ->'
													+ '#data_' + id);
											var iframeInner = $('#prv')
													.contents().find(
															'#data_' + id)
													.html($('#' + id).val());

										});
					});

			$('#image-picker').on(
					'change',
					function() {
						var id = $(this).find(":checked").attr('id');
						$('#prv').contents().find('#data_image_url').attr(
								"src", $('#' + id).data('img-src'));

					});
		}
		$(document).ready(function() {
			initTextArea();
			initHooks();

			$("#slide_paragraph").on('change keyup paste', function() {
				console.log('sss');
			});
		});
	</script>
</body>
</html>
