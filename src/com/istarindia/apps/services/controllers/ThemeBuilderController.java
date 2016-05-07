package com.istarindia.apps.services.controllers;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.UiTheme;
import com.istarindia.apps.services.UiThemeService;

import sun.swing.UIAction;

/**
 * Servlet implementation class ThemeBuilderController
 */
@WebServlet("/theme_editor")
public class ThemeBuilderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThemeBuilderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> params = request.getParameterNames();
		
		while (params.hasMoreElements()){
			
			try {
				String param = params.nextElement();
				System.err.println(param+" :"+request.getParameter(param));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		UiThemeService service = new UiThemeService();
		service.addNewTheme(request.getParameterMap());
		
		response.sendRedirect("/content/creative_admin/create_theme.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}