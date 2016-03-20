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
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

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

<body  ng-app="">

	<div class="wrapper">
		<jsp:include page="content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Add/Edit Slide</h1>
			</div>
		</div>
		<form action="/content/create_slide" name="" method="GET"
			data-parsley-validate="" novalidate="" class="sky-form">
			<%
				Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
				LessonUtils utils = new LessonUtils();
				CMSSlide slide = new CMSSlide();
				Boolean newSlide = true;
				if (request.getParameterMap().containsKey("slide_id")) {
					System.err.println("Its an old SLide ");
					newSlide = false;
					SlideDAO dao = new SlideDAO();
					slide = (new LessonUtils())
							.convertSlide(dao.findById(Integer.parseInt(request.getParameter("slide_id"))));
			%>
			<input name="is_edit" value="true" type="hidden"> <input
				name="slide_id" value="<%=request.getParameter("slide_id")%>"
				type="hidden">
			<%
				} else {
					System.err.println("Its an new  SLide ");
					newSlide = true;
					slide.setTemplateName(request.getParameter("slide_type"));
			%>
			<input name="is_edit" value="false" type="hidden">
			<%
				}
			%>
			<div class="container-fluid" style="padding: 0px !important">
				<input type="hidden" name="template"
					value="<%=slide.getTemplateName()%>"> <input
					type="hidden" name="ppt_id"
					value="<%=request.getParameter("ppt_id")%>">
				<div class="row">
					<div class="col-md-5">
						<%=utils.getEditProfileEdit(slide, ppt, newSlide)%>
						<fieldset>
							<section>
								<label class="label">Select Slide Transition</label> <label
									class="select"> <select name="slideTransition"
									value="<%=slide.getTransition()%>">
										<%
											for (String type : SlideTransition.SlideTransitionTypes) {

												if (type.equalsIgnoreCase(slide.getTransition())) {
										%>
										<option selected="selected" value="<%=type%>"><%=type%></option>
										<%
											} else {
										%>
										<option value="<%=type%>"><%=type%></option>
										<%
											}
											}
										%>
								</select> <i></i>
								</label>
							</section>
							<section>
								<label class="label">Select Background Transition</label> <label
									class="select"> <select name="backgroundTransition"
									value="<%=slide.getBackgroundTransition()%>"%>>
										<%
 	for (String type : SlideTransition.BackgroundTransition) {
 		if (type.equalsIgnoreCase(slide.getBackgroundTransition())) {
 %>
										<option selected="selected" value="<%=type%>"><%=type%></option>
										<%
											} else {
										%>
										<option value="<%=type%>"><%=type%></option>
										<%
											}
											}
										%>
								</select> <i></i>
								</label>
							</section>
							<section>
								<label class="label">Select Slide Background color</label> <label
									class="select"> <input type="color" id="slide_color"
									name="backgroundColor" value="<%=slide.getBackground()%>">
								</label>
							</section>
						</fieldset>


						<fieldset>
							<section>
								<label class="label">Teacher Notes</label> 
								<label class="textarea"> <textarea rows="3" name="teacher_notes" 
								data-parsley-required="true" data-parsley-length="[5,250]" 
								data-parsley-required-message="Please provide teacher notes" 
								data-parsley-length-message="It should be 5-250 characters long">
								<%=slide.getTeacherNotes()%> </textarea>

								</label>
								<div class="note">
									<strong>Note:</strong> This is where we will put in the
									paragraph.
								</div>
							</section>
						</fieldset>
						<fieldset>
							<section>
								<label class="label">Student Notes</label> 
								<label class="textarea"> <textarea rows="3"name="student_notes" 
								data-parsley-required="true" data-parsley-length="[5,250]" 
								data-parsley-required-message="Please provide student notes" 
								data-parsley-length-message="It should be 5-250 characters long"> 
								<%=slide.getStudentNotes()%></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is where we will put in the
									paragraph.
								</div>
							</section>
						</fieldset>

						<footer>
							<button type="submit" class="btn-u">Submit</button>
						</footer>
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
								
								try {
								TaskDAO TDAO = new  TaskDAO();
								Task task = new Task();
								task.setItemType("LESSON");
								task.setItemId(ppt.getLesson().getId());
								task = TDAO.findByExample(task).get(0);
								TaskLogDAO dao = new TaskLogDAO();
								TaskLog sample  = new TaskLog();
								sample.setTaskId(task.getId());
								sample.setItemType("SLIDE");
								sample.setItem_id(Integer.parseInt(request.getParameter("slide_id")));
								
								List<TaskLog> items = dao.findByExample(sample);
								for(TaskLog log : items) {
								
									IstarUser user = (new IstarUserDAO()).findById(log.getActorId());
									
									%>
								<div class="comment">
									<img
										src="https://cdn2.iconfinder.com/data/icons/lil-faces/233/lil-face-4-512.png"
										alt="">
									<div class="overflow-h">
										<strong><%=user.getName() %></strong>
										<p><%=log.getComments() %></p>

									</div>
								</div>
								<% } } catch(Exception e) {} %>

							</div>
						</div>
					</div>

					<div class="col-md-6" style="margin-top: -254px;">

						<div id="phone_area" style="display: block;">
							<div id="phone_placeholder"
								style="display: block; height: 1024px;">
								<div id="htc_one_emulator"
									style="transform: scale(1); transform-origin: 0px 0px 0px;">
									<div id="frame_htc_one_emulator" class="frame_scroller">
<iframe src="/content/mobile_preview.jsp?template_name=<%=slide.getTemplateName()%>&slide_id=<%=request.getParameter("slide_id")%>&lesson_theme=<%=ppt.getLesson().getLesson_theme()%>"  
 frameborder="0" id='prv' style="background-color: #fff;margin-top: 217px;width: 360px;        height: 593px;"> </iframe>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
		</form>

	</div>
	</div>
	<jsp:include page="content_admin/includes/footer.jsp"></jsp:include>
	</div>


	<!-- JS Global Compulsory -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
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
	<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	 <script type="text/javascript" src="<%=baseURL %>assets/js/plugins/parsley.js"></script>

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
		tinymce.init({
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
							setup: function (editor) {
						        editor.on('change', function () {
						        	var text1 = tinyMCE.activeEditor.getContent();
						        	console.log("new COntent -> "+ tinyMCE.activeEditor.getContent());
									var iframeInner = $('#prv').contents().find('#data_slide_paragraph').html(text1);
						        	tinyMCE.triggerSave();
						        });
						    }
				});
	}
	
	function initHooks(){
		$( ".updateble" ).each( function( index, listItem ) {
			 var id = $(this).attr('id');
			$('#'+id).keyup(function() {
				console.log('new value ->'+ '#data_'+id);
				var iframeInner = $('#prv').contents().find('#data_'+id).html($('#'+id).val());
				
			});
		});
		
		$('#image-picker').on('change', function() {
			var id = $(this).find(":checked").attr('id');
			$('#prv').contents().find('#data_image_url').attr("src",$('#'+id).data('img-src'));

			});
	}
	$( document ).ready(function() {
		initTextArea();
		initHooks();
		
		
		
	});
	
		
		
		
	</script>
</body>
</html>
