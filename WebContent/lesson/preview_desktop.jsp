<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><!doctype html>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

PresentaionDAO dao = new PresentaionDAO();
int lessonID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
Presentaion ppt =  dao.findById(lessonID);
String lesson_theme = ppt.getLesson().getLesson_theme_desktop();

String nuetral = url.substring(0, url.length() - request.getRequestURI().length()) +"/";

String style_body = "background-size: cover;";

%>


<html lang="en">
<head>

<meta charset="utf-8">

<title>TALENTIFY</title>

<meta name="description" content="A framework for easily creating beautiful presentations using HTML">
<meta name="author" content="Hakim El Hattab">

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/reveal/css/reveal.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/animate.css" />

<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/style.css">

<%
int themeID = 100;
try {
		themeID = Integer.parseInt(lesson_theme);
		if ((new UiThemeDAO()).findById(themeID) != null) {
%>
	<jsp:include page="/themes/desktop/desktop_yellow.jsp"></jsp:include>
<%
	}
	} catch (Exception e) {
		//nothing ToDo
	}
%>
<!-- Code syntax highlighting -->
<script>
			var link = document.createElement( 'link' );
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = window.location.search.match( /print-pdf/gi ) ? '<%=nuetral%>student/css/print/pdf.css' : '<%=nuetral%>student/css/print/paper.css';
			document.getElementsByTagName( 'head' )[0].appendChild( link );
		</script>

</head>

