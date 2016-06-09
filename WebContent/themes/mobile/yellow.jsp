<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.*"%>
	
<%@page import="org.hibernate.Criteria"%>
<%@page import="org.hibernate.HibernateException"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
	<% 
	PresentaionDAO dao = new PresentaionDAO();
	Presentaion ppt = new Presentaion();
	int lessonID = 0 ;
	int pptID =  0;
	if ((request.getParameterMap().containsKey("ppt_id"))){
		pptID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
		ppt =  dao.findById(pptID);
	} else if ((request.getParameterMap().containsKey("lesson_id"))) {
		 lessonID = Integer.parseInt(request.getParameter("lesson_id").toString());
		 Lesson lesson = (new LessonDAO()).findById(lessonID);
		 ppt = lesson.getPresentaion();
	} else {
		ppt = (Presentaion) request.getAttribute("ppt");
	}
	
	String lesson_theme = "0";
	try {
		lesson_theme = ppt.getLesson().getLesson_theme();
		System.err.println("debuggggggggg "+lesson_theme);
	} catch(NullPointerException npe) {
		System.out.println( ppt.getLesson().getId());
	}
	
	String sql = "select * from ui_theme as T where T.id="+lesson_theme;
	UiThemeDAO uiDao = new UiThemeDAO();
	Session uiSession = uiDao.getSession();
	SQLQuery query = uiSession.createSQLQuery(sql);
	query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	List<HashMap<String, Object>> results = query.list();
		HashMap<String, String> theme = (HashMap<String, String>)query.list().get(0);
	%>
<style>

body {
	background-color: <%=theme.get("background_color")%>;
	//#FDC530 !important;
}

@font-face {
    font-family: 'Alice';
    src: url('/themes/fonts/Alice/Alice-Regular.ttf');
}

@font-face {
    font-family: 'Asap-Bold';
    src: url('/themes/fonts/Asap/Asap-Bold.ttf');
}

@font-face {
    font-family: 'Asap-BoldItalic';
    src: url('/themes/fonts/Asap/Asap-BoldItalic.ttf');
}

@font-face {
    font-family: 'Biryani';
    src: url('/themes/fonts/Biryani/Biryani-Regular.ttf');
}

@font-face {
    font-family: 'ComingSoon';
    src: url('/themes/fonts/Coming_Soon/ComingSoon.ttf');
}

@font-face {
    font-family: 'Coockie';
    src: url('/themes/fonts/Coockie/Coockie-Regular.ttf');
}

@font-face {
    font-family: 'Domine';
    src: url('/themes/fonts/Domine/Domine-Regular.ttf');
}

@font-face {
    font-family: 'DroidSerif';
    src: url('/themes/fonts/Droid_Serif/DroidSerif.ttf');
}

@font-face {
    font-family: 'Exo';
    src: url('/themes/fonts/Exo/Exo-Regular.ttf');
}

@font-face {
    font-family: 'Lato';
    src: url('/themes/fonts/Lato/Lato-Regular.ttf');
}

@font-face {
    font-family: 'LeagueScript';
    src: url('/themes/fonts/League_Script/LeagueScript.ttf');
}

@font-face {
    font-family: 'LibreBaskerville';
    src: url('/themes/fonts/Libre_Baskerville/LibreBaskerville-Regular.ttf');
}

@font-face {
    font-family: 'NatoSerif';
    src: url('/themes/fonts/Nato_Serif/NatoSerif-Regular.ttf');
}

@font-face {
    font-family: 'OpenSans';
    src: url('/themes/fonts/Open_Sans/OpenSans-Regular.ttf');
}

@font-face {
    font-family: 'Prata';
    src: url('/themes/fonts/Prata/Prata-Regular.ttf');
}

@font-face {
    font-family: 'Raleway';
    src: url('/themes/fonts/Raleway/Raleway-Regular.ttf');
}

@font-face {
    font-family: 'Quicksand';
    src: url('/themes/fonts/Quicksand/Quicksand-Regular.ttf');
}

@font-face {
    font-family: 'Roboto';
    src: url('/themes/fonts/Roboto/Roboto-Regular.ttf');
}

.reveal  .ONLY_TITLE h2 {
	margin-top: -37%;
	margin-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-family: '<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
	
}

