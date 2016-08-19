package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.UserTypes;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/auth/login")
public class LoginController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
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
		request.getSession().removeAttribute("user");
		request.getSession(true);
		if (request.getParameterMap().containsKey("email") && request.getParameterMap().containsKey("password")) {
			try {
				IstarUserDAO dao = new IstarUserDAO();
				IstarUser user = dao.findByEmail(request.getParameter("email")).get(0);
				if (user.getPassword().equalsIgnoreCase(request.getParameter("password"))) {
					
					if(user.getUserType().toLowerCase().equalsIgnoreCase(UserTypes.CONTENT_ADMIN) ||
							user.getUserType().toLowerCase().equalsIgnoreCase(UserTypes.CONTENT_CREATOR) ||
							user.getUserType().toLowerCase().equalsIgnoreCase(UserTypes.CONTENT_REVIEWER) ||
							user.getUserType().toLowerCase().equalsIgnoreCase(UserTypes.CREATIVE_ADMIN) ||
							user.getUserType().toLowerCase().equalsIgnoreCase(UserTypes.CREATIVE_CREATOR)  
							
							
					) {
						request.getSession().setMaxInactiveInterval(-1);
						request.getSession().setAttribute("jsession_id", request.getSession().getId()+"-"+System.currentTimeMillis());
						request.getSession().setAttribute("user", user);
						CMSRegistry.addUserSessionLogEntry(request.getSession().getAttribute("jsession_id").toString(), user.getId(),"LOGIN");
						request.setAttribute("msg", "Welcome to iStar, " + user.getName());
						request.getRequestDispatcher("/" + user.getUserType().toLowerCase() + "/dashboard.jsp").forward(request, response);
					} else {
						request.setAttribute("msg", "You are not authorized to enter the content creation area.");
						request.getRequestDispatcher("/index.jsp").forward(request, response);
					}
					
					
				} else {
					request.setAttribute("msg", "Wrong Username or password");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
			} catch (java.lang.IndexOutOfBoundsException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Missing Username or password");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}

		} else {
			request.setAttribute("msg", "Missing Username or password");
			request.getRequestDispatcher("/content/index.jsp").forward(request, response);
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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