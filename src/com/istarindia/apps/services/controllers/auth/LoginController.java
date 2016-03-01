package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.UserService;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		printParams(request);
		if (request.getParameterMap().containsKey("email") && request.getParameterMap().containsKey("password")) {
			try {
				IstarUserDAO dao = new IstarUserDAO();
				IstarUser user = dao.findByEmail(request.getParameter("email")).get(0);
				if (user.getPassword().equalsIgnoreCase(request.getParameter("password"))) {
					System.out.println("---------->"+ request.getParameter("remember"));
					boolean remember = false ;
					 try {
					if(request.getParameter("remember").equalsIgnoreCase("on")) {
						remember = true;
					}} catch(NullPointerException npe) {}
					 
					 if(remember)
					 {
						 	System.out.println("----------> Remeber ME ");
						 	//MessageDigest md = MessageDigest.getInstance("MD5");
					        //md.update(request.getParameter("email").toString().getBytes());
					        
					       //byte byteData[] = md.digest();
					 
					        //convert the byte to hex format method 1
					        //StringBuffer sb = new StringBuffer();
					        //for (int i = 0; i < byteData.length; i++) {
					         //sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
					        //}
							UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
							String uuid = uid.randomUUID().toString();
							Cookie c = new Cookie("token", uuid);
							c.setMaxAge(365 * 24 * 60 * 60); // one year
							c.setPath("/");
							response.addCookie(c);
							//System.out.println(">>>>>"+c.getName());
							request.getSession().setAttribute("user", user);

							UserService service = new UserService();
							service.saveSessionToken(uuid, user.getId());
							CMSRegistry.writeAuditLog("User Logged in ->" + ((IstarUser) request.getSession().getAttribute("user")).getEmail(), user.getId());
							request.setAttribute("msg", "Welcome to iStar, " + user.getName());
							//request.getRequestDispatcher("/"+user.getUserType().toLowerCase() + "/dashboard.jsp").forward(request, response);
							response.sendRedirect(request.getContextPath() + "/" + user.getUserType().toLowerCase() + "/dashboard.jsp");
					 }
					 else
					 {
						 request.getSession().setAttribute("user", user);
						 CMSRegistry.writeAuditLog("User Logged in ->" + ((IstarUser) request.getSession().getAttribute("user")).getEmail(), user.getId());
							request.setAttribute("msg", "Welcome to iStar, " + user.getName());
							response.sendRedirect(request.getContextPath() + "/" + user.getUserType().toLowerCase() + "/dashboard.jsp");
					 }	 
					
					
				}
			} catch (java.lang.IndexOutOfBoundsException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Missing Username or password");
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
				request.getRequestDispatcher("/content_admin/index.jsp").forward(request, response);
				rd.forward(request, response);
			}

		} else {
			request.setAttribute("msg", "Missing Username or password");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}