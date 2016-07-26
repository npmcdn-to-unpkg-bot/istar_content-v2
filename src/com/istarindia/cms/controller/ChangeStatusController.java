package com.istarindia.cms.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.Cmsession;
import com.istarindia.apps.dao.CmsessionDAO;
import com.istarindia.apps.dao.Course;
import com.istarindia.apps.dao.DBUTILS;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Module;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.TaskReviewer;
import com.istarindia.apps.dao.TaskReviewerDAO;
import com.istarindia.apps.services.TaskService;
import com.istarindia.apps.services.controllers.auth.CreateSlideController;
import com.istarindia.apps.services.task.CreateLessonTaskManager;
import com.istarindia.apps.services.task.EmailSendingUtility;

/**
 * Servlet implementation class ChangeStatusController
 */
@WebServlet("/change_status")
public class ChangeStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String deployment_type;
	private String host;
	private String port;
	private String user1;
	private String pass;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeStatusController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("new_status") && request.getParameterMap().containsKey("task_id"))
		{
			Properties properties = new Properties();
			String propertyFileName = "app.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertyFileName + "' not found in the classpath");
			}
			
			
			deployment_type = properties.getProperty("deployment_type");
			host = properties.getProperty("host");
			port = properties.getProperty("port");
			user1 = properties.getProperty("emailFrom");
			pass = properties.getProperty("emailFromPassword");
			
			
			
			
			
			int task_id = Integer.parseInt(request.getParameter("task_id"));
			String new_status = request.getParameter("new_status");
			IstarUser user = (IstarUser)request.getSession().getAttribute("user");
			
			Task t = new TaskDAO().findById(task_id);
			Lesson ll = new LessonDAO().findById(t.getItemId());
			Cmsession cm = ll.getCmsession();
			Module mm = cm.getModule();
			Course cc = mm.getCourse();
			String resultMessage = "The status for the LESSON - "+ll.getTitle()+"\nSESSION - "+cm.getTitle()+"\nMODULE - "+mm.getModuleName()+"\nCOURSE - " 
			+cc.getCourseName()+"    \nis changed from "+new TaskDAO().findById(task_id).getStatus() +" to "+ new_status+"\nby user "+user.getEmail();
			
			
			CreateLessonTaskManager.pushTaskNotification(new TaskDAO().findById(task_id), user, "The status for the task is changed from "+new TaskDAO().findById(task_id).getStatus() +" to "+ new_status);
			new TaskService().updateStatus(task_id, new_status);
			
			HashMap<String, String> recipient= new HashMap<>();
			//content creator
			
			String  content_email = (new IstarUserDAO().findById(t.getActorId())).getEmail();
			if(content_email != null)
			{
				recipient.put(content_email, content_email);// recipient+";"+content_email;
			}
			
			//content reviewer
			List<TaskReviewer> review_list = new TaskReviewerDAO().findByProperty("task", t);
			if(review_list!=null && review_list.size()>0)
			{
				for(TaskReviewer tr : review_list)
				{
					String review_email = tr.getContentReviewer().getEmail();
					recipient.put(review_email, review_email);
				}
			}
			
			// misc users 
			//vaibhav@istarindia.com, surga.thilakan@istarindia.com , sreeram@istarindia.com 
			for (String taskReviewer : properties.get("email_mandatory_list").toString().split(",")) {
				recipient.put(taskReviewer, taskReviewer);
			}
			String subject = "changes in content"; 
			new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						if (deployment_type.trim().equalsIgnoreCase("prod")) {
							EmailSendingUtility.sendEmail(host, port, user1, pass, recipient, subject, resultMessage); 
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			};
			
			
			
			if(new_status.equalsIgnoreCase(StatusTypes.COMPLETED)) {
				request.setAttribute("message_success", "The task has been sent for review successfully!");
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
			else if(new_status.equalsIgnoreCase(StatusTypes.UNPUBLISHED)) {
				request.setAttribute("message_success", "The task has been unpublished!");
			}
			
			String redirectUrl = new String();
			if (request.getParameterMap().containsKey("source_link")){
				//The request is from the "modify course details" page!
				IstarUserDAO dao = new IstarUserDAO();
				Session session = dao.getSession();
				String sql = "select L.session_id from task T, Lesson L where T.item_type= 'LESSON' and T.item_id=L.id and T.id="+task_id;
				
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				DBUTILS util = new DBUTILS();
				List<HashMap<String, Object>> results = util.executeQuery(sql);
				   
				HashMap<String, Object> result = (HashMap<String, Object>) results.get(0);
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
