package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.AssessmentQuestionService;

/**
 * Servlet implementation class DeleteQuestionController
 */
@WebServlet("/delete_question")
public class DeleteQuestionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuestionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer question_id = Integer.parseInt(request.getParameter("question_id"));
		Integer assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
		AssessmentDAO assessmentDAO = new AssessmentDAO();
		Assessment assessment = assessmentDAO.findById(assessment_id);
		AssessmentQuestionService service = new AssessmentQuestionService();
		
		service.deleteQuestionFromAssessment(assessment_id, question_id);

		response.sendRedirect("/content/edit_lesson?task_id=" + assessment.getLesson().getTask().getId());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
