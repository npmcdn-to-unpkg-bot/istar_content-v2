package com.istarindia.cms.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.ContentReviewer;
import com.istarindia.apps.dao.ContentReviewerDAO;
import com.istarindia.apps.dao.DBUTILS;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.QuestionDAO;
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

		if (request.getParameterMap().containsKey("question_id")) {
			// response.getWriter().append("Served at:
			// ").append(request.getContextPath());

			QuestionDAO questiondao = new QuestionDAO();
			Question question = questiondao.findById(Integer.parseInt(request.getParameter("question_id")));
			LessonDAO lessonDAO = new  LessonDAO();
			int lessonId = Integer.parseInt(request.getParameter("lesson_id"));
			Lesson lesson = lessonDAO.findById(lessonId);
			Task task = lesson.getTask();

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
			log.setTitle("Review comment added by " + user.getEmail());
			log.setSessionID(request.getSession().getAttribute("jsession_id").toString());
			log.setItemType("QUESTION");
			log.setItem_id(Integer.parseInt(request.getParameter("question_id")));
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
			response.sendRedirect("/content/lesson/review_question.jsp?question_id=" + question.getId() + "&lesson_id="
					+ Integer.parseInt(request.getParameter("lesson_id")));

		} else if (!request.getParameterMap().containsKey("is_edit")) {

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
			log.setTitle("Review comment added by " + user.getEmail());
			log.setSessionID(request.getSession().getAttribute("jsession_id").toString());
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
			try {
				markLessonAsReviewed(request);
			} catch (Exception e) {
				System.err.println("Probably no reviewers were alloted");
			}

			response.sendRedirect("/content/content_reviewer/dashboard.jsp");

		} else {

			SlideDAO dao1 = new SlideDAO();
			Slide slide = new Slide();
			int slideID = 0;
			if (request.getParameter("slide_id").equalsIgnoreCase("null")) {
				String sql1 = "SELECT s.id FROM 	slide s WHERE 	s.presentation_id = " + request.getParameter("ppt_id") + "  ORDER BY 	s.order_id ASC";
				DBUTILS util = new DBUTILS();
				List<HashMap<String, Object>> results = util.executeQuery(sql1);
				for (HashMap<String, Object> object : results) {
					for (String string : object.keySet()) {
						slideID = Integer.parseInt(object.get(string).toString());
					}
				}
				slide = dao1.findById(slideID);
			} else {
				slideID = Integer.parseInt(request.getParameter("slide_id"));
				slide = dao1.findById(slideID);
			}

			TaskDAO dao = new TaskDAO();
			Task task = dao.findById(slide.getPresentaion().getLesson().getTask().getId());

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
			log.setTitle("Review comment added by " + user.getEmail());
			log.setSessionID(request.getSession().getAttribute("jsession_id").toString());
			log.setItemType("SLIDE");
			log.setItem_id(slideID);
			Session session = lDAO.getSession();
			Transaction tx = null;

			try {
				tx = session.beginTransaction();
				lDAO.save(log);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}

			try {
				if (request.getParameterMap().containsKey("from") && request.getParameter("from").equalsIgnoreCase("review_slide")) {
					response.sendRedirect("/content/review_slide.jsp?ppt_id=" + slide.getPresentaion().getId()
							+ "&slide_id=" + slide.getId());
				} else if (request.getParameterMap().containsKey("from") && request.getParameter("from").equalsIgnoreCase("edit_slide")) {
					response.sendRedirect("/content/fill_template.jsp?ppt_id=" + slide.getPresentaion().getId()
							+ "&slide_id=" + slide.getId() + "&slide_type=" + slide.getTemplate());
				} else {
					response.sendRedirect("/content/index.jsp");
				}
			} catch (Exception e) {
				response.sendRedirect("/content/review_task?task_id=" + task.getId());
				response.getWriter().append("Served at: ").append(request.getContextPath());
			}

		}

	}

	private void markLessonAsReviewed(HttpServletRequest request) {

		int lesson_id = Integer.parseInt(request.getParameter("lesson_id"));
		LessonDAO lessonDAO = new LessonDAO();
		Lesson lesson = lessonDAO.findById(lesson_id);

		TaskDAO taskDAO = new TaskDAO();
		Task task = lesson.getTask();

		TaskReviewerDAO trdao = new TaskReviewerDAO();

		List<TaskReviewer> reviewers = trdao.findByProperty("task", task);
		IstarUser u = ((IstarUser) request.getSession().getAttribute("user"));
		ContentReviewer cr = new ContentReviewerDAO().findById(u.getId());
		
		boolean finally_disapproved = true;
		boolean finally_approved = true;
		
		if (request.getParameter("review").equalsIgnoreCase("DIS_APPROVED")) {
			for (TaskReviewer r : reviewers) {
				if (r.getContentReviewer().getId().equals(u.getId())) {
					r.setStatus("DIS_APPROVED");
					Session session1 = trdao.getSession();
					Transaction tx1 = null;
					try {
						tx1 = session1.beginTransaction();
						trdao.attachDirty(r);
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

			for (TaskReviewer rr : (List<TaskReviewer>) trdao.findByProperty("task", task)) {
				if (!rr.getStatus().equalsIgnoreCase("DIS_APPROVED")) {
					finally_disapproved = false;
					break;
				}
			}

			if (finally_disapproved) {
				task.setStatus("DIS_APPROVED");
				Session session1 = taskDAO.getSession();
				Transaction tx1 = null;
				try {
					tx1 = session1.beginTransaction();
					taskDAO.attachDirty(task);
					tx1.commit();
				} catch (HibernateException e) {
					if (tx1 != null)
						tx1.rollback();
					e.printStackTrace();
				} finally {
					session1.close();
				}
			}

		} else {

			for (TaskReviewer r : reviewers) {
				if (r.getContentReviewer().getId().equals(u.getId())) {
					r.setStatus("APPROVED");
					Session session = trdao.getSession();
					Transaction tx = null;
					try {
						tx = session.beginTransaction();
						trdao.attachDirty(r);
						tx.commit();
					} catch (HibernateException e) {
						if (tx != null)
							tx.rollback();
						e.printStackTrace();
					} finally {
						session.close();
					}
				}
			}

			for (TaskReviewer r : (List<TaskReviewer>) trdao.findByProperty("task", task)) {
				if (!r.getStatus().equalsIgnoreCase("APPROVED")) {
					finally_approved = false;
					break;
				}
			}

			if (finally_approved) {
				task.setStatus("APPROVED");
				Session session1 = taskDAO.getSession();
				Transaction tx1 = null;
				try {
					tx1 = session1.beginTransaction();
					taskDAO.attachDirty(task);
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
		
		boolean is_review_finished = true ;
		
		for(TaskReviewer tr : reviewers ) {
			if(tr.getStatus().equalsIgnoreCase("REVIEWER_ASSIGNED") && u.getId()!=tr.getContentReviewer().getId()) {
				is_review_finished = false;
			}
		}
		
		if(is_review_finished && !finally_approved) {
			task = taskDAO.findById(task.getId());
			task.setStatus("DIS_APPROVED");
			Session session11 = taskDAO.getSession();
			Transaction tx11 = null;
			try {
				tx11 = session11.beginTransaction();
				taskDAO.attachDirty(task);
				tx11.commit();
			} catch (HibernateException e) {
				if (tx11 != null)
					tx11.rollback();
				e.printStackTrace();
			} finally {
				session11.close();
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
