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
import com.istarindia.apps.dao.Cmsession;
import com.istarindia.apps.dao.CmsessionDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.CourseService;
import com.istarindia.apps.services.LessonService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.apps.services.task.CreateLessonTaskManager;


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
			
			//Associate All Learning objectives for this session to this Lesson 
			if (request.getParameterMap().containsKey("learningObjectives")) {
				learningObjectives = (String[]) request.getParameterMap().get("learningObjectives");
				for (String element : learningObjectives) {
					ite.add(new LearningObjectiveDAO().findById(Integer.parseInt(element)));
				}
			}
			
			for (String assign : request.getParameter("selected_items").split(",")) {
				if(assign.startsWith("session_")) {
					createLesson(request, assign,title, duration,tags,ite,user.getId(), request.getSession().getId());
				}
			}
			request.setAttribute("message_success", "New lesson has been created successfully!");
			request.getRequestDispatcher("/lesson/new_lesson.jsp").forward(request, response);
		} else {
			request.setAttribute("message_failure", "Something was missing!");
			request.getRequestDispatcher(user.getUserType().toLowerCase()+"/dashboard.jsp").forward(request, response);	
		}	
	}

	
	
	private void createLesson(HttpServletRequest request, String session_id, String title, int duration, String tags, Set<LearningObjective> ite, int user_id, String user_session_id) {
		int cmsession_id = Integer.parseInt(session_id.replace("session_", ""));
		CmsessionDAO dao = new CmsessionDAO();
		
		for (Lesson lesss : dao.findById(cmsession_id).getLessons()) {
			ite.addAll(lesss.getLearningObjectives());
		}
		String lessonTheme = "43";
		String lessonDesktopTheme = "43";
		Cmsession cms =  (new CmsessionDAO()).findById(cmsession_id);
		
		Lesson lesson = new LessonService().createLessonForBulk(cms, duration, LessonTypes.LESSON, tags, lessonTheme, lessonDesktopTheme, title, user_id);
		
		CourseService.updateLearningObjectiveWithLesson(lesson, ite);
		lesson = (new LessonDAO()).findById(lesson.getId());
		Task task = lesson.getTask();

		String comments = "New Lesson with title: " + lesson.getTitle()+ "(ID " + lesson.getId() + ") " + " for TaskID " + lesson.getTask().getId() + ") is created by " + (new IstarUserDAO().findById(user_id)).getEmail();
		CMSRegistry.addTaskLogEntry(request, task.getStatus(), comments, task.getId(), "LESSON", lesson.getId(), "New Lesson is created");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
