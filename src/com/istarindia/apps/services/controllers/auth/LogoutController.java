package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/auth/logout")
public class LogoutController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		printParams(request);
		try {
			IstarUser user = (IstarUser) request.getSession().getAttribute("user");
			System.out.println("user to logout ->>"+ user.getEmail());
			request.getSession().removeAttribute("user");
			IstarUserDAO dao = new IstarUserDAO();
			Session session1 = dao.getSession();
			Transaction tx = null;
			try {
				user.setIstarAuthorizationToken("");
				tx = session1.beginTransaction();
				dao.attachDirty(user);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session1.close();
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Missing Username or password");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		} catch (org.hibernate.HibernateException e) {
			// TODO: handle exception
		}

		try {
			Cookie cookie = new Cookie("token", "");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			request.setAttribute("msg", "You are successfully Logged out.");
			response.sendRedirect(request.getContextPath() + "/index.jsp");

		} catch (java.lang.IndexOutOfBoundsException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Missing Username or password");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
