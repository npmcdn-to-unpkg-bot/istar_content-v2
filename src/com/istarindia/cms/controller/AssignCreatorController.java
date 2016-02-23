package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.services.TaskService;

@WebServlet("/assign_creator")
public class AssignCreatorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignCreatorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameterMap().containsKey("courses") && request.getParameterMap().containsKey("creator_id"))
		{
			String course[] = request.getParameterValues("courses");
			if(course.length>0)
			{
				//new TaskService().assignCourseToContentCreator(course_id, content_creator_id);
				
				return;
			}
		}
		if(request.getParameterMap().containsKey("modules") && request.getParameterMap().containsKey("creator_id"))
		{
			String modules[] = request.getParameterValues("modules");
			if(modules.length>0)
			{
				
				
				return;
			}
		}
		
		if(request.getParameterMap().containsKey("cmsessions") && request.getParameterMap().containsKey("creator_id"))
		{
			String cmsessions[] = request.getParameterValues("smsessions");
			if(cmsessions.length>0)
			{
				
				
				return;
			}
		}
		if(request.getParameterMap().containsKey("lessons") && request.getParameterMap().containsKey("creator_id"))
		{
			String lessons[] = request.getParameterValues("lessons");
			if(lessons.length>0)
			{
				
				
				return;
			}
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
