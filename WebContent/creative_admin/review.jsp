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
<title>Creative Admin Dashboard | iStar CMS</title>

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
				<h1 class="pull-left">Media Review</h1>
			</div>
			<%
				ArrayList<String> details = (ArrayList<String>) request.getAttribute("media_details");
			%>
		</div>
		<div class="container content-sm">
			<div class="text-center margin-bottom-50">
				<h2 class="title-v2 title-center"><%=details.get(0)%></h2>
				<p class="space-lg-hor"><%=details.get(1)%></p>
			</div>

			<div class="row  margin-bottom-30">
				<div class="col-sm-6 sm-margin-bottom-30">
					<a href="<%=details.get(4)%>" rel="gallery"
						class="fancybox img-hover-v1" title="Image 1"> <%
				 	if (!details.get(4).endsWith(".mp4")) {
				 		System.out.println("item name"+details.get(4));
				 %> <span><img class="img-responsive"
											src="<%=details.get(4)%>" alt="image"></span> <%
				 	} else {System.out.println("item name"+details.get(4));
				 %> <span>
				 
				 
				 <figure>
				<video id="my-video" class="video-js" controls preload="auto"
					style="margin-left: -80px;" width="1020"
					poster="http://www.baquickbites.com/_assets/images/my_video_poster.jpg" data-setup="{}">
					<source src="<%=details.get(4)%>" type='video/mp4'>

				</video>

			</figure>
			</span> <%
 	}
 %>
					</a>
				</div>
				<div class="col-sm-6 sm-margin-bottom-30">
					<fieldset>

						<form action="/content/media_review_by_admin" >
							<input type="hidden" name="task_id" value="<%=details.get(6)%>">
							<section>
								<label style="font-size: 17px; font-family:" OpenSans", Arial, sans-serif;">Tags:</label>
								<label class="input"> <%=details.get(2)%>
								</label>
							</section>

							<section>
								<label style="font-size: 17px; font-family:" OpenSans", Arial, sans-serif;">Session:</label>
								<label> <%=details.get(3)%>
								</label>

							</section>
							<section>
								<label style="font-size: 17px; font-family:" OpenSans", Arial, sans-serif;">Previous
									Comments:</label> <label> <%=details.get(5)%>
								</label>

							</section>
							<section>
								<label style="font-size: 17px; font-family:" OpenSans", Arial, sans-serif;" >
									Comment</label> <label class="input"> <TEXTAREA NAME="comment"
										ROWS="5" cols="75"></TEXTAREA>
								</label>
							</section>

							<footer>
								<button type="submit" class="btn-u" name="button"
									value="APPROVED">Approve</button>
								<button type="submit" name="button" class="btn-u"
									value="DIS_APPROVED">Disapprove</button>

							</footer>
						</form>
					</fieldset>
				</div>
			</div>

			<div class="row"></div>
		</div>
	</div>
	</div>
	</div>
	<jsp:include page="../content_admin/includes/footer.jsp"></jsp:include>
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
	<script src="http://vjs.zencdn.net/ie8/1.1.1/videojs-ie8.min.js"></script>

	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<script src="<%=baseURL%>assets/plugins/tagz/bootstrap-tagsinput.js"
		type="text/javascript" charset="utf-8"></script>

	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>

	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
