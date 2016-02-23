package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.services.TaskService;

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
			new TaskService().updateStatus(task_id, new_status);
			
			IstarUser user = (IstarUser)request.getSession().getAttribute("user");
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
