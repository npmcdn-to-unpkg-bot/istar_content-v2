<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.services.LessonService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
<%@ page import="com.istarindia.apps.dao.*"%>

<%
	String url = request.getRequestURL().toString();
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
			<div class="container-fluid ">
				<h1 class="pull-left">Review Lesson</h1>
			</div>
			<%
				Lesson lesson = (Lesson) request.getAttribute("lesson");
			%>
		</div>
		<br />
		<div class="container-fluid height-1000"
			style="padding: 0px !important">

			<div class="col-md-4">

				<div class="col-md-12">
					<div class=" col-md-12 ">
						<div class="panel panel-sea">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-tasks"></i>Lesson Details
								</h3>
							</div>
							
							<div class="panel-body">
								<form action="/content/review_lesson" id="review-lesson-form"
									class="sky-form">
									<input type="hidden" name="lesson_id" value="<%=lesson.getId()%>" /> 
									<input type="hidden" name="cmsession_id" value="<%=lesson.getCmsession().getId()%>" />
									<input type="hidden" id="status" name="review" value="none" />
									<fieldset>
										<section>
											<label>Title</label> <label class="input">
												<input readonly="readonly" value="<%=lesson.getTitle()%>"
												type="text" name="title" placeholder="Title of Lesson">
												<b class="tooltip tooltip-bottom-right">The title of the lesson</b>
											</label>
										</section>

										<section>
											<label>Duration</label> 
											<label class="input">
												<input readonly="readonly" value="<%=lesson.getDuration()%>" type="number" name="duration" placeholder="Duration of Lesson"> 
												<b class="tooltip tooltip-bottom-right">The duration of the lesson</b>
											</label>
										</section>
										
										<section>
											<label>Tags</label> 
											<label class="input"> 
												<input readonly="readonly" data-role="tagsinput" value="<%=lesson.getTags()%>" type="text" name="Tags" class="tagcontainer" placeholder="Tags of Lesson"> 
												<b class="tooltip tooltip-bottom-right">The tags of the lesson</b>
											</label>
										</section>
										
										<% try { 
											if(lesson.getPresentaion()!=null) {
												int mobile_theme_id =  Integer.parseInt(lesson.getLesson_theme());
												String mobile_theme_name = new UiThemeDAO().findById(mobile_theme_id).getName();
										%>
										<section>
											<label>Mobile Theme</label> 
											<label class="input"> 
												<input readonly="readonly"  value="<%=mobile_theme_name%>" type="text" name="mobile_theme"  placeholder="Mobile theme"> 
												<b class="tooltip tooltip-bottom-right">Mobile theme of the lesson</b>
											</label>
										</section>
										<% } } catch (Exception e) { } %>
										
										<% try { 
											if(lesson.getPresentaion()!=null) {
												int desktop_theme_id =  Integer.parseInt(lesson.getLesson_theme_desktop());
												String desktop_theme_name = new UiThemeDAO().findById(desktop_theme_id).getName();
										%>
										<section>
											<label>Desktop Theme</label> 
											<label class="input"> 
												<input readonly="readonly"  value="<%=desktop_theme_name%>" type="text" name="desktop_theme"  placeholder="Desktop theme"> 
												<b class="tooltip tooltip-bottom-right">Desktop theme of the lesson</b>
											</label>
										</section>
										<% } } catch (Exception e) { } %>
										
										<% try { %>
										<section>
											<label>Subject</label> <label class="input">
												<input readonly="readonly" value="<%=lesson.getLesson_subject()%>"
												type="text" name="title" placeholder="Subject of Lesson">
												<b class="tooltip tooltip-bottom-right">The subject of the lesson</b>
											</label>
										</section>
										<% } catch (Exception e) { } %>
										
										<%
											LessonUtils lessonUtils = new LessonUtils();
											ArrayList<LearningObjective> lesson_lo_list = lessonUtils.getSelectedLOsOftheLesson(lesson.getId());
										%>
										<section>
											<label>List of Selected Learning Objectives</label>
											<div class="row">
				
												<%
													if (!lesson_lo_list.isEmpty()) {
														for (LearningObjective lesson_lo : lesson_lo_list) {
												%>
				
												<div class="col col-12">
													<label class="checkbox"><input type="checkbox"
														name="learningObjectives" checked="checked" disabled="disabled"
														value="<%=lesson_lo.getId()%>"> <i></i><%=lesson_lo.getTitle()%></label>
												</div>
				
												<% } } else { %>
				
												<div class="col col-12">
													<label><i class="fa fa-exclamation"></i> Learning objectives were not chosen for the lesson! </label>
												</div>
				
												<% } %>
											</div>
										</section>
										
										<section>
											<label> Review Comments</label> <label class="input">
												<textarea rows="3" name="review_notes" placeholder=" Please enter text" id="review_notes"></textarea>
											</label>
										</section>
									</fieldset>
									
									<footer>
										<div class="row">
											<section class=" col-md-12">
												<button id="disapprove-slide-btn" style="float: left" type="button" class="btn-u-red col-xs-4 btn-u" data-toggle="modal"  data-value="DIS_APPROVED" data-target="#confirm-lesson-review-modal"><i class="fa fa-ban" aria-hidden="true"></i> &nbsp; Disapprove</button>
												
												<% if(lesson.getPresentaion() != null) { %>
													<div class="btn-group col-xs-4 ">  
														<button style="width: 100%;" type="button" class="btn btn-u-blue dropdown-toggle" data-toggle="dropdown" aria-expanded="true"> Preview &nbsp; <i class="fa fa-play" aria-hidden="true"></i> </button>  
															<ul class="dropdown-menu" role="menu">
																<li><a onclick="openWin(&quot;/content/lesson/preview.jsp?ppt_id=<%=lesson.getPresentaion().getId() %>&quot;)" href="#">Mobile Preview &nbsp; <i class="fa fa-mobile" aria-hidden="true"></i></a></li>  
																<li><a target="_blank" href="/content/lesson/preview_desktop.jsp?ppt_id=<%=lesson.getPresentaion().getId() %>">Speaker Preview &nbsp; <i class="fa fa-desktop" aria-hidden="true"></i> </a></li> 
															</ul> 
													</div>
												<% } %>
												
												<button id="approved-slide-btn" style="float: right" type="button" class="btn-u btn-u-green col-xs-4 btn-u"  data-toggle="modal" data-value="APPROVED" data-target="#confirm-lesson-review-modal">Approve &nbsp; <i class="fa fa-check" aria-hidden="true"></i></button>
											</section>
										</div>
									</footer>
									
									
								</form>
							</div>
							
							<div class="modal fade" id="confirm-lesson-review-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">Confirm Submit</div>
										<div class="modal-body">Are you sure you want to continue?</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
											<button id="confirm-lesson-review-btn" type="submit" class="btn btn-success success">Confirm</button>
										</div>
									</div>
								</div>
							</div>
							
						</div>

					</div>
				</div>

				<% if(lesson.getPresentaion() != null) { %>
				
				 <div class="col-sm-12">
					<div class=" col-md-12 ">
						<div class="panel panel-sea profile">
							<div class="panel-heading overflow-h">
								<h3>
									<i class="fa fa-comments-o">Review Comments</i>
								</h3>
							</div>

							<div id="scrollbar4" style="height: auto !important" class="panel-body no-padding mCustomScrollbar" data-mcs-theme="minimal-dark">
								<div class="comment" style="    font-size: larger; padding: 5px;  padding-left: 29px;  color: cadetblue;  background-color: beige;">
									<label>Lesson comments:</label>
								</div>		
								<%
									//lesson comments-
									SlideDAO slideDAO = new SlideDAO();
									TaskDAO TDAO = new TaskDAO();
									Task task = new Task();
									TaskLogDAO dao = new TaskLogDAO();
									try {
										TaskLog sample1 = new TaskLog();
										sample1.setTaskId(task.getId());
										sample1.setItemType("LESSON");
										sample1.setItem_id(lesson.getId());
										sample1.setChangedStatus("COMPLETED");
										List<TaskLog> items = dao.findByExample(sample1);
										for (TaskLog log : items) {
											IstarUser user = (new IstarUserDAO()).findById(log.getActorId());
											if( !(log.getComments().trim().isEmpty()) ) {
								%>
					
								<div class="comment" style="padding-bottom: 15px;">
								<div class="overflow-h">
									<div class="row">
										<div class="col-sm-12">
											<div class="col-md-3">
												<strong><%=user.getName()%> : </strong> 
											</div>
											<div class="col-md-9">
												<%=log.getComments()%>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<%
										}
									}
								} catch (Exception e) {
								}
							%>
								<div class="comment" style="    font-size: larger; padding: 5px;  padding-left: 29px;  color: cadetblue;  background-color: beige;">
									<label>Slide comments:</label>
								</div>
							<%
								//SLide comments-
								try {
									task = lesson.getTask();
									TaskLog sample = new TaskLog();
									sample.setTaskId(task.getId());
									sample.setItemType("SLIDE");
									List<TaskLog> items = dao.findByExample(sample);
									for (TaskLog log : items) {
										IstarUser user = (new IstarUserDAO()).findById(log.getActorId());
										if(!(log.getComments().trim().isEmpty()) ) {
							%>
							<div class="comment" style="padding-bottom: 15px;">
								<div class="overflow-h">
									<div class="row">
										<div class="col-sm-12">
											<div class="col-md-3">
												<strong><%=user.getName()%> : </strong> 
											</div>
											<div class="col-md-9">
												<%=log.getComments()%>
											</div>
										</div>
									</div>
									<div style="float: right;">
									<% if(slideDAO.findById(log.getItem_id()) != null ) { %>
										<a class="" target="_blank" href="/content/review_slide.jsp?ppt_id=<%=lesson.getPresentaion().getId() %>&slide_id=<%=log.getItem_id() %>">Review slide</a>
									<% } else { %>
										Deleted slide
									<% } %>
									</div>
								</div>
							</div>
							
							<%
										}
									}
								} catch (Exception e) {
								}
							%>

						</div>

						</div>
					</div>
				</div> 
				
				<% } else if(lesson.getAssessment() != null) { %>
				
				 <div class="col-sm-12">
					<div class=" col-md-12 ">
						<div class="panel panel-sea profile">
							<div class="panel-heading overflow-h">
								<h3>
									<i class="fa fa-comments-o">Review Comments</i>
								</h3>
							</div>

							<div id="scrollbar4" style="height: auto !important" class="panel-body no-padding mCustomScrollbar" data-mcs-theme="minimal-dark">
								<div class="comment" style="    font-size: larger; padding: 5px;  padding-left: 29px;  color: cadetblue;  background-color: beige;">
									<label>Lesson comments:</label>
								</div>		
								<%
									TaskDAO TDAO = new TaskDAO();
									Task task = new Task();
									TaskLogDAO dao = new TaskLogDAO();
									try {
										TaskLog sample1 = new TaskLog();
										sample1.setTaskId(task.getId());
										sample1.setItemType("LESSON");
										sample1.setItem_id(lesson.getId());
										sample1.setChangedStatus("COMPLETED");
										List<TaskLog> items = dao.findByExample(sample1);
										for (TaskLog log : items) {
											IstarUser user = (new IstarUserDAO()).findById(log.getActorId());
											if( !(log.getComments().trim().isEmpty()) ) {
								%>
					
								<div class="comment" style="padding-bottom: 15px;">
								<div class="overflow-h">
									<div class="row">
										<div class="col-sm-12">
											<div class="col-md-3">
												<strong><%=user.getName()%> : </strong> 
											</div>
											<div class="col-md-9">
												<%=log.getComments()%>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<%
										}
									}
								} catch (Exception e) {
								}
							%>
								<div class="comment" style="    font-size: larger; padding: 5px;  padding-left: 29px;  color: cadetblue;  background-color: beige;">
									<label>Slide comments:</label>
								</div>
							<%
								//Question comments-
								try {
									task = lesson.getTask();
									TaskLog sample = new TaskLog();
									QuestionDAO questionDAO = new QuestionDAO();
									sample.setTaskId(task.getId());
									sample.setItemType("QUESTION");
									List<TaskLog> items = dao.findByExample(sample);
									for (TaskLog log : items) {
										IstarUser user = (new IstarUserDAO()).findById(log.getActorId());
										if(!(log.getComments().trim().isEmpty()) ) {
							%>
							<div class="comment" style="padding-bottom: 15px;">
								<div class="overflow-h">
									<div class="row">
										<div class="col-sm-12">
											<div class="col-md-3">
												<strong><%=user.getName()%> : </strong> 
											</div>
											<div class="col-md-9">
												<%=log.getComments()%>
											</div>
										</div>
									</div>
									<div style="float: right;">
									<% if(questionDAO.findById(log.getItem_id()) != null ) { %>
										<a class="" target="_blank"href="/content/lesson/review_question.jsp?lesson_id=<%=lesson.getId() %>&question_id=<%=log.getItem_id() %>">Review question</a>
									<% } else { %>
										Deleted slide
									<% } %>
									</div>
								</div>
							</div>
							
							<%
										}
									}
								} catch (Exception e) {
								}
							%>

						</div>

						</div>
					</div>
				</div> 
				<% } %>
			</div>

			<div class="col-md-8">
				
				<% if (LessonService.isEmptyLesson(lesson)) { %>
				
					<div class="alert alert-warning fade in text-center">
						<h4>Lesson is empty!!</h4>
					</div>
					
				<%
					} else {
						LessonUtils utils = new LessonUtils();
						out.println(utils.getReviewForm(lesson));
					}
				%>
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
	<script type="text/javascript" src="<%=baseURL%>tinymce/4/tinymce.min.js"></script>
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

							//On modal activation; update "status" input field with the value from respective button clicked (approved/disapproved)
							$('#confirm-lesson-review-modal').on( 'show.bs.modal', function(e) {
									var $invoker = $(e.relatedTarget);
									var status = $invoker.data("value");
									$('#status').attr("value", status);
							});

							//On confirm button (in the modal) click; submit the form
							$('#confirm-lesson-review-btn').click(function() {
									$( "#review-lesson-form" ).submit();
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
