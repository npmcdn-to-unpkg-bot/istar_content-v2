package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.TaskReviewer;
import com.istarindia.apps.dao.TaskReviewerDAO;
import com.istarindia.apps.services.LessonService;

/**
 * Servlet implementation class ApprovedLessonForCreatorController
 */
@WebServlet("/approved_lesson_creator")
public class ApprovedLessonForCreatorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovedLessonForCreatorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		ArrayList<ArrayList<String>> list_to_be_displayed = new ArrayList<ArrayList<String>>(); 
		//id, lessonName, session, module, course,  review_by, request for publish
		//these values can be accessed in jsp page to render the data 
		List<Lesson> lessons= new LessonService().getAllLessonAssignedTO_ContentCreator(user.getId(), StatusTypes.APPROVED);
		for(Lesson lesson : lessons)
		{
			ArrayList<String> embed_list = new ArrayList<String>();
			embed_list.add(lesson.getId().toString());
			embed_list.add(lesson.getTitle());
			embed_list.add(lesson.getCmsession().getTitle());
			embed_list.add(lesson.getCmsession().getModule().getModuleName());
			embed_list.add(lesson.getCmsession().getModule().getCourse().getCourseName());
			Task task = new TaskDAO().findByItemId(lesson.getId()).get(0);
			
			List <TaskReviewer> taskreview = new TaskReviewerDAO().findByProperty("task", task);
			if(taskreview.size()>0)
			{
				StringBuffer buff = new StringBuffer();
				for(TaskReviewer review : taskreview)
				{
					buff.append(review.getContentReviewer().getName());
					buff.append(",");
				}
				embed_list.add(buff.toString());
			}
			else
			{
				embed_list.add("reviewer not assigned");
			}	
			embed_list.add(lesson.getId().toString()); //request for publish
			
			list_to_be_displayed.add(embed_list);
		}	
		request.setAttribute("lessons", list_to_be_displayed);
		request.getRequestDispatcher("/content_creator/approved_lesson.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
