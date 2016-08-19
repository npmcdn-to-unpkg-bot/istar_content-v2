package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.MediaService;

/**
 * Servlet implementation class ApprovedLessonForCreatorController
 */
@WebServlet("/assigned_media_creative_admin")
public class TaskAssignedForCreativeAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskAssignedForCreativeAdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		ArrayList<ArrayList<String>> list_to_be_displayed = new ArrayList<ArrayList<String>>(); 
		List<Image> images= new MediaService().getAllImagesCreatedByContentCreator(user.getId());
		for(Image image : images)
		{
			ArrayList<String> embed_list = new ArrayList<String>();
			embed_list.add(image.getId().toString());
			embed_list.add(image.getTitle());
			embed_list.add(image.getDescription());
			Task task = new TaskDAO().findByItemId(image.getId()).get(0);
			embed_list.add(task.getItemType());
			embed_list.add(new IstarUserDAO().findById(task.getActorId()).getName());
			embed_list.add(task.getStatus());
			
			list_to_be_displayed.add(embed_list);
		}	
		request.setAttribute("images", list_to_be_displayed);
		request.getRequestDispatcher("/media/media_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
