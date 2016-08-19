package com.istarindia.cms.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class ApprovedLessonForCreatorController
 */
@WebServlet("/draft_media")
public class TaskDraftForCreativeCreaorController extends HttpServlet {/*
	private static final long serialVersionUID = 1L;
       
    *//**
     * @see HttpServlet#HttpServlet()
     *//*
    public TaskDraftForCreativeCreaorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		ArrayList<ArrayList<String>> list_to_be_displayed = new ArrayList<ArrayList<String>>(); 
		//id, lessonName, session, module, course,  review_by, request for publish
		//these values can be accessed in jsp page to render the data 
		List<Task> tasks  = new MediaService().getAllTaskAssignedForCreativeCreator(user.getId(), StatusTypes.DRAFT); 
		for(Task task : tasks)
		{
			ArrayList<String> embed_list = new ArrayList<String>();
		
		 * id, title, task name, and action
		 * 
			embed_list.add(task.getId().toString());
			
			
			if(task.getItemType().equals("IMAGE"))
			{
				Image img = task.getImage();
				
				embed_list.add("IMAGE");
				embed_list.add(img.getTitle());
				embed_list.add(img.getDescription());//3
				
			}
			else if (task.getItemType().equals("VIDEO"))
			{
				Video vid = task.getVideo();
				embed_list.add("VIDEO");
				embed_list.add(vid.getTitle());
				embed_list.add(vid.getDescription());//3
				
			}	
			
			
			
			TaskManager manager = (new TaskManagerFactory()).getManager(task.getItemType());
			System.out.println(">>>task is >> "+task.getStatus());
			embed_list.add(manager.getTaskStatusForm(task,user));
			System.out.println(manager.getTaskStatusForm(task,user));
			
			
			list_to_be_displayed.add(embed_list);
		}	
		request.setAttribute("tasks", list_to_be_displayed);
		request.getRequestDispatcher("/creative_creator/inprogress.jsp").forward(request, response);
	
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

*/}
