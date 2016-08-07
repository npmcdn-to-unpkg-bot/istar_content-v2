package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
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
			if(assign.startsWith("lesson")) {
				String[] review_user = request.getParameterValues("review_user");
				ArrayList<Integer> new_reviewerIds = new ArrayList<>(); 
				
				if(review_user!=null) {
					for(String r : review_user ) {
						try {
							new_reviewerIds.add(Integer.parseInt(r));
						} catch (Exception e) {
							//System.err.println(review_user);
						}
					}
				} else {
					// Remove all previous reviewers. No need to add new reviewers
				}
				
				try {
					int lesson_id = Integer.parseInt(assign.replace("lesson_", ""));
					TaskDAO dao = new  TaskDAO();
					
					Task example = new Task();
					example.setItemId(lesson_id);
					example.setItemType("LESSON");
					Task task = dao.findByExample(example).get(0);
					
					assignActor(task, request.getParameter("assign_user"), request );
					assignReviewer(task, new_reviewerIds, request );
					request.setAttribute("message_success", ErrorMessages.ALL_OK);
				} catch (NullPointerException e) {
					request.setAttribute("message_failure", ErrorMessages.MISSING_REVIEWER);
				}
			}
		}
		request.getRequestDispatcher("/content_admin/course_structure.jsp").forward(request, response);
	}

	private void assignReviewer(Task task, ArrayList<Integer> new_reviewerIds, HttpServletRequest request) {
		StringBuffer sql = new StringBuffer();
		List<TaskReviewer> existing_reviewers = new TaskReviewerDAO().findByProperty("task", task);
		ArrayList<Integer> existing_reviewerIds = new ArrayList<>();
		String comments = new String();
		StringBuffer removedReviewersEmailList = new StringBuffer();
		StringBuffer addedReviewersEmailList = new StringBuffer();
		
		for(TaskReviewer tr : existing_reviewers) {
			existing_reviewerIds.add(tr.getContentReviewer().getId());
		}
		
		for(Integer existing_reviewerId : existing_reviewerIds) {
			if(!new_reviewerIds.contains(existing_reviewerId)) {
				sql.append(" ; delete from task_reviewer where task_id = " + task.getId() + " and reviewer_id = " + existing_reviewerId);
				removedReviewersEmailList.append(new IstarUserDAO().findById(existing_reviewerId).getEmail() + " , ");
			}
		}
		
		for(Integer new_reviewerId : new_reviewerIds) {
			if(!existing_reviewerIds.contains(new_reviewerId)) {
				sql.append(" ; insert into  task_reviewer (id, task_id, reviewer_id, status) values ( (select max(id)+1 from task_reviewer), " + task.getId() + " , " + new_reviewerId + " , 'REVIEWER_ASSIGNED')");
				addedReviewersEmailList.append(new IstarUserDAO().findById(new_reviewerId).getEmail() + " , ");
			}
		}

		
		if(!removedReviewersEmailList.toString().isEmpty()) {
			comments = removedReviewersEmailList.toString() + " have been removed from reviewer list by " +  ((IstarUser)request.getSession().getAttribute("user")).getEmail() + " ; ";
		}
		
		if (!addedReviewersEmailList.toString().isEmpty()) {
			comments = comments  + addedReviewersEmailList.toString() + " have been added to reviewer list by " +  ((IstarUser)request.getSession().getAttribute("user")).getEmail() + " ; ";
		}
		
		try {
			TaskReviewerDAO dao = new TaskReviewerDAO();
			Session session = dao.getSession();
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			int result = query.executeUpdate();
			session.beginTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!comments.isEmpty()) {
			CMSRegistry.addTaskLogEntry(request, "CONTENT_ASSIGNED", comments, task.getId(), "LESSON", task.getItemId(), "Reviewers updated for the lesson");
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
			
			dao.save(task);
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		} finally {
			session1.close();
		}

		String comments = "The Lesson was assigned to "+(new IstarUserDAO().findById(Integer.parseInt(content_id))).getEmail()+" by "+ ((IstarUser)request.getSession().getAttribute("user")).getEmail();
		CMSRegistry.addTaskLogEntry(request, "CONTENT_ASSIGNED", comments, task.getId(), "LESSON", task.getItemId(), "Actor is assigned for the lesson");
		
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