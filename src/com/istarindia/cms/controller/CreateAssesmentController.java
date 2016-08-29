package com.istarindia.cms.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.cmsutils.ErrorMessages;
import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.DBUTILS;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.task.CreateLessonTaskManager;

/**
 * Servlet implementation class CreateAssesment
 */
@WebServlet("/create_assesment")
public class CreateAssesmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAssesmentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("task_id")) {
			AssessmentDAO assessmentDAO= new AssessmentDAO();
			int task_id = Integer.parseInt(request.getParameter("task_id").toString());
			Task task = (new TaskDAO()).findById(task_id);
			Lesson lesson = task.getLesson();
			int lesson_id = lesson.getId();
			
			if(lesson.getAssessment()==null && lesson.getPresentaion()==null) {
				Assessment assessment = new Assessment();
				assessment.setLesson(lesson);
				assessment.setAssessmentdurationminutes(lesson.getDuration());
				assessment.setAssessmenttitle(lesson.getTitle());
				
				Calendar calendar = Calendar.getInstance();
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
				assessment.setCreatedAt(currentTimestamp);
				
				Session session = assessmentDAO.getSession();
				Transaction tx = null;
				try {
					tx = session.beginTransaction();
					assessmentDAO.save(assessment);
					tx.commit();
				} catch (HibernateException e) {
					if (tx != null)
						tx.rollback();
					e.printStackTrace();
				} finally {
					session.close();
				}
				
				String sql = "update lesson set dtype='ASSESSMENT' where id = " + lesson_id;
				DBUTILS util = new DBUTILS();
				util.executeUpdate(sql);
				
				String comments = "New assessment(ID " + assessment.getId() + ") " + " for lesson: " + lesson.getTitle() + "(TaskID " + task_id + ") is created by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail();
				CMSRegistry.addTaskLogEntry(request, task.getStatus(), comments, task_id, "LESSON", lesson_id, "New Assessment is created");
				
				request.setAttribute("message_success", "New assessment has been created. Please provide necessary details for the assessment before proceeding to add questions");
			}
			request.getRequestDispatcher("/edit_lesson?task_id="+task_id).forward(request, response);
			
		} else {
			request.setAttribute("message_failure", "Something went wrong. Please try again!");
			request.getRequestDispatcher("index.jsp").forward(request, response);
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
