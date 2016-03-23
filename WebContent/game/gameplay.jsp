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
											ArrayList<Stage> stages = game.getStages();
											
											if(request.getAttribute("prev_stage_id")==null)
											{
												for(Asset a : game.getAssets())
												{	
													request.getSession().setAttribute(a.getName(),0);
													System.err.println("just started the game with "+ a.getName() +" = "+request.getSession().getAttribute(a.getName()));
												}
												out.println(new GameService().startGame(game));
											}
											else
											{
										int prev_stage_id = Integer.parseInt(request.getAttribute("prev_stage_id").toString());
										if(request.getAttribute("option_id")!=null)
										{
											int option_id = Integer.parseInt(request.getAttribute("option_id").toString());
											Stage stage = stages.get(prev_stage_id-1);
											ArrayList<Option> options = stage.getOptions();
											String marking_scheme = options.get(option_id-1).getMakringScheme();
											//marking_scheme = marking_scheme.replace("\\+", "+");
											String [] schemes=null;
											String [] score=null;
											//	if (marking_scheme.contains("-")) {
											schemes = marking_scheme.split(";");
											
											StringBuffer sb= new StringBuffer();
											
											for(String schema : schemes)
											{
												
											
												for(Asset a : game.getAssets())
												{
													if(schema.contains(a.getName()))
													{
														sb.append(a.getName()+"=");
														if(a.getDataType().equalsIgnoreCase("Integer"))
														{
															
															Double new_val = (Double)new GameService().getScriptEvaluation(a.getName(), request.getSession().getAttribute(a.getName()).toString(), schema);
															request.getSession().setAttribute(a.getName(), new_val.intValue());
															sb.append(new_val.intValue()+";");
															System.err.println("current value of "+a.getName()+"= "+request.getSession().getAttribute(a.getName()));
														}
														else
														{
															
															request.getSession().setAttribute(a.getName(), schema.split("=")[1]);
															sb.append(schema.split("=")[1]+";");
															System.err.println("current value of "+a.getName()+"= "+request.getSession().getAttribute(a.getName()));	
														}
														
													}
												}
												
											}
											
											int student_id =16;
											new GameService().updateStudentGame(student_id, sb.toString(),game.getId());
											
										/* 	for(String scheme : schemes)
											{
												String asset_array[]=null;
												if(scheme.contains("-"))
												{
													asset_array = scheme.split("\\-");
													int new_score = Integer.parseInt(request.getSession().getAttribute(asset_array[0]).toString()) - Integer.parseInt(asset_array[1]);
													request.getSession().setAttribute(asset_array[0], new_score);
													
													System.err.println(" minus from score " + asset_array[1]);
													System.err.println(" current "+asset_array[0]+" is  " + request.getSession().getAttribute(asset_array[0]));
													
												}else if(scheme.contains("+"))
												{
													asset_array = scheme.split("\\+");
													int new_score = Integer.parseInt(request.getSession().getAttribute(asset_array[0]).toString()) + Integer.parseInt(asset_array[1]);
													request.getSession().setAttribute(asset_array[0], new_score);
													
													System.err.println(" add to score " + asset_array[1]);
													System.err.println(" current "+asset_array[0]+" is  " + request.getSession().getAttribute(asset_array[0]));
													
												}
												else if(scheme.contains("*"))
												{
													asset_array = scheme.split("\\*");
													int new_score = Integer.parseInt(request.getSession().getAttribute(asset_array[0]).toString()) * Integer.parseInt(asset_array[1]);
													request.getSession().setAttribute(asset_array[0], new_score);
													
													System.err.println(" multiply to score " + asset_array[1]);
													System.err.println(" current "+asset_array[0]+" is  " + request.getSession().getAttribute(asset_array[0]));
													
												}else if(scheme.contains("/"))
												{
													asset_array = scheme.split("\\/");
													int new_score = Integer.parseInt(request.getSession().getAttribute(asset_array[0]).toString()) / Integer.parseInt(asset_array[1]);
													request.getSession().setAttribute(asset_array[0], new_score);
													
													System.err.println(" divide from score " + asset_array[1]);
													System.err.println(" current "+asset_array[0]+" is  " + request.getSession().getAttribute(asset_array[0]));
													
												}
											}	 */
													
											int stage_id = options.get(option_id - 1).getJump_to();
												out.println(new GameService().getNextStageForm(game, stage_id));
											} else {
												int stage_id = prev_stage_id + 1;
												if (stage_id == -100 || stage_id > stages.size()) {
													out.println("Game Over");
													out.println("Your score is: "+request.getSession().getAttribute("score"));
												} else {
													out.println(new GameService().getNextStageForm(game, stage_id));
												}
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
