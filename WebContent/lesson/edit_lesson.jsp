<%@page import="com.istarindia.apps.services.UiThemeService"%>
<%@page import="com.istarindia.apps.Themes"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>
<%@page import="com.istarindia.apps.cmsutils.reports.ReportUtils"%>

<%@page import="com.istarindia.apps.services.LessonService"%>
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
                    int task_id = Integer.parseInt(request.getAttribute("task_id").toString());
                %>
		</div>
		<BR />
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
								<form action="/content/update_lesson" id="sky-form4"
									class="sky-form">
									<input type="hidden" name="lesson_id"
										value="<%=lesson.getId()%>" /> <input type="hidden"
										name="cmsession_id" value="<%=lesson.getCmsession().getId()%>" />
									<fieldset>

										<section>
											<label>Title of Lesson</label> <label class="input">
												<input value="<%=lesson.getTitle()%>" type="text"
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
											<label>Lesson Themes</label>
											<div class="row">
												<%
													boolean flag = false;
												List thes = (new UiThemeDAO()).findAll();
												 Collections.sort(thes);

													for (UiTheme themeName : (List<UiTheme>)thes) {
												%>
												<div class="col col-6">
													<%
														if (lesson.getLesson_theme().equalsIgnoreCase(themeName.getId().toString())) {
																flag = true;
													%>
													<label class="radio"><input type="radio"
														name="lesson_theme" checked="checked"
														value="<%=themeName.getId()%>"> <i
														class="rounded-x"></i><%=themeName.getName()%></label>
												</div>
												<%
													} else {
												%>
												<label class="radio"><input type="radio"
													name="lesson_theme" value="<%=themeName.getId()%>">
													<i class="rounded-x"></i><%=themeName.getName()%></label>
											</div>
											<%
												}
												}
											%>
											<%
												if (flag == false) {
											%><div class="col col-4">
												<label class="radio"><input type="radio"
													checked="checked" name="lesson_theme" value="0"> <i
													class="rounded-x"></i>Dafault</label>
											</div>
											<%
												}
											%>
									
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
							
							<section><button type='button'  class='btn-u' data-target='#myModal' data-toggle='modal' >Choose more</button></section>

							</fieldset>

									<footer>
										<button type="submit" class="btn-u">Update Lesson</button>
									</footer>
								</form>

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

							<%
									try {
										SlideDAO slideDAO = new SlideDAO();
										TaskDAO TDAO = new TaskDAO();
										Task task = new Task();
										task.setItemType("LESSON");
										task.setItemId(lesson.getId());
										task = TDAO.findByExample(task).get(0);
										TaskLogDAO dao = new TaskLogDAO();
										TaskLog sample = new TaskLog();
										sample.setTaskId(task.getId());
										sample.setItemType("SLIDE");
										List<TaskLog> items = dao.findByExample(sample);
										for (TaskLog log : items) {

											IstarUser user = (new IstarUserDAO()).findById(log.getActorId());
											if(slideDAO.findById(log.getItem_id()) != null) {
											
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
										<a class="" target="_blank" href="/content/fill_tempate.jsp?ppt_id=<%=lesson.getPresentaion().getId() %>&slide_id=<%=log.getItem_id() %>&slide_type=<%=slideDAO.findById(log.getItem_id()).getTemplate() %>">Edit slide</a>
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
                                //Edited By Kunal on 24/03/2016 for editing question
                                if (request.getAttribute("__EDIT_QUESTION") != null) {
                                    String questionId = request.getAttribute("__QUESTION_ID").toString();
                                    out.println(lessonUtils.getFormForQuestionEdit(lesson, questionId));
                                } else {
                                    out.println(lessonUtils.getEditForm(lesson, task_id));
                                }

                                // Create Two block 
                                // Top one has to be add new SLide  
                                //Botton one is list of slides
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

	<script
		src="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
		type="text/javascript" charset="utf-8"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/js/plugins/validation.js"></script>

	<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.colVis.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>
	
	<script type="text/javascript">
	var responsiveHelper_dt_basic = undefined;
	var responsiveHelper_datatable_fixed_column = undefined;
	var responsiveHelper_datatable_col_reorder = undefined;
	var responsiveHelper_datatable_tabletools = undefined;
	
	var breakpointDefinition = {
		tablet : 1024,
		phone : 480
	};
        	//Lesson details validation
            jQuery(document).ready(function () {
                App.init();
                Validation.lessonValidation();

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
        	    }
        	    else {
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
				});

		function DisplayFilePath() {
			var filepath = $(":file").val();
			var filename = filepath.substr(filepath.lastIndexOf('\\') + 1,
					filepath.length);
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

		$(document).ready(function() {
		});

		function openWin(url) {
			myWindow = window.open(url, "", "width=412, height=659"); // Opens a new window

			return false;
		}

		function openWinSpeaker(url) {
			myWindow = window.open(url, "", "width=1024, height=768"); // Opens a new window

			return false;
		}
		/* jQuery(document)
		        .ready(
		                function () {
		                    App.init();
		                    try {
		                        tinymce
		                                .init({
		                                    selector: 'textarea',
		                                    height: 50,
		                                    plugins: [
		                                        'advlist autolink lists link image charmap print preview anchor',
		                                        'searchreplace visualblocks code fullscreen',
		                                        'insertdatetime media table contextmenu paste code'],
		                                    toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
		                                    content_css: [
		                                        '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
		                                        '//www.tinymce.com/css/codepen.min.css']
		                                });
		                    } catch (err) {
		                        // TODO: handle exception
		                    }
		                }); */
	</script>
	<!--[if lt IE 9]>
        <script src="assets/plugins/respond.js"></script>
        <script src="assets/plugins/html5shiv.js"></script>
        <script src="assets/plugins/placeholder-IE-fixes.js"></script>
        <![endif]-->

</body>
</html>