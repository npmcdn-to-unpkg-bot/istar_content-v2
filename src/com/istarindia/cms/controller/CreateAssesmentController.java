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

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
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
		AssessmentDAO assessmentDAO= new AssessmentDAO();
		Assessment assessment = new Assessment();
		Integer lesson_id = Integer.parseInt(request.getParameter("lesson_id"));
		assessment.setLesson((new LessonDAO()).findById(lesson_id));
		
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
		
		Lesson lesson = (new LessonDAO()).findById(Integer.parseInt(request.getParameter("lesson_id")));
		
			lesson.setDtype("ASSESSMENT");
			LessonDAO ldao = new LessonDAO();
 			Session session1 = ldao.getSession();
			Transaction tx1 = null;
			try {
				tx1 = session1.beginTransaction();
				ldao.attachDirty(lesson);
				tx1.commit();
			} catch (HibernateException e) {
				if (tx1 != null)
					tx1.rollback();
				e.printStackTrace();
			} finally {
				session1.close();
			}
		
		CreateLessonTaskManager.pushTaskNotification(lesson, (IstarUser) request.getSession().getAttribute("user"), "An Assessment for the lesson was created.");
		
		request.setAttribute("lesson", lesson);
		request.setAttribute("task_id", Integer.parseInt(request.getParameter("task_id")));
		request.getRequestDispatcher("lesson/edit_lesson.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
