<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="java.util.*"%>
<%@page import="com.istarindia.apps.cmsutils.CMSFolder"%>
<%@page import="com.istarindia.apps.services.CMSUtils"%>
<%@page import="com.istarindia.apps.services.FolderService"%>
<%@page import="com.istarindia.cms.controller.MediaUploadController"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
%>
<%!private String getFolderTree() {
		StringBuffer sb = new StringBuffer();
		sb = CMSUtils.getAllFolders();

		FolderService d = new FolderService();
		Folder root = d.getRootFolder();
		StringBuffer sb1 = new StringBuffer();
		d.getFolderRecursively(root, sb1);
		return sb1.toString();

	}%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title>Upload media | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="shortcut icon" href="<%=baseURL%>assets/img/talentify_logo_fav_48x48.png" />

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
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">

<!-- CSS Theme -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-skins/dark.css">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.css">

</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container">
				<h1 class="pull-left">Upload media</h1>
			</div>
		</div>
		<!--/container-->
		<div class="container-fluid content">
			<div class="row">
			<% 
			int item_id = (int)request.getAttribute("item_id");
			%>
				<form action="<%=baseURL%>media_upload" class="sky-form"
					method="POST" novalidate="novalidate" onsubmit="myFunction()"
					enctype="multipart/form-data">
					<input type="hidden" name="item_id" value="<%=item_id %>" />
					<input type="hidden" id="selected_items" name="selected_items" />
					<div class="col-md-8">
						<header>Select Folder</header>
						<fieldset>
							<div class="panel panel-grey margin-bottom-40">
								<div class="panel-body">
									<div id="html1">
										<ul>
											<%=getFolderTree()%>
										</ul>
									</div>
								</div>
							</div>
						</fieldset>
						<div class="margin-bottom-60"></div>
					</div>
					<div class="col-md-4">
						<header>Media details</header>
						<fieldset>
							<section>
								<label class="label">Tags</label> <label class="input">
									<input type="text" name="tags" data-role="tagsinput"
									class="tagcontainer">
								</label>
							</section>
							<section>
								<label class="label">File input</label>
								<label for="file" class="input input-file">
									<div class="button">
									<input type="file" id="file" name="file" onchange="this.parentNode.nextSibling.value = this.value">Browse</div><input type="text" readonly>

								</label>
							</section>
						</fieldset>
						<footer>
	               			<label id="err" style="display: block;color:#ee9393"></label>
							<button type="submit" class="btn-u">Upload and Mark as Complete</button>
						</footer>
						<div class="margin-bottom-60"></div>
					</div>
				</form>
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
	<script type="text/javascript"
		src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
		charset="utf-8"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/js/plugins/style-switcher.js"></script>
	<script type="text/javascript">
	
		function myFunction() {
			var selectedElmsIds = $('#html1').jstree("get_selected");
			if (selectedElmsIds == ""){
				event.preventDefault();
				document.getElementById("err").innerHTML = "Please select the target folder(s)";
				return false; 
				}
			else {
				document.getElementById("err").innerHTML = "";
			}
			$('#selected_items').val(selectedElmsIds);
			console.log(selectedElmsIds);
		}
		jQuery(document).ready(function() {
			App.init();
			StyleSwitcher.initStyleSwitcher();
			$('#html1').jstree({
				"core" : {
					"themes" : {
						"variant" : "large"
					}
				},
				"checkbox" : {
					"keep_selected_style" : false,
					"three_state" : false,
				},
				"plugins" : [ "checkbox" ]
			});
			var selectedElmsIds = $('#html1').jstree("get_selected");
		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
