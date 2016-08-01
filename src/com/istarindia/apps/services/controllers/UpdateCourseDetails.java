package com.istarindia.apps.services.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.Cmsession;
import com.istarindia.apps.dao.ContentAdmin;
import com.istarindia.apps.dao.Course;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Module;
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
		
		switch (request.getParameter("action").toString()) {
		case "reorder":
			reorder(request, response);
			break;
		case "update":
			update_course(request, response);
			break;
		case "create":
			add_item(request, response);
			break;	
		}
	}

	/*	
	 * Add new course/module/session 
 	 */	
	private void add_item(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException, IOException {

		CourseService service = new CourseService();
		
		switch (request.getParameter("entity_type").toString()) {
		case "course":
			String course_name = request.getParameter("title").toString();
			String course_description = request.getParameter("description").toString();
			Course course = service.createNewCourse(course_name, course_description);
			
			if(course == null) {
				request.setAttribute("message_failure", "Please try again! Duplicate course found!");
			} else {
				request.setAttribute("message_success", "New course has been added successfully!");
			}
			request.getRequestDispatcher("/content_admin/course_list.jsp").forward(request, response);
			break;
			
		case "module":
			int course_id = Integer.parseInt(request.getParameter("course_id").toString());
			String module_name = request.getParameter("title").toString();
			Module module = service.createNewModule(module_name, course_id);

			if(module == null) {
				request.setAttribute("message_failure", "Please try again! Duplicate module found!");
			} else {
				request.setAttribute("message_success", "New module has been added successfully!");
			}
			request.getRequestDispatcher("/content_admin/modify_course.jsp?course_id=" +course_id).forward(request, response);
			break;
			
		case "session":
			int module_id = Integer.parseInt(request.getParameter("module_id").toString());
			String cmsession_name = request.getParameter("title").toString();
			String cmsession_description = request.getParameter("description").toString();
			Cmsession session = service.createNewCmsession(cmsession_name, cmsession_description, module_id, (ContentAdmin)request.getSession().getAttribute("user"));

			if(session == null) {
				request.setAttribute("message_failure", "Please try again! Something is wrong!");
			} else {
				request.setAttribute("message_success", "New sessioin has been added successfully!");
			}
			request.getRequestDispatcher("/content_admin/modify_module.jsp?module_id=" +module_id).forward(request, response);
			break;
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
		String entity_type = request.getParameter("entity_type").toString();
		
		if(order_holder.length == 0) {
		
			request.setAttribute("message_failure", "No " + entity_type + "s to reorder! Operation is aborted");
	
		} else {
			
			switch (entity_type) {
			case "modules":
				updateModuleOrder(request, order_holder);
				request.getRequestDispatcher("/content_admin/modify_course.jsp?course_id=" +Integer.parseInt(request.getParameter("course_id"))).forward(request, response);
				break;
				
			case "sessions":
				updateSessionOrder(request, order_holder);
				request.getRequestDispatcher("/content_admin/modify_module.jsp?module_id=" +Integer.parseInt(request.getParameter("module_id"))).forward(request, response);
				break;
			
			case "lessons":
				updateLessonOrder(request, order_holder);
				request.getRequestDispatcher("/content_admin/modify_session.jsp?session_id=" +Integer.parseInt(request.getParameter("session_id"))).forward(request, response);
				break;
			
			case "slides":
				updateSlideOrder(request, order_holder);
				response.sendRedirect("/content/edit_lesson?task_id=" +Integer.parseInt(request.getParameter("task_id")) );
				break;
			
			default:
				request.setAttribute("message_failure", "Something went wrong. Please try again!");
				response.sendRedirect("/content/index.jsp");
				break;
			}
			
		}
	}

	private static void updateModuleOrder(HttpServletRequest request, String[] order_holder) {
		
		try {
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

			request.setAttribute("message_success", "Modules have been reordered successfully! Please recheck to confirm");
			
		} catch (Exception e) {
			request.setAttribute("message_failure", "Something went wrong. Please try again!");
			//System.err.println("sql: " + sql);
		}
		
	}

	private static void updateSessionOrder(HttpServletRequest request, String[] order_holder) {
		
		try {
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

			request.setAttribute("message_success", "Sessions have been reordered successfully! Please recheck to confirm");
			
		} catch (Exception e) {
			request.setAttribute("message_failure", "Something went wrong. Please try again!");
			//System.err.println("sql: " + sql);
		}
		
	}

	private static void updateLessonOrder(HttpServletRequest request, String[] order_holder) {
		
		try {
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

			request.setAttribute("message_success", "Lessons have been reordered successfully! Please recheck to confirm");
			
		} catch (Exception e) {
			request.setAttribute("message_failure", "Something went wrong. Please try again!");
			//System.err.println("sql: " + sql);
		}
		
	}

	private static void updateSlideOrder(HttpServletRequest request, String[] order_holder) {
		
		try {
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

			request.setAttribute("message_success", "Slides have been reordered successfully! Please recheck to confirm");
			
		} catch (Exception e) {
			request.setAttribute("message_failure", "Something went wrong. Please try again!");
			//System.err.println("sql: " + sql);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
