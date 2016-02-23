package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.QualificationPack;
import com.istarindia.apps.services.QualificationPackService;

/**
 * Servlet implementation class UpdateQualificationPackController
 */
@WebServlet("/update_qualification_pack")
public class UpdateQualificationPackController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQualificationPackController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("qp_id") && request.getParameterMap().containsKey("qp_code") && request.getParameterMap().containsKey("jobrole") )
		{
			String jobrole = request.getParameter("jobrole");
			String qp_code = request.getParameter("qp_code");
			int qp_id = Integer.parseInt(request.getParameter("qp_id"));
			QualificationPack qp = new QualificationPackService().updateQualificationPack(qp_id, qp_code,jobrole);
			request.setAttribute("qualification_pack_id", qp.getId());
			request.getRequestDispatcher("/edit_qualification_pack").forward(request, response);
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
