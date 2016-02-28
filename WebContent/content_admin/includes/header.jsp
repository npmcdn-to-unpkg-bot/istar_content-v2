<%@page import="com.istarindia.apps.role.ChildLink"%>
<%@page import="javafx.scene.Parent"%>
<%@page import="com.istarindia.apps.role.ParentLink"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.dao.IstarUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
try{
if (request.getSession().getAttribute("user")==null) {
	System.out.println("redirecting to index");
	String url1 = "/content/"+ "index.jsp";
	request.getRequestDispatcher(url1).forward(request, response);
	
	System.out.println("came back to header");
}
}catch(NullPointerException ex)
{
	System.out.println("npe");
	String url1 = "/content/"+ "index.jsp";
	request.getRequestDispatcher(url1).forward(request, response);
}
String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
System.out.println("session user1 ="+request.getSession().getAttribute("user"));

%><div class="header">
	<div class="container-fluid ">
		<!-- Logo -->
		<a class="logo" href="/content/index.jsp"> <img src="<%=baseURL %>assets/img/logo1-default.png" alt="Logo">
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
				<% for(ParentLink parent :  CMSRegistry.menu.getLinks())  {
					boolean able_to_see=false;
					for(ChildLink child :  parent.getChildren())  {
						String userRold = ((IstarUser)request.getSession().getAttribute("user")).getUserType().toLowerCase();
						if(child.getValidRoles().contains(userRold)) {
							able_to_see = true;
							break;
						}
					}
					if(able_to_see)
					{
					%>
				<li class="dropdown"><a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> <%=parent.getDisplayName() %> </a>
					<ul class="dropdown-menu">
					<% for(ChildLink child :  parent.getChildren())  {
						String userRold = ((IstarUser)request.getSession().getAttribute("user")).getUserType().toLowerCase();
						if(child.getValidRoles().contains(userRold)) {
						%>
						<li><a href="<%=child.getUrl() %>"><%=child.getDisplayName() %></a></li>
						<% }  } %>
					</ul>
				</li>
				
				<% 
					}
					}  %>
			</ul>
		</div>
	</div>
</div>