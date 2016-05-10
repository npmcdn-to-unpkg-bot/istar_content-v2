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
		
		UiThemeService service = new UiThemeService();
		if(request.getParameterMap().containsKey("is_edit")){
			service.editTheme(request.getParameterMap());
		} else{
			service.addNewTheme(request.getParameterMap());
		}
		
		response.sendRedirect("/content/creative_admin/view_themes.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}