<%@page import="javax.sound.midi.SysexMessage"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.istarindia.cms.game.Game"%>
<%@page import="com.istarindia.apps.games.services.GameSerializer"%>
<%@ page import="com.istarindia.apps.games.services.GameService"%>
<%@ page import="javax.xml.bind.*"%><%@ page import="java.io.*"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
Game game = new GameService().getGame();
String htmlSnippet = "";
if(request.getAttribute("stage_id")!=null) {
	System.err.println("stage_id is "+Integer.parseInt(request.getAttribute("stage_id").toString()));
	GameSerializer ser = new GameSerializer();
	 htmlSnippet = ser.getNextStage(game, Integer.parseInt(request.getAttribute("stage_id").toString()));
} else {
	System.err.println("stage_id is null");
	GameSerializer ser = new GameSerializer();
	 htmlSnippet = ser.getIntro(game);
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
			<button type="submit" class="navigate-right enabled" aria-label="next slide"></button>
		</aside>
		</form>	
	</div>
	<script src="<%=baseURL %>assets/plugins/reveal/lib/js/head.min.js"></script>

</body>
</html>