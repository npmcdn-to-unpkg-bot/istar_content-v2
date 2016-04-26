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
              href="<%=baseURL%>assets/css/theme-colors/default.css"
              id="style_color">
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
                                                   name="cmsession_id"
                                                   value="<%=lesson.getCmsession().getId()%>" />
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
                                                               class="tooltip tooltip-bottom-right">The duration of
                                                            the lesson</b>
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
                                                    <label class="label">Lesson Themes</label>
                                                    <div class="row">
                                                        <%
                                                            for (String themeName : Themes.lessonThemes) {
                                                        %>
                                                        <div class="col col-4">
                                                            <%
                                                                if (lesson.getLesson_theme().equalsIgnoreCase(themeName)) {
                                                            %>
                                                            <label class="radio"><input type="radio"
                                                                                        name="lesson_theme" checked="checked"
                                                                                        value="<%=themeName%>"> <i class="rounded-x"></i><%=themeName%></label>
                                                                <%
                                                                } else {
                                                                %>
                                                            <label class="radio"><input type="radio"
                                                                                        name="lesson_theme" value="<%=themeName%>"> <i
                                                                                        class="rounded-x"></i><%=themeName%></label>
                                                                <%
                                                                    }
                                                                %>
                                                        </div>
                                                        <%
                                                            }
                                                        %>
                                                    </div>
                                                </section>

                                                <section>
                                                    <label class="label">Subject </label>
                                                    <div class="row">
                                                        <%
                                                            for (String subject : Themes.subjects) {
                                                        %>
                                                        <div class="col col-4">
                                                            <%
                                                                if (lesson.getLesson_subject().equalsIgnoreCase(subject)) {
                                                            %>
                                                            <label class="radio"><input type="radio"
                                                                                        name="lesson_subject" checked="checked"
                                                                                        value="<%=subject%>"> <i class="rounded-x"></i><%=subject%></label>
                                                                <%
                                                                } else {
                                                                %>
                                                            <label class="radio"><input type="radio"
                                                                                        name="lesson_subject" value="<%=subject%>"> <i
                                                                                        class="rounded-x"></i><%=subject%></label>
                                                                <%
                                                                    }
                                                                %>
                                                        </div>
                                                        <%
                                                            }
                                                        %>
                                                    </div>
                                                </section>
                                                <section>
                                                    <label class="label">List of Learning Objectives</label>
                                                    <div class="row">
                                                        <%
                                                            for (LearningObjective obj : lesson.getLearningObjectives()) {
                                                        %>
                                                        <div class="col col-12">
                                                            <label class="checkbox"><input type="checkbox"
                                                                                           name="learningObjectives" checked="checked"
                                                                                           value="<%=obj.getId()%>"> <i></i><%=obj.getTitle()%></label>
                                                        </div>
                                                        <%
                                                            }
                                                        %>
                                                    </div>
                                                </section>


                                            </fieldset>

                                            <footer>
                                                <button type="submit" class="btn-u">Update Lesson</button>
                                            </footer>
                                        </form>

                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="col-sm-12">

                            <div class=" col-md-12 ">
                                <div class="panel panel-sea">

                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            <i class="fa fa-comments-o"></i> Review Comments
                                        </h3>
                                    </div>

                                    <div class="panel-body">

                                        <div class="panel panel-profile profile">
                                            <%
                                                TaskLogDAO dao = new TaskLogDAO();
                                                TaskLog sample = new TaskLog();
                                                if (request.getParameterMap().containsKey("task_id")) {
                                                    sample.setTaskId(Integer.parseInt(request.getParameter("task_id")));
                                                } else {
                                                    sample.setTaskId(Integer.parseInt(request.getAttribute("task_id").toString()));
                                                }
                                                sample.setItemType("LESSON");

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
                                            %>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>
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
                                    a Assessment</a> <a class="btn-u btn-u-xs btn-u-orange" href="/content/create_game?lesson_id=<%=lesson.getId()%>&task_id=<%=task_id%>"
                                                    style="margin-top: 20px">Create a Game</a>
                            </p>
                        </div>
                        <%
                            } else {
                                //Edited By Kunal on 24/03/2016 for editing question
                                LessonUtils utils = new LessonUtils();
                                if (request.getAttribute("__EDIT_QUESTION") != null) {
                                    String questionId = request.getAttribute("__QUESTION_ID").toString();
                                    out.println(utils.getFormForQuestionEdit(lesson, questionId));
                                } else {
                                    out.println(utils.getEditForm(lesson, task_id));
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
            
            <script type="text/javascript">
            (function($) {
                if (!$.curCSS) {
                   $.curCSS = $.css;
                }
            })(jQuery);
            
            </script>
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
            src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"
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

        <script type="text/javascript">
        	//Lesson details validation
            jQuery(document).ready(function () {
                App.init();
                Validation.lessonValidation();
                //$('#sortable').tableDnD();
                $( "#slidess_ord" ).sortable();
                $( "#save_order" ).submit(function( event ) {
                	var idsInOrder = $('#slidess_ord').sortable("toArray");
                	//alert( idsInOrder );
                	$('#order_holder').val(idsInOrder);
                	//<%=baseURL%>/content/edit_lesson?task_id=1
                	//window.location = '<%=baseURL%>/content/edit_lesson?task_id='+task_id;
                	//return false;
                	
                	 // event.preventDefault();
                	});
            });

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
            
            $(document).ready(function() {
                $('#checkBtn').on('click', function(e) {
                    var cnt = $("input[name='answers']:checked").length;
                    if (cnt < 1) 
                    {
                    	$("#err").text("(Note: At least one correct option should be selected before proceeding)");
                        e.preventDefault();
                    }
                });
            });
            
            function openWin(url) {
                myWindow = window.open(url, "", "width=412, height=659"); // Opens a new window

                return false;
            }

            function openWinSpeaker(url) {
                myWindow = window.open(url, "", "width=1024, height=768"); // Opens a new window

                return false;
            }
            jQuery(document)
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
                            });
        </script>
        <!--[if lt IE 9]>
        <script src="assets/plugins/respond.js"></script>
        <script src="assets/plugins/html5shiv.js"></script>
        <script src="assets/plugins/placeholder-IE-fixes.js"></script>
        <![endif]-->

    </body>
</html>
