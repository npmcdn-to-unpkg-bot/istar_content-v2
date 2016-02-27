package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.TaskService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

@WebServlet("/assign_creator")
public class AssignCreativeController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignCreativeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printParams(request);
		for (String assign : request.getParameter("selected_items").split(",")) {
			if(assign.startsWith("image")) {
				
				assignImage(assign,request.getParameter("assign_user"));
				System.out.println(request.getParameterValues("review_user").length);
			}
			System.out.println(assign);
		}

		response.sendRedirect(request.getContextPath() + "/content_admin/course_structure.jsp");
	}

	private void assignImage(String assign, String creator) {
		// TODO Auto-generated method stub
		String image_id= assign.replace("image_", "");
		
		System.out.println(">>>"+image_id);
		TaskDAO dao = new  TaskDAO();
		Task task  = dao.findByItemId(Integer.parseInt(image_id)).get(0);
		task.setActorId(Integer.parseInt(creator));
		task.setStatus(StatusTypes.CREATIVE_ASSIGNED);
		Session session1 = dao.getSession();
		Transaction tx1 = null;
		try {
			tx1 = session1.beginTransaction();
			
			dao.save(task);
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		} finally {
			session1.close();
		}
	}

	private void assignVideo(String assign, String creator) {
		// TODO Auto-generated method stub
		String image_id= assign.replace("image_", "");
		
		System.out.println(">>>"+image_id);
		TaskDAO dao = new  TaskDAO();
		Task task  = dao.findByItemId(Integer.parseInt(image_id)).get(0);
		task.setActorId(Integer.parseInt(creator));
		task.setStatus(StatusTypes.CREATIVE_ASSIGNED);
		Session session1 = dao.getSession();
		Transaction tx1 = null;
		try {
			tx1 = session1.beginTransaction();
			
			dao.save(task);
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		} finally {
			session1.close();
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
