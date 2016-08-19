package com.istarindia.cms.controller;

import javax.servlet.annotation.WebServlet;

import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class MediaReviewByAdmin
 */
@WebServlet("/media_review_by_admin")
public class MediaReviewByAdmin extends IStarBaseServelet {/*
	private static final long serialVersionUID = 1L;
       
    *//**
     * @see HttpServlet#HttpServlet()
     *//*
    public MediaReviewByAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *//*
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
			ImageReviewDAO dao = new ImageReviewDAO();
			ImageReview review;
			Task task = new TaskDAO().findById(Integer.parseInt(request.getParameter("task_id")));
			List<ImageReview> reviews = dao.findByProperty("task",task);
			if(reviews.size()>0 )
			{
				review = reviews.get(0);
				String prev_comment = review.getComment();
				review.setComment(prev_comment+", "+comment);
			}
			else
			{
				review = new ImageReview();
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
				request.setAttribute("message_success", "The media has been approved and published successfully!");
			}
			else
			{
				new TaskService().updateStatus(task.getId(), StatusTypes.DIS_APPROVED);
				request.setAttribute("message_success", "The media has been sent back to the creator with your comments!");
			}	
			
		}
		request.getRequestDispatcher("completed_media_creative_admin").forward(request, response);
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

*/}
