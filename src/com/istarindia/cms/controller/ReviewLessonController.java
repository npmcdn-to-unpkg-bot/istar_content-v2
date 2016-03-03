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

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.SlideDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.TaskLog;
import com.istarindia.apps.dao.TaskLogDAO;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class ReviewLessonController
 */
@WebServlet("/review_lesson")
public class ReviewLessonController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewLessonController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		printParams(request);

		// Add comments on lesson
		if (!request.getParameterMap().containsKey("is_edit")) {

			TaskDAO dao = new TaskDAO();
			Task task = new Task();
			task.setItemId(Integer.parseInt(request.getParameter("lesson_id")));
			task.setItemType("LESSON");
			task = dao.findByExample(task).get(0);

			TaskLogDAO lDAO = new TaskLogDAO();
			TaskLog log = new TaskLog();
			IstarUser user = (IstarUser) request.getSession().getAttribute("user");
			log.setActorId(user.getId());
			log.setChangedStatus("COMPLETED");
			log.setTaskId(task.getId());
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			log.setCreatedAt(currentTimestamp);
			log.setComments(request.getParameter("review_notes"));
			log.setItemType("LESSON");
			Session session = lDAO.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();

				lDAO.attachDirty(log);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			// content/content_reviewer/dashboard.jsp
			response.sendRedirect("/content/content_reviewer/dashboard.jsp");
			response.getWriter().append("Served at: ").append(request.getContextPath());
		} else {
			/// review_lesson?is_edit=true&slide_id=4&template=ONLY_TITLE&
			// ppt_id=9&review_notes=%3Cp%3Essssssssssssssssssss%3C%2Fp%3E
			
			SlideDAO dao1 = new SlideDAO();
			Slide slide = dao1.findById(Integer.parseInt(request.getParameter("slide_id")));
			
			TaskDAO dao = new TaskDAO();
			Task task = new Task();
			task.setItemId(slide.getPresentaion().getLesson().getId());
			task.setItemType("LESSON");
			task = dao.findByExample(task).get(0);

			TaskLogDAO lDAO = new TaskLogDAO();
			TaskLog log = new TaskLog();
			IstarUser user = (IstarUser) request.getSession().getAttribute("user");
			log.setActorId(user.getId());
			log.setChangedStatus("COMPLETED");
			log.setTaskId(task.getId());
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			log.setCreatedAt(currentTimestamp);
			log.setComments(request.getParameter("review_notes"));
			log.setItemType("SLIDE");
			Session session = lDAO.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();

				lDAO.attachDirty(log);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			// content/content_reviewer/dashboard.jsp
			response.sendRedirect("/content/content_reviewer/dashboard.jsp");
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
