package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.LessonTypes;
import com.istarindia.apps.dao.CmsessionDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.services.LessonService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;


/**
 * Servlet implementation class CreateLessonController
 */
@WebServlet("/create_lesson")
public class CreateLessonController  extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
    
    public CreateLessonController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printParams(request);
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		if(request.getParameterMap().containsKey("title") && request.getParameterMap().containsKey("duration") && request.getParameterMap().containsKey("Tags") && request.getParameterMap().containsKey("selected_items") )
		{
			String title = request.getParameter("title");
			int duration = Integer.parseInt(request.getParameter("duration"));
			String tags= "";
			String learningObjectives[];
			Set<LearningObjective> ite = new HashSet<LearningObjective>();
			if (request.getParameterMap().containsKey("Tags")) {
				tags = request.getParameter("Tags");
			}
			if (request.getParameterMap().containsKey("learningObjectives")) {
				learningObjectives = (String[]) request.getParameterMap().get("learningObjectives");
				for (String element : learningObjectives) {
					ite.add(new LearningObjectiveDAO().findById(Integer.parseInt(element)));
				}
			}
			for (String assign : request.getParameter("selected_items").split(",")) {
				if(assign.startsWith("session_")) {
					createLesson(assign,title, duration,tags,ite,user.getId());
				}
				System.out.println(assign);
			}
			//request.setAttribute("lesson", lesson);
			request.getRequestDispatcher(user.getUserType().toLowerCase()+"/dashboard.jsp").forward(request, response);
		}
		else
		{
			
		}	
		
		
	}

	
	
	private void createLesson(String session_id, String title, int duration, String tags, Set<LearningObjective> ite, int user_id) {
		int cmsession_id = Integer.parseInt(session_id.replace("session_", ""));
		
		Lesson lesson = new LessonService().createLessonForBulk(cmsession_id, duration, LessonTypes.LESSON, tags, LessonTypes.LESSON, title, ite, user_id);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
