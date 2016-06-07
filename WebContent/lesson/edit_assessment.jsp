<%@page import="com.istarindia.apps.Themes"%>
<%@page import="com.istarindia.apps.cmsutils.LessonUtils"%>

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
.modal-dialog {
	width: 100% !important;
    height: 100% !important;
    padding-right: 2% !important;
    padding-left: 2% !important;
}

.modal-content {
  height: auto !important;
  min-height: 100% !important;
  border-radius: 0 !important;
}</style>
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
                    int assessment_id = Integer.parseInt(request.getParameter("assessment_id").toString());
					Assessment assessment = (new AssessmentDAO()).findById(assessment_id);
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
									<i class="fa fa-tasks"></i>Assessment Details
								</h3>
							</div>
							<div class="panel-body">
								<form action="/content/update_assessment" id="sky-form4"
									class="sky-form">
									<input type="hidden" name="assessment_id" value="<%=assessment_id%>" /> 
									<input type="hidden" name="update_assessment" value="true" /> 
									<fieldset>
									
										<section>
											<label>Assessment Title</label> <label class="input">
												<input value="<%=assessment.getAssessmenttitle()%>" type="text"
												name="title" placeholder="Assessment Title"> <b
												class="tooltip tooltip-bottom-right">Assessment Title</b>
											</label>
										</section>
										
										<%
											ArrayList<String> types = new ArrayList();
											types.add("STATIC");
											types.add("ADAPTIVE");
											types.add("TREE");
											types.add("RANDOM");
											
											String selected = "";
											
										%>


										<section>
											<label>Assessment Type</label> <label class='input'>
												<select class='form-control valid' name='assessment_type'>

													<%
														for (String type : types) {
															if (assessment.getAssessmentType().equalsIgnoreCase(type)) {
													%>

													<option value='<%=type%>' selected='selected'><%=type%></option>

													<%
														} else {
													%>
													<option value='<%=type%>'><%=type%></option>

													<%
														}

														}
													%>

											</select>
											</label>
										</section>

										<section>
											<label>Duration</label> <label class="input">
												<input value="<%=assessment.getAssessmentdurationminutes()%>" type="number"
												name="duration" placeholder="Duration of Lesson"> <b
												class="tooltip tooltip-bottom-right">The duration of the Assessment</b>
											</label>
										</section>
										
										<section>
											<label>Number of Questions</label> <label class="input">
												<input value="<%=assessment.getNumber_of_questions()%>" type="number"
												name="number_of_questions" placeholder="Number of Questions"> <b
												class="tooltip tooltip-bottom-right">Number of Questions</b>
											</label>
										</section>
									</fieldset>
									
									<footer>
										<button type="submit" class="btn-u" style="float:right;" >Update</button>
									</footer>
								</form>

							</div>
						</div>
					</div>

				</div>
			</div>

			<div class="col-md-8">
				<%
					LessonUtils utils = new LessonUtils();
					if (request.getParameterMap().containsKey("question_id")) {
						int question_id = Integer.parseInt(request.getParameter("question_id").toString());
						out.println(utils.getFormForAssessmentQuestionEdit(assessment_id, question_id,request));
					} else {
						out.println(utils.getAssessmentEditForm(assessment_id));
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
                var selected = [];
                
                if ( $.fn.dataTable.isDataTable( "#datatable_report_92" ) ) {
        	        table = $("#datatable_report_92" ).DataTable({
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
        	        table = $( "#datatable_report_92" ).DataTable( {
        	        	"processing": true,
                        "rowCallback": function( row, data ) {
                            if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                                $(row).addClass('selected');
                            }
                        }
        	        } );
        	    }
                $('#datatable_report_92_body').on('click', 'tr', function () {
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
            			$('#selected_items').val(ids);
                    }

                });
            });

			function DisplayFilePath(){
                var filepath=$(":file").val();
                var filename=filepath.substr(filepath.lastIndexOf('\\')+1,filepath.length);
            document.getElementById("formfield").value = filename;
            }
            $('input.correctOption').on('change', function() {
            	if($('#qType').val()=='1'){
                $('input.correctOption').not(this).prop('checked', false);  
            	}
            });

            $('#qType').on('change', function() {
            	if($('#qType').val()=='1'){
            		$('input.correctOption').removeAttr('checked');
            	}
            });
            
        </script>
</body>
</html>