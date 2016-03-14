<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title>Creative Creator Dashboard | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico">

<!-- Web Fonts -->
<link rel='stylesheet' type='text/css' href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600&amp;subset=cyrillic,latin'>

<!-- CSS Global Compulsory -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/global.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid">
				<h1 class="pull-left">Creative Creator Dashboard</h1>
			</div>
			<!--/container-->
		</div>
		
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<section id="processes" class="g-bg-dark-blue1">
				<div class="container content-md g-text-height-md">
					<div class="row g-mb-60 text-center g-heading-v7">
						<div class="col-sm-8 col-sm-offset-2">
							<h2 class="h2 color-light">
								<span class="block-name">Work Process</span>
							</h2>
						</div>
					</div>
					
					<div class="row g-mb-20">
						<div class="col-md-6 g-mb-20 steps-v1--lspace">
							<ul class="list-unstyled steps-v1 g-bg-dark-blue2 g-no-mb">
								<li>
									<h3 class="heading-sm h3">
										<a href="<%=baseURL %>creative_creator/assigned.jsp">Assigned tasks</a>
									</h3>
								</li>
								<li><p>This will let you view all media tasks which are assigned.</p></li>
								<li class="steps-v1__block g-rounded-50x"><i class="steps-v1__icon icon-fire"></i> <img class="steps-v1__img g-rounded-50x" src="http://htmlstream.com/preview/unify-v1.9.1/One-Pages/Business/assets/img-temp/testimonials/img1.jpg" alt=""></li>
							</ul>
						</div>
						
						<div class="col-md-6 steps-v1--rspace">
							<ul class="list-unstyled steps-v1 g-bg-dark-blue2 g-no-mb">
								<li>
									<h3 class="heading-sm h3">
										<a href="<%=baseURL %>creative_creator/inprogress.jsp">Inprogress tasks</a>
									</h3>
								</li>
								<li><p>This will let you view all media tasks in progress.</p></li>
								<li class="steps-v1__block g-rounded-50x"><i class="steps-v1__icon icon-energy"></i> <img class="steps-v1__img img-responsive g-rounded-50x" src="http://htmlstream.com/preview/unify-v1.9.1/One-Pages/Business/assets/img-temp/testimonials/img1.jpg" alt=""></li>
							</ul>
						</div>
						<div class="row g-mb-50"> </div>
					</div>
				
					<div class="row g-mb-20">
						<div class="col-md-6 g-mb-20 steps-v1--lspace">
							<ul class="list-unstyled steps-v1 g-bg-dark-blue2 g-no-mb">
								<li>
									<h3 class="heading-sm h3">
										<a href="<%=baseURL %>creative_creator/not_reviewed.jsp">Not Reviewed tasks</a>
									</h3>
								</li>
								<li><p>This will let you view all media tasks which are to be reviewed. </p></li>
								<li class="steps-v1__block g-rounded-50x"><i class="steps-v1__icon icon-fire"></i> <img class="steps-v1__img g-rounded-50x" src="http://htmlstream.com/preview/unify-v1.9.1/One-Pages/Business/assets/img-temp/testimonials/img1.jpg" alt=""></li>
							</ul>
						</div>
						
						<div class="col-md-6 steps-v1--rspace">
							<ul class="list-unstyled steps-v1 g-bg-dark-blue2 g-no-mb">
								<li>
									<h3 class="heading-sm h3">
										<a href="<%=baseURL %>creative_creator/disapproved.jsp">Disapproved tasks</a>
									</h3>
								</li>
								<li><p>This will let you view all media tasks disapproved. </p></li>
								<li class="steps-v1__block g-rounded-50x"><i class="steps-v1__icon icon-energy"></i> <img class="steps-v1__img img-responsive g-rounded-50x" src="http://htmlstream.com/preview/unify-v1.9.1/One-Pages/Business/assets/img-temp/testimonials/img1.jpg" alt=""></li>
							</ul>
						</div>
						<div class="row g-mb-50"></div>
					</div>
				
					<div class="row g-mb-20">
						<div class="col-md-6 g-mb-20 steps-v1--lspace">
							<ul class="list-unstyled steps-v1 g-bg-dark-blue2 g-no-mb">
								<li>
									<h3 class="heading-sm h3">
										<a href="<%=baseURL %>creative_admin/published.jsp">Published tasks</a>
									</h3>
								</li>
								<li><p>This will let you view all published media tasks. </p></li>
								<li class="steps-v1__block g-rounded-50x"><i class="steps-v1__icon icon-fire"></i> <img class="steps-v1__img g-rounded-50x" src="http://htmlstream.com/preview/unify-v1.9.1/One-Pages/Business/assets/img-temp/testimonials/img1.jpg" alt=""></li>
							</ul>
						</div>
						
						<div class="row g-mb-50"> </div>
					</div>
				</div>	
			</section>
		</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
	</div>

	<!-- JS Global Compulsory -->
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/smoothScroll.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/js/plugins/style-switcher.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
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
