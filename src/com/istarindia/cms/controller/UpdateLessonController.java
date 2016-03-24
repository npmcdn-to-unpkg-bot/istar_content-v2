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
import com.istarindia.apps.dao.*;
import com.istarindia.apps.services.CourseService;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * inputs in request Integer cmsession_id, Integer duration, String
		 * lessonType, String tags, String title, String[] learningObjectives
		 */

		printParams(request);
		IstarUser user = (IstarUser) request.getSession().getAttribute("user");
		String tags = "";
		String learningObjectives[];
		Set<LearningObjective> ite = new HashSet<LearningObjective>();
		if (request.getParameterMap().containsKey("lesson_id") && request.getParameterMap().containsKey("cmsession_id") && request.getParameterMap().containsKey("duration") && request.getParameterMap().containsKey("title"))

		{
			int lesson_id = Integer.parseInt(request.getParameter("lesson_id"));
			Task task = new Task();
			task.setItemId(lesson_id);
			task.setItemType("LESSON");
			task = (new TaskDAO()).findByExample(task).get(0);
			int cmsession_id = Integer.parseInt(request.getParameter("cmsession_id"));
			int duration = Integer.parseInt(request.getParameter("duration"));
			String lesson_theme = request.getParameter("lesson_theme");
			String title = request.getParameter("title");
			String lesson_subject = request.getParameter("lesson_subject");
			if (request.getParameterMap().containsKey("Tags")) {
				tags = request.getParameter("Tags");
			}
			if (request.getParameterMap().containsKey("learningObjectives")) {
				learningObjectives = (String[]) request.getParameterMap().get("learningObjectives");
				for (String element : learningObjectives) {
					System.err.println("Seected LO -->"+ element);
					ite.add(new LearningObjectiveDAO().findById(Integer.parseInt(element)));
				}
			}
			LessonService service = new LessonService();
			Lesson lesson = (Lesson) service.updateLesson(lesson_id, cmsession_id, duration, tags, title, "dtype", ite, lesson_theme, lesson_subject);
			(new CourseService()).clearLearningObjectiveWithLesson(lesson, ite);
			(new CourseService()).updateLearningObjectiveWithLesson(lesson, ite);
			request.setAttribute("message_success", "Lesson updated successfully!");
			request.setAttribute("lesson", lesson);
			request.setAttribute("task_id", task.getId());
			request.getRequestDispatcher("/lesson/edit_lesson.jsp").forward(request, response);
		} else {
			System.out.println("Something missing .... ");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
