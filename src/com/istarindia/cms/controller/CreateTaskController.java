package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.CreativeAdmin;
import com.istarindia.apps.dao.CreativeAdminDAO;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Video;
import com.istarindia.apps.dao.VideoDAO;
import com.istarindia.apps.services.TaskService;

/**
 * Servlet implementation class CreateTaskController
 */
@WebServlet("/create_task")
public class CreateTaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTaskController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("media_type") && request.getParameterMap().containsKey("description") && request.getParameterMap().containsKey("title") && request.getParameterMap().containsKey("selected_items"))
		{
			int cmsession_id=0;
			for (String cmsession : request.getParameter("selected_items").split(",")) {
				if(cmsession.startsWith("session_")) {
					cmsession_id = Integer.parseInt(cmsession.replace("session_", ""));
				}
				System.out.println("session is "+cmsession);
			
			IstarUser user =((IstarUser)request.getSession().getAttribute("user"));
			int item_id=0;
			String desc = request.getParameter("description");
			String media_type = request.getParameter("media_type");
			String title = request.getParameter("title");
			if(media_type.equalsIgnoreCase("IMAGE"))
			{
				ImageDAO dao = new ImageDAO();
				Image img = new Image();
				img.setDescription(desc);
				img.setTitle(title);
				img.setSessionid(cmsession_id);
				Session session = dao.getSession();
				Transaction tx = null;
				try {
					tx = session.beginTransaction();
					dao.save(img);
					tx.commit();
				} catch (HibernateException e) {
					if (tx != null)
						tx.rollback();
					e.printStackTrace();
				} finally {
					session.close();
				}
				
				
					item_id = img.getId();  
			}
			else if(media_type.equalsIgnoreCase("VIDEO"))
			{
				VideoDAO dao = new VideoDAO();
				Video vid = new Video();
				vid.setDescription(desc);
				vid.setTitle(title);
				vid.setSessionId(cmsession_id);
				Session session1 = dao.getSession();
				Transaction tx1 = null;
				try {
					tx1 = session1.beginTransaction();
					dao.save(vid);
					tx1.commit();
				} catch (HibernateException e) {
					if (tx1 != null)
						tx1.rollback();
					e.printStackTrace();
				} finally {
					session1.close();
				}
				item_id = vid.getId();
			}	
			
			
			
			CreativeAdmin cretaive_admin = (CreativeAdmin)new CreativeAdminDAO().findAll().get(0);
			new TaskService().createTask(item_id, media_type, "ALL", StatusTypes.CREATED, "CREATE_"+media_type, cretaive_admin.getId(), user.getId());

			request.setAttribute("message_success", "Media task has been created successfully!");
			request.getRequestDispatcher("/media/create_task.jsp").forward(request, response);	
			}
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
