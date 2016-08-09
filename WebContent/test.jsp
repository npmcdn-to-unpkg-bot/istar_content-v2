<%@page import="com.istarindia.apps.services.CourseService"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.*"%>
<%@page import="com.istarindia.apps.dao.*"%><%@page import="java.util.*"%>

<% 

long now = System.currentTimeMillis();

		
		CourseDAO dao = new CourseDAO();
		for (Course course : (List<Course>) dao.findAll()) {
			//for (Module mod : course.getModules()) {
			//	for (Cmsession sess : mod.getCmsessions()) {
				//	for (Lesson string : sess.getLessons()) {
						
				//	}
			//	}
			//}
		}
		long now1 = System.currentTimeMillis();
		System.out.println("Time tlo load this page "+(now1-now)/1000) ;
		
		%>