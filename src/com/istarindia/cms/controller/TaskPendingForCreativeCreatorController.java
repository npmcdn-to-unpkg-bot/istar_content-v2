package com.istarindia.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.MediaService;
import com.istarindia.apps.services.task.TaskManager;
import com.istarindia.apps.services.task.TaskManagerFactory;

/**
 * Servlet implementation class MediaUploadController
 */
@WebServlet("/tasks_pending")
public class TaskPendingForCreativeCreatorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static File fileUploadPath;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskPendingForCreativeCreatorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		ArrayList<ArrayList<String>> list_to_be_displayed = new ArrayList<ArrayList<String>>(); 
		List<Image> images= new MediaService().getAllImagesAssignedTO_CreativeCreator(user.getId(),  StatusTypes.CREATED);
		for(Image image : images)
		{
			ArrayList<String> embed_list = new ArrayList<String>();
			embed_list.add(image.getId().toString());
			embed_list.add(image.getTitle());
			Task task = new TaskDAO().findByItemId(image.getId()).get(0);
			embed_list.add(new IstarUserDAO().findById(task.getActorId()).getName());
			
			TaskManager manager = (new TaskManagerFactory()).getManager(task.getItemType());
			embed_list.add(manager.getTaskStatusForm(task,user));
		
			embed_list.add(image.getId().toString());
			embed_list.add(image.getId().toString());
			list_to_be_displayed.add(embed_list);
		}	
		request.setAttribute("images", list_to_be_displayed);
		
		request.getRequestDispatcher("/content_reviewer/completed_lesson.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
