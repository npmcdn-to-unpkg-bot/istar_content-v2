package com.istarindia.apps.services.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.Vacancy;
import com.istarindia.apps.dao.VacancyDAO;

/**
 * Servlet implementation class UpdateVacanyController
 */
@WebServlet("/update_vacany")
public class UpdateVacanyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateVacanyController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("name");
		String desc = request.getParameter("desc");

		VacancyDAO dao = new VacancyDAO();
		Vacancy v = dao.findById(id);
		v.setDescription(desc);
		v.setProfileTitle(title);
		Session session1 = dao.getSession();
		Transaction tx1 = null;

		try {
			tx1 = session1.beginTransaction();
			Short iiii = 1;
			dao.attachDirty(v);
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null) {
				tx1.rollback();
			}
			// e.printStackTrace();
		} finally {
			session1.close();
		}
		
		response.sendRedirect("/content/trainer/edit_vacancies.jsp?id="+id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
