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

import com.istarindia.apps.dao.Handouts;
import com.istarindia.apps.dao.HandoutsDAO;

/**
 * Servlet implementation class UpdateHandoutController
 */
@WebServlet("/update_handout")
public class UpdateHandoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateHandoutController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("is_edit").equalsIgnoreCase("true")) {
			int handout_id = Integer.parseInt(request.getParameter("handout_id"));
			String handout_title = request.getParameter("title");
			String handout_text = request.getParameter("handouts_text");
			HandoutsDAO dao = new HandoutsDAO();
			Handouts handout = dao.findById(handout_id);

			handout.setDataBloB(handout_text);
			handout.setTitle(handout_title);

			Session session = dao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				dao.save(handout);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		} else {
			int handout_id = Integer.parseInt(request.getParameter("lessson_id"));
			String handout_title = request.getParameter("title");
			String handout_text = request.getParameter("handouts_text");
			HandoutsDAO dao = new HandoutsDAO();
			Handouts handout = new Handouts();
			handout.setLessonId(handout_id);
			handout.setDataBloB(handout_text);
			handout.setTitle(handout_title);

			Session session = dao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				dao.save(handout);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		}

		response.sendRedirect("/content/content_creator/handouts.jsp");
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
