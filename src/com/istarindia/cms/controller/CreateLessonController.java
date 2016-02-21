package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.services.LessonService;


/**
 * Servlet implementation class CreateLessonController
 */
@WebServlet("/create_lesson_controller")
public class CreateLessonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public CreateLessonController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/*	inputs in request
		 * Integer cmsession_id, Integer duration, String lessonType, String tags, String title, 
		String[] learningObjectives*/
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		String  tags="";
		String learningObjectives[];
		Set<LearningObjective> ite = new HashSet<LearningObjective>();
		if (request.getParameterMap().containsKey("cmsession_id") && request.getParameterMap().containsKey("duration") 
			&& request.getParameterMap().containsKey("lessonType") && request.getParameterMap().containsKey("title")  )
		{
			int cmsession_id = Integer.parseInt(request.getParameter("cmsession_id"));
			int duration = Integer.parseInt("duration");
			String lessonType = request.getParameter("lessonType");
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
			LessonService service = new LessonService();
			Lesson lesson= (Lesson) service.createLesson(cmsession_id, duration, lessonType, tags, title, "dtype", ite, user.getId());
			
			request.setAttribute("lesson", lesson);
			response.sendRedirect(request.getContextPath() + "/lesson/edit_lesson.jsp");
		}
		else
		{
			request.setAttribute("errMsg", "Mandatory Fields Missing");
			response.sendRedirect(request.getContextPath() + "/lesson/new_lesson.jsp");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
