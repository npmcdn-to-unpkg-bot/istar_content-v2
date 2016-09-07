<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.ocpsoft.prettytime.PrettyTime"%>
<%@page import="java.util.*"%> 
<%@page import="java.text.*"%>
<%@page import="com.istarindia.apps.*"%>
<%@page import="com.istarindia.apps.services.*"%>
<%@page import="com.istarindia.apps.cmsutils.*"%>
<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.cms.lessons.CMSSlide"%>

<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%>

<%
	int slide_id = 0;
	int order_id = 0;
	int version_id = 0;
	Boolean newSlide = true;
	String new_media_title="";
	String previous_slide_url ="#";
	String next_slide_url ="#";
	String slide_type = "";
	String bgImage = "none";
	int ppt_id = Integer.parseInt(request.getParameter("ppt_id"));
	Presentaion ppt = (new PresentaionDAO()).findById(ppt_id);
	Lesson lesson = ppt.getLesson();
	
	int themeID  = Integer.parseInt(lesson.getLesson_theme_desktop());
	
	Task task = new Task();
	task = lesson.getTask();
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	PrettyTime prettyTime = new PrettyTime();
	SlideService service = new SlideService();
	LessonUtils lessonUtils = new LessonUtils();
	CMSSlide cMSSlide = new CMSSlide();
	SlideDAO slideDao = new SlideDAO();
	MediaUtils mediaUtils = new MediaUtils();
	
	Slide slide = new Slide();
	ArrayList<SlideVersion> versions = new ArrayList<SlideVersion>();
	ArrayList<Image> bgImages = (ArrayList<Image>) mediaUtils.findAllBackgrounds(request);
	List<HashMap<String, String>> logs = null;  

	if(task.getStatus().equalsIgnoreCase("PUBLISHED"))
	{
		request.setAttribute("message_failure", "This lesson is already published and cannot be edited!");
		request.getRequestDispatcher("/invalid_access.jsp").forward(request, response);
	} else {
		try {
			if (request.getParameterMap().containsKey("slide_id")) {
				slide_id = Integer.parseInt(request.getParameter("slide_id"));
				previous_slide_url = service.getPreviousSlideEditUrl(ppt_id, slide_id);
				next_slide_url = service.getNextSlideEditUrl(ppt_id, slide_id);
				versions = service.getPreviousRevisions(slide_id);
				
				newSlide = false;
				
				slide = slideDao.findById(slide_id);
				order_id = slide.getOrder_id();

				if(request.getParameterMap().containsKey("version_id")) {
					version_id = Integer.parseInt(request.getParameter("version_id"));
					cMSSlide = (new LessonUtils()).convertSlide(version_id);
				} else {
					cMSSlide = (new LessonUtils()).convertSlide(slide);
				}
				
				if(request.getParameterMap().containsKey("slide_type")) {
					slide_type = request.getParameter("slide_type");
				} else {
					slide_type = cMSSlide.getTemplateName();
				}
				
				bgImage = cMSSlide.getImage_BG();
				
				logs = lessonUtils.getSlideComments(slide_id);
				
			} else {
				newSlide = true;
				if(request.getParameterMap().containsKey("slide_type")) {
					slide_type = request.getParameter("slide_type");
				}
			}
			cMSSlide.setTemplateName(slide_type);
		} catch (Exception e ) { 
			e.printStackTrace();
			
		}
		
		new_media_title = service.getNewMediaTitle(slide_id, ppt.getLesson().getCmsession().getId());
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
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

<link rel="shortcut icon" href="<%=baseURL%>assets/img/talentify_logo_fav_48x48.png" />

<!-- Web Fonts -->
<link rel='stylesheet' type='text/css' href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600&amp;subset=cyrillic,latin'>

<!-- CSS Global Compulsory -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">

<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.css">
<link href="<%=baseURL%>assets/plugins/jquery-contextmenu/src/jquery.contextMenu.css" rel="stylesheet" type="text/css" />

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/select2-4.0.3/dist/css/select2.css">

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

				<h1 class=" col col-md-4" style="text-align: center;">Add/Edit Slide </h1>

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
						<li><a href="#template" data-toggle="tab">Template</a></li>
						<li><a href="#content" data-toggle="tab">Content</a></li>
						<li><a href="#ui" data-toggle="tab">Theme</a></li>
						<% if(slide_id != 0 && versions.size() > 0) { %>
						<li><a href="#versions" data-toggle="tab">Previous versions</a></li>
						<% } %>
						<% if(slide_id != 0) { %>
						<li><a href="#comments" data-toggle="tab">Review Comments</a></li>
						<% } %>
						<li class="active"><a href="#desktop" data-toggle="tab">Desktop Preview</a></li>
					</ul>
					
					<form  action="/content/create_slide" id="slide-form" method="POST">
					<input type="hidden" name="template" value="<%=slide_type%>"> 
					<input type="hidden" name="ppt_id" value="<%=ppt_id%>">
					<input name="is_edit" value="<%=!newSlide%>" type="hidden"> 
					<input name="slide_id" value="<%=slide_id%>" type="hidden">
					<input name="order_id" value="<%=order_id%>" type="hidden">
					
					<div class="tab-content">
						<div id="template" class="tab-pane fade in ">
							<div class="panel panel-sea">
								<div class="panel-heading">
									<h3 class="panel-title"> <i class="fa fa-tasks"></i> Try different template </h3>
								</div>
								<div class="panel-body">
								<fieldset>
									<section>									
										<select id="slidy_type_id" class="form-control" name="slide_type" style="width: 317px;">
											<%
												for (String template : CMSRegistry.slideTemplates) {
											%>
											
											<option value='<%=template%>'><%=template%></option>
											
											<% } %>
											
											<option value='<%=slide_type%>' selected="selected"><%=slide_type%></option>
											
										</select>
									</section>
									
								</fieldset>
								</div>
							</div>
						</div>
						
						<div id="content" class="tab-pane fade in ">
							<div class="panel panel-sea">
								<div class="panel-heading">
									<h3 class="panel-title"> <i class="fa fa-tasks"></i> Slide Details </h3>
								</div>
								<div class="panel-body" >
									<div class="sky-form" >
										
										<%=lessonUtils.getEditProfileEdit(cMSSlide, ppt, newSlide, request)%>
										
										<fieldset>
										
											<section>
												<label class="label">Teacher Notes</label> 
												<label class="textarea"> 
													<textarea rows="3" name="teacher_notes" data-parsley-required="true" data-parsley-length="[5,9250]" data-parsley-required-message="Please provide teacher notes" data-parsley-length-message="It should be 5-9250 characters long">
														<%=cMSSlide.getTeacherNotes()%> 
													</textarea>
												</label>
												<div class="note">
													<strong>Note:</strong> This is where we will put in the paragraph.
												</div>
											</section>
											<section>
												<label class="label">Student Notes</label> 
												<label class="textarea"> 
													<textarea rows="3" name="student_notes" data-parsley-required="true" data-parsley-length="[5,9250]" data-parsley-required-message="Please provide student notes" data-parsley-length-message="It should be 5-9250 characters long"> 
														<%=cMSSlide.getStudentNotes()%>
													</textarea>
												</label>
												<div class="note">
													<strong>Note:</strong> This is where we will put in the paragraph.
												</div>
											</section>
										
										</fieldset>
										<footer>
										
											<% if(slide_type.contains("IMAGE")||slide_type.contains("VIDEO")) { %>
												<button type='button' class="btn-u" style="float:right;"  data-target='#imageModal' data-toggle='modal'>
													Upload new Media
												</button>
											<% } %>
											
										</footer>
									</div>
								</div>
							</div>
						</div>
						
						<div id="ui" class=" tab-pane fade in ">
							<div class="panel panel-sea">
								<div class="panel-heading">
									<h3 class="panel-title"> <i class="fa fa-tasks"></i>
										Extras
									</h3>
								</div>
								<div class="panel-body">
									<div class="sky-form" >
										<fieldset >
											<div class="row">
												<section class="col col-md-6">
													<label class="label">Select Background Image</label> 
													<label class="select"> 
														<select name="image_bg" id="image-bg-picker" value="<%=bgImage%>">
															<option selected="selected" value="none">None</option>
															
															<% for (Image bg : bgImages) { %>													
																<option value="<%=bg.getUrl()%>"><%=bg.getTitle()%></option>
															<% } %>
															
															<option selected="selected" value="<%=bgImage%>"><%=bgImage%></option>
														</select> 
														<i></i> 
													</label>
												</section>
												
												<section class="col col-md-6">
													<label class="label">Select Slide Background color</label> <label class="select"> 
														<input type="color" id="slide_color" name="backgroundColor" value="<%=cMSSlide.getBackground()%>">
													</label>
												</section>
											</div>
											
											<div class="row">
												<section class="col col-md-6">
													<label class="label">Select Slide Transition</label> <label class="select"> 
														<select name="slideTransition">
															<%
																for (String slideTransitions : SlideTransition.SlideTransitionTypes) {
															%>
															
															<option value="<%=slideTransitions%>"><%=slideTransitions%></option>
															
															<% } %>
															
															<option value="Slide" selected="selected">Slide</option>
															
														</select> <i></i>
													</label>
												</section>
												<section class="col col-md-6">
													<label class="label">Select Background Transition</label> <label class="select"> 
														<select name="backgroundTransition">
															<%
																for (String slideTransitions : SlideTransition.BackgroundTransition) {
															%>
															
															<option value="<%=slideTransitions%>"><%=slideTransitions%></option>
															
															<% } %>
															
															<option value="Slide" selected="selected">Slide</option>
															
														</select> <i></i>
													</label>
												</section>
											</div>
										</fieldset>
										
										<footer>
											
											<%  if(slide_id != 0) { %>
												<a href='/content/creative_admin/edit_theme.jsp?theme_id=<%=themeID %>&slide_id=<%=slide_id %>' class='btn-u' target="_blank">Preview/Edit Theme</a>												
											<% } %>
											
										</footer>
									</div>
								</div>
							</div>
						</div>
						
						<% if (versions.size() > 0 ) { %>
						<div id="versions" class="tab-pane fade in ">
							<section>
								<table class="table table-striped table-teal">
								<tr>
									<th>Version ID </th>
									<th>Template </th>
									<th>Last updated at </th>
									<th>Action </th>
								</tr>
								
								<% for (SlideVersion version : versions ) { %>
									<tr>
										<td><%=version.getId() %> </td>
										<td><%=version.getTemplate() %> </td>
										<td><%=prettyTime.format(simpleDateFormat.parse(version.getCreatedAt().toString()))%> </td>
										<td> <a target='_blank' class="btn-u btn-custom" href="/content/fill_template.jsp?ppt_id=<%=ppt_id%>&slide_id=<%=slide_id %>&version_id=<%=version.getId()%>">Restore</a> </td>
									</tr>
								<% } %>
								
								</table>									
									
							</section>
						</div>
						
						<% } %>
						
						<% if (slide_id > 0 ) { %>
						<div id="comments" class="tab-pane fade in ">
							<div class="panel panel-sea">
								<div class="panel-heading">
									<h3 class="panel-title"> <i class="fa fa-tasks"></i> View/add review comments for the slide </h3>
								</div>
								<div class="panel-body " data-mcs-theme="minimal-dark" >
									<%
										for(HashMap<String, String> log : logs){
											try {
												String typeStatus = log.get("changed_status").toString();
												String desc = log.get("comment").toString();
												if (desc.length() > 100) {
													desc = desc.substring(0, 250);
												}
												String name = log.get("actor_name").toString();

												int userID = Integer.parseInt(log.get("actor_id").toString());
												IstarUser actor = new IstarUserDAO().findById(userID);
												String imageURL = actor.getImageUrl();
												if(imageURL == null) {
													imageURL = actor.getUserType().toLowerCase() + ".png" ; 
												}
									%>
		
									<div class="alert-blocks alert-blocks-pending alert-dismissable">
										<img  src="/video/img/user_images/<%=imageURL %>" alt="<%=name %>">
										<div>
											<strong class="color-yellow"><a href='<%=baseURL%>task/profile.jsp?id=<%=userID %>'><%=name %></a>
											<small class="pull-right"><em><%=prettyTime.format(simpleDateFormat.parse(log.get("created_at").toString()))%></em></small></strong>
											<p><%=desc%></p>
										</div>
									</div>
									<%
											} catch (Exception e) {
											}
										}
									%>
								</div>
								<footer>
									<section style=" float: right; margin: 0;">
										<button type='button' class='btn-u' data-target='#reviewCommentModal' data-toggle='modal'>Add new comment</button>
									</section>
								</footer>
							</div>
						</div>
						
						
						<% } %>
						<div class="tab-pane fade in active" id="desktop">
							<div id="desktop_area"  class="dynamic-preview" style="background-image: url('/content/assets/img/frames/desktop.png')">
								<iframe id="d-preview" class="desktop-preview-frame" src=""> </iframe>
							</div>
						</div>
					</div>
					
					</form>
				</div>
				
				
				<div class=" col-md-3 ">
					<div style="margin-bottom: 12%;">	
						<button id="submit-form" type="button" class="btn-u submit-slide-button" style="float:right">UPDATE SLIDE</button>
					</div>
					<div class="panel panel-sea" >
						<div class="panel-heading">
							<h3 class="panel-title"> <i class="fa fa-tasks"></i> Mobile Preview </h3>
						</div>
						<div class="panel-body ">
							<div class="sky-form  " id="mobile">
								<div id="mobile_area" class="dynamic-preview" style="background-image: url('/content/assets/img/frames/mobile.png')">
									<iframe id='m-preview' class="mobile-preview-frame" src=""> </iframe>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>

	<div class='modal fade' id='imageModal' tabindex='-1'  role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" id="myModalLabel4">Upload Media</h4>
					</div>
					
					<div class="modal-body">
						<form action="<%=baseURL%>media_upload" class="sky-form" method="POST"  novalidate="novalidate"  enctype="multipart/form-data">
							<input type="hidden" id="item_id" name="item_id" value="0" />
							<input type="hidden" id="slide_id" name="slide_id" value="<%=slide_id%>" />
							<input type="hidden" id="slide_type" name="slide_type" value="<%=slide_type %>" />
							<input type="hidden" id="selected_items" name="selected_items" value="0" />
							<input type="hidden" id="session_id" name="session_id" value="<%=ppt.getLesson().getCmsession().getId() %>" />
							<input type="hidden" id="ppt_id" name="ppt_id" value="<%=ppt.getId() %>" />
							<input type="hidden" id="new_media_title" name="new_media_title" value="<%=new_media_title %>" />
							<fieldset>
								<section class="label">
									<label class="label">Tags</label> <label class="input">
										<input type="text" name="tags" data-role="tagsinput" class="tagcontainer">
									</label>
								</section>
								<section>
									<label class="label">File input</label>
									<label for="file" class="input input-file">
										<div class="button">
											<input type="file" id="file" name="file" onchange="this.parentNode.nextSibling.value = this.value">Browse
										</div>
										<input type="text" readonly="">
									</label>
								</section>
								<section style="margin-bottom: 2%; float: right;">
									<button type="button" class="btn-u btn-u-default" data-dismiss="modal">Close</button>
									<button type="submit" class="btn-u btn-u-primary" >Upload</button> 
								</section>
							</fieldset>
						</form>
					</div>
					
					<div class="modal-footer"> </div>
					
				</div>
			</div>
		</div>
		
		<div class='modal fade' id='reviewCommentModal' tabindex='-1'  role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
						<h4 class="modal-title" id="myModalLabel4">Add comment</h4>
					</div>
					
					<div class="modal-body">
						<form action="/content/review_lesson" name="" method="GET" class="sky-form">
							<input name='is_edit' type="hidden" value="true" />
							<input name='slide_id' type="hidden" value="<%=slide_id %>" />
							<input name='ppt_id' type="hidden" value="<%=ppt_id %>" />
							<input name='from' type=hidden value="edit_slide" />
							<fieldset>							
								<section>								
									<label class="label">Review Notes</label> 
									<label class="textarea"> 
										<textarea rows="3" name="review_notes" placeholder=" Please enter text"></textarea>
									</label>
									<div class="note">
										<strong>Note:</strong> This is where we will put in the Review Notes.
									</div>
								</section>
								<section style="margin-bottom: 2%; float: right;">
									<button type="button" class="btn-u btn-u-default" data-dismiss="modal">Close</button>
									<button type="submit" class="btn-u" >Submit</button> 
								</section>
							</fieldset>
						</form>
					</div>
					
					<div class="modal-footer"> </div>
					
				</div>
			</div>
		</div>

	<!-- JS Global Compulsory -->
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>

	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="http://rvera.github.io/image-picker/image-picker/image-picker.js" type="text/javascript"></script>
    <script src="<%=baseURL%>assets/plugins/jquery-contextmenu/src/jquery.contextMenu.js" type="text/javascript"></script>

	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js" type="text/javascript" charset="utf-8"></script>
	<script src="//cdn.ckeditor.com/4.5.9/full/ckeditor.js"></script>
	
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/plugins/parsley.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/select2-4.0.3/dist/js/select2.full.js"></script>
	<script src="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/plugins/validation.js"></script>

	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
	<script type="text/javascript">

	    $('#submit-form').click(function(e){
	    	$('#slide-form').submit();
	    })
	    
		function initTextArea() {
			try {
				$("#image-picker").imagepicker();
				///CKEDITOR.replace('paragraph', {
				//	height : 100
				//});
			} catch (err) {
				console.log(err);
			}
			
			try {
				var bodyEditor = CKEDITOR.replace('slide_paragraph', {
					readOnly : false
				});
				bodyEditor.on('mode', function() {
					if (this.mode == 'source') {
						var editable = bodyEditor.editable();
						editable.attachListener(editable, 'input', function() {
							var text1 = CKEDITOR.instances.Editor.document.getBody().getHtml();
							$('.dynamic-preview > iframe').each(function(index, value) {
								$(this).contents().find('#slide_paragraph').html(text1);
							});
						});
					}
				});
				
				bodyEditor.on('change', function() {
					var text1 = bodyEditor.document.getBody().getHtml()
					$('.dynamic-preview > iframe').each(function(index, value) {
						$(this).contents().find('#data_slide_paragraph').html(text1);
					});
				});
			} catch (err) {
				console.log(err);
			}
		}
		
		function initHooks() {
			$(".updateble").each( function(index, listItem) {
				var id = $(this).attr('id');
				if ($("#" + id).is("input")) {
					$('#' + id).keyup( function() {
						$('.dynamic-preview > iframe').each(function(index, value) {
							$(this).contents().find('#data_' + id).html($('#' + id).val());
						});
					});
				} else {
					console.log(id);
				}
			});
			
			$('#image-picker').on( 'change', function() {
				var id = $(this).find(":checked").attr('id');
				$('.dynamic-preview > iframe').each(function(index, value) {
					$(this).contents().find('#data_image_url').attr( "src", $('#' + id).data('img-src'));
				});
			});
			$('#image-bg-picker').on( 'change', function() {
				var bgurl = $(this).find(":checked").val();
				var bgurl_desktop =  bgurl.replace(".png", "_desktop.png");
				$('.mobile-preview-frame').contents().find('.slide-background').css( 'background-image', "url(" + bgurl + ")");
				$('.desktop-preview-frame').contents().find('.slide-background').css( 'background-image', "url(" + bgurl_desktop + ")");
			});
		}
		
		function initColorChange() {
			$('#slide_color').on( 'change', function() {
				$('.dynamic-preview > iframe').each(function(index, value) {
					$(this).contents().find('.slide-background').css( 'background-color', $('#slide_color').val());
				});
			});
		}
		
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
		
		function initBgImage() {
			try {
				var mobile_bg = "<%=cMSSlide.getImage_BG()%>";
				var desktop_bg = mobile_bg.replace(".png", "_desktop.png");
				
				$('.mobile-preview-frame').contents().find('.slide-background').css( 'background-image', 'url('+mobile_bg+')');
				$('.desktop-preview-frame').contents().find('.slide-background').css( 'background-image', 'url('+desktop_bg+')');
			} catch (err) {
				console.log(err);
			}
		}
		
		$(document).ready(function() {
			setupFrames();
			$("#image-bg-picker").select2({
			    placeholder: "Select slide background image",
			    allowClear: true
			});
			initTextArea();
			initHooks();
			initColorChange();
			initBgImage();
			$('.mobile-preview-frame .slides').css('top','75%');

			$("#slidy_type_id").change(function() {
				var slideId = <%=request.getParameter("slide_id")%> ;
				if(slideId != null) {
					var url = "	<%=baseURL%>fill_template.jsp?ppt_id=<%=request.getParameter("ppt_id")%>&slide_id=<%=request.getParameter("slide_id")%>&slide_type="+$(this).val();
	 			} else {
					var url = "	<%=baseURL%>fill_template.jsp?ppt_id=<%=request.getParameter("ppt_id")%>&slide_type="+$(this).val();
				}
				window.location.href=url;
			});

			$("#version_id").change(function() {
				var slideId = <%=request.getParameter("slide_id")%> ;
				var version_id = $(this).val();
				if(version_id != "NONE") {
					var url = "	<%=baseURL%>fill_template.jsp?ppt_id=<%=request.getParameter("ppt_id")%>&slide_id=<%=request.getParameter("slide_id")%>&version_id="+$(this).val();
					window.location.href=url;
				}
			});
			
			$.contextMenu({
	            selector: '.thumbnail', 
	            callback: function(key, options) {
	                var mediaUrl = $(this).find('img').first().attr('src');
	                $.ajax({
	                	type: "GET",
	                	url: "/content/media_upload?delfile="+mediaUrl, 
	                });    
                    location.reload(); 
	            },
	            
	            items: {
	                "delete": {name: "Delete", icon: "delete"}
	            }
	        });
			

            Validation.slideValidation();

		});
	</script>
</body>
</html>