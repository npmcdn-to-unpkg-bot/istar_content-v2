
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
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.Video;
import com.istarindia.apps.dao.VideoDAO;
import com.istarindia.apps.services.MediaService;
import com.istarindia.apps.services.task.TaskManager;
import com.istarindia.apps.services.task.TaskManagerFactory;

/**
 * Servlet implementation class MediaUploadController
 */
@WebServlet("/created_media_content_creator")
public class TaskCreatedByContentCreatorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static File fileUploadPath;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskCreatedByContentCreatorController() {
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
		List<Task> tasks= new MediaService().getAllTaskCreatedByContentCreator(user.getId());
		for(Task task : tasks)
		{
			ArrayList<String> embed_list = new ArrayList<String>();
			embed_list.add(task.getId().toString());//0
			if(task.getItemType().equalsIgnoreCase("IMAGE"))
			{System.out.println("task id here is "+ task.getId());
				embed_list.add(new ImageDAO().findById(task.getItemId()).getTitle());//1
			}	
			else if(task.getItemType().equalsIgnoreCase("VIDEO"))
			{
				embed_list.add(new VideoDAO().findById(task.getItemId()).getTitle());//1
			}	
			embed_list.add(task.getTaskName());//2
			
			embed_list.add(new IstarUserDAO().findById(task.getActorId()).getName());//3
			TaskManager manager = (new TaskManagerFactory()).getManager(task.getItemType());
			embed_list.add(manager.getTaskStatusForm(task,user));
			
			list_to_be_displayed.add(embed_list);
		}	
		request.setAttribute("tasks", list_to_be_displayed);
		request.getRequestDispatcher("/media/media_list.jsp").forward(request, response);
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
