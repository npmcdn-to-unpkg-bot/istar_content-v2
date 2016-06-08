package com.istarindia.apps.cmsutils.reports.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class EditEntityController
 */
@WebServlet("/edit_entity")
public class EditEntityController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEntityController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int report_id = Integer.parseInt(request.getParameter("report_id"));
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("report_id", report_id);
		request.setAttribute("id", id);
		request.getRequestDispatcher("/trainer/generic_edit.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		printParams(request);
		
		/* 
		 * key-> report_id : value ->152
key-> entity_name : value ->vacancy
key-> profile_title : value ->Tally / MS Office
key-> location : value ->Bangalore
key-> description : value ->ISTAR provides training in Accounts, Finance, Economics, Taxation,Banking , Financial markets,Mutual funds ,Information Technology other commerce /IT/Management related areas in UG and PG colleges for BCOM , BBM, BBA, B.A students.  The trainers should be B.Com with min of 2 years of experience and  are required to deliver iSTAR content in colleges.

		 */
		String sql="";
		if(request.getParameter("submit").equalsIgnoreCase("update"))
		{
			int  reportID= Integer.parseInt(request.getParameter("report_id"));
			String tableName = request.getParameter("entity_name").trim();
			int  rowID= Integer.parseInt(request.getParameter("ROWID"));
			HashMap<String, String> keyVals = new HashMap<>();
			
			String upd = new String();
			
			for (Object name : request.getParameterMap().keySet()) {
				if (!(name.toString().equalsIgnoreCase("report_id") || name.toString().equalsIgnoreCase("entity_name") || name.toString().equalsIgnoreCase("ROWID") || name.toString().equalsIgnoreCase("submit"))) {
				keyVals.put(name.toString(), request.getParameter(name.toString()));
				upd = upd + " "+ name.toString() + " ='"+ request.getParameter(name.toString()) +"' , ";
				} 
			}
			sql = "UPDATE  "+tableName+"  SET  "+ upd.substring(0, upd.length()-2)  + " WHERE (id='"+rowID+"')";
		}
		else if(request.getParameter("submit").equalsIgnoreCase("delete"))
		{
			String tableName = request.getParameter("entity_name").trim();
			int  rowID= Integer.parseInt(request.getParameter("ROWID"));
			 sql ="DELETE FROM "+tableName+" WHERE id='"+rowID+"'";
			
		}	
		
		try {
			IstarUserDAO dao = new IstarUserDAO();
			
			Session session = dao.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			int result = query.executeUpdate();
			System.err.println("result ===" + result);
			session.beginTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getRequestDispatcher("/").forward(request, response);
		
		
	}

}
