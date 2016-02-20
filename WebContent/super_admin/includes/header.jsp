<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>><% String url = request.getRequestURL().toString();
String basePath = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%><div class="header header-sticky">
	<div class="container">
		<!-- Logo -->
		<a class="logo" href="/"> <img src="<%=basePath%>assets/img/logo1-default.png" alt="Logo">
		</a>
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="fa fa-bars"></span>
		</button>
		<!-- End Toggle -->
	</div>
	<!--/end container-->

	<!-- Collect the nav links, forms, and other content for toggling -->
	<div class="collapse navbar-collapse mega-menu navbar-responsive-collapse">
		<div class="container">
			<ul class="nav navbar-nav">
				<li><a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> Home </a></li>
				<li><a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> Organization </a></li>
				<li><a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> Users </a></li>
				<li><a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> Content </a></li>
			</ul>
		</div>
		<!--/end container-->
	</div>
	<!--/navbar-collapse-->
</div>