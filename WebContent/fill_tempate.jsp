<%@page import="com.istarindia.cms.lessons.CMSSlide"%><%@page import="com.istarindia.apps.services.SlideService"%>

<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.services.LessonService"%>
<%@page import="com.istarindia.apps.*"%>
<%@page import="com.istarindia.apps.SlideTransition"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.istarindia.apps.dao.*"%>

<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
	
	
%>
<%
				Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
				SlideService service = new SlideService();
				int ppt_id = Integer.parseInt(request.getParameter("ppt_id"));
				int slide_id = 0;
				int previous_slide_id = 0 ;
				int next_slide_id = 0;
				String previous_slide_type = "NO_CONTENT"; 
				String next_slide_type = "NO_CONTENT"; 
				try {
					if (request.getParameterMap().containsKey("slide_id")) {
						slide_id = Integer.parseInt(request.getParameter("slide_id"));
						
						previous_slide_id = service.getPreviousSlideId(ppt_id, slide_id);
						next_slide_id = service.getNextSlideId(ppt_id, slide_id);
						
						previous_slide_type = service.getPreviousSlideType(ppt_id, slide_id);
						next_slide_type = service.getNextSlideType(ppt_id, slide_id);
					}
				} catch (Exception e ) { }
				
				String new_media_title = service.getNewMediaTitle(slide_id, ppt.getLesson().getCmsession().getId());
				
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

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

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

		<form action="/content/create_slide" name="" method="GET" data-parsley-validate="" novalidate="" class="sky-form">
			<div class='row-fluid'>
				<div class="col-md-1" style="min-height: 2000px; vertical-align: middle;">
						<% if(previous_slide_id != 0) { %>
							<a style="z-index:99999; width: 100%;" class="left carousel-control" 
							href="<%=baseURL%>fill_tempate.jsp?ppt_id=<%=request.getParameter("ppt_id") %>&slide_id=<%=previous_slide_id%>&slide_type=<%=previous_slide_type %>"> 
								<span  class="glyphicon glyphicon-chevron-left"></span> 
							</a>
						<% }%>
					</div>
				<div class='col-md-10'>
					<%
						LessonUtils utils = new LessonUtils();
						CMSSlide slide = new CMSSlide();
						Boolean newSlide = true;
						String slide_type = "";
						if (request.getParameterMap().containsKey("slide_id")) {
							newSlide = false;
							SlideDAO dao = new SlideDAO();
							slide_type = request.getParameter("slide_type");

							slide = (new LessonUtils())
									.convertSlide(dao.findById(Integer.parseInt(request.getParameter("slide_id"))));
							slide.setTemplateName(request.getParameter("slide_type"));
							
					%>
					
					<input name="is_edit" value="true" type="hidden"> 
					<input name="slide_id" value="<%=request.getParameter("slide_id")%>" type="hidden">
					
					<%
						} else {
							newSlide = true;
							slide.setTemplateName(request.getParameter("slide_type"));
					%>
					
					<input name="is_edit" value="false" type="hidden">
					
					<%
						}
					%>
					
					<div class="container-fluid" style="padding: 0px !important">
						<input type="hidden" name="template" value="<%=slide.getTemplateName()%>"> 
						<input type="hidden" name="ppt_id" value="<%=request.getParameter("ppt_id")%>">
						<div class="row">
							<%
								ImageUtils dao1 = new ImageUtils();
								ArrayList<Image> images = (ArrayList<Image>) dao1.findAllBackgrounds(request);
							%>
							<div class="col-md-7">
								
								<%=utils.getEditProfileEdit(slide, ppt, newSlide, request)%>
								
								<fieldset style=" margin-top: 5%;">
									
									<% try {
										if (request.getParameter("slide_type").toLowerCase().contains("image")
										    || request.getParameter("slide_type").toLowerCase().contains("video")) { %>
										    
											<section style="margin-top: -12%;     margin-right: 5%; float: right;">
													<button type='button' class='btn-u' data-target='#imageModal' data-toggle='modal'>Upload new Media</button>
											</section>
											
									<% 	}  } catch (Exception e ) { } %>
									
									<section>
										<label class="label">Select Image Background</label> 
										<label class="select"> 
											<select name="image_bg" id="image-bg-picker" value="<%=slide.getImage_BG()%>">
												<option selected="selected" value="none">None</option>
												<%
													for (Image type : images) {
												%>
													<option value="<%=type.getUrl()%>"><%=type.getTitle()%></option>
												<%
													}
													if (request.getParameterMap().containsKey("slide_id")) {
														Slide slide1 = (new SlideDAO())
																.findById(Integer.parseInt((request.getParameter("slide_id")).toString()));
														LessonUtils lUtils = new LessonUtils();
														CMSSlide cmsslide = lUtils.convertSlide(slide1);
														String BG_url = cmsslide.getImage_BG();
												%>
													<option selected="selected" value="<%=BG_url%>"><%=BG_url%></option>
												<%
													}
												%>
											</select> 
										<i></i> </label>
									</section>

									<section>
										<label class="label">Teacher Notes</label> 
										<label class="textarea"> 
											<textarea rows="3" name="teacher_notes" data-parsley-required="true" data-parsley-length="[5,9250]" data-parsley-required-message="Please provide teacher notes" data-parsley-length-message="It should be 5-9250 characters long">
												<%=slide.getTeacherNotes()%> 
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
												<%=slide.getStudentNotes()%>
											</textarea>
										</label>
										<div class="note">
											<strong>Note:</strong> This is where we will put in the paragraph.
										</div>
									</section>

									<div class="row">
										<section class="col col-md-6">
											<label class="label">Select Slide Transition</label> <label class="select"> <select name="slideTransition">
													<%
														//REMOVE THE BELOW 3 LINES WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE
														ArrayList<String> slidetransitions = SlideTransition.SlideTransitionTypes;
														Collections.shuffle(slidetransitions);
														int i = 0;

														for (String type : SlideTransition.SlideTransitionTypes) {

															if (type.equalsIgnoreCase(slide.getTransition())) {
													%>
													<%-- REMOVE THE BELOW WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE --%>
													<%-- <option selected="selected" value="<%=type%>"><%=type%></option> --%>
													<%
														} else {
													%>
													<option value="<%=slidetransitions.get(i)%>"><%=slidetransitions.get(i++)%></option>
													<%-- REPLACE THE BELOW WITH ABOVE LINE WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE --%>
													<%--<option value="<%=type%>"><%=type%></option> --%>
													<%
														}
														}
													%>
											</select> <i></i>
											</label>
										</section>
										<section class="col col-md-6">
											<label class="label">Select Background Transition</label> <label class="select"> <select name="backgroundTransition">
													<%
														//REMOVE THE BELOW 3 LINES WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE
														ArrayList<String> backgroundtransitions = SlideTransition.BackgroundTransition;
														Collections.shuffle(backgroundtransitions);
														int j = 0;

														for (String type : SlideTransition.BackgroundTransition) {
															if (type.equalsIgnoreCase(slide.getBackgroundTransition())) {
													%>
													<%-- REMOVE THE BELOW WHEN RANDOMIZING TRANSITIONS IS NOT NEEDED ANYMORE --%>
													<%-- <option selected="selected" value="<%=type%>"><%=type%></option> --%>
													<%
														} else {
													%>
													<option value="<%=backgroundtransitions.get(j)%>"><%=backgroundtransitions.get(j++)%></option>
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
										<label class="label">Select Slide Background color</label> <label class="select"> <input type="color" id="slide_color" name="backgroundColor" value="<%=slide.getBackground()%>">
										</label>
									</section>
									<%
										int order_id = 0;
										if (request.getParameterMap().containsKey("slide_id")) {
											order_id = (new SlideDAO()).findById(Integer.parseInt(request.getParameter("slide_id"))).getOrder_id();
										}
									%>
									<section class="col col-md-3">
										<label class="label">Slide number</label> <label class="input"> <input id="order_id" class="updateble" type="number" name="order_id" value="<%=order_id%>">
									</section>
									<section class="col col-md-3">
										<label class="label"><br /></label>
										<button type="submit" class="btn-u">Submit</button>
									</section>

								</footer>

							</div>

							<div class="col-md-5">
								
								<a href='/content/media/create_task.jsp' class='btn-u' target="_blank">Create New Media Task</a>
								
								<% 
								// If there is a theme assigned 
								// If there is a slide edit page 
								try {
								PresentaionDAO pDAO = new PresentaionDAO();
								int themeID  = Integer.parseInt(pDAO.findById(ppt_id).getLesson().getLesson_theme_desktop());
								
								if(request.getParameterMap().containsKey("slide_id")) {
									
								
								
								
								%>
								
								<a href='/content/creative_admin/edit_theme.jsp?theme_id=<%=themeID %>&slide_id=<%=slide_id %>' class='btn-u' target="_blank">Preview/Edit Theme</a>
								
								<% } } catch(Exception e) {} %>
								<select id="slidy_type_id" class="form-control" name="slide_type" style="margin-top: 50px; width: 317px;">
									<%
										for (String template : CMSRegistry.slideTemplates) {
											if (template.equalsIgnoreCase(request.getParameter("slide_type"))) {
									%>
									<option value='<%=template%>' selected='selected'><%=template%></option>
									<%
										} else {
									%>
									<option value='<%=template%>'><%=template%></option>
									<%
										}
										}
									%>
								</select>
								<div id="phone_area" style="display: block;">
									<div id="phone_placeholder" style="display: block; height: 1024px;">
										<div id="htc_one_emulator" style="transform: scale(1); transform-origin: 0px 0px 0px;">
											<div id="frame_htc_one_emulator" class="frame_scroller">

												<iframe src="/content/mobile_preview.jsp?ppt_id=<%=request.getParameter("ppt_id")%>&template_name=<%=request.getParameter("slide_type")%>&slide_id=<%=request.getParameter("slide_id")%>&lesson_theme=<%=ppt.getLesson().getLesson_theme()%>" frameborder="0" id='prv' style="background-color: #fff; width: 365px; height: 636px; margin-top: 176px;"> </iframe>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-profile profile">
								<div class="panel-heading overflow-h">
									<h2 class="panel-title heading-sm pull-left">
										<i class="fa fa-comments-o"></i> Review Comments
									</h2>
									<% if(request.getParameterMap().containsKey("slide_id")) {%>
									<section style=" float: right;">
											<button type='button' class='btn-u' data-target='#reviewCommentModal' data-toggle='modal'>Add new comment</button>
									</section>
									<% } %>
								</div>
								<div id="scrollbar4" class="panel-body no-padding mCustomScrollbar" data-mcs-theme="minimal-dark">
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
										<img src="https://cdn2.iconfinder.com/data/icons/lil-faces/233/lil-face-4-512.png" alt="">
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
				</div>
			<div class='col-md-1' style= "min-height: 2000px;  vertical-align: middle;">
				<% if(next_slide_id != 0) { %>
					<a style="z-index:99999; width: 100%;" class="right carousel-control" 
					href="<%=baseURL%>fill_tempate.jsp?ppt_id=<%=request.getParameter("ppt_id") %>&slide_id=<%=next_slide_id%>&slide_type=<%=next_slide_type %>"> 
						<span  class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					</a>
				<% }%>
			</div>
		</div>
		</form>
	</div>

  <div class='modal fade' id='imageModal' tabindex='-1' 
  	role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
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
						<input type="hidden" id="slide_type" name="slide_type" value="<%=request.getParameter("slide_type")%>" />
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
	<!-- JS Customization -->
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="http://rvera.github.io/image-picker/image-picker/image-picker.js" type="text/javascript"></script>

	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js" type="text/javascript" charset="utf-8"></script>
	<script src="//cdn.ckeditor.com/4.5.9/full/ckeditor.js"></script>
	
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/plugins/parsley.js"></script>

	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
	<script type="text/javascript">
		function initTextArea() {
			try {
				$("#image-picker").imagepicker();
				CKEDITOR.replace('paragraph', {
					height : 100
				});
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
							var text1 = CKEDITOR.instances.Editor.document.getBody().getHtml()
							var iframeInner = $('#prv').contents().find('#slide_paragraph').html(text1);
						});
					}
				});
				
				bodyEditor.on('change', function() {
					var text1 = bodyEditor.document.getBody().getHtml()
					var iframeInner = $('#prv').contents().find(
							'#data_slide_paragraph').html(text1);
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
						var iframeInner = $('#prv').contents().find('#data_' + id).html($('#' + id).val());
					});
				} else {
					console.log(id);
				}
			});
			$('#image-picker').on( 'change', function() {
				var id = $(this).find(":checked").attr('id');
				$('#prv').contents().find('#data_image_url').attr( "src", $('#' + id).data('img-src'));
			});
			$('#image-bg-picker').on( 'change', function() {
				var bgurl = $(this).find(":checked").val();
				$('#prv').contents().find('.slide-backgrund').css( 'background-image', "url(" + bgurl + ")");
				$('#prv').contents().find('.slide-background').css( 'background-size', "cover");
			});
		}
		function initColorChange() {
			$('#slide_color').on( 'change', function() {
				$('#prv').contents().find('.slide-background').css( 'background-color', $('#slide_color').val());
			});
		}
		
		$(document).ready(function() {
			initTextArea();
			initHooks();
			initColorChange();
			
			$("#slidy_type_id").change(function() {
				var slideId = <%=request.getParameter("slide_id")%> ;
				if(slideId != null) {
					var url = "	<%=baseURL%>fill_tempate.jsp?ppt_id=<%=request.getParameter("ppt_id")%>&slide_id=<%=request.getParameter("slide_id")%>&slide_type="+$(this).val();
	 			} else {
					var url = "	<%=baseURL%>fill_tempate.jsp?ppt_id=<%=request.getParameter("ppt_id")%>&slide_type="+$(this).val();
				}
				window.location.href=url;
			});
			  
		});
	</script>
</body>
</html>