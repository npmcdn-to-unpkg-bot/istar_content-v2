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

<body ng-app="">

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
					newSlide = false;
					SlideDAO dao = new SlideDAO();

					slide = (new LessonUtils())
							.convertSlide(dao.findById(Integer.parseInt(request.getParameter("slide_id"))));
					slide.setTemplateName(request.getParameter("slide_type"));
			%>
			<input name="is_edit" value="true" type="hidden"> <input
				name="slide_id" value="<%=request.getParameter("slide_id")%>"
				type="hidden">
			<%
				} else {
					System.err.println("It is a new Slide ");
					newSlide = true;
					slide.setTemplateName(request.getParameter("slide_type"));
			%>
			<input name="is_edit" value="false" type="hidden">
			<%
				}
			%>
			<div class="container-fluid" style="padding: 0px !important">
				<input type="hidden" name="template"
					value="<%=slide.getTemplateName()%>"> <input type="hidden"
					name="ppt_id" value="<%=request.getParameter("ppt_id")%>">
				<div class="row">
					<%
						ImageUtils dao1 = new ImageUtils();
					ArrayList<Image> images = (ArrayList<Image>) dao1.findAllBackgrounds(request);
					%><div class="col-md-7">
						<%=utils.getEditProfileEdit(slide, ppt, newSlide,request)%>
						<fieldset>
							<section>
								<label class="label">Select Image Background</label> <label
									class="select"> <select name="image_bg"
									id="image-bg-picker" value="<%=slide.getImage_BG()%>">
									<option selected="selected" value="none">None</option>
										
										<%
											for (Image type : images) {
										%>
										<option  value="<%=type.getUrl()%>"><%=type.getUrl()%></option>
										<%
											}
										if (request.getParameterMap().containsKey("slide_id")){ 
											Slide slide1 = (new SlideDAO()).findById(Integer.parseInt((request.getParameter("slide_id")).toString()));
											LessonUtils lUtils = new LessonUtils();
											CMSSlide cmsslide = lUtils.convertSlide(slide1);
											String BG_url = cmsslide.getImage_BG();
											
										%>
										
										<option selected="selected" value="<%=BG_url%>"><%=BG_url%></option>
										
										<% 
										}
										%>
								</select> <i></i>
								</label>
							</section>
							
							<section>
								<label class="label">Teacher Notes</label> <label
									class="textarea"> <textarea rows="3"
										name="teacher_notes" data-parsley-required="true"
										data-parsley-length="[5,9250]"
										data-parsley-required-message="Please provide teacher notes"
										data-parsley-length-message="It should be 5-9250 characters long">
								<%=slide.getTeacherNotes()%> </textarea>

								</label>
								<div class="note">
									<strong>Note:</strong> This is where we will put in the
									paragraph.
								</div>
							</section>
							<section>
								<label class="label">Student Notes</label> <label
									class="textarea"> <textarea rows="3"
										name="student_notes" data-parsley-required="true"
										data-parsley-length="[5,9250]"
										data-parsley-required-message="Please provide student notes"
										data-parsley-length-message="It should be 5-9250 characters long"> 
								<%=slide.getStudentNotes()%></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is where we will put in the
									paragraph.
								</div>
							</section>
							
							<div class="row">
							<section class="col col-md-6">
								<label class="label">Select Slide Transition</label> <label
									class="select"> <select name="slideTransition">
										<%
											
											//REMOVE THE BELOW 3 LINES WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE
											ArrayList<String> slidetransitions = SlideTransition.SlideTransitionTypes;
											Collections.shuffle(slidetransitions);
											int i=0;
											
											for (String type : SlideTransition.SlideTransitionTypes) {
											
												if (type.equalsIgnoreCase(slide.getTransition())) {
										%>
										<%-- REMOVE THE BELOW WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE --%>
										<%-- <option selected="selected" value="<%=type%>"><%=type%></option> --%>
										<%
											} else {
										%>
										<option value="<%=slidetransitions.get(i)%>"><%=slidetransitions.get(i++) %></option>
										<%-- REPLACE THE BELOW WITH ABOVE LINE WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE --%>
										<%--<option value="<%=type%>"><%=type%></option> --%>
										<%
											}
										}
										%>
								</select> <i></i>
								</label>
							</section>
							<section  class="col col-md-6">
								<label class="label">Select Background Transition</label> <label
									class="select"> <select name="backgroundTransition">
										<%
										//REMOVE THE BELOW 3 LINES WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE
										ArrayList<String> backgroundtransitions = SlideTransition.BackgroundTransition;
										Collections.shuffle(backgroundtransitions);
										int j=0;
										
 											for (String type : SlideTransition.BackgroundTransition) {
 	 										if (type.equalsIgnoreCase(slide.getBackgroundTransition())) {
										%>
										<%-- REMOVE THE BELOW WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE --%>
										<%-- <option selected="selected" value="<%=type%>"><%=type%></option> --%>
										<%
											} else {
										%>
										<option value="<%=backgroundtransitions.get(j)%>"><%=backgroundtransitions.get(j++) %></option>
										<%-- REPLACE THE BELOW WITH ABOVE LINE WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE --%>
										<%-- <option value="<%=type%>"><%=type%></option> --%>
										<%
											}
											}
										%>
								</select> <i></i>
								</label>
							</section>
							</div>
						</fieldset>


						<footer class="col col-md-12">
							<section class="col col-md-6">
								<label class="label">Select Slide Background color</label> <label
									class="select"> <input type="color" id="slide_color"
									name="backgroundColor" value="<%=slide.getBackground()%>">
								</label>
							</section>
							<% int order_id = 0;
							if (request.getParameterMap().containsKey("slide_id")) {
								order_id = (new SlideDAO()).findById(Integer.parseInt(request.getParameter("slide_id"))).getOrder_id();
							} 	%>
							
							<section class="col col-md-3">
								<label class="label">Slide number</label> <label class="input">
									<input id="order_id" class="updateble" type="number"
									name="order_id" value="<%=order_id%>">
							</section>
							<section class="col col-md-3">
								<label class="label"><br/></label> 
								<button type="submit" class="btn-u">Submit</button>
							</section>
						
						</footer>
						
					</div>

					<div class="col-md-5">
						<iframe
							src="/content/mobile_preview.jsp?template_name=<%=slide.getTemplateName()%>&slide_id=<%=request.getParameter("slide_id")%>&lesson_theme=<%=ppt.getLesson().getLesson_theme()%>"
							frameborder="0" id='prv'
							style="background-color: #fff; width: 412px; height: 659px;">
						</iframe>
					</div>
					<div class="panel panel-profile profile" >
							<div class="panel-heading overflow-h">
								<h2 style="margin-top: 50px;" class="panel-title heading-sm pull-left">
									<i class="fa fa-comments-o"></i> Review Comments
								</h2>

							</div>
							<div id="scrollbar4"
								class="panel-body no-padding mCustomScrollbar"
								data-mcs-theme="minimal-dark">

								<%
									try {
										TaskDAO TDAO = new TaskDAO();
										Task task = new Task();
										task.setItemType("LESSON");
										task.setItemId(ppt.getLesson().getId());
										task = TDAO.findByExample(task).get(0);
										TaskLogDAO dao = new TaskLogDAO();
										TaskLog sample = new TaskLog();
										sample.setTaskId(task.getId());
										sample.setItemType("SLIDE");
										sample.setItem_id(Integer.parseInt(request.getParameter("slide_id")));

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
	<script src="<%=baseURL%>assets/plugins/ckeditor/ckeditor.js"></script>
	<script src="<%=baseURL%>assets/plugins/ckeditor/plugins/simple_image_browser/plugin.js"></script>
	
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/js/plugins/parsley.js"></script>

	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
	<script type="text/javascript">
		function initTextArea() {

			try {
				$("#image-picker").imagepicker();
				CKEDITOR.replace('paragraph', { height: 100 });
			} catch (err) {
				// TODO: handle exception
			}
			
			try {
				// Handle when the Source changes.
			var bodyEditor = CKEDITOR.replace('slide_paragraph', {
				readOnly : false
			});

			bodyEditor.on('mode', function() {
				if (this.mode == 'source') {
					var editable = bodyEditor.editable();
					editable.attachListener(editable, 'input', function() {
						//alert('source changed');
						var text1 = CKEDITOR.instances.Editor.document
								.getBody().getHtml()
						console.log("new COntent 222-> " + text1);
						var iframeInner = $('#prv').contents().find(
								'#data_slide_paragraph').html(text1);
					});
				}
			});

			// Handle when the HTML changes.
			bodyEditor.on('change', function() {
				var text1 = bodyEditor.document.getBody().getHtml()
				console.log("new COntent111 -> " + text1);
				var iframeInner = $('#prv').contents().find(
						'#data_slide_paragraph').html(text1);
			});
		} catch (err) {
			// TODO: handle exception
		}
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
						console.log('Kamini');
						var id = $(this).find(":checked").attr('id');
						$('#prv').contents().find('#data_image_url').attr(
								"src", $('#' + id).data('img-src'));

					});

			$('#image-bg-picker').on(
					'change',
					function() {
						var bgurl = $(this).find(":checked").val();
						console.log(bgurl);
						$('#prv').contents().find('.slide-background').css(
								'background-image', "url(" + bgurl + ")");//
						$('#prv').contents().find('.slide-background').css(
								'background-size', "cover");
					});
		}

		function initColorChange() {

			$('#slide_color').on(
					'change',
					function() {
						console.log("color chnaged " + $('#slide_color').val()
								+ " --- " + $('.slides section'));
						$('#prv').contents().find('.slide-background').css(
								'background-color', $('#slide_color').val());
					});

		}
		$(document).ready(function() {
			initTextArea();
			
			initHooks();
			initColorChange();//slide_color

		});
	</script>
</body>
</html>
