package com.istarindia.cms.controller;

import java.io.IOException;
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
import com.istarindia.apps.dao.MediaReview;
import com.istarindia.apps.dao.MediaReviewDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.TaskService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class MediaReviewByAdmin
 */
@WebServlet("/media_review_by_admin")
public class MediaReviewByAdmin extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MediaReviewByAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		printParams(request);
		if(request.getParameterMap().containsKey("button") && request.getParameterMap().containsKey("task_id"))
		{
			String comment="";
			if(request.getParameter("comment")!= null)
			{
				comment = request.getParameter("comment");
			}
			MediaReviewDAO dao = new MediaReviewDAO();
			MediaReview review;
			Task task = new TaskDAO().findById(Integer.parseInt(request.getParameter("task_id")));
			List<MediaReview> reviews = dao.findByProperty("task",task);
			if(reviews.size()>0 )
			{
				review = reviews.get(0);
				String prev_comment = review.getComment();
				review.setComment(prev_comment+", "+comment);
			}
			else
			{
				review = new MediaReview();
				review.setTask(task);
				review.setComment(comment);
			}	
			
			
			
			Session session = dao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				dao.attachDirty(review);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			if(request.getParameter("button").equalsIgnoreCase(StatusTypes.APPROVED))
			{
				new TaskService().updateStatus(task.getId(), StatusTypes.PUBLISHED);
			}
			else
			{
				new TaskService().updateStatus(task.getId(), StatusTypes.DIS_APPROVED);
			}	
			
		}
		request.getRequestDispatcher("completed_media_creative_admin").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
