package com.istarindia.cms.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.cmsutils.reports.IStarColumn;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printParams(request);

		if(request.getParameterMap().containsKey("question_id")){
			//response.getWriter().append("Served at: ").append(request.getContextPath());
			
			QuestionDAO questiondao = new QuestionDAO();
			Question question = questiondao.findById(Integer.parseInt(request.getParameter("question_id")));
			
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
			response.sendRedirect("/content/lesson/review_question.jsp?question_id="+question.getId()+"&lesson_id="+Integer.parseInt(request.getParameter("lesson_id")));

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
			markLessonAsReviewed(request);
			
			response.sendRedirect("/content/content_reviewer/dashboard.jsp");
			response.getWriter().append("Served at: ").append(request.getContextPath());
			
		} else {
			
		SlideDAO dao1 = new SlideDAO();
		Slide slide = new Slide();
		int slideID = 0;
		if(request.getParameter("slide_id").equalsIgnoreCase("null")) {
		
			String sql1 = "SELECT s.id FROM 	slide s WHERE 	s.presentation_id = "+request.getParameter("ppt_id") +"  ORDER BY 	s.order_id ASC";
			IstarUserDAO dao = new IstarUserDAO();
			Session session = dao.getSession();
			SQLQuery query = session.createSQLQuery(sql1);
			System.err.println(sql1);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			   DBUTILS util = new DBUTILS();
				List<HashMap<String, Object>> results = util.executeQuery(sql1);
				for (HashMap<String, Object> object : results) {
					for (String string : object.keySet()) {
						//System.out.println(object.get(string));
						slideID = Integer.parseInt(object.get(string).toString());
					}
			}
			slide = dao1.findById(slideID);
		} else {
			
			slideID = Integer.parseInt(request.getParameter("slide_id"));
			slide = dao1.findById(Integer.parseInt(request.getParameter("slide_id")));
		}
		
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
		log.setTitle("Review comment added by " + user.getEmail());
		log.setSessionID(request.getSession().getAttribute("jsession_id").toString());
		log.setItemType("SLIDE");
		log.setItem_id(slideID);
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
		
		try {
			if (request.getParameterMap().containsKey("from") && request.getParameter("from").equalsIgnoreCase("review_slide")) {
				response.sendRedirect("/content/fill_tempate_review.jsp?ppt_id="+slide.getPresentaion().getId()+"&slide_id="+slide.getId());
			} else if (request.getParameterMap().containsKey("from") && request.getParameter("from").equalsIgnoreCase("edit_slide")) {
				response.sendRedirect("/content/fill_tempate1.jsp?ppt_id="+slide.getPresentaion().getId()+"&slide_id="+slide.getId()+"&slide_type="+slide.getTemplate());
			} else {
				response.sendRedirect("/content/index.jsp");
			}
		} catch (Exception e) {
			response.sendRedirect("/content/review_task?task_id="+task.getId());
			System.err.println("oops");
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
		
	}
		
	}

	private void markLessonAsReviewed(HttpServletRequest request) {
		//System.err.println("i m in review lesson controller");;
		if(request.getParameter("review").equalsIgnoreCase("DIS_APPROVED"))
		{
			TaskDAO dao = new TaskDAO();
			Task task = new Task();
			task.setItemId(Integer.parseInt(request.getParameter("lesson_id")));
			task.setItemType("LESSON");
			task = dao.findByExample(task).get(0);
			
			
			TaskReviewerDAO trdao = new   TaskReviewerDAO();
			List<TaskReviewer> reviewers  = trdao.findByProperty("task", task);
			IstarUser u = ((IstarUser) request.getSession().getAttribute("user"));
			ContentReviewer cr = new ContentReviewerDAO().findById(u.getId());
			for(TaskReviewer r : reviewers)
			{
				if(r.getContentReviewer().getId().equals(u.getId()))
				{
					//System.err.println("-----"+task.getId());
					//System.err.println("taskreviewerid is ===="+r.getId());
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
						//System.err.println(e.getMessage());
						e.printStackTrace();
					} finally {
						session1.close();
					}
				}
			}

			boolean finally_disapproved=true;
			for(TaskReviewer rr :( List<TaskReviewer>)trdao.findByProperty("task", task))
			{
				if(!rr.getStatus().equalsIgnoreCase("DIS_APPROVED"))
				{
					finally_disapproved=false;
					break;
				}
			}

			if(finally_disapproved) {
				task.setStatus("DIS_APPROVED");
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
			
		} else	{
			TaskDAO dao = new TaskDAO();
			Task task = new Task();
			task.setItemId(Integer.parseInt(request.getParameter("lesson_id")));
			task.setItemType("LESSON");
			task = dao.findByExample(task).get(0);
			TaskReviewerDAO trdao = new   TaskReviewerDAO();
			//TaskReviewer tr = new TaskReviewer();
			List<TaskReviewer> reviewers  = trdao.findByProperty("task", task);
			IstarUser u = ((IstarUser) request.getSession().getAttribute("user"));
		//	tr.setContentReviewer((ContentReviewer) request.getSession().getAttribute("user"));
			//tr.setTask(task);
			//tr = trdao.findByExample(tr).get(0);
			for(TaskReviewer r : reviewers)
			{
				if(r.getContentReviewer().getId().equals(u.getId()))
				{
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
			

			boolean finally_approved=true;
			for(TaskReviewer r :( List<TaskReviewer>)trdao.findByProperty("task", task))
			{
				if(!r.getStatus().equalsIgnoreCase("APPROVED"))
				{
					finally_approved=false;
					break;
				}
			}

			if(finally_approved) {
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
