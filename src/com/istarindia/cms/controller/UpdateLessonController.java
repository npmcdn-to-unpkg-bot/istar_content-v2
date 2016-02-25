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
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.LessonTypes;
import com.istarindia.apps.dao.*;
import com.istarindia.apps.services.LessonService;
import com.istarindia.apps.services.PresentationService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class EditLessonController
 */
@WebServlet("/update_lesson")
public class UpdateLessonController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	public UpdateLessonController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * inputs in request Integer cmsession_id, Integer duration, String
		 * lessonType, String tags, String title, String[] learningObjectives
		 */

		printParams(request);
		IstarUser user = (IstarUser) request.getSession().getAttribute("user");
		String tags = "";
		String learningObjectives[];
		Set<LearningObjective> ite = new HashSet<LearningObjective>();
		if (request.getParameterMap().containsKey("lesson_id") && request.getParameterMap().containsKey("cmsession_id")
				&& request.getParameterMap().containsKey("duration")
				&& request.getParameterMap().containsKey("lessonType")
				&& request.getParameterMap().containsKey("title")) 
		
		{
			int lesson_id = Integer.parseInt(request.getParameter("lesson_id"));
			int cmsession_id = Integer.parseInt(request.getParameter("cmsession_id"));
			int duration = Integer.parseInt(request.getParameter("duration"));
			String lessonType = request.getParameter("lessonType");

			String title = request.getParameter("title");
			if (request.getParameterMap().containsKey("tags")) {
				tags = request.getParameter("tags");
			}
			if (request.getParameterMap().containsKey("learningObjectives")) {
				learningObjectives = (String[]) request.getParameterMap().get("learningObjectives");
				for (String element : learningObjectives) {
					ite.add(new LearningObjectiveDAO().findById(Integer.parseInt(element)));
				}
			}
			/*
			 * <option value="Presentation">Presentation</option> <option
			 * value="Assessment">Assessment</option> <option
			 * value="Game">Game</option>
			 */
			Lesson lesson = (new LessonDAO()).findById(lesson_id);
			switch (lessonType) {
			case "Presentation":
				new PresentationService().createPresentation(cmsession_id, duration, tags, title, "", null, user.getId(), lesson.getId());
				lesson.setLessonType(LessonTypes.PRESENTATION);
				//lesson = (Presentaion) lesson;
				break;
			case "Assessment":
				lesson.setLessonType(LessonTypes.ASSESSMENT);
				//lesson = (Assessment) lesson;
				break;
			case "Game":
				lesson.setLessonType(LessonTypes.GAME);
				//lesson = (Game) lesson;
				break;
			}

			LessonService service = new LessonService();
			lesson = (Lesson) service.updateLesson(lesson_id, cmsession_id, duration, lessonType, tags, title, "dtype",
					ite);

			request.setAttribute("lesson", lesson);
			request.getRequestDispatcher("/lesson/edit_lesson.jsp").forward(request, response);
		} else {
			System.out.println("Something missing .... ");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
