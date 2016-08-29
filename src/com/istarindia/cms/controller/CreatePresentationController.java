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
import com.istarindia.apps.dao.DBUTILS;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.CMSRegistry;

/**
 * Servlet implementation class CreatePresentationController
 */
@WebServlet("/create_ppt")
public class CreatePresentationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePresentationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("task_id")) {
			PresentaionDAO presentationDAO= new PresentaionDAO();
			int task_id = Integer.parseInt(request.getParameter("task_id").toString());
			Task task = (new TaskDAO()).findById(task_id);
			Lesson lesson = task.getLesson();
			int lesson_id = lesson.getId();
			
			if(lesson.getAssessment()==null && lesson.getPresentaion()==null) {
				Presentaion presentaion = new Presentaion();
				presentaion.setLesson(lesson);

				Session session = presentationDAO.getSession();
				Transaction tx = null;
				try {
					tx = session.beginTransaction();
					presentationDAO.save(presentaion);
					tx.commit();
				} catch (HibernateException e) {
					if (tx != null)
						tx.rollback();
					e.printStackTrace();
				} finally {
					session.close();
				}
				
				String comments = "New presentation(ID " + presentaion.getId() + ") " + " for lesson: " + lesson.getTitle() + "(TaskID " + task_id + ") is created by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail();
				CMSRegistry.addTaskLogEntry(request, task.getStatus(), comments, task_id, "LESSON", lesson_id, "New Presentation is created");
				
				request.setAttribute("message_success", "New presentation has been created.");
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
