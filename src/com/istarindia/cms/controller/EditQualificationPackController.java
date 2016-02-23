package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.QualificationPack;
import com.istarindia.apps.dao.QualificationPackDAO;

/**
 * Servlet implementation class EditQualificationPackController
 */
@WebServlet("/edit_qualification_pack")
public class EditQualificationPackController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQualificationPackController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("qualification_pack_id"))
		{
			QualificationPack qp = new QualificationPackDAO().findById(Integer.parseInt(request.getParameter("qualification_pack_id")));
			request.setAttribute("qualification_pack", qp);
			request.getRequestDispatcher("/qualification_pack/edit_qualification.jsp").forward(request, response);
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
