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
import com.istarindia.apps.dao.ContentCreator;
import com.istarindia.apps.dao.ContentCreatorDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.TaskReviewer;
import com.istarindia.apps.dao.TaskReviewerDAO;
import com.istarindia.apps.services.LessonService;
import com.istarindia.apps.services.task.TaskManager;
import com.istarindia.apps.services.task.TaskManagerFactory;


@WebServlet("/lesson_assigned_reviewer")
public class LessonAssignedToReviewerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LessonAssignedToReviewerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		ArrayList<ArrayList<String>> list_to_be_displayed = new ArrayList<ArrayList<String>>(); 
		//id, lessonName, session, module, course,  created by, review		
		//these values can be accessed in jsp page to render the data 
		List<Lesson> lessons= new LessonService().getAllLessonAssignedToReviewer(user.getId());
		for(Lesson lesson : lessons)
		{
			ArrayList<String> embed_list = new ArrayList<String>();
			embed_list.add(lesson.getId().toString());
			embed_list.add(lesson.getTitle());
			embed_list.add(lesson.getCmsession().getTitle());
			embed_list.add(lesson.getCmsession().getModule().getModuleName());
			embed_list.add(lesson.getCmsession().getModule().getCourse().getCourseName());
			
			List<Task> task_list = new TaskDAO().findByItemId(lesson.getId());
			Task task = null;
			for(Task task1: task_list)
			{
				if(task1.getItemType().equalsIgnoreCase("LESSON"))
				{
					task= task1;
				}
			}
			embed_list.add(new ContentCreatorDAO().findById(task.getActorId()).getName());
			TaskManager manager = (new TaskManagerFactory()).getManager(task.getItemType());
			embed_list.add(manager.getTaskStatusForm(task,user));
			embed_list.add(lesson.getId().toString()); //review the lesson
			
			list_to_be_displayed.add(embed_list);
		}	
		request.setAttribute("lessons", list_to_be_displayed);
		
		request.getRequestDispatcher("/content_reviewer/assigned_lesson.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
