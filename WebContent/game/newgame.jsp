<%@page import="com.istarindia.cms.game.Asset"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.istarindia.cms.game.Game"%>
<%@page import="com.istarindia.apps.games.services.GameSerializer"%>

<%@ page import="javax.xml.bind.*"%><%@ page import="java.io.*"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
int parent_item_id = Integer.parseInt(request.getParameter("parent_item_id"));
GameSerializer ser = new GameSerializer();
Game game = ser.getGame(parent_item_id);
String htmlSnippet = "";
if(request.getAttribute("stage_id")!=null && !request.getAttribute("stage_id").toString().equals("-100")) {
	System.err.println("stage_id is "+Integer.parseInt(request.getAttribute("stage_id").toString()));
	
	 htmlSnippet = ser.getNextStage(game, Integer.parseInt(request.getAttribute("stage_id").toString()));
} 
else if ( request.getAttribute("stage_id")!=null && request.getAttribute("stage_id").toString().equals("-100"))
{
	System.err.println("game over");
	 htmlSnippet =ser.getGameOverScreen(request.getSession().getAttribute("star_points").toString());
}
else
{
	for (Asset iterable_element : game.getAssets()) {
		if(iterable_element.getDataType().equalsIgnoreCase("Integer")) {
			request.getSession().setAttribute(iterable_element.getName(), 0);
		}
		else if(iterable_element.getDataType().equalsIgnoreCase("String"))
		{
			request.getSession().setAttribute(iterable_element.getName(), "");
		}
	}
	htmlSnippet = ser.getIntro(game);
}

//just for testing
for(Asset a : game.getAssets())
{
	System.out.println("value of "+a.getName()+" in session is "+ request.getSession().getAttribute(a.getName()));					
}

%><!doctype html>
<html lang="en">

<head>
<meta charset="utf-8">

<title>Murder Mystery</title>

<meta name="description" content="A framework for easily creating beautiful presentations using HTML">
<meta name="author" content="Hakim El Hattab">

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">

<link rel="stylesheet" href="<%=baseURL %>assets/css/game.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/reveal/css/theme/sky.css" id="theme">
 <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<!-- Code syntax highlighting -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/reveal/lib/css/zenburn.css">
<% if(request.getHeader("User-Agent").indexOf("Mobile") != -1) {
	//System.err.println("This is Mobile");
%><link href="<%=baseURL %>assets/plugins/reveal/css/mobile.css" rel="stylesheet" type="text/css" />

<%  } else { %>
<link href="<%=baseURL %>assets/plugins/reveal/css/mobile.css" rel="stylesheet" type="text/css" />
<%  } %>
<!--[if lt IE 9]>
		<script src="lib/js/html5shiv.js"></script>
		<![endif]-->
</head>
<body>

<body>
	<div class="reveal">
		<form action="/content/play_game" style="display: block;">
		<div class="slides">
			<%=htmlSnippet %>
		</div>
		<aside class="controls" style="display: block;">
			<button type="submit" class="navigate-right enabled" aria-label="next slide" ></button>
		</aside>
		</form>	
	</div>
	<script src="<%=baseURL %>assets/plugins/reveal/lib/js/head.min.js"></script>

</body>

   <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jqueryui-touch-punch/0.2.2/jquery.ui.touch-punch.min.js"></script>
<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"></script>
 <script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script type="text/javascript" src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.8.17/jquery-ui.min.js"></script> -->

<script type="text/javascript">
console.log("in js");
$( init );
var option_ids = [];

function init() {
	console.log("in drag");
	
  $('.dragable').draggable();
  
  $("#droppable" ).droppable({
      drop: function( event, ui ) {
        $( this )
           .addClass( "ui-state-highlight" )
          .find( "p" )
            .html( "Dropped!" ); 
        var draggableId = ui.draggable.attr("id");
        draggableId =  draggableId.replace("option_","");
        //var droppableId = $(this).attr("id");
        option_ids.push(draggableId);
      
       // alert(option_ids.join());
        
       
      }
    });
 
}

$(".navigate-right").on('click', function(){
	
	var stage_id = $('#themes').find('input[name="stage_id"]').val();
	var stage_type = $('#themes').find('input[name="stage_type"]').val();
	var prev_stage_id = $('#themes').find('input[name="prev_stage_id"]').val();
	if(stage_type==='MULTIPLE_OPTION_DRAG_DROP')
	{
	//	alert('abc '+abc);
		  $.ajax({
	      type: "POST",
	      url: '/content/play_game?options=' + option_ids+'&stage_id='+stage_id+'&stage_type='+stage_type+'&prev_stage_id='+prev_stage_id 
	      
	});
	}
		
	
});

 
</script>
</html>