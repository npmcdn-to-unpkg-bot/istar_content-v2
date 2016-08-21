<%@page import="com.istarindia.apps.services.SlideService"%>
<%@page import="com.istarindia.cms.lessons.CMSSlide"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.services.LessonService"%><%@page
	import="com.istarindia.apps.*"%><%@page
	import="com.istarindia.apps.SlideTransition"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
<%@ page import="com.istarindia.apps.dao.*"%>

<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
%>

<%
	int slide_id = Integer.parseInt(request.getParameter("slide_id"));
	int ppt_id = Integer.parseInt(request.getParameter("ppt_id"));
	
	SlideService service = new SlideService();
	LessonUtils lessonUtils = new LessonUtils();
	SlideDAO slideDao = new SlideDAO();
	
	Presentaion ppt = (new PresentaionDAO()).findById(ppt_id);

	String previous_slide_url = service.getPreviousSlideReviewUrl(ppt_id, slide_id);
	Slide slide = slideDao.findById(slide_id);
	String next_slide_url = service.getNextSlideReviewUrl(ppt_id, slide_id);
	String slide_type = slide.getTemplate();
	List<HashMap<String, String>> logs = lessonUtils.getSlideComments(slide_id);

	Lesson lesson = ppt.getLesson();
	Task task = lesson.getTask();

	if (task.getStatus().equalsIgnoreCase("PUBLISHED")) {
		request.setAttribute("message_failure", "This lesson is already published and cannot be accessed!");
		request.getRequestDispatcher("/invalid_access.jsp").forward(request, response);
	}
	
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
		<jsp:include page="content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid row">
				<div class=" col col-md-4">
					<% if (!previous_slide_url.equalsIgnoreCase("#")) { %>
						<a class="left carousel-control custom-control" href="<%=baseURL%><%=previous_slide_url%>"> 
							<span class="glyphicon glyphicon-chevron-left custom-control-icon"></span>
						</a>
					<% } %>
				</div>

				<h1 class=" col col-md-4" style="text-align: center;">Add/Edit Slide</h1>

				<div class=" col col-md-4">
					<% if (!next_slide_url.equalsIgnoreCase("#")) { %>
						<a class="right carousel-control custom-control" href="<%=baseURL%><%=next_slide_url%>"> 
							<span class="glyphicon glyphicon-chevron-right custom-control-icon"></span>
						</a>
					<% } %>
				</div>
			</div>
		</div>
		
		
		<br/>  <br/>
		
		
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<div class=" col-md-12 ">
				<div class="tab-v2 col-md-9 sky-form" id="tabs1">
					
					<ul class="nav nav-tabs">
						<li><a href="#new_comment" data-toggle="tab">Add New Comment</a></li>
						<% if(logs.size() > 0) {%>
						<li><a href="#comments" data-toggle="tab">Review Comments</a></li>
						<% } %>
						<li class="active"><a href="#desktop" data-toggle="tab">Desktop Preview</a></li>
					</ul>
					
					<form id="review-form" action="/content/review_lesson" name="" method="GET" class="sky-form">
				
					<input name="is_edit" value="true" type="hidden"> 
					<input name="slide_id" value="<%=slide_id%>" type="hidden">
					<input type="hidden" name="ppt_id" value="<%=ppt_id%>">
					<input type="hidden" name="from" value="review_slide">
					
						<div class="tab-content">
							<div id="new_comment" class="tab-pane fade in ">
								<div class="panel panel-sea">
									
									<div class="panel-heading">
										<h3 class="panel-title"> <i class="fa fa-tasks"></i> Add new comment </h3>
									</div>
									
									<div class="panel-body">
										<fieldset>
											<section>
												<label class="label">Comment</label> 
												<label class="textarea">
													<textarea rows="5" name="review_notes" placeholder=" Please enter text"></textarea>
												</label>
											</section>
										</fieldset>
										
										<footer>
											<button id="submit-comment" data-target="#confirm-submit-comment-modal" 
												data-toggle="modal" style="float:right" type="button" class="btn-u">Submit</button>
										</footer>
									</div>
									
								</div>
							</div>
						
							<div id="comments" class="tab-pane fade in ">
								<div class="panel panel-profile profile">
									
									<div class="panel-body no-padding" data-mcs-theme="minimal-dark"> 
										<%  try { for(HashMap<String, String> log : logs ) { %>
										
										<div class="comment">
											<img src="https://cdn2.iconfinder.com/data/icons/lil-faces/233/lil-face-4-512.png" alt="">
											<div class="overflow-h">
												<strong><%=log.get("actor_name")%></strong>
												<p><%=log.get("comment")%></p>
											</div>
										</div>
										
										<% } } catch(Exception e) {} %>
										
									</div>
									
								</div>
							</div>
							
							<div class="tab-pane fade in active" id="desktop">
								<div id="desktop_area"  class="dynamic-preview" style="background-image: url('/content/assets/img/frames/desktop.png')">
									<iframe id="d-preview" class="desktop-preview-frame" src="#"> </iframe>
								</div>
							</div>
						</div>
					
					</form>
				</div>
				
				<div class=" col-md-3 ">
					<div class="panel panel-sea" >
						<div class="panel-heading">
							<h3 class="panel-title"> <i class="fa fa-tasks"></i> Mobile Preview </h3>
						</div>
						<div class="panel-body ">
							<div class="sky-form  " id="mobile">
								<div id="mobile_area" class="dynamic-preview" style="background-image: url('/content/assets/img/frames/mobile.png')">
									<iframe id='m-preview' class="mobile-preview-frame" src="#"> </iframe>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="confirm-submit-comment-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">Confirm Submit</div>
				<div class="modal-body">Are you sure you want to proceed?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button id="confirm-submit-comment-btn" type="submit" class="btn btn-success success">Proceed</button>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="content_admin/includes/footer.jsp"></jsp:include>

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

	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
	<script type="text/javascript">
		function initTextArea() {
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

		$(document).ready(function() {
			setupFrames();
			initTextArea();

		});
		

	    $('#confirm-submit-comment-btn').click(function(e){
	    	$('#review-form').submit();
	    })
	    
		function setupFrames() {
			var dWidth = $("#desktop_area").width() * 0.96 ;
			var dHeight = dWidth * 768 / 1024 ;
			var dScale =  dWidth / 1024 ;
			var dUrl = "/content/desktop_preview.jsp?ppt_id=<%=ppt_id%>&template_name=<%=slide_type%>&slide_id=<%=slide_id%>&lesson_theme=<%=ppt.getLesson().getLesson_theme()%>";
			var dLocation = dUrl + "&scale=" + dScale;
			$("#d-preview").attr("src", dLocation);
			$("#d-preview").css("width", dWidth);
			$("#d-preview").css("height", dHeight);
			
			var mWidth = $("#mobile_area").width() * 0.91 ;
			var mHeight = mWidth * 1650 / 900 ;
			var mScale =  mWidth / 900 ;
			var mUrl = "/content/mobile_preview.jsp?ppt_id=<%=ppt_id%>&template_name=<%=slide_type%>&slide_id=<%=slide_id%>&lesson_theme=<%=ppt.getLesson().getLesson_theme()%>";
			var mLocation = mUrl + "&scale=" + mScale;
			$("#m-preview").attr("src", mLocation);
			$("#m-preview").css("width", mWidth);
			$("#m-preview").css("height", mHeight);
		}
		
	</script>
</body>
</html>