<body style="<%=style_body%>;background-color:  ">
	
	<div class="reveal">
		<div class="slides">
			<%=ppt.outputSlidesForDesktop().replaceAll("data-transition='fade'", "data-transition='slide'").
			replaceAll("data-transition='concave'", "data-transition='slide'").
			replaceAll("data-transition='convex'", "data-transition='slide'").
			replaceAll("data-transition='zoom'", "data-transition='slide'").
			replaceAll("data-background-transition='zoom'", "data-background-transition='slide'").
			replaceAll("data-background-transition='fade'", "data-background-transition='slide'").
			replaceAll("data-background-transition='concave'", "data-background-transition='slide'")%>
		</div>
	</div>
	<% 
	IstarUser user = (IstarUser) request.getSession().getAttribute("user");
    String displayName = "Welcome " + user.getName();
    if(user.getUserType().equalsIgnoreCase("CONTENT_REVIEWER")) { %>
<button onclick="add_review_comment()" data-toggle="modal" data-target="#reviewCommentModal" style="position: absolute;bottom: 10%; right: 10%; z-index: 999">
<img src="http://i.stack.imgur.com/8BVKM.png"></button>

<button onclick="Reveal.slide(0)" data-toggle="modal" data-target="#myModal" style="position: absolute;bottom: 10%; right: 20%; z-index: 999">Go to First Slide </button>

<button onclick="view_teacher_notes()" data-toggle="modal" data-target="#myModal" style="position: absolute;bottom: 10%; right: 30%; z-index: 999">Speaker Notes</button>


<% } else {
%>
<button onclick="add_edit()" data-toggle="modal" data-target="#reviewCommentModal" style="position: absolute;bottom: 10%; right: 10%; z-index: 999">
<img src="http://i.stack.imgur.com/8BVKM.png"></button>

<button onclick="Reveal.slide(0)" data-toggle="modal" data-target="#myModal" style="position: absolute;bottom: 10%; right: 20%; z-index: 999">Go to First Slide </button>
<button onclick="view_teacher_notes()" data-toggle="modal" data-target="#myModal" style="position: absolute;bottom: 10%; right: 30%; z-index: 999">Speaker Notes</button>



<% } %>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>

	<script type="text/javascript" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.js"></script>

	<script src="<%=nuetral%>student/lib/js/head.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/js/reveal.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/plugin/zoom-js/zoom.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/typed-js/js/typed.js"></script>
	<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>

	<script>
	
	function view_teacher_notes(){
		var id =$('.present').attr('id');
		console.log(id);
		
		
	}
	
	
		var orginal_listitem_font_size = <%=(new UiThemeDAO()).findById(themeID).getListitemFontSize()%> ;
		var window_size = $(window).width();
		
		Reveal.initialize({
			center : true,
			controls : true,
			width: window_size,
			transition: 'slide', 
			showNotes: true, 
			
			
			dependencies: [
							{ src: 'http://lab.hakim.se/reveal-js/plugin/zoom-js/zoom.js', async: true },
							{ src: 'http://lab.hakim.se/reveal-js/plugin/notes/notes.js', async: true }
						]
		});
		
		//Save background colour from the theme to a global variable
		var orgBgColor = '<%=(new UiThemeDAO()).findById(themeID).getBackgroundColor()%>';			
		//console.log("background colour from theme: "+orgBgColor);
		
		//Set first slide's bg color to the body
		document.body.style.background = $('.present').data("bgcolor");								
		if ($('.present').data("bgcolor") == "none") {
			//console.log("first silde doesnt have bg color");
			//if the first slide doesn't have bg color set; then reset the body color to the original from theme
			document.body.style.background = orgBgColor;
			//console.log("#95: body-bgcolor: "+document.body.style.background);
		}

		Reveal.addEventListener('slidechanged', function(event) {
			document.body.style.background = $('.present').data("bgcolor");
			if ($('.present').data("bgcolor") == "none") {
				document.body.style.background = orgBgColor;
			}

			var currentURL = window.location.href; //currentURL+"#/"+ 
			var res = currentURL.split("#");
			currentURL = res[0] ///#1001
			//console.log(currentURL + "#/" + event.currentSlide.id);
			history.pushState({}, "URL Rewrite Example", currentURL + "#"
					+ event.currentSlide.id);
			
			
			//$('.video111').css('position','absolute');
			$('.video111').css('height',(window.screen.availHeight+100)+'px');
			$('.video111').css('position','absolute');
			$('.video111').css('top', '-'+(window.screen.availHeight-50)/2+'px');
			$('.video111').css('margin-left','-18%');
			//To make the title animated as typing - 
			
			//var slideID = $('.present').attr('id');
			//console.log(" typing " + slideID);
			//$('#'+slideID + " #data_slide_title").addClass( "animated infinite bounce" );// css('class','css-typing');
			// find the hright of the slide and make it the height f the row 
			//height: 1024px;display: table-cell;vertical-align: middle;
			
			var height_slide = $('#'+event.currentSlide.id).css('height');
			$('#'+event.currentSlide.id).css('display','table');
			console.log('height_slide->'+ height_slide);
			$('#'+event.currentSlide.id+ " .row").css('height',height_slide);
			$('#'+event.currentSlide.id+ " .row").css('display','table-cell');
			$('#'+event.currentSlide.id+ " .row").css('vertical-align','middle');
			
		});

		Reveal.addEventListener('fragmentshown', function(event) {
			//var temp = orginal_listitem_font_size;

			$('.fragment').each(function(index, value) {
				try {

					if ($(this).attr('id').indexOf("-") != -1) {
						$('#' + $(this).attr('id')).css({
							'font-size' : orginal_listitem_font_size + 'px'
						});
					}
				} catch (errr) {
					//Console.log($(this).attr('id'));
				}
			});
			
			$('#' + event.fragment.id).css({
				'font-size' : orginal_listitem_font_size * 1.5 + 'px'
			});
		});
		//http://localhost:8080/content/lesson/preview_desktop.jsp?ppt_id=22#4187
		function add_edit() {
			var currentURL = window.location.href; //currentURL+"#/"+ 
			var slideID = <%=request.getParameter("slide_id")%>;
			if(currentURL.indexOf("#") > -1) {
				slideID  = currentURL.split("#")[1];
				
			}
			var url = '/content/fill_tempate.jsp?ppt_id=<%=request.getParameter("ppt_id")%>&slide_id='+slideID+'&slide_type=ONLY_TITLE_PARAGRAPH';
			console.log(url);
			var win = window.open(url, '_blank');
			win.focus();
		}
		
		<% 
		 if(user.getUserType().equalsIgnoreCase("CONTENT_REVIEWER")) { %>
			function add_review_comment() {
				$('#idreview_notes').val("");
				
				var currentURL = window.location.href; //currentURL+"#/"+ 
				var slideID = <%=request.getParameter("slide_id")%>;
				// take # from url and put into slide_id
				if(currentURL.indexOf("#") > -1) {
					slideID  = currentURL.split("#")[1];
					$('#slidee_id').val(slideID);
				}
				
			}
			
			
	<% }
		
		
		
		%>
		
		/* 
		key-> is_edit : value ->true
key-> slide_id : value ->252
key-> ppt_id : value ->20
key-> review_notes : value ->sssssssssssssss*/
		
		$( "#sbnmm" ).click(function() {
			
			console.log('mgkjhgkjhg');
			
			var slide_id = $('slidee_id').val();
			var is_edit = "true";
			var idreview_notes = $('idreview_notes').val();
			$.ajax({
				  type: "POST",
				  url: '/content/review_lesson',
				  data: 'is_edit=true&ppt_id=<%=request.getParameter("ppt_id")%>&slide_id='+slide_id+'&review_notes='+idreview_notes,
				  success: success,
				  dataType: dataType
				});
		
		});
	</script>
	<% 
		 if(user.getUserType().equalsIgnoreCase("CONTENT_REVIEWER")) { %>

<div class='modal fade' id='reviewCommentModal' tabindex='-1'  role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
					<h4 class="modal-title" id="myModalLabel4">Add comment</h4>
				</div>
				
				<div class="modal-body">
						<input name='is_edit' type="hidden" value="true" />
						<input name='slide_id' type="hidden" value="<%=request.getParameter("slide_id")%>" />
						<input name='ppt_id' type="hidden" value="<%=request.getParameter("ppt_id")%>" />
						<input name='from' type=hidden value="review_slide" />
						<fieldset>							
							<section>								
								<label class="label">Review Notes</label> 
								<label class="textarea"> 
									<textarea rows="3" name="review_notes" placeholder=" Please enter text"></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> This is where we will put in the Review Notes.
								</div>
							</section>
							<section style="margin-bottom: 2%; float: right;">
								<button type="button" class="btn-u btn-u-default" data-dismiss="modal">Close</button>
								<button type="submit" class="btn-u" >Submit</button> 
							</section>
						</fieldset>
				</div>
				
				<div class="modal-footer"> </div>
				
			</div>
		</div>
	</div>

<% }	%>
</body>


</html>
