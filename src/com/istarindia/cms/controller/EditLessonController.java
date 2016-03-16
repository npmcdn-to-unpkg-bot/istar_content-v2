package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.LessonService;
import com.istarindia.apps.services.TaskService;

/**
 * Servlet implementation class EditLessonController
 */
@WebServlet("/edit_lesson")
public class EditLessonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public EditLessonController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*	inputs in request
		 * Integer cmsession_id, Integer duration, String lessonType, String tags, String title, 
		String[] learningObjectives*/
		TaskDAO dao = new TaskDAO();
		Task task = dao.findById(Integer.parseInt(request.getParameter("task_id")));
		
		new TaskService().updateStatus(task.getId(), StatusTypes.DRAFT);
		
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		String  tags="";
		String learningObjectives[];
		Set<LearningObjective> ite = new HashSet<LearningObjective>();
		if (request.getParameterMap().containsKey("task_id"))
		{
			int lesson_id = task.getItemId();
			String title = request.getParameter("title");
			if(request.getParameterMap().containsKey("tags"))
			{
				 tags =  request.getParameter("tags");
			}
			if(request.getParameterMap().containsKey("learningObjectives"))
			{
				learningObjectives =  (String[])request.getParameterMap().get("learningObjectives");
				for(String element : learningObjectives)
				{
					ite.add(new LearningObjectiveDAO().findById(Integer.parseInt(element)));
				}
			}
		
			Lesson lesson= (new LessonDAO()).findById(lesson_id);
			
			request.setAttribute("lesson", lesson);
			request.setAttribute("task_id", Integer.parseInt(request.getParameter("task_id")));
			request.getRequestDispatcher("/lesson/edit_lesson.jsp").forward(request, response);
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
