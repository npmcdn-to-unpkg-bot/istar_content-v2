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

import com.istarindia.apps.dao.ContentReviewer;
import com.istarindia.apps.dao.ContentReviewerDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
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

		// Add comments on question
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
					}
				
				else if (!request.getParameterMap().containsKey("is_edit")) {

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
				}
				
				else {
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
			Slide nextSlide;
			try {
				nextSlide = new Slide();
				session = dao.getSession();
				
				ArrayList<Slide> items = new ArrayList<>();
				
				String sql1 = "select * from slide where presentation_id=" + slide.getPresentaion().getId() + " order by id";
				SQLQuery query = session.createSQLQuery(sql1);
				query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				List<HashMap<String, Object>> results = query.list();
				for (HashMap<String, Object> slide1 : results) {
					Slide slide2 = new Slide();
					slide2.setSlideText(slide1.get("slide_text").toString());
					slide2.setTemplate(slide1.get("template").toString());
					slide2.setId(Integer.parseInt(slide1.get("id").toString()));
					items.add(slide2);
				}
				
				for (int i=0;i< items.size();i++) {
					//System.err.println("slide.getId() --> "+slide.getId());
					//System.err.println("slide.getPresentaion().getSlides().get(i).getId() --> "+items.get(i).getId());
					if(slide.getId() == items.get(i).getId()) {
						nextSlide = items.get(i+1);
					}
				}
				response.sendRedirect("/content/fill_tempate_review.jsp?ppt_id="+slide.getPresentaion().getId()+"&slide_id="+nextSlide.getId()+"&slide_type="+nextSlide.getTemplate());
				response.getWriter().append("Served at: ").append(request.getContextPath());
			} catch (Exception e) {
				response.sendRedirect("/content/review_task?task_id="+task.getId());
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
