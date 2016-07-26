<%-- 
    Document   : batchgroup_listing
    Created on : 08 April, 2016, 12:52:00 PM
    Author     : Kunal Chakravertti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<% String url = request.getRequestURL().toString();
    String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css"/>

        <!-- CSS Theme -->
        <link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
        <link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

        <!-- CSS Customization -->
        <link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
        <title>Batch-Group Listing</title>
    </head>
    <body>
        
        <div class="wrapper">
            <jsp:include page="includes/header.jsp"></jsp:include>
                <div class="breadcrumbs">
                    <div class="container-fluid height-1000" style="padding: 0px !important">
                        <div class="col-md-12">
                       
                        ${batchGroupList}
                    </div>
                </div>
                <jsp:include page="includes/footer.jsp"></jsp:include>
                </div>
                <!-- JS Global Compulsory -->
                <script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
            <script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
            <script type="text/javascript" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
            <!-- JS Implementing Plugins -->
            <script type="text/javascript" src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
            <script type="text/javascript" src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
            <!-- JS Customization -->
            <script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
            <!-- JS Page Level -->
            <script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
            <script src="<%=baseURL%>assets/plugins/datatables/jquery.dataTables.min.js"></script>
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
                    tablet: 1024,
                    phone: 480
                };
                jQuery(document).ready(function () {
                    App.init();

                });
            </script>
            <!--[if lt IE 9]>
            <script src="assets/plugins/respond.js"></script>
            <script src="assets/plugins/html5shiv.js"></script>
            <script src="assets/plugins/placeholder-IE-fixes.js"></script>
            <![endif]-->
    </body>
</html>
