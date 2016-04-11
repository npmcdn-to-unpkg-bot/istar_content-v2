<%-- 
    Document   : batchgroup_listing
    Created on : 08 April, 2016, 12:52:00 PM
    Author     : Kunal Chakravertti
--%>

<<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>

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
        <title>Batch-Group | iStar CMS</title>

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
              href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
        <link rel="stylesheet"
              href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">
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
            <jsp:include page="includes/header.jsp"></jsp:include>
                <div class="breadcrumbs">
                    <div class="container-fluid ">
                        <h1 class="pull-left">Create/Update Batch-Group</h1>

                    </div>

                </div>
                <div class="container-fluid height-1000"
                     style="padding: 0px !important">
                    <div class="row">
                        <form action="/content/batch_controller?_action=savebatchgrp" id="sky-form4" class="sky-form" method="post">

                            <div class="col-md-6">
                                <div class="alert alert-warning fade in text-center">
                                    <fieldset>
                                        <section>
                                            <label>Title of Batch-Group*</label> <label class="input" > <input
                                                    value="${batchGroup.name}" type="text" name="name" placeholder="Name of Batch-Group">
                                            <b class="tooltip tooltip-bottom-right">The name of the
                                                Batch-Group*</b>
                                        </label>
                                    </section>
                                    <section>
                                        <label>Maximum Students*</label> <label class="input">
                                            <input value="${batchGroup.maxStudents}" type="number" name="students"
                                                   placeholder="Maximum number Of Students"> <b
                                                   class="tooltip tooltip-bottom-right">Maximum Number of Students
                                            </b>
                                        </label>
                                    </section>
                                    <section>
                                        <label>Course</label> <label class="input">
                                            <select name="course">
                                                <c:if test="${courseId == '0'}">
                                                    <option value="0">--Select--</option>
                                                    <c:forEach items="${courseList}" var="cs">
                                                        <option value="${cs.id}">${cs.courseName}</option>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${courseId != '0'}">
                                                    <option value="0">--Select--</option>
                                                    <c:forEach items="${courseList}" var="cs">
                                                        <c:if test="${courseId == cs.id}">
                                                            <option value="${cs.id}" selected="selected">${cs.courseName}</option>
                                                        </c:if>
                                                        <c:if test="${courseId != cs.id}">
                                                            <option value="${cs.id}">${cs.courseName}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                            </select>

                                        </label>
                                    </section>

                                    <input type="hidden" name="oldcourse" value="${courseId}"/>
                                    <input type="hidden" name="id" value="${batchGroup.id}"/>
                                </fieldset>
                                <footer>
                                    <c:if test="${action == 'create'}">
                                        <button type="submit" class="btn-u"  >Create Batch-Group</button>
                                    </c:if>
                                    <c:if test="${action == 'edit'}">
                                        <button type="submit" class="btn-u"  >Update Batch-Group</button>
                                    </c:if>

                                    <label id="err" style="display: block;color:#ee9393"></label>


                                </footer>


                            </div>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    </div>
    <jsp:include page="includes/footer.jsp"></jsp:include>
    </div>


    <!-- JS Global Compulsory -->
    <script type="text/javascript"
    src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
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
<script type="text/javascript"
src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>
<!-- JS Customization -->
<script
    src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"
type="text/javascript" charset="utf-8"></script>
<script src="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"></script>

<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
type="text/javascript" charset="utf-8"></script>

<!-- JS Page Level -->
<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
<script type="text/javascript" src="<%=baseURL%>assets/js/plugins/validation.js"></script>
<script type="text/javascript">
    function myFunction() {
        var selectedElmsIds = $('#html').jstree("get_selected");
        if (selectedElmsIds == "") {
            event.preventDefault();
            document.getElementById("err").innerHTML = "Please select the session and try again";
            return false;
        }
        else {
            document.getElementById("err").innerHTML = "";
        }
        $('#selected_items').val(selectedElmsIds);
        console.log(selectedElmsIds);

    }
    jQuery(document).ready(function () {
        App.init();
        Validation.lessonValidation();
        $('#html1').jstree({
            "core": {
                "multiple": false,
                "themes": {
                    "variant": "large"
                }
            },
            "checkbox": {
                "keep_selected_style": false,
                "three_state": false
            },
            "plugins": ["checkbox"]
        });
        $('#selected_items').val("aaaa");

    });
</script>
<!--[if lt IE 9]>
<script src="assets/plugins/respond.js"></script>
<script src="assets/plugins/html5shiv.js"></script>
<script src="assets/plugins/placeholder-IE-fixes.js"></script>
<![endif]-->

</body>
</html>
