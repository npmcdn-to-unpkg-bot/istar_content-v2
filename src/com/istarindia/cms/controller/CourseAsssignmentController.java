package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.cmsutils.ErrorMessages;
import com.istarindia.apps.dao.ContentReviewer;
import com.istarindia.apps.dao.ContentReviewerDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.TaskReviewer;
import com.istarindia.apps.dao.TaskReviewerDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.apps.services.task.CreateLessonTaskManager;

import javassist.compiler.SyntaxError;

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
			System.err.println("selected_items--"+ request.getParameter("selected_items"));
			System.err.println("assign->"+ assign);
			if(assign.startsWith("lesson")) {
				String[] review_user = request.getParameterValues("review_user");
				ArrayList<Integer> new_reviewerIds = new ArrayList<>(); 
				
				if(review_user!=null) {
					for(String r : review_user ) {
						try {
							new_reviewerIds.add(Integer.parseInt(r));
						} catch (Exception e) {
							//System.err.println(review_user);
							System.err.println("73");
						}
					}
				} else {
					System.err.println("7666");
					// Remove all previous reviewers. No need to add new reviewers
				}
				
				try {
					
					int lesson_id = Integer.parseInt(assign.replace("lesson_", ""));
					LessonDAO dao = new  LessonDAO();
					Task task = dao.findById(lesson_id).getTask();
					System.err.println("Old Actor ID ---"+task.getActorId());
					task.setActorId(Integer.parseInt(request.getParameter("assign_user")));
					System.err.println("91111->"+ task.getId() );
					System.err.println("New Actor ID ---"+task.getActorId());
					
					{
						System.out.println( "Number os New Reqviers "+ request.getParameterMap().get("review_user").length );

						Set<TaskReviewer> taskReviewers = new HashSet<>();
						
						for (String reviewerID : request.getParameterMap().get("review_user")) {
							TaskReviewer t = new TaskReviewer();
							t.setContentReviewer((ContentReviewer)(new IstarUserDAO()).findById(Integer.parseInt(reviewerID)));
							t.setStatus("REVIEWER_ASSIGNED");
							t.setTask(task);
							taskReviewers.add(t);
						}
						
						task.setTaskReviewers(taskReviewers);
					}
					//cleanTaskForReviewers();
					assignActor(task, request.getParameter("assign_user"), request );
					assignReviewer(task, new_reviewerIds, request );
					request.setAttribute("message_success", ErrorMessages.ALL_OK);
				} catch (NullPointerException e) {
					System.err.println("9444");
					request.setAttribute("message_failure", ErrorMessages.MISSING_REVIEWER);
				}
			}
		}
		request.getRequestDispatcher("/content_admin/course_structure.jsp").forward(request, response);
	}

	private void assignReviewer(Task task, ArrayList<Integer> new_reviewerIds, HttpServletRequest request) {
		System.out.println( "Number os New Reqviers "+ request.getParameterMap().get("review_user").length );
		Set<TaskReviewer> taskReviewers = new HashSet<>();
		
		for (String reviewerID : request.getParameterMap().get("review_user")) {
			TaskReviewer t = new TaskReviewer();
			t.setContentReviewer((ContentReviewer)(new IstarUserDAO()).findById(Integer.parseInt(reviewerID)));
			t.setStatus("REVIEWER_ASSIGNED");
			t.setTask(task);
			
			taskReviewers.add(t);
		}
		
		task.setTaskReviewers(taskReviewers);
		
		List<TaskReviewer> existing_reviewers = new TaskReviewerDAO().findByProperty("task", task);
		ArrayList<Integer> existing_reviewerIds = new ArrayList<>();
		String comments = new String();
		StringBuffer removedReviewersEmailList = new StringBuffer();
		StringBuffer addedReviewersEmailList = new StringBuffer();
		System.err.println(" existing revieeers ->" + existing_reviewers.size());
		for(TaskReviewer tr : existing_reviewers) {
			existing_reviewerIds.add(tr.getContentReviewer().getId());
		}
		
		TaskReviewerDAO taskReviewerDAO = new TaskReviewerDAO();

		for(Integer new_reviewerId : new_reviewerIds) {
			if(!existing_reviewerIds.contains(new_reviewerId)) {
				TaskReviewer taskReviewer = new TaskReviewer();
				taskReviewer.setContentReviewer(new ContentReviewerDAO().findById(new_reviewerId));
				taskReviewer.setStatus("REVIEWER_ASSIGNED");
				taskReviewer.setTask(task);
				/*Session session = taskReviewerDAO.getSession();
				Transaction tx = null;
				try {
					tx = session.beginTransaction();
					taskReviewerDAO.save(taskReviewer);
					tx.commit();
				} catch (Exception e) {
					if (tx != null) {
						tx.rollback();
					}
					e.printStackTrace();
				} finally {
					session.close();
				}*/
			}
		}

		for(TaskReviewer existing_reviewer : existing_reviewers) {
			if(!new_reviewerIds.contains(existing_reviewer.getId())) {
				/*Session session = taskReviewerDAO.getSession();
				Transaction tx = null;
				try {
					tx = session.beginTransaction();
					taskReviewerDAO.delete(existing_reviewer);
					tx.commit();
				} catch (Exception e) {
					if (tx != null) {
						tx.rollback();
					}
					e.printStackTrace();
				} finally {
					session.close();
				}*/
				
			}
		}
		
		/*TaskDAO dao = new TaskDAO();
		Session session = dao.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.attachDirty(task);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}*/
		if(!removedReviewersEmailList.toString().isEmpty()) {
			comments = removedReviewersEmailList.toString() + " have been removed from reviewer list by " +  ((IstarUser)request.getSession().getAttribute("user")).getEmail() + " ; ";
		}
		
		if (!addedReviewersEmailList.toString().isEmpty()) {
			comments = comments  + addedReviewersEmailList.toString() + " have been added to reviewer list by " +  ((IstarUser)request.getSession().getAttribute("user")).getEmail() + " ; ";
		}
		
		if(!comments.isEmpty()) {
			CMSRegistry.addTaskLogEntry(request, "CONTENT_ASSIGNED", comments, task.getId(), "LESSON", task.getLesson().getId(), "Reviewers updated for the lesson");
		}
	}

	private void assignActor(Task task, String content_id, HttpServletRequest request) {
		TaskDAO dao = new  TaskDAO();
		
		task.setActorId(Integer.parseInt(content_id));
		task.setStatus(StatusTypes.CONTENT_ASSIGNED);
		Session session1 = dao.getSession();
		Transaction tx1 = null;
		try {
			tx1 = session1.beginTransaction();
			
			dao.attachDirty(task);
			tx1.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		} finally {
			session1.close();
		}

		String comments = "The Lesson was assigned to "+(new IstarUserDAO().findById(Integer.parseInt(content_id))).getEmail()+" by "+ ((IstarUser)request.getSession().getAttribute("user")).getEmail();
		CMSRegistry.addTaskLogEntry(request, "CONTENT_ASSIGNED", comments, task.getId(), "LESSON", task.getLesson().getId(), "Actor is assigned for the lesson");
		
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