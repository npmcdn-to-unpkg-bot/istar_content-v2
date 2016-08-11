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
		PresentaionDAO dao= new PresentaionDAO();
		Presentaion ppt = new Presentaion();
		
		int lesson_id = Integer.parseInt(request.getParameter("lesson_id").toString());
		Lesson lesson = (new LessonDAO()).findById(lesson_id);
		
		int task_id =  Integer.parseInt(request.getParameter("task_id").toString());
		Task task = lesson.getTask();
		
		ppt.setLesson(lesson);
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
		
		String comments = "New presentation(ID " + ppt.getId() + ") " + " for lesson: " + lesson.getTitle() + "(TaskID " + task_id + ") is created by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail();
		CMSRegistry.addTaskLogEntry(request, task.getStatus(), comments, task_id, "LESSON", lesson_id, "New presentation is created");
		
		request.setAttribute("lesson", lesson);
		request.setAttribute("task_id", lesson.getTask().getId());
		response.sendRedirect("/content/edit_lesson?task_id="+task.getId());
		//request.send getRequestDispatcher("/lesson/edit_lesson.jsp").forward(request, response);;

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
