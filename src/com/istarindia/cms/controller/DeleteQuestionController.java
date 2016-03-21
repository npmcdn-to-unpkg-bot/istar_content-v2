package com.istarindia.cms.controller;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.AssessmentQuestion;
import com.istarindia.apps.dao.AssessmentQuestionDAO;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.QuestionDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.QuestionService;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
		//http://127.0.0.1:8080/content/delete_slide?ppt_id=6&slide_id=25
		/*Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
		SlideDAO dao = new SlideDAO();
		Slide slide= dao.findById(Integer.parseInt(request.getParameter("slide_id")));
		*/
		Integer question_id = Integer.parseInt(request.getParameter("question_id"));
		Integer assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
		Assessment assessment1 = new AssessmentDAO().findById(assessment_id);
		Lesson lesson = new LessonDAO().findById(assessment1.getLesson().getId());
		TaskDAO taskdao = new TaskDAO();
		List<Task> taskItems = taskdao.findByItemId(lesson.getId());
		Integer task_id = new Integer(0);
		for (Task item : taskItems) {
			task_id = item.getId();
		}
 			// TODO Auto-generated method stub
			ArrayList<ArrayList<String>> table = new ArrayList<>();
			AssessmentQuestionDAO dao = new AssessmentQuestionDAO();
			Session session = dao.getSession();
			
			
			Assessment assessment = new AssessmentDAO().findById(assessment_id);
			Question question = new QuestionDAO().findById(question_id);
			AssessmentQuestion assessmentQuestion = dao.findByMapping(assessment, question);
			System.out.println("assessmentQuestion "  + assessmentQuestion.getId());
			try {
				Transaction tx = null;
				tx = session.beginTransaction();
				dao.delete(assessmentQuestion);
				tx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		request.setAttribute("task_id", task_id);
	//	request.setAttribute("lesson", lesson);
		response.sendRedirect("/content/edit_lesson?lesson_id=" + lesson.getId()+"&task_id="+task_id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
