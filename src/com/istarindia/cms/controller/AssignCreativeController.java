package com.istarindia.cms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.CreativeCreatorDAO;
import com.istarindia.apps.dao.ImageTask;
import com.istarindia.apps.dao.ImageTaskDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.VideoTask;
import com.istarindia.apps.dao.VideoTaskDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

@WebServlet("/assign_creator")
public class AssignCreativeController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignCreativeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		for (String assign : request.getParameter("selected_items").split(",")) {
			if(assign.startsWith("image")) {
				
				assignImageTask(assign, request);
				
			} else if(assign.startsWith("video")) {
				
				assignVideoTask(assign, request);
				
			}
		}
		
		request.setAttribute("message_success", "Task has been assigned successfully!");
		request.getRequestDispatcher("/creative_admin/assign_creative.jsp").forward(request, response);

	//	response.sendRedirect(request.getContextPath() + "/creative_admin/assign_creative.jsp");
	}

	private void assignImageTask(String assign, HttpServletRequest request) {
		int creative_id = Integer.parseInt( request.getParameter("assign_user").toString() );
		String taskid= assign.replace("image_task_", "");
		ImageTaskDAO dao = new  ImageTaskDAO();
		ImageTask task  = dao.findById(Integer.parseInt(taskid));
		task.setActorId(creative_id);
		task.setStatus(StatusTypes.CREATIVE_ASSIGNED);
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
		
		if(!(new ImageTaskDAO()).findByExample(task).isEmpty()) {
			saveImageTaskLog(task, creative_id, request);
		}
	}

	private void saveImageTaskLog(ImageTask task, int creative_creator_id, HttpServletRequest request ) {
		try {
			String creativeEmail = (new CreativeCreatorDAO()).findById(creative_creator_id).getEmail();
			String comments = WordUtils.capitalize(task.getItemType().toLowerCase()) + " task (TaskID " + task.getId() + ") has been assigned to " + creativeEmail + " by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail();
			String title = WordUtils.capitalize(task.getItemType().toLowerCase()) + " task has been assigned to artist";
			CMSRegistry.addTaskLogEntry(request, "ASSIGNED", comments, task.getId(), task.getItemType(), task.getImage().getId(), title);			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private void assignVideoTask(String assign, HttpServletRequest request) {
		int creative_id = Integer.parseInt( request.getParameter("assign_user").toString() );
		String taskid= assign.replace("video_task_", "");
		VideoTaskDAO dao = new  VideoTaskDAO();
		VideoTask task  = dao.findById(Integer.parseInt(taskid));
		task.setActorId(creative_id);
		task.setStatus(StatusTypes.CREATIVE_ASSIGNED);
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
		
		if(!(new VideoTaskDAO()).findByExample(task).isEmpty()) {
			saveVideoTaskLog(task, creative_id, request);
		}
	}

	private void saveVideoTaskLog(VideoTask task, int creative_creator_id, HttpServletRequest request ) {
		try {
			String creativeEmail = (new CreativeCreatorDAO()).findById(creative_creator_id).getEmail();
			String comments = WordUtils.capitalize(task.getItemType().toLowerCase()) + " task (TaskID " + task.getId() + ") has been assigned to " + creativeEmail + " by " + ((IstarUser)request.getSession().getAttribute("user")).getEmail();
			String title = WordUtils.capitalize(task.getItemType().toLowerCase()) + " task has been assigned to artist";
			CMSRegistry.addTaskLogEntry(request, "ASSIGNED", comments, task.getId(), task.getItemType(), task.getVideo().getId(), title);			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
