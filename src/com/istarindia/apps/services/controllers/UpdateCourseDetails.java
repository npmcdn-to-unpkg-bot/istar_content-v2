package com.istarindia.apps.services.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.services.CourseService;

/**
 * Servlet implementation class SaveSlideOrderController
 */
@WebServlet("/update_course")
public class UpdateCourseDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCourseDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("action").equalsIgnoreCase("reorder")){
			reorder(request, response);
			
		} else if(request.getParameter("action").equalsIgnoreCase("update")) {
			update_course(request, response);
		}
	}

	
	/*	
	 * Update course/module/session details
 	 */	
	private void update_course(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException, IOException {

		CourseService service = new CourseService();
		
		switch (request.getParameter("entity_type").toString()) {
		case "course":
			int course_id = Integer.parseInt(request.getParameter("course_id").toString());
			String course_name = request.getParameter("title").toString();
			String course_description = request.getParameter("description").toString();
			service.updateCourse(course_id, course_name, course_description);
			
			request.setAttribute("message_success", "Course details are updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_course.jsp?course_id=" +course_id).forward(request, response);
			break;
			
		case "module":
			int module_id = Integer.parseInt(request.getParameter("module_id").toString());
			String module_name = request.getParameter("title").toString();
			service.updateModule(module_id, module_name);
			
			request.setAttribute("message_success", "Module details are updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_module.jsp?module_id=" +module_id).forward(request, response);
			break;
			
		case "session":
			int cmsession_id = Integer.parseInt(request.getParameter("session_id").toString());
			String cmsession_name = request.getParameter("title").toString();
			String cmsession_description = request.getParameter("description").toString();
			service.updateSession(cmsession_id, cmsession_name, cmsession_description);
			
			request.setAttribute("message_success", "Session details are updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_session.jsp?module_id=" +cmsession_id).forward(request, response);
			break;
		
		}
		
	}

	/*	
	 * Update module/session/lesson order
 	 */	
	private void reorder(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException, IOException {
		
		String order_holder_string = request.getParameter("order_holder");
		String[] order_holder = order_holder_string.split(",");
		
		switch (request.getParameter("entity_type").toString()) {
		case "modules":
			updateModuleOrder(order_holder);
			request.setAttribute("message_success", "Module order is updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_course.jsp?course_id=" +Integer.parseInt(request.getParameter("course_id"))).forward(request, response);
			break;
			
		case "sessions":
			updateSessionOrder(order_holder);
			request.setAttribute("message_success", "Session order is updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_module.jsp?module_id=" +Integer.parseInt(request.getParameter("module_id"))).forward(request, response);
			break;
		
		case "lessons":
			updateLessonOrder(order_holder);
			request.setAttribute("message_success", "Lesson order is updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_session.jsp?session_id=" +Integer.parseInt(request.getParameter("session_id"))).forward(request, response);
			break;
		
		case "slides":
			updateSlideOrder(order_holder);
			response.sendRedirect("/content/edit_lesson?task_id=" +Integer.parseInt(request.getParameter("task_id")) );
			break;
		
		default:
			response.sendRedirect("/content/index.jsp");
			break;
			
		}	
		
	}

	private static void updateModuleOrder(String[] order_holder) {
		
		StringBuffer sql = new StringBuffer();
		for (int i=0;i<order_holder.length;i++){
			sql.append("UPDATE module SET order_id = "+(i+1)+" WHERE ID ="+order_holder[i]+"; ");
		}
		
		IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		session.beginTransaction();
		query.executeUpdate();
		session.getTransaction().commit();
		
	}

	private static void updateSessionOrder(String[] order_holder) {
		
		StringBuffer sql = new StringBuffer();
		for (int i=0;i<order_holder.length;i++){
			sql.append("UPDATE cmsession SET order_id = "+(i+1)+" WHERE ID ="+order_holder[i]+"; ");
		}
		
		IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		session.beginTransaction();
		query.executeUpdate();
		session.getTransaction().commit();
		
	}

	private static void updateLessonOrder(String[] order_holder) {
		
		StringBuffer sql = new StringBuffer();
		for (int i=0;i<order_holder.length;i++){
			sql.append("UPDATE lesson SET order_id = "+(i+1)+" from task WHERE lesson.ID =task.item_id and task.item_type='LESSON' and task.id="+order_holder[i]+"; ");
		}

		IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		session.beginTransaction();
		query.executeUpdate();
		session.getTransaction().commit();
		
	}

	private static void updateSlideOrder(String[] order_holder) {
		
		StringBuffer sql = new StringBuffer();
		for (int i=0;i<order_holder.length;i++){
			sql.append("UPDATE slide SET order_id = "+(i+1)+" WHERE ID ="+order_holder[i]+"; ");
		}
		
		IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		session.beginTransaction();
		query.executeUpdate();
		session.getTransaction().commit();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
