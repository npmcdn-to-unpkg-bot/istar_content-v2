package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.MediaTypes;
import com.istarindia.apps.dao.CmsessionDAO;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageReview;
import com.istarindia.apps.dao.ImageReviewDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.Video;

/**
 * Servlet implementation class MediaReviewController
 */
@WebServlet("/media_review")
public class MediaReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MediaReviewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {/*
		int task_id = Integer.parseInt(request.getParameter("task_id"));
		Task task = new TaskDAO().findById(task_id);
		ArrayList<String> details = new ArrayList<String>();
		if(task.getItemType().equalsIgnoreCase(MediaTypes.IMAGE))
		{
			Image image = task.getImage();
			
			details.add(image.getTitle());
			details.add(image.getDescription());
			details.add(image.getTags());
			details.add(new CmsessionDAO().findById(image.getSessionid()).getTitle());
			details.add(image.getUrl());
			List <ImageReview> media_review= new ImageReviewDAO().findByProperty("task",task);
			StringBuffer sb = new StringBuffer();
			if(media_review.size()>0)
			{
				for(ImageReview review: media_review)
				{
					sb.append(review.getComment()+",");
				}
			}
			details.add(sb.toString());
			details.add(task.getId().toString());
		}
		else if(task.getItemType().equalsIgnoreCase(MediaTypes.VIDEO))
		{
			Video video = task.getVideo();
			details.add(video.getTitle());
			details.add(video.getDescription());
			details.add(video.getTags());
			details.add(new CmsessionDAO().findById(video.getSessionId()).getTitle());
			details.add(video.getUrl());
			List <ImageReview> media_review= new ImageReviewDAO().findByProperty("task",task);
			StringBuffer sb = new StringBuffer();
			if(media_review.size()>0)
			{
				for(ImageReview review: media_review)
				{
					sb.append(review.getComment()+",");
				}
			}
			details.add(sb.toString());
			details.add(task.getId().toString());
		}
		
		request.setAttribute("media_details", details);
		request.getRequestDispatcher("/creative_admin/review.jsp").forward(request, response);
	*/}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
