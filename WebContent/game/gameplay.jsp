<%@page import="javax.sound.midi.SysexMessage"%>
<%@page import="com.istarindia.apps.games.services.GameService"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="com.istarindia.apps.dao.IstarUser"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.istarindia.apps.cmsutils.reports.*"%>
<%@page import="javax.xml.bind.*"%>
<%@page import="com.istarindia.cms.game.*"%>
<%@page import="java.net.*"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="java.io.File"%>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
	
	

		// req.getServletContext().getRealPath("/WEB-INF/fileName.properties")
		

	
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title>Content Creator Dashboard | iStar CMS</title>

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
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">

<!-- CSS Theme -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>

<body>
	<div class="container content ">
		<div class="row">
			<div class="col-md-12">
				<div class="col-md-12">
					<!-- Info Panel -->

					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title">
								<i class="fa fa-tasks"></i> Game
							</h3>
						</div>
						<div class="panel-body">
							
		<%
		Game game = new GameService().getGame();
		if(request.getAttribute("stage_id")==null)
		{
			System.err.println("in null");
			out.println(new GameService().startGame(game));
		}
		else
		{
			int stage_id = Integer.parseInt(request.getAttribute("stage_id").toString());
			if(stage_id==-100)
			{
				out.println("Game Over");
			}
			else
			{
				out.println(new GameService().getNextStageForm(game,stage_id));
			}	
		}
		
		
		%>

						</div>

					</div>
					<!-- End Info Panel -->
				</div>

			</div>
		</div>
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
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/data.js"></script>
	<script src="https://code.highcharts.com/highcharts-3d.js"></script>
	<script
		src="http://127.0.0.1:8080/content/assets/plugins/datatables/jquery.dataTables.min.js"></script>
	<script
		src="http://127.0.0.1:8080/content/assets/plugins/datatables/dataTables.colVis.min.js"></script>
	<script
		src="http://127.0.0.1:8080/content/assets/plugins/datatables/dataTables.tableTools.min.js"></script>
	<script
		src="http://127.0.0.1:8080/content/assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script
		src="http://127.0.0.1:8080/content/assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>

	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/js/plugins/style-switcher.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>

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
