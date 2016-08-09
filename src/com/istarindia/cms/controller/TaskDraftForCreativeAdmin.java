package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.ContentCreatorDAO;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.Video;
import com.istarindia.apps.dao.VideoDAO;
import com.istarindia.apps.services.MediaService;
import com.istarindia.apps.services.task.TaskManager;
import com.istarindia.apps.services.task.TaskManagerFactory;

/**
 * Servlet implementation class TaskDraftForCreativeAdmin
 */
@WebServlet("/task_draft_creative_admin")
public class TaskDraftForCreativeAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskDraftForCreativeAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		ArrayList<ArrayList<String>> list_to_be_displayed = new ArrayList<ArrayList<String>>(); 
		//id, lessonName, session, module, course,  review_by, request for publish
		//these values can be accessed in jsp page to render the data 
		List<Task> tasks  = new MediaService().getAllMediaTaskInDraft();
		for(Task task : tasks)
		{
			ArrayList<String> embed_list = new ArrayList<String>();
		/*
		 * id, title, task name, and action
		 * */
			embed_list.add(task.getId().toString());
			
			
			if(task.getItemType().equals("IMAGE"))
			{
				Image img = task.getImage();
				
				embed_list.add("IMAGE");
				embed_list.add(img.getTitle());
				embed_list.add(img.getDescription());//3
				embed_list.add(new IstarUserDAO().findById(task.getActorId()).getName());//4
			}
			else if (task.getItemType().equals("VIDEO"))
			{
				Video vid = task.getVideo();
				embed_list.add("VIDEO");
				embed_list.add(vid.getTitle());
				embed_list.add(vid.getDescription());//3
				embed_list.add(new IstarUserDAO().findById(task.getActorId()).getName());//4
			}	
			
			
			
			TaskManager manager = (new TaskManagerFactory()).getManager(task.getItemType());
			System.out.println(">>>task is >> "+task.getStatus());
			embed_list.add(manager.getTaskStatusForm(task,user));
			System.out.println(manager.getTaskStatusForm(task,user));
			
			
			list_to_be_displayed.add(embed_list);
		}	
		request.setAttribute("tasks", list_to_be_displayed);
		request.getRequestDispatcher("/creative_admin/inprogress.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
