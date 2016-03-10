package com.istarindia.cms.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.ContentReviewer;
import com.istarindia.apps.dao.ContentReviewerDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.SlideDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.TaskLog;
import com.istarindia.apps.dao.TaskLogDAO;
import com.istarindia.apps.dao.TaskReviewer;
import com.istarindia.apps.dao.TaskReviewerDAO;
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
			log.setItem_id(Integer.parseInt(request.getParameter("lesson_id")));
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
			 markLessonAsReviewed(request);
			
			response.sendRedirect("/content/content_reviewer/dashboard.jsp");
			response.getWriter().append("Served at: ").append(request.getContextPath());
		} else {
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
			log.setItem_id(Integer.parseInt(request.getParameter("slide_id")));
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

	private void markLessonAsReviewed(HttpServletRequest request) {
		System.err.println("i m hgere");;
		if(request.getParameter("review").equalsIgnoreCase("DIS_APPROVED"))
		{
			TaskDAO dao = new TaskDAO();
			Task task = new Task();
			task.setItemId(Integer.parseInt(request.getParameter("lesson_id")));
			task.setItemType("LESSON");
			task = dao.findByExample(task).get(0);
			
			
			TaskReviewerDAO trdao = new   TaskReviewerDAO();
			TaskReviewer tr = new TaskReviewer();
			IstarUser u = ((IstarUser) request.getSession().getAttribute("user"));
			ContentReviewer cr = new ContentReviewerDAO().findById(u.getId());
			tr.setContentReviewer(cr);
			tr.setTask(task);
			System.err.println("-----"+task.getId());
			tr = trdao.findByExample(tr).get(0);
			tr.setStatus("DIS_APPROVED");
			Session session1 = trdao.getSession();
			Transaction tx1 = null;
			try {
				tx1 = session1.beginTransaction();

				trdao.attachDirty(tr);
				tx1.commit();
			} catch (HibernateException e) {
				if (tx1 != null)
					tx1.rollback();
				e.printStackTrace();
			} finally {
				session1.close();
			}
			
			
			task.setStatus("DIS_APPROVED");
			Session session = dao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();

				dao.attachDirty(task);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			
		}
		else
		{
			TaskDAO dao = new TaskDAO();
			Task task = new Task();
			task.setItemId(Integer.parseInt(request.getParameter("lesson_id")));
			task.setItemType("LESSON");
			task = dao.findByExample(task).get(0);
			TaskReviewerDAO trdao = new   TaskReviewerDAO();
			TaskReviewer tr = new TaskReviewer();
			tr.setContentReviewer((ContentReviewer) request.getSession().getAttribute("user"));
			tr.setTask(task);
			tr = trdao.findByExample(tr).get(0);
			tr.setStatus("APPROVED");
			Session session = trdao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();

				trdao.attachDirty(tr);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			
			boolean finally_approved=true;
			for(TaskReviewer r :( List<TaskReviewer>)trdao.findByProperty("task", task))
			{
				if(!r.getStatus().equalsIgnoreCase("APPROVED"))
				{
					finally_approved=false;
					break;
				}
			}
			
			if(finally_approved)
			{
				task.setStatus("APPROVED");
				Session session1 = dao.getSession();
				Transaction tx1 = null;
				try {
					tx1 = session1.beginTransaction();

					dao.attachDirty(task);
					tx1.commit();
				} catch (HibernateException e) {
					if (tx1 != null)
						tx1.rollback();
					e.printStackTrace();
				} finally {
					session1.close();
				}
			}
			
				
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
