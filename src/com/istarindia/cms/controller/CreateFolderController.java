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

import com.istarindia.apps.dao.Folder;
import com.istarindia.apps.dao.FolderDAO;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class CreateFolderController
 */
@WebServlet("/create_folder")
public class CreateFolderController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFolderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("atelast coming in controler");
		printParams(request);
		if(request.getParameterMap().containsKey("selected_items") && request.getParameterMap().containsKey("folder_name"))
		{
			System.out.println("i am here");
			for (String folder_id : request.getParameter("selected_items").split(",")) {
				FolderDAO dao = new FolderDAO();
				Folder folder = new Folder();
				folder.setName(request.getParameter("folder_name"));
				folder.setParentId(Integer.parseInt(folder_id));
				Session session1 = dao.getSession();
				Transaction tx1 = null;
				try {
					tx1 = session1.beginTransaction();
					dao.save(folder);
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
		request.setAttribute("message_success", "New folder has been created successfully!");
		request.getRequestDispatcher("/creative_admin/new_folder.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
