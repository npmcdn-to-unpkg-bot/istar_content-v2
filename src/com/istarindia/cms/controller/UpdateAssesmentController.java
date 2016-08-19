package com.istarindia.cms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.AssessmentService;

/**
 * Servlet implementation class CreateAssesment
 */
@WebServlet("/update_assessment")
public class UpdateAssesmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAssesmentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String assessment_type = "";
		int assessment_id, number_of_questions, duration;
		
		if (request.getParameterMap().containsKey("update_assessment")) {

			assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
			String title = request.getParameter("title");
			number_of_questions = Integer.parseInt(request.getParameter("number_of_questions"));
			assessment_type = request.getParameter("assessment_type");
			duration = Integer.parseInt(request.getParameter("duration"));

			AssessmentService service = new AssessmentService();
			Assessment assessment = (Assessment) service.updateAssessment(assessment_id, title, duration,
					assessment_type, number_of_questions);

			request.setAttribute("message_success", "Assessment updated successfully!");
			//response.sendRedirect("/content/lesson/edit_assessment.jsp?assessment_id=" + assessment_id);
			response.sendRedirect("/content/edit_lesson?task_id="+assessment.getLesson().getTask().getId());

		} else if (request.getParameterMap().containsKey("assessment_id")
				&& request.getParameterMap().containsKey("assessment_type")
				&& request.getParameterMap().containsKey("number_of_questions")) {
			assessment_id = Integer.parseInt(request.getParameter("assessment_id"));

			number_of_questions = Integer.parseInt(request.getParameter("number_of_questions"));
			assessment_type = request.getParameter("assessment_type");

			AssessmentService service = new AssessmentService();
			Assessment assessment = (Assessment) service.updateAssessment(assessment_id, assessment_type,
					number_of_questions);
			request.setAttribute("message_success", "Lesson updated successfully!");
			request.setAttribute("assessment", assessment);
			Lesson lesson = assessment.getLesson();
			// Lesson lesson = (new
			// LessonDAO()).findById(Integer.parseInt(request.getParameter("lesson_id")));
			request.setAttribute("lesson", lesson);
			TaskDAO dao = new TaskDAO();
			Task t = lesson.getTask();

			request.setAttribute("task_id", t.getId());
			response.sendRedirect("/content/edit_lesson?task_id="+t.getId());
			//request.getRequestDispatcher("/lesson/edit_lesson.jsp").forward(request, response);

		} else {
			System.out.println("Something missing .... ");
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
