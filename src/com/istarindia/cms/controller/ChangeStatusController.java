package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.TaskService;
import com.istarindia.apps.services.controllers.auth.CreateSlideController;
import com.istarindia.apps.services.task.CreateLessonTaskManager;

/**
 * Servlet implementation class ChangeStatusController
 */
@WebServlet("/change_status")
public class ChangeStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeStatusController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("new_status") && request.getParameterMap().containsKey("task_id"))
		{
			int task_id = Integer.parseInt(request.getParameter("task_id"));
			String new_status = request.getParameter("new_status");
			IstarUser user = (IstarUser)request.getSession().getAttribute("user");
			CreateLessonTaskManager.pushTaskNotification(new TaskDAO().findById(task_id), user, "The status for the task is chamged from "+new TaskDAO().findById(task_id).getStatus() +" to "+ new_status);
			new TaskService().updateStatus(task_id, new_status);
			
			
			
			response.sendRedirect(request.getContextPath() + "/" + user.getUserType().toLowerCase() + "/dashboard.jsp");
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
