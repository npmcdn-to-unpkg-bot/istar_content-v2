<%@page import="com.istarindia.apps.services.UiThemeService"%>
<%@page import="com.istarindia.apps.Themes"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.cmsutils.reports.ReportUtils"%>

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
<style type="text/css">
#lo-modal-dialog {
	width: 100% !important;
    height: 100% !important;
    padding-right: 2% !important;
    padding-left: 2% !important;
}

#lo.modal-content {
  height: auto !important;
  min-height: 100% !important;
  border-radius: 0 !important;
}

</style>
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
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.css">
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
<link
	href="http://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"
	rel="stylesheet">

<link
	href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css"
	rel="stylesheet">
	
</head>

<body>

	<div class="wrapper">
		<jsp:include page="../content_admin/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Edit Lesson</h1>
				<!-- <ul class="pull-right breadcrumb">
                                <li><a href="/">Home</a></li>
                                <li><a href="">Content Admin </a></li>
                                <li class="active">Edit Lesson</li>
                        </ul> -->
			</div>
			<%
                    Lesson lesson = (Lesson) request.getAttribute("lesson");
                    int task_id = lesson.getTask().getId();//Integer.parseInt(request.getAttribute("task_id").toString());
                %>
		</div>
		<BR />
		<div class="container-fluid height-1000"
			style="padding: 0px !important">
			<div class="col-md-3">
				<div class="col-md-12">
					<div class=" col-md-12 ">
						<div class="panel panel-sea">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-tasks"></i>Lesson Details
								</h3>
							</div>
							<div class="panel-body">
								<form action="/content/update_lesson" id="sky-form4"
									class="sky-form">
									<input type="hidden" name="lesson_id"
										value="<%=lesson.getId()%>" /> <input type="hidden"
										name="cmsession_id" value="<%=lesson.getCmsession().getId()%>" />
									<fieldset>

										<section>
											<label>Title of Lesson</label> <label class="input">
												<input value="<%=lesson.getTitle()%>" type="text" spellcheck="true"
												name="title" placeholder="Title of Lesson"> <b
												class="tooltip tooltip-bottom-right">The title of the
													lesson</b>
											</label>
										</section>

										<section>
											<label>Duration of Lesson</label> <label class="input">
												<input value="<%=lesson.getDuration()%>" type="number"
												name="duration" placeholder="Duration of Lesson"> <b
												class="tooltip tooltip-bottom-right">The duration of the
													lesson</b>
											</label>
										</section>
										<section>
											<label> Tags</label> <label class="input"> <input
												data-role="tagsinput" value="<%=lesson.getTags()%>"
												type="text" name="Tags" class="tagcontainer"
												placeholder="Tags of Lesson"> <b
												class="tooltip tooltip-bottom-right">The tags of the
													lesson</b>
											</label>
										</section>
										<section>
											<label>Mobile Lesson Themes</label>
											<select class="form-control input-lg" name="lesson_theme">
												<%
													boolean flag = false;
												List thes = (new UiThemeDAO()).findAll();
												 Collections.sort(thes);

													for (UiTheme themeName : (List<UiTheme>)thes) {
												%>
												<%
														if (lesson.getLesson_theme().equalsIgnoreCase(themeName.getId().toString())) {
																flag = true;
													%>
													
													<option value="<%=themeName.getId() %>" selected='selected'><%=themeName.getName() %></option>
												<% } else {  %>
													<option value="<%=themeName.getId() %>"><%=themeName.getName() %></option>
												
												<% } } %>
											</select>
										</section>
										<section>
											<label>Desktop Lesson Themes</label>
											<select class="form-control input-lg" name="lesson_desktop_theme">
												<%
												

													for (UiTheme themeName : (List<UiTheme>)thes) {
												%>
												<%
														if (lesson.getLesson_theme_desktop().equalsIgnoreCase(themeName.getId().toString())) {
																flag = true;
													%>
													<option value="<%=themeName.getId() %>" selected='selected'><%=themeName.getName() %></option>
												<% } else {  %>
													<option value="<%=themeName.getId() %>"><%=themeName.getName() %></option>
												
												<% } } %>
											</select>
										</section>
							<section>
								<label>Subject </label>
								<div class="row">
									<%
										flag = false;
										for (String subject : Themes.subjects) {
									%>
									<div class="col col-4">
										<%
											if (lesson.getLesson_subject().equalsIgnoreCase(subject)) {
													flag = true;
										%>
										<label class="radio"><input type="radio"
											name="lesson_subject" checked="checked" value="<%=subject%>">
											<i class="rounded-x"></i><%=subject%></label>
										<%
											} else {
										%>
										<label class="radio"><input type="radio"
											name="lesson_subject" value="<%=subject%>"> <i
											class="rounded-x"></i><%=subject%></label>
										<%
											}

												if (flag == false) {
										%>
										<label class="radio"><input type="radio"
											checked="checked" name="lesson_subject" value="none">
											<i class="rounded-x"></i>None</label>
									</div>
									<%
										}
										}
									%>
								</div>
							</section>

							<section>

								<%
									LessonUtils lessonUtils = new LessonUtils();
									ArrayList<LearningObjective> lesson_lo_list = lessonUtils.getSelectedLOsOftheLesson(lesson.getId());
									ArrayList<LearningObjective> session_lo_list = lessonUtils.getUnselectedLOsInTheSameSession(lesson.getId());
								%>

								<label>List of Selected Learning Objectives</label>
								<div class="row">

									<%
										if (!lesson_lo_list.isEmpty()) {
											for (LearningObjective lesson_lo : lesson_lo_list) {
									%>

									<div class="col col-12">
										<label class="checkbox"><input type="checkbox"
											name="learningObjectives" checked="checked"
											value="<%=lesson_lo.getId()%>"> <i></i><%=lesson_lo.getTitle()%></label>
									</div>

									<%
										}
										} else {
									%>

									<div class="col col-12">
										<label><i class="fa fa-exclamation"></i>
											None</label>
									</div>

									<%
										}
									%>
								</div>
								<br> <label>List of Learning Objectives to choose from Session</label>
								<div class="row">

									<%
										if (!session_lo_list.isEmpty()) {
											for (LearningObjective session_lo : session_lo_list) {
									%>

									<div class="col col-12">
										<label class="checkbox"><input type="checkbox"
											name="learningObjectives" value="<%=session_lo.getId()%>">
											<i></i><%=session_lo.getTitle()%></label>
									</div>

									<%
										}
										} else {
									%>

									<div class="col col-12">
										<label class='label'><i class="fa fa-exclamation"></i> None</label>
									</div>

									<%
										}
									%>
									
								</div>
							</section>
							
							<section>
								<button type='button'  class='btn-u' data-target='#myModal' data-toggle='modal' >Choose more</button>
							</section>

							</fieldset>

									<footer >
										<button type="submit" class="btn-u" style="float:left">Update </button>
										<button class="btn-u"  id="send-for-review" data-toggle="modal" style="float:right"
											data-target="#confirm-send-for-review-modal" type="button">Send for Review</button>
									</footer>
								</form>

							</div>
							
							<div class="modal fade" id="confirm-send-for-review-modal"
								tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
								aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">Confirm Submit</div>
										<div class="modal-body">Are you sure you want to send the lesson for review?</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
											<button id="confirm-send-for-review-btn" type="submit" class="btn btn-success success">Send for review</button>
										</div>
									</div>
								</div>
							</div>
							
							<div class='modal fade' id='myModal' tabindex='-1' role='dialog'
								aria-labelledby='myModalLabel' aria-hidden='true'>
								<div id='lo-modal-dialog' class='modal-dialog'>
									<div id='lo-modal-content' class='modal-content'>
										<div class='modal-header'>
											<button aria-hidden='true' data-dismiss='modal' class='close'
												type='button'>×</button>
											<h4 id='myModalLabel1' class='modal-title'>Choose
												Learning Objectives</h4>
										</div>
										<div class='modal-body'>
											<form id='lo_form' class='form-horizontal' role='form'
												action='/content/update_lesson' method='POST'>
												<input type="hidden" name="lesson_id"
										value="<%=lesson.getId()%>" /> <input type="hidden"
										name="cmsession_id" value="<%=lesson.getCmsession().getId()%>" />
										<input type='hidden' name='lesson_id'
													value=" <%=lesson.getId()%>"> <input
													type='hidden' id='learningObjectives' name='learningObjectives' />
												<input type='hidden' id='only_learning_objectives'
													value='true' name='only_learning_objectives' />
												<div class='form-group'>
													<label style='margin-left: 1%; color: gray'>[NOTE:
														Select learning objectives and submit]</label><label id='errLO'
														style='color: red'></label>
													<button type='submit' id='loBtn' class='btn-u'
														style='float: right; margin-right: 1%;'>Proceed</button>
													<div class='col-lg-offset-2 col-lg-10'
														style='margin: 0% !important; width: 100%'>

														<%
															HashMap<String, String> conditions = new HashMap();
															conditions.put("lesson_id",lesson.getId().toString());
														
														%>
														<%=(new ReportUtils()).getReport(91, conditions, ((IstarUser) request.getSession().getAttribute("user")), "LESSON").toString()%>

													</div>
												</div>
											</form>
										</div>
										<div class='modal-footer'>
											<button data-dismiss='modal' class='btn-u btn-u-default'
												type='button'>Close</button>
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
								<h3 >
									<i class="fa fa-comments-o">Review Comments</i>
								</h3>
							</div>

							<div id="scrollbar4 " style="height: auto !important" class="panel-body no-padding mCustomScrollbar" data-mcs-theme="minimal-dark">
								
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
												<strong><%=user.getName()%> </strong> 
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
												<strong><%=user.getName()%> </strong> 
											</div>
											<div class="col-md-9">
												<%=log.getComments()%>
											</div>
										</div>
									</div>
									<div style="float: right;">
									<% if(slideDAO.findById(log.getItem_id()) != null ) { %>
										<a class="" target="_blank" href="/content/fill_template.jsp?ppt_id=<%=lesson.getPresentaion().getId() %>&slide_id=<%=log.getItem_id() %>">Edit slide</a>
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

			<div class="col-md-9">
				<%
                            if (LessonService.isEmptyLesson(lesson)) {
                        %>
				<div class="alert alert-warning fade in text-center">
					<h4>Please Create a Presentation/Assessment/Game</h4>
					<p>
						<a class="btn-u btn-u-xs btn-u-red"
							href="/content/create_ppt?lesson_id=<%=lesson.getId()%>&task_id=<%=task_id%>">Create
							a Presentation</a> <a class="btn-u btn-u-xs btn-u-sea"
							href="/content/create_assesment?lesson_id=<%=lesson.getId()%>&task_id=<%=task_id%>">Create
							a Assessment</a> <a class="btn-u btn-u-xs btn-u-orange"
							href="/content/create_game?lesson_id=<%=lesson.getId()%>&task_id=<%=task_id%>"
							style="margin-top: 20px">Create a Game</a>
					</p>
				</div>
				<%
                      } else {
                          if (request.getParameterMap().containsKey("question_id")) {
                              int questionId = Integer.parseInt(request.getParameter("question_id").toString());
                              int assessmentId = Integer.parseInt(request.getParameter("assessment_id").toString());
                              out.println(lessonUtils.getFormForAssessmentQuestionEdit(assessmentId, questionId, request));
                          } else {
                              out.println(lessonUtils.getEditForm(lesson, task_id));
                          }
                      }
                %>
			</div>
		</div>
		<jsp:include page="../content_admin/includes/footer.jsp"></jsp:include>
	</div>

	<!-- JS Global Compulsory -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>

	<script src="http://code.jquery.com/jquery-2.2.3.js"></script>
	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

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
		src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"
		type="text/javascript" charset="utf-8"></script>

	<script src="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
		type="text/javascript" charset="utf-8"></script>

	<script src="<%=baseURL%>assets/plugins/ckeditor/ckeditor.js"></script>
	<!-- JS Page Level -->
	<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.colVis.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>
	
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/plugins/validation.js"></script>
	
	<script type="text/javascript">
			var responsiveHelper_dt_basic = undefined;
			var responsiveHelper_datatable_fixed_column = undefined;
			var responsiveHelper_datatable_col_reorder = undefined;
			var responsiveHelper_datatable_tabletools = undefined;
			
			var breakpointDefinition = {
				tablet : 1024,
				phone : 480
			};
			
			//On confirm button (in the modal) click; mark the lesson as completed
			$('#confirm-send-for-review-btn').click(function() {
				var task_id = <%=request.getParameter("task_id")%> ;
				var url = "<%=baseURL%>change_status?task_id="+task_id+"&new_status=COMPLETED";
				window.location.href=url;
			});
			
        	//Lesson details validation
            jQuery(document).ready(function () {
                App.init();
                Validation.lessonValidation();
				
                InitTextAreaForAssessment();
                
                //Slide list is sortable
                $( "#slidess_ord" ).sortable();
                $( "#update_order" ).submit(function( event ) {
                	var idsInOrder = $('#slidess_ord').sortable("toArray");
                	$('#order_holder').val(idsInOrder);
                });
                
                
				var selected = [];
                
                if ( $.fn.dataTable.isDataTable( "#datatable_report_91" ) ) {
        	        table = $("#datatable_report_91" ).DataTable({
        	        	destroy: true,
        	        	"processing": true,
                        "rowCallback": function( row, data ) {
                            if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                                $(row).addClass('selected');
                                
                            }
                        }
        	        } );
        	    } else {
        	        table = $( "#datatable_report_91" ).DataTable( {
        	        	"processing": true,
                        "rowCallback": function( row, data ) {
                            if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                                $(row).addClass('selected');
                            }
                        }
        	        } );
        	    }
                $('#datatable_report_91_body').on('click', 'tr', function () {
                    var id = this.id;
                    var index = $.inArray(id, selected);
             
                    if ( index === -1 ) {
                        selected.push( id );
                    } else {
                        selected.splice( index, 1 );
                    }
             
                    $(this).toggleClass('selected');
                } );
                
                $('#checkBtn').on('click', function(e) {
                    var cnt = $("input[name='answers']:checked").length;
                    if (cnt < 1) 
                    {
                    	$("#err").text("(Note: At least one correct option should be selected before proceeding)");
                        e.preventDefault();
                    }

                });

            	$('#loBtn').on('click', function(e) {
                    var ids = $.map(table.rows('.selected').data(), function (item) {
                        return item[0]
                    });
                    
                    if (ids.length < 1) 
                    {
                    	$("#errLO").text("  (Note: Please select at least one learning objective before proceeding)");
                        e.preventDefault();
                    } else {
            			$('#learningObjectives').val(ids);
                    }

                });

				$('#confirm-delete-slide-modal').on( 'show.bs.modal', function(e) {
						var $invoker = $(e.relatedTarget);
						var slide_id = $invoker.data('slide-id');
						var delete_url = $('#confirm-delete-slide-btn').attr("href").split('slide_id=')[0] +"slide_id="+ slide_id;
						$('#confirm-delete-slide-btn').attr("href", delete_url);
					});

				$('#confirm-duplicate-slide-modal').on( 'show.bs.modal', function(e) {
						var $invoker = $(e.relatedTarget);
						var slide_id = $invoker.data('slide-id');
	                	var idsInOrder = $('#slidess_ord').sortable("toArray");
						var duplicate_url = $('#confirm-duplicate-slide-btn').attr("href").split('slide_id=')[0] +"slide_id="+ slide_id+"&order_holder="+idsInOrder;
						$('#confirm-duplicate-slide-btn').attr("href", duplicate_url);
					});
				});

		function DisplayFilePath() {
			var filepath = $(":file").val();
			var filename = filepath.substr(filepath.lastIndexOf('\\') + 1, filepath.length);
			document.getElementById("formfield").value = filename;
		}
		
		$('input.correctOption').on('change', function() {
			if ($('#qType').val() == '1') {
				$('input.correctOption').not(this).prop('checked', false);
			}
		});

		$('#qType').on('change', function() {
			if ($('#qType').val() == '1') {
				$('input.correctOption').removeAttr('checked');
			}
		});

		function openWin(url) {
			myWindow = window.open(url, "", "width=400, height=659"); // Opens a new window

			return false;
		}

		function openWinSpeaker(url) {
			myWindow = window.open(url, "", "width=1024, height=768"); // Opens a new window

			return false;
		}
		
		function InitTextAreaForAssessment() {
			$('.init_textarea').each(function(i, obj) {
				var elemId = $(obj).attr('id');
				console.log(elemId);
				try {
					CKEDITOR.replace(elemId, { 
						"extraPlugins" : 'imagebrowser',
						"imageBrowser_listUrl" : "/content/GalleryJsonController"
					});
				} catch (err) {
					console.log("CKEditor faced issue while initializing-" + err);
				}
			});
			
		}
		
	</script>

</body>
</html>