package com.istarindia.apps.services.controllers;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.Cmsession;
import com.istarindia.apps.dao.CmsessionDAO;
import com.istarindia.apps.dao.ContentAdmin;
import com.istarindia.apps.dao.ContentAdminDAO;
import com.istarindia.apps.dao.Course;
import com.istarindia.apps.dao.CourseDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Module;
import com.istarindia.apps.dao.ModuleDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.CourseService;

/**
 * Servlet implementation class SaveSlideOrderController
 */
@WebServlet("/update_course")
public class UpdateCourseDetails extends HttpServlet {
	CMSRegistry cMSRegistry = new CMSRegistry();
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
		case "replicate":
			replicate_item(request, response);
		
			break;	
		}
	}

	private void replicate_item(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CourseService service = new CourseService();
		
		switch (request.getParameter("entity_type").toString()) {
		case "lesson":
			int lesson_id = Integer.parseInt(request.getParameter("lesson_id").toString());
			int dest_session_id = Integer.parseInt(request.getParameter("dest_session_id").toString());
			Lesson oldLesson = new LessonDAO().findById(lesson_id);
			Lesson newLesson = service.replicateLesson(lesson_id, dest_session_id);
			
			if(newLesson == null) {
				request.setAttribute("message_failure", "Please try again! There was a problem!");
				request.getRequestDispatcher("/content_admin/course_structure.jsp").forward(request, response);
			} else {
				request.setAttribute("message_success", "Lesson has been successfully replicated. This is the new lesson");
				request.getRequestDispatcher("/edit_lesson?task_id="+ newLesson.getTask().getId()).forward(request, response);

				String comments = "Lesson with name " + oldLesson.getTitle() + " (ID " + oldLesson.getId() + ") is replicated to new lesson with ID: " + newLesson.getId() + "by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
				CMSRegistry.addTaskLogEntry(request, "REPLICATED", comments, oldLesson.getTask().getId(), "LESSON", oldLesson.getId(), "Lesson is replicated");
				CMSRegistry.addTaskLogEntry(request, "CREATED", comments, newLesson.getTask().getId(), "LESSON", newLesson.getId(), "Lesson is replicated");
			}
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
				
				String comments = "Course with name " + course_name + " and with ID " + course.getId() + " is created by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
				CMSRegistry.addTaskLogEntry(request, "CREATED", comments, 0, "COURSE", course.getId(), "New course is created");
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
				
				String comments = "Module with name " + module_name + " and with ID " + module.getId() + " is created by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
				CMSRegistry.addTaskLogEntry(request, "CREATED", comments, 0, "MODULE", module.getId(), "New module is created");
			}
			request.getRequestDispatcher("/content_admin/modify_course.jsp?course_id=" +course_id).forward(request, response);
			break;
			
		case "session":
			int module_id = Integer.parseInt(request.getParameter("module_id").toString());
			String cmsession_name = request.getParameter("title").toString();
			String cmsession_description = request.getParameter("description").toString();
			ContentAdmin ca = new ContentAdmin(); 
			System.err.println("141 ->"+ request.getSession().getAttribute("user").getClass().toString());
			if(request.getSession().getAttribute("user").getClass().toString().equalsIgnoreCase("ContentAdmin")) {
				 ca = (ContentAdmin)request.getSession().getAttribute("user");
			} else {
				 ca = (ContentAdmin)(new ContentAdminDAO()).findAll().get(0);
			}
			Cmsession cmsession = service.createNewCmsession(cmsession_name, cmsession_description, module_id, ca);

			if(cmsession == null) {
				request.setAttribute("message_failure", "Please try again! Something is wrong!");
			} else {
				request.setAttribute("message_success", "New sessioin has been added successfully!");
				
				String comments = "Session with name " + cmsession_name + " and with ID " + cmsession.getId() + " is created by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
				CMSRegistry.addTaskLogEntry(request, "CREATED", comments, 0, "CMSESSION", cmsession.getId(), "New session is created");
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
		String comments  = "" ;
		int id = 0;
		String item_type = request.getParameter("entity_type").toString(); 
		switch (item_type) {
		case "course":
			id = Integer.parseInt(request.getParameter("course_id").toString());
			String course_name = request.getParameter("title").toString();
			String course_description = request.getParameter("description").toString();
			
			service.updateCourse(id, course_name, course_description);

			comments = "Course with id " + id + " is updated as Title: " + course_name + " ; Description: " + course_description + " ; by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
			CMSRegistry.addTaskLogEntry(request, "UPDATED", comments, 0, "COURSE", id, "Course details are updated");
			
			request.setAttribute("message_success", "Course details are updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_course.jsp?course_id=" +id).forward(request, response);
			break;
			
		case "module":
			id = Integer.parseInt(request.getParameter("module_id").toString());
			String module_name = request.getParameter("title").toString();
			
			service.updateModule(id, module_name);

			comments = "Module with id " + id + " is updated as Title: " + module_name + " ; by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
			CMSRegistry.addTaskLogEntry(request, "UPDATED", comments, 0, "MODULE", id, "Module name is updated");
			
			request.setAttribute("message_success", "Module details are updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_module.jsp?module_id=" +id).forward(request, response);
			break;
			
		case "session":
			id = Integer.parseInt(request.getParameter("session_id").toString());
			String cmsession_name = request.getParameter("title").toString();
			String cmsession_description = request.getParameter("description").toString();
			
			service.updateSession(id, cmsession_name, cmsession_description);
			
			comments = "Session with id " + id + " is updated as Title: " + cmsession_name + " ; Description: " + cmsession_description + " ; by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
			CMSRegistry.addTaskLogEntry(request, "UPDATED", comments, 0, "CMSESSION", id, "Session details are updated");
			
			request.setAttribute("message_success", "Session details are updated successfully!");
			request.getRequestDispatcher("/content_admin/modify_session.jsp?session_id=" +id).forward(request, response);
			break;
		}
			
	}

	/*	
	 * Update module/session/lesson order
 	 */	
	private void reorder(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException, IOException {
		String order_holder_string = request.getParameter("order_holder").toString();
		
		String entity_type = request.getParameter("entity_type").toString();
		String comments = "";
		
		if(order_holder_string.length() == 0) {
			request.setAttribute("message_failure", "No " + entity_type + " to reorder! Operation is aborted");
			
			switch (entity_type) {
			case "modules":
				request.getRequestDispatcher("/content_admin/modify_course.jsp?course_id=" + Integer.parseInt(request.getParameter("course_id"))).forward(request, response);
				break;
				
			case "sessions":
				request.getRequestDispatcher("/content_admin/modify_module.jsp?module_id=" + Integer.parseInt(request.getParameter("module_id"))).forward(request, response);
				break;
			
			case "lessons":
				request.getRequestDispatcher("/content_admin/modify_session.jsp?session_id=" + Integer.parseInt(request.getParameter("session_id"))).forward(request, response);
				break;
			
			case "slides":
				request.getRequestDispatcher("/edit_lesson?task_id=" +Integer.parseInt(request.getParameter("task_id"))).forward(request, response);
				break;
			
			default:
				request.setAttribute("message_failure", "Something went wrong. Please try again!");
				response.sendRedirect("/content/index.jsp");
				break;
			}
		} else {
			String[] order_holder = order_holder_string.split(",");
			switch (entity_type) {
			case "modules":
				updateModuleOrder(request, order_holder);
				
				int course_id = Integer.parseInt(request.getParameter("course_id"));
				String course_name = new CourseDAO().findById(course_id).getCourseName();
				comments = "Modules in course: " + course_name + " (ID " + course_id + ") are reordered as : [" + Arrays.toString(order_holder) + "] ; by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
				CMSRegistry.addTaskLogEntry(request, "REORDER", comments, 0, "MODULES", course_id, "Modules are reordered");
				
				request.getRequestDispatcher("/content_admin/modify_course.jsp?course_id=" +Integer.parseInt(request.getParameter("course_id"))).forward(request, response);
				break;
				
			case "sessions":
				updateSessionOrder(request, order_holder);
				
				int module_id = Integer.parseInt(request.getParameter("module_id").toString());
				String module_name = new ModuleDAO().findById(module_id).getModuleName();
				comments = "Sessions in module: " + module_name + " (ID " + module_id + ") are reordered as : [" + Arrays.toString(order_holder) + "] ; by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
				CMSRegistry.addTaskLogEntry(request, "REORDER", comments, 0, "CMSESSIONS", module_id, "Sessions are reordered");

				request.getRequestDispatcher("/content_admin/modify_module.jsp?module_id=" +Integer.parseInt(request.getParameter("module_id"))).forward(request, response);
				break;
			
			case "lessons":
				updateLessonOrder(request, order_holder);
				
				int session_id = Integer.parseInt(request.getParameter("session_id").toString());
				String session_name = new CmsessionDAO().findById(session_id).getTitle();
				comments = "Lessons in session: " + session_name + " (ID " + session_id + ") are reordered as : [" + Arrays.toString(order_holder) + "] ; by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
				CMSRegistry.addTaskLogEntry(request, "REORDER", comments, 0, "LESSONS", session_id, "Lessons are reordered");

				request.getRequestDispatcher("/content_admin/modify_session.jsp?session_id=" +Integer.parseInt(request.getParameter("session_id"))).forward(request, response);
				break;
			
			case "slides":
				updateSlideOrder(request, order_holder);
				
				int task_id = Integer.parseInt(request.getParameter("task_id").toString());
				String lesson_name = request.getParameter("lesson_name");
				comments = "Slides in lesson: " + lesson_name + " (TaskID " + task_id + ") are reordered as : [" + Arrays.toString(order_holder) + "] ; by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail(); 
				CMSRegistry.addTaskLogEntry(request, "REORDER", comments, 0, "SLIDES", task_id, "Slides are reordered");

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

		StringBuffer sql = new StringBuffer();		
		try {
			for (int i=0;i<order_holder.length;i++){
				sql.append("UPDATE module SET order_id = " + (i+1) + " WHERE ID =" + order_holder[i] + "; ");
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
			e.printStackTrace();
			request.setAttribute("message_failure", "Something went wrong. Please try again!");
			//System.err.println("sql: " + sql);
		}
		
	}

	private static void updateSessionOrder(HttpServletRequest request, String[] order_holder) {

		StringBuffer sql = new StringBuffer();
		try {
			for (int i=0;i<order_holder.length;i++){
				sql.append("UPDATE cmsession SET order_id = " + (i+1) + " WHERE ID =" + order_holder[i] + "; ");
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

		StringBuffer sql = new StringBuffer();
		try {
			for (int i=0;i<order_holder.length;i++){
				sql.append("UPDATE lesson SET order_id = " + (i+1) + " from task WHERE lesson.ID =task.item_id and task.item_type='LESSON' and task.id=" + order_holder[i] + "; ");
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

		StringBuffer sql = new StringBuffer();
		try {
			for (int i = 0 ; i < order_holder.length ; i++){
				sql.append("UPDATE slide SET order_id = " + (i+1) + " WHERE ID =" + order_holder[i] + "; ");
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
			e.printStackTrace();
			System.err.println("sql: " + sql);
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
