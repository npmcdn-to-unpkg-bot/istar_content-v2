package com.istarindia.apps.services.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.IstarUserDAO;

/**
 * Servlet implementation class SaveSlideOrderController
 */
@WebServlet("/save_order")
public class SaveSlideOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveSlideOrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		for(Object key: request.getParameterMap().keySet()) {
			System.err.println("key-> "+ key.toString() + " : value ->"+ request.getParameter(key.toString()));
		}
		if (request.getParameterMap().containsKey("order_holder")){
			String order_holder_string = request.getParameter("order_holder");
			String[] order_holder = order_holder_string.split(",");
			
			updateSlideOrder(order_holder);
			
		}
		
		response.sendRedirect("/content/edit_lesson?task_id=" +Integer.parseInt(request.getParameter("task_id")) );
	}

	private static void updateSlideOrder(String[] order_holder) {
		
		StringBuffer sql = new StringBuffer();
		for (int i=0;i<order_holder.length;i++){
			sql.append("UPDATE slide SET order_id = "+(i+1)+" WHERE ID ="+order_holder[i]+"; ");
		}
		
		IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		session.beginTransaction();
		query.executeUpdate();
		session.getTransaction().commit();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
