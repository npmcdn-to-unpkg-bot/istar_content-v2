package com.istarindia.cms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.TaskService;

/**
 * Servlet implementation class EditMediaController
 */
@WebServlet("/edit_media")
public class EditMediaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMediaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {/*
		// TODO Auto-generated method stub
		TaskDAO dao = new TaskDAO();
		Task task = dao.findById(Integer.parseInt(request.getParameter("task_id")));
		
		new TaskService().updateStatus(task.getId(), StatusTypes.DRAFT);
		if(task.getItemType().equalsIgnoreCase("IMAGE"))
		{
			request.setAttribute("item_id", task.getImage().getId());
		}
		else  if(task.getItemType().equalsIgnoreCase("VIDEO"))
		{
			request.setAttribute("item_id", task.getVideo().getId());
		}
		
		request.getRequestDispatcher("/creative_creator/upload_media.jsp").forward(request, response);
	*/}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
