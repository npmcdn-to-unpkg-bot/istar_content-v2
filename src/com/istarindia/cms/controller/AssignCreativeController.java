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
			if(assign.startsWith("task")) {
				
				assignTask(assign,request.getParameter("assign_user"));
				
			}
			System.out.println(assign);
		}
		request.setAttribute("message_success", "Task has been assigned successfully!");
		request.getRequestDispatcher("/creative_admin/assign_creative.jsp").forward(request, response);

	//	response.sendRedirect(request.getContextPath() + "/creative_admin/assign_creative.jsp");
	}
	
	private void assignTask(String assign, String content_id) {
		String taskid= assign.replace("task_", "");
		TaskDAO dao = new  TaskDAO();
		Task task  = dao.findById(Integer.parseInt(taskid));
		task.setActorId(Integer.parseInt(content_id));
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
