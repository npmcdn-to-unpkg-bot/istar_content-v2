package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.TaskReviewer;
import com.istarindia.apps.dao.TaskReviewerDAO;
import com.istarindia.apps.services.LessonService;

/**
 * Servlet implementation class LessonPerObjectiveController
 */
@WebServlet("/learning_objective_lesson")
public class LessonPerObjectiveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LessonPerObjectiveController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("learning_obj_id"))
		{
			IstarUser user = (IstarUser)request.getSession().getAttribute("user");
			ArrayList<ArrayList<String>> list_to_be_displayed = new ArrayList<ArrayList<String>>(); 
			//id, lessonName, session, module, course, assigned_to, reviewed_by, mobile preview, desktop preview, delete lesson
			//these values can be accessed in jsp page to render the data 
			Set<Lesson> lessons = new LessonService().getLessonForLearningObjective(Integer.parseInt(request.getParameter("learning_obj_id")));
			for(Lesson lesson : lessons)
			{
				ArrayList<String> embed_list = new ArrayList<String>();
				embed_list.add(lesson.getId().toString());
				embed_list.add(lesson.getTitle());
				embed_list.add(lesson.getCmsession().getTitle());
				embed_list.add(lesson.getCmsession().getModule().getModuleName());
				embed_list.add(lesson.getCmsession().getModule().getCourse().getCourseName());
				Task task = new TaskDAO().findByItemId(lesson.getId()).get(0);
				embed_list.add(new IstarUserDAO().findById(task.getActorId()).getName());
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
				embed_list.add(lesson.getId().toString());
				embed_list.add(lesson.getId().toString());
				embed_list.add(lesson.getId().toString());

				list_to_be_displayed.add(embed_list);
			}	
			request.setAttribute("lessons", list_to_be_displayed);
			response.sendRedirect(request.getContextPath() + "/learning_objective/view_learning_obj.jsp");
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
