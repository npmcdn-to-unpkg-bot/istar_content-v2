package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.TaskService;
import com.istarindia.apps.services.controllers.auth.CreateSlideController;
import com.istarindia.apps.services.task.CreateLessonTaskManager;

/**
 * Servlet implementation class ChangeStatusController
 */
@WebServlet("/change_status")
public class ChangeStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeStatusController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("new_status") && request.getParameterMap().containsKey("task_id"))
		{
			int task_id = Integer.parseInt(request.getParameter("task_id"));
			String new_status = request.getParameter("new_status");
			IstarUser user = (IstarUser)request.getSession().getAttribute("user");
			CreateLessonTaskManager.pushTaskNotification(new TaskDAO().findById(task_id), user, "The status for the task is changed from "+new TaskDAO().findById(task_id).getStatus() +" to "+ new_status);
			new TaskService().updateStatus(task_id, new_status);
			if(new_status.equalsIgnoreCase(StatusTypes.COMPLETED)) {
				request.setAttribute("message_success", "New task has been sent for review successfully!");
			}
			else if(new_status.equalsIgnoreCase(StatusTypes.DRAFT)) {
				request.setAttribute("message_success", "The task has been updated as In Progress successfully!");
			}
			else if(new_status.equalsIgnoreCase(StatusTypes.DIS_APPROVED)) {
				request.setAttribute("message_success", "The task has been successfully sent back to the content creator!");
			}
			else if(new_status.equalsIgnoreCase(StatusTypes.APPROVED)) {
				request.setAttribute("message_success", "The task has been successfully approved!");
			}
			else if(new_status.equalsIgnoreCase(StatusTypes.PUBLISHED)) {
				request.setAttribute("message_success", "The task has been successfully published!");
			}
			else if(new_status.equalsIgnoreCase(StatusTypes.REQUEST_FOR_PUBLISH)) {
				request.setAttribute("message_success", "The request has been sent for publishing the task!");
			}
			else if(new_status.equalsIgnoreCase(StatusTypes.DELETED)) {
				request.setAttribute("message_success", "The task has been successfully deleted!");
			}
			
			String redirectUrl = new String();
			if (request.getParameterMap().containsKey("source_link")){
				//The request is from the "modify course details" page!
				IstarUserDAO dao = new IstarUserDAO();
				Session session = dao.getSession();
				String sql = "select L.session_id from task T, Lesson L where T.item_type= 'LESSON' and T.item_id=L.id and T.id="+task_id;
				
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				
				HashMap<String, Object> result = (HashMap<String, Object>) query.list().get(0);
				int session_id = Integer.parseInt(result.get("session_id").toString());
				redirectUrl = "content_admin/modify_session.jsp?session_id="+session_id;
				
			} else {
				redirectUrl=user.getUserType().toLowerCase() + "/dashboard.jsp";
			}
			request.getRequestDispatcher(redirectUrl).forward(request, response);
			//response.sendRedirect(request.getContextPath() + "/" + user.getUserType().toLowerCase() + "/dashboard.jsp");
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
