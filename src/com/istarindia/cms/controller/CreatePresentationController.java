package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.services.task.CreateLessonTaskManager;

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
		PresentaionDAO dao= new PresentaionDAO();
		Presentaion ppt = new Presentaion();
		ppt.setLesson((new LessonDAO()).findById(Integer.parseInt(request.getParameter("lesson_id"))));
		
		Session session = dao.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.save(ppt);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		Lesson lesson = (new LessonDAO()).findById(Integer.parseInt(request.getParameter("lesson_id")));
		CreateLessonTaskManager.pushTaskNotification(lesson, (IstarUser) request.getSession().getAttribute("user"), "A presentation for the lesson was created.");
		
		Integer task_id = Integer.parseInt(request.getParameter("task_id"));
		request.setAttribute("lesson", lesson);
		request.setAttribute("task_id", task_id);
		request.getRequestDispatcher("/edit_lesson").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
