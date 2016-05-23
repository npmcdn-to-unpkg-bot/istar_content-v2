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

/**
 * Servlet implementation class SaveSlideOrderController
 */
@WebServlet("/update_order")
public class UpdateOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateOrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameterMap().containsKey("order_holder")){
			String order_holder_string = request.getParameter("order_holder");
			String[] order_holder = order_holder_string.split(",");
			
			switch (request.getParameter("entity_type").toString()) {
			case "modules":
				updateModuleOrder(order_holder);
				response.sendRedirect("/content/content_admin/modify_course_structure.jsp?course_id=" +Integer.parseInt(request.getParameter("course_id")) );
				break;
			case "sessions":
				updateSessionOrder(order_holder);
				response.sendRedirect("/content/content_admin/modify_course_structure.jsp?module_id=" +Integer.parseInt(request.getParameter("module_id")) );
				break;
			case "lessons":
				updateLessonOrder(order_holder);
				response.sendRedirect("/content/content_admin/modify_course_structure.jsp?session_id=" +Integer.parseInt(request.getParameter("session_id")) );
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