.reveal  .ONLY_2TITLE h2 {
	margin-top: -37%;
	margin-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;    text-transform: uppercase;
	font-family: <%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal  .ONLY_2TITLE_IMAGE h2 {
	margin-left: 2%;
    padding-right: 2%;
    color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE h2 {
    margin-left: 2%;
    padding-right: 3%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
	width: 95%;
}

.reveal .ONLY_TITLE_PARAGRAPH  h2 {
	margin-top: -37%;
	margin-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}
.reveal .ONLY_TITLE_TABLE h2 {
	margin-top: -37%;
	margin-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_IMAGE h2 {
    margin-top: 4%;
    margin-left: 2%;
    margin-right: -2%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}


.reveal .ONLY_TITLE_VIDEO h2 {
	position: absolute;
    margin-top: -50%;
    margin-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}


.reveal .ONLY_PARAGRAPH_TITLE h2{
    margin-top: 5%;
	margin-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;    text-transform: uppercase;
	font-family: <%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
	
	}

.reveal .SIMPLE_LIST___ONLY_TITLE_LIST h2 {
	margin-left: 2%;
	margin-top: -38%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_LIST_NUMBERED h2 {
	margin-left: 2%;
	margin-top: -38%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_TREE h2 {
	margin-left: 2%;
	margin-top: -35% !important;
	padding-bottom: 40px;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal  .ONLY_2TITLE h3 {
	margin-left: 2%;
	margin-top: 8%;
	color: <%=theme.get("subtitle_____font_color")%>;
	font-weight: <%=theme.get("subtitle_____font_weight")%>;
	font-size: <%=theme.get("subtitle_____font_size")%>px;
	line-height: <%=theme.get("subtitle_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family: <%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
	
}

.reveal  .ONLY_2TITLE_IMAGE h3 {
	margin-left: 2%;
    padding-top: 4%;
	color: <%=theme.get("subtitle_____font_color")%>;
	font-weight: <%=theme.get("subtitle_____font_weight")%>;
	font-size: <%=theme.get("subtitle_____font_size")%>px;
	line-height: <%=theme.get("subtitle_____line_height")%>;
	text-align: <%=theme.get("subtitle_____text_alignment")%>;
	font-family: <%=theme.get("subtitle_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE .paragraph p {
	list-style: none;
	margin-top: 8%;
    z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_PARAGRPH  p {
	    margin-top: 4%;
}

.reveal .ONLY_PARAGRAPH  table  {
	margin: auto;
    border-collapse: collapse;
    vertical-align: baseline;
    display: table;
    margin-top: 5%;
    font-size: 4vh;
    color: <%=theme.get("paragraph_____font_color")%>;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
    width: 100%;
    font-weight: 400;
}

.reveal .ONLY_PARAGRAPH  thead  {
	display: table-header-group;
	border-collapse: collapse;
    margin: 0;
    padding: 0;
    border: 3px solid black;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}

.reveal .ONLY_PARAGRAPH   td  {
	text-align: left;
    padding: 8px;
    border: 3px solid black;
}

.reveal .ONLY_PARAGRAPH  th {
	text-align: left;
    padding: 8px;
    border: 3px solid black;
    font-weight: bold;
    background: #e0e0e0;
}

.reveal .ONLY_PARAGRAPH  tr  {
    display: table-row;
    margin: 0;
    padding: 0;
    border: 3px solid black;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}

.reveal .ONLY_TITLE_PARAGRAPH  table  {
	margin: auto;
    border-collapse: collapse;
    vertical-align: baseline;
    display: table;
    margin-top: 5%;
    font-size: 4vh;
    color: <%=theme.get("paragraph_____font_color")%>;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
    width: 100%;
    font-weight: 400;
}

.reveal .ONLY_TITLE_PARAGRAPH  thead  {
	display: table-header-group;
	border-collapse: collapse;
    margin: 0;
    padding: 0;
    border: 3px solid black;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}

.reveal .ONLY_TITLE_PARAGRAPH   td  {
	text-align: left;
    padding: 8px;
    border: 3px solid black;
}

.reveal .ONLY_TITLE_PARAGRAPH  th {
	text-align: left;
    padding: 8px;
    border: 3px solid black;
    font-weight: bold;
    background: #e0e0e0;
}

.reveal .ONLY_TITLE_PARAGRAPH  tr  {
    display: table-row;
    margin: 0;
    padding: 0;
    border: 3px solid black;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}

.reveal .ONLY_TITLE_TABLE  table  {
	margin: auto;
    border-collapse: collapse;
    vertical-align: baseline;
    display: table;
    margin-top: 5%;
    font-size:4vh;
    color: <%=theme.get("paragraph_____font_color")%>;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
    width: 100%;
    font-weight: 400;
}

.reveal .ONLY_TITLE_TABLE  thead  {
	display: table-header-group;
	border-collapse: collapse;
    margin: 0;
    padding: 0;
    border: 3px solid black;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}

.reveal .ONLY_TITLE_TABLE   td  {
	text-align: left;
    padding: 8px;
    border: 3px solid black;
}

.reveal .ONLY_TITLE_TABLE  th {
	text-align: left;
    padding: 8px;
    border: 3px solid black;
    font-weight: bold;
    background: #e0e0e0;
}

.reveal .ONLY_TITLE_TABLE  tr  {
    display: table-row;
    margin: 0;
    padding: 0;
    border: 3px solid black;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}

.reveal .ONLY_TITLE_TABLE th p {
	text-align: left;
    padding: 8px;
    font-weight: bold;
    background: #e0e0e0;
}


.reveal .ONLY_TITLE_TABLE .paragraph>p {
	padding-bottom: 4%;
    list-style: none;
	margin-top: 3%;
	padding-right: 5%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_PARAGRAPH_TITLE .paragraph {
	list-style: none;
	margin-top: -45%;
	margin-left: 2%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;

}

.reveal .ONLY_PARAGRAPH .paragraph  {
	margin-top: -41%;
	list-style: none;
	z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_PARAGRAPH_IMAGE  .paragraph  {
	margin-top: -45%;
	margin-left: 2%;
	width: 130%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE .paragraph  {
	margin-left: 2%;
    margin-top: 1%;
	list-style: none;
    z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_PARAGRAPH  .paragraph {
	margin-left: 2%;
	list-style: none;
	margin-top: 3%;
	padding-right: 2%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .SIMPLE_LIST___ONLY_TITLE_LIST ul li  {
	list-style: none !important;
	padding-bottom: 48px;
	padding-right: 52px;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .SIMPLE_LIST___ONLY_TITLE_LIST ul  {
    margin-left: 2%;
    padding-top: 10%;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_LIST_NUMBERED ul li  {
	list-style: none !important;
	padding-bottom: 48px;
	padding-right: 52px;
	list-style-type: decimal-leading-zero;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_LIST_NUMBERED ul  {
    margin-left: 2%;
    padding-top: 10%;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_TREE ul li  {
    margin-bottom: 20px;
	padding-right: 40px;
    list-style-type: none;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_TREE ul  {
    margin-top: 45px;
	margin-left: 2%;
    padding-bottom: 56px;
    list-style-type: none;
}

.reveal .ONLY_LIST ul li  {
	list-style: none !important;
	padding-bottom: 48px;
	padding-right: 52px;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_LIST ul  {
    margin-left: 2%;
    margin-top: -41%;
}

.reveal .ONLY_LIST_NUMBERED ul li  {
	list-style: none !important;
	padding-bottom: 48px;
	padding-right: 52px;
	list-style-type: decimal-leading-zero;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_LIST_NUMBERED ul  {
    margin-left: 2%;
    margin-top: -41%;
}

.ONLY_2TITLE_TREE h1 {
	margin-top: -26%;
	margin-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.ONLY_2TITLE_TREE h2 {
	text-align: left;
	margin-left: 2%;
	color: #7E4B0E;
	font-weight: 400;
	margin-top: 13%;
	position: absolute;
}

.ONLY_2TITLE_TREE h3 {
	text-align: left;
	margin-left: 2%;
	color: #FDC530;
	font-weight: 400;
	position: absolute;
	margin-top: 49%;
}

.ONLY_2TITLE_TREE h4 {
	text-align: left;
	margin-left: 2%;
	color: #FDC530;
	font-weight: 400;
	position: absolute;
	margin-top: 75%;
}

.ONLY_2TITLE_TREE h5 {
	text-align: left;
	margin-left: 2%;
	color: #FDC530;
	font-weight: 400;
	position: absolute;
	margin-top: 49%;
	margin-left: 55%;
}

.ONLY_2TITLE_TREE h6 {
	text-align: left;
	margin-left: 2%;
	color: #FDC530;
	font-weight: 400;
	position: absolute;
	margin-top: 104%;
}
.reveal .ONLY_PARAGRAPH_IMAGE img {
	width: 180%;
}

.reveal .ONLY_TITLE_IMAGE img {
	width: 180%;
}

.reveal .ONLY_2TITLE_IMAGE img {
	width: 180%;
}

.reveal .ONLY_IMAGE img {
	width: 180%;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE img {
	width: 180%;
}

.reveal .ONLY_PARAGRAPH_TITLE #data_slide_paragraph {
	
}



.reveal .ONLY_2BOX #top {
	height: 900px;
	padding-top: 300px;
	margin-top: -110%;
	width: 129%;
	margin-left: -10%;
}


.reveal .ONLY_2BOX #bottom {
	height: 1000px;
	padding-top: 41px;
	margin-left: -10%;
	width: 120%;
}


.reveal .ONLY_2BOX #top h2 {
	color: <%=theme.get("title_____font_color")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-family: '<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
	padding-top: 26%;
	padding-bottom: 5%;
	margin-left: 11%;
}

.reveal .ONLY_2BOX #bottom h3 {
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family: <%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
	padding-top: 26%;
	margin-left: 11%;
	padding-bottom: 4%;
}

.reveal .ONLY_2BOX #top li {
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
	list-style: none;
	margin-left: 12%;
	padding-right: 31%;
}


.reveal .ONLY_2BOX #bottom li {
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
	list-style: none;
	margin-left: 12%;
	padding-right: 31%;
}



/* Below are of no use.. just for the demo */



.reveal .ONLY_TITLE_ASSESSMENT_2COLUMNS {
	background: white;;
}

.reveal .ONLY_TITLE_ASSESSMENT_2COLUMNS h2 {
	font-weight: 700;
	font-size: 90px;
	color: white;
	background-color: #9cc4a6;
	line-height: 2.7;
	width: 116%;
	padding-top: 130px;
	margin-top: -63%;
	margin-left: -10%;
	text-align: left;
	margin-left: 7%;
}

.reveal .ONLY_TITLE_ASSESSMENT_2COLUMNS table tr td {
	font-weight: 700;
	font-size: 44px;
	line-height: 1.2;
	text-align: left;
	padding-top: 83px !important;
}

.reveal .ONLY_TITLE_ASSESSMENT_2COLUMNS table tr td input {
	width: 121px;
	height: 101px;
	padding-top: 44px;
}

.reveal .ONLY_2TITLE_TABLE h2 {
	font-weight: bolder;
	font-size: 133px;
	padding-top: 130px;
	color: #e89d81;
	line-height: 2.7;
	width: 100%;
	margin-top: -64%;
	text-align: left;
	margin-left: 0%;
}

.reveal .ONLY_2TITLE_TABLE table tr td {
	font-weight: 400;
	font-size: 50px;
	padding-top: 39px;
	padding: 14px;
	text-align: left;
}

.reveal .ONLY_2TITLE_TABLE table tr td input {
	width: 121px;
	height: 101px;
	padding-top: 44px;
}

.reveal .ONLY_2TITLE_TABLE {
	background: white;;
}

.reveal .ONLY_2TITLE_TABLE h3 {
	font-weight: bold;
	font-size: 67px;
	color: #e89d81;
	line-height: 2.7;
	width: 100%;
	margin-top: -17%;
	text-align: left;
	margin-left: 0%;
}



/* Infographics */

.reveal .IN_OUT_1___ONLY_TITLE_LIST .fragment.first {

  -webkit-transform: translate(0, -20%);
          transform: translate(0, -20%); 
          }

.reveal .IN_OUT_1___ONLY_TITLE_LIST .fragment.first.visible {
    -webkit-transform: translate(0, 0);
            transform: translate(0, 0); }

.reveal .IN_OUT_1___ONLY_TITLE_LIST h2 {
    margin-left: 44%;
    margin-top: -42%;
    color: #ffffff;
    font-weight: 100;
    font-size: 64px;
    line-height: 1;
    text-align: left;
    font-family: Roboto;
    padding-bottom: 10%;
}


.reveal .IN_OUT_1___ONLY_TITLE_LIST h3 {    
    margin-left: 44%;
    margin-top: 19%;
    color: #ffffff;
    padding-bottom: 16%;
    font-weight: 100;
    font-size: 64px;
    line-height: 1;
    text-align: left;
    font-family: Roboto;
}


.reveal .IN_OUT_1___ONLY_TITLE_LIST .parent {
    list-style: none !important;
    color: #ffffff;
    font-weight: 100;
    font-size: 37px;
    line-height: 1;
    text-align: left;
    font-family: Roboto;
    margin-left: 9%;
}


.reveal .IN_OUT_1___ONLY_TITLE_LIST .child  {    
	list-style: none !important;
    color: #ffffff;
    font-weight: 100;
    font-size: 37px;
    line-height: 1;
    text-align: left;
    font-family: Roboto;
    margin-left: 26%;
}

.reveal .IN_OUT_1___ONLY_TITLE_LIST .img-init{
    position: absolute;
    margin-top: -8%;
    margin-left: -56%;
    zoom: 119%;
}

.reveal .IN_OUT_1___ONLY_TITLE_LIST .img-top{
    position: fixed;
    margin-left: -38%;
    width: 16%;
    margin-top: 55%;
}

.reveal .IN_OUT_1___ONLY_TITLE_LIST .img-bottom{
    position: fixed;
    margin-left: -48%;
    width: 33%;
    margin-top: 21%;
}
    
.reveal .IN_OUT_1___ONLY_TITLE_LIST li.parent{
	background: url("/content/assets/img/coin.png") no-repeat top left !important;
    padding: 5%;
    padding-left: 8%;

}


.reveal .IN_OUT_1___ONLY_TITLE_LIST li.child{
	list-style-image: url("/content/assets/img/box.png") !important;
    padding-left: 6%;
}



/* IN_OUT_2 infographic */

.reveal .IN_OUT_2___ONLY_TITLE_LIST .in_out_2_top{
	position:fixed;
	margin-top: -57%;
}
.reveal .IN_OUT_2___ONLY_TITLE_LIST .in_out_2_bottom{
	position:fixed;
	margin-top: 28%;
}
.reveal .IN_OUT_2___ONLY_TITLE_LIST .top_title{
	position:fixed;
	height:50%;
	color: <%=theme.get("title_____font_color")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-family: '<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
	
}
.reveal .IN_OUT_2___ONLY_TITLE_LIST .bottom_title{
	position:fixed;
	height:50%;
	color: <%=theme.get("title_____font_color")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-family: '<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .top_title .img-top{
    position: fixed;
	margin-top: 53%;
    zoom: 120%;
    margin-left: -2%;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .bottom_title .img-bottom{
	position:fixed;
	margin-top: 65%;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .top_list{
    font-weight: 100;
    font-size: 37px;
    line-height: 1;
    text-align: left;
    font-family: Roboto;
	position:fixed;   
    margin-top: 15%;
    margin-left: 40%;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .bottom_list{
    font-weight: 100;
    font-size: 37px;
    line-height: 1;
    text-align: left;
    font-family: Roboto;
	position:fixed;
    margin-top: 25%;
    margin-left: 27%;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .top_list .parent{
font-size: 40px;margin-left: 130%;padding-bottom: 10%;
}
.reveal .IN_OUT_2___ONLY_TITLE_LIST .top_list .child{
font-size: 40px;margin-left: 175%;padding-bottom: 10%;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .bottom_list .parent{
font-size: 40px; margin-left: 235%;padding-bottom: 25%;
}
.reveal .IN_OUT_2___ONLY_TITLE_LIST .bottom_list .child{
font-size: 40px; margin-left: 335%;padding-bottom: 25%;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .top_list li.parent{
	list-style-image: url("/content/assets/img/in_out_2_infographic/top_drop_big.png") !important;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .top_list li.child{
	list-style-image: url("/content/assets/img/in_out_2_infographic/top_drop_small.png") !important;
}

.reveal .IN_OUT_2___ONLY_TITLE_LIST .bottom_list li.parent{
	list-style-image: url("/content/assets/img/in_out_2_infographic/bottom_smoke_1.png") !important;
}
.reveal .IN_OUT_2___ONLY_TITLE_LIST .bottom_list li.child{
	list-style-image: url("/content/assets/img/in_out_2_infographic/bottom_smoke_2.png") !important;
}
</style>