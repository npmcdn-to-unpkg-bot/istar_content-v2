<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.ocpsoft.prettytime.PrettyTime"%>
<%@page import="java.util.*"%> 
<%@page import="java.text.*"%>
<%@page import="com.istarindia.apps.*"%>
<%@page import="com.istarindia.apps.services.*"%>
<%@page import="com.istarindia.apps.cmsutils.*"%>
<%@page import="com.istarindia.apps.dao.*"%>

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
				LessonUtils lessonUtils = new LessonUtils();
				Assessment assessment = lesson.getAssessment();

				List<HashMap<String, String>> logs = lessonUtils.getQuestionComments(question_id);

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
				
				<% if(!logs.isEmpty()) { %>
				<div class="panel panel-sea">
					<div class="panel-heading">
						<h3 class="panel-title"> <i class="fa fa-tasks"></i> View/add review comments for the slide </h3>
					</div>
					<div class="panel-body " data-mcs-theme="minimal-dark" >
						<%
							for(HashMap<String, String> log : logs){
								try {
									SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
									PrettyTime p = new PrettyTime();
									String typeStatus = log.get("changed_status").toString();
									String desc = log.get("comment").toString();
									if (desc.length() > 100) {
										desc = desc.substring(0, 250);
									}
									String name = log.get("actor_name").toString();
						%>

						<div class="alert-blocks alert-blocks-pending alert-dismissable">
							<img  src="<%=baseURL%>assets/img/typo.png" alt="X">
							<div>
								<strong class="color-yellow"><%=name %>
								<small class="pull-right"><em><%=p.format(ft.parse(log.get("created_at").toString()))%></em></small></strong>
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
				<% } %>
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
											<li>
													<%=obj.getTitle()%>
											</li>
											<%
												}
											%>
										</ul>
									</div>
								</section>
								
								<div class="row">
									<section class="col col-md-6">
										<label>Question Type</label> <label class="input"> 
										<input readonly="readonly" value="<%=question.getQuestionType()%>" >
												<b class="tooltip tooltip-bottom-right">Type of the question</b>
										</label>
									</section>
									<section class="col col-md-6">
										<label>Difficulty Level</label> <label class="input">
											<input readonly="readonly" value="<%=question.getDifficultyLevel()%>" >
												<b class="tooltip tooltip-bottom-right">Difficulty level of the question</b>
										</label>
									</section>
									<%-- <section class="col col-md-4">
										<label>Depth</label> <label class="input"> <input
											readonly="readonly" value="<%=question.getSpecifier()%>">
											<b class="tooltip tooltip-bottom-right">Difficulty level
												of the question</b>
										</label>
									</section> --%>
								</div>
								<section>
									<label>Question Text</label><label class="input">
										<input readonly="readonly" value="<%=question.getQuestionText()%>" >
												<b class="tooltip tooltip-bottom-right">The question text</b>
									</label>
								</section>
								<br/>
								<section>
									<label>Option list:</label>
								</section>
								<%
								AssessmentOptionService service = new AssessmentOptionService();
								ArrayList<Integer> options_list = new ArrayList<Integer>();
								options_list = service.getOptionIdsForQuestion(question_id);
								AssessmentOptionDAO dao = new AssessmentOptionDAO();
								String[] check = new String[]{"unchecked","unchecked","unchecked","unchecked","unchecked"};
								for(int i=0; i < options_list.size() && dao.findById(options_list.get(i)).getText().length()>0 ; i++){
								if (dao.findById(options_list.get(i)).getMarkingScheme()!=null){
									check[i]="checked";
								}%>
								<section>
									<label class="checkbox"><input type="checkbox" disabled="disabled" name="answers" <%=check[i]%>><i></i>
										<%=dao.findById(options_list.get(i)).getText()%>
									</label>
								</section>
								<%
								}
								%>
								
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
