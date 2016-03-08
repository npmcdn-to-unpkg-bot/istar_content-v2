package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.cmsutils.ErrorMessages;
import com.istarindia.apps.dao.ContentReviewerDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.TaskReviewer;
import com.istarindia.apps.dao.TaskReviewerDAO;
import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.apps.services.task.CreateLessonTaskManager;

/**
 * Servlet implementation class CourseAsssignmentController
 */
@WebServlet("/course/assignment")
public class CourseAsssignmentController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CourseAsssignmentController() {
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
		/*
		 * key-> selected_items : value ->lesson_36,lesson_35 key-> assign_user
		 * : value ->6 key-> review_user : value ->1
		 */
		for (String assign : request.getParameter("selected_items").split(",")) {
			if(assign.startsWith("lesson")) {
				String[] reviewer = request.getParameterValues("review_user");
				try {
					assignLesson(assign,request.getParameter("assign_user"),reviewer, request );
				} catch (NullPointerException e) {
					request.setAttribute("message_failure", ErrorMessages.MISSING_REVIEWER);

					//e.printStackTrace();
				}
			}
		}
		request.getRequestDispatcher("/content_admin/course_structure.jsp").forward(request, response);
	}

	private void assignLesson(String assign, String content_id, String[] reviewer, HttpServletRequest request) {
		String lessonid= assign.replace("lesson_", "");
		
		System.out.println(">>>"+lessonid);
		TaskDAO dao = new  TaskDAO();
		Task task  = dao.findByItemId(Integer.parseInt(lessonid)).get(0);
		task.setActorId(Integer.parseInt(content_id));
		task.setStatus(StatusTypes.CONTENT_ASSIGNED);
		Session session1 = dao.getSession();
		Transaction tx1 = null;
		try {
			tx1 = session1.beginTransaction();
			
			dao.save(task);
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		} finally {
			session1.close();
		}
		CreateLessonTaskManager.pushTaskNotification(new LessonDAO().findById(Integer.parseInt(lessonid)), (IstarUser)request.getSession().getAttribute("user"), "The Lesson was assigned to "+(new IstarUserDAO().findById(Integer.parseInt(content_id))).getEmail()+" by "+ ((IstarUser)request.getSession().getAttribute("user")).getEmail());
		TaskReviewerDAO dao2 = new TaskReviewerDAO();
		List<TaskReviewer> rev = dao2.findByProperty("task", task);
		List<Integer> already_added = new ArrayList<Integer>();
		for(TaskReviewer r : rev)
		{
			already_added.add(r.getContentReviewer().getId());
		}
		
			for(String reviewer_id: reviewer )
			{
				System.out.println("review is "+reviewer_id);
				if(!already_added.contains(Integer.parseInt(reviewer_id)))
				{
					System.out.println("inside review is "+reviewer_id);
					TaskReviewer review = new TaskReviewer();
					review.setContentReviewer(new ContentReviewerDAO().findById(Integer.parseInt(reviewer_id)));
					review.setStatus(StatusTypes.REVIEWER_ASSIGNED);
					review.setTask(task);
					Session session2 = dao2.getSession();
					Transaction tx2 = null;
					try {
						tx2 = session2.beginTransaction();
						
						dao2.save(review);
						tx2.commit();
					} catch (HibernateException e) {
						if (tx2 != null)
							tx2.rollback();
						e.printStackTrace();
					} finally {
						session2.close();
					}
					
					CreateLessonTaskManager.pushTaskNotification(new LessonDAO().findById(Integer.parseInt(lessonid)), (IstarUser)request.getSession().getAttribute("user"), "The Lesson was assigned reviewer as  "+(new IstarUserDAO().findById(Integer.parseInt(reviewer_id))).getEmail()+" by "+ ((IstarUser)request.getSession().getAttribute("user")).getEmail());

				} else {
					CreateLessonTaskManager.pushTaskNotification(new LessonDAO().findById(Integer.parseInt(lessonid)), (IstarUser)request.getSession().getAttribute("user"), "The Lesson was assigned reviewer as  "+(new IstarUserDAO().findById(Integer.parseInt(reviewer_id))).getEmail()+" by "+ ((IstarUser)request.getSession().getAttribute("user")).getEmail());

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