<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>

<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<% 
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
	PresentaionDAO dao = new PresentaionDAO();
	int lessonID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
	Presentaion ppt =  dao.findById(lessonID);
	String lesson_theme = ppt.getLesson().getLesson_theme();
	String style_body = "background-size: cover;";
%>

<html lang="en">
<head>

<meta charset="utf-8">

<title>TALENTIFY</title>
<link rel="shortcut icon" href="<%=baseURL%>assets/img/talentify_logo_fav_48x48.png" />

<meta name="description" content="A framework for easily creating beautiful presentations using HTML">
<meta name="author" content="Hakim El Hattab">

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">

<link rel="stylesheet" href="<%=baseURL%>assets/plugins/reveal/css/reveal.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/animate.css" />
<!-- Include the yellow.jsp for styling only if everything is good for inclusion.
lesson may not have theme saved
lesson_theme may have a string in place
lesson_theme may have a theme_id which doesnt have an entry in ui_theme table
 -->
<%
int themeID = 43;
try {
themeID = Integer.parseInt(lesson_theme);
if ((new UiThemeDAO()).findById(themeID) != null) {
%>
	<jsp:include page="/themes/mobile/yellow.jsp"></jsp:include>
<% 
	} 
} catch (Exception e){
	//nothing ToDo
}
%>
	
<!-- Code syntax highlighting -->
	<script>
		var link = document.createElement( 'link' );
		link.rel = 'stylesheet';
		link.type = 'text/css';
		link.href = window.location.search.match( /print-pdf/gi ) ? '<%=baseURL%>assets/plugins/reveal/css/print/pdf.css' : '<%=baseURL%>assets/plugins/reveal/css/print/paper.css';
		document.getElementsByTagName( 'head' )[0].appendChild( link );
	</script>

</head>
<body style="<%=style_body%>">
	<div class="reveal">
		<div class="slides">
		<%=ppt.outputSlides() %>
		</div>
	</div>
<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/js/reveal.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/plugin/zoom-js/zoom.js"></script>

<%
		int auto_slide_duration = 5000;

		try {
			Properties properties = new Properties();
			String propertyFileName = "app.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertyFileName + "' not found in the classpath");
			}
			
			auto_slide_duration = Integer.parseInt(properties.getProperty("auto_slide_duration"));
		} catch (Exception e ) {
			e.printStackTrace();
		}
%>

	<script>
		Reveal.initialize({
			center : false,
			controls : false,
		    slideNumber:  'c/t', 
		    autoSlide: <%=auto_slide_duration%>
		});

		var orgBgColor = '#ffffff';			
		
		$(document).ready(function(){
			orgBgColor = '<%=(new UiThemeDAO()).findById(themeID).getBackgroundColor()%>';
			updateSlideBgColor();
		});

		function updateSlideBgColor() {
			if ($('.present').data("bgcolor") == "none") {
				document.body.style.background = orgBgColor;
			} else {
				document.body.style.background = $('.present').data("bgcolor");
			}
		}

		function updateURL() {
			var currentURL = window.location.href; 
			var res = currentURL.split("#");
			currentURL = res[0];
			history.pushState({}, "URL Rewrite Example", currentURL + "#" + event.currentSlide.id);
		}

		Reveal.addEventListener('slidechanged', function(event) {
			updateSlideBgColor();
			updateURL();
		});
		
		function restoreHistory() {
			try{
				var slide_id = window.location.href.split("#")[1];
				if(slide_id > 0) {
				    var slide_number = 0;
					var temp = -1;
				    
					$( ".slide" ).each(function( index ) {
						if($( this ).attr("id") == slide_id) {
							slide_number = temp;
						  } else {
							  temp = temp + 1;
						  }
					});
					
					if(slide_number >= 0) {
						Reveal.slide(slide_number,0, 0);
						updateSlideBgColor();
					}
				} else {
					Reveal.slide(0);
					updateSlideBgColor();
				}
		    } catch(err) {
		    	console.log(err);
		    }
		}

		Reveal.addEventListener( 'ready', function( event ) {
			restoreHistory() ;
		} );
		
	</script>

</body>
</html>
