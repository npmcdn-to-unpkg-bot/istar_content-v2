package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.*;
import com.istarindia.apps.services.LessonService;
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
		String[] learningObjectives = null;
		StringBuffer lo_ids = new StringBuffer();
		
		Set<LearningObjective> ite = new HashSet<LearningObjective>();LessonService service = new LessonService();
			Lesson lesson = new Lesson();
			if (request.getParameterMap().containsKey("learningObjectives")) {
				learningObjectives = (String[]) request.getParameterMap().get("learningObjectives");
				for (int i = 0; learningObjectives != null && i < learningObjectives.length; i++) {
					lo_ids.append(learningObjectives[i]);
					if (i < learningObjectives.length - 1) {
						lo_ids.append(',');
					}
				}
			}
			
			int lesson_id = Integer.parseInt(request.getParameter("lesson_id"));
			Task task = new Task();
			task.setItemId(lesson_id);
			task.setItemType("LESSON");
			task = (new TaskDAO()).findByExample(task).get(0);
			
			if(request.getParameterMap().containsKey("only_learning_objectives")) {
				lesson = (Lesson) service.updateLesson(lesson_id, lo_ids.toString());
			} else {
				int duration = Integer.parseInt(request.getParameter("duration"));
				String lesson_theme = request.getParameter("lesson_theme");
				String lesson_desktop_theme = request.getParameter("lesson_desktop_theme");
				String title = request.getParameter("title");
				String lesson_subject = request.getParameter("lesson_subject");
				
				if (request.getParameterMap().containsKey("Tags")) {
					tags = request.getParameter("Tags");
				}
				service.pushLessonUpdateNotification(task.getId(), lesson_id, user, lesson_theme, lesson_desktop_theme, title);
				lesson = (Lesson) service.updateLesson(lesson_id, title, duration, tags, lesson_theme, lesson_subject, lo_ids.toString(), lesson_desktop_theme);
			}
			
			request.setAttribute("message_success", "Lesson updated successfully!");
			request.setAttribute("lesson", lesson);
			request.setAttribute("task_id", task.getId());
			request.getRequestDispatcher("/lesson/edit_lesson.jsp").forward(request, response);
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
