package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.services.PresentationService;

/**
 * Servlet implementation class TeacherNotesPreview
 */
@WebServlet("/teacher_notes_preview")
public class TeacherNotesPreview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public TeacherNotesPreview() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			String notes = new PresentationService().getTeacherNotesForPreview(Integer.parseInt(request.getParameter("lesson_id")));
			request.setAttribute("notes", notes);
			response.sendRedirect(request.getContextPath() +"lesson/teacher_notes_preview.jsp");
		}
		catch(NullPointerException ex)
		{
			request.setAttribute("errMsg", ex.getMessage());
			ex.printStackTrace();
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
