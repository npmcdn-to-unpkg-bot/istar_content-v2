package com.istarindia.apps.services.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.TaskLog;
import com.istarindia.apps.dao.TaskLogDAO;
import com.istarindia.apps.dao.UiTheme;
import com.istarindia.apps.dao.UiThemeDAO;
import com.istarindia.apps.services.UiThemeService;

/**
 * Servlet implementation class ThemeBuilderController
 */
@WebServlet("/theme_editor")
public class ThemeBuilderController  extends IStarBaseServelet {
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
		int theme_id = 0;
		UiThemeService service = new UiThemeService();
		if(request.getParameterMap().containsKey("is_edit")){
			theme_id = Integer.parseInt(request.getParameter("theme_id"));
			service.editTheme(request.getParameterMap());
		} else{
			theme_id = service.addNewTheme(request.getParameterMap());
		}
		
		UiThemeDAO dao = new UiThemeDAO();
		UiTheme theme = dao.findById(theme_id);
		IstarUser user = (IstarUser) request.getSession().getAttribute("user");
		
		String message = new String();
		String status = new String();
		String title = new String();
		
		if(!request.getParameterMap().containsKey("is_edit")) {
			title =  theme.getName() + " theme is created";
			message = "A new theme is created wih ID ->" + theme_id + " by " + user.getEmail() + " ; Name -> " + theme.getName();
			status = "CREATED";
		} else {
			title = theme.getName() + " theme is updated";
			message = theme.getName() + " Theme with ID ->" + theme_id + " has been updated by " + user.getEmail();
			status = "UPDATED";
		}
		
		try {
			pushThemeNotification(theme_id, user , title, message, status, request.getSession().getId());
		} catch (Exception e) {
			//printParams(request);
		}
		
		response.sendRedirect("/content/creative_admin/view_themes.jsp");
		
	}
	
	public static void pushThemeNotification(int theme_id, IstarUser user, String title, String message, String status, String jsession_id) {

		TaskLogDAO dao = new TaskLogDAO();
		TaskLog log = new TaskLog();
		log.setActorId(user.getId());
		log.setChangedStatus(status);
		log.setComments(message);
		log.setSessionID(jsession_id);
		log.setTitle(title);
		
		log.setItem_id(theme_id);
		log.setTaskId(0);
		log.setItemType("THEME");
		
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		log.setCreatedAt(currentTimestamp);
		
		Session session = dao.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.save(log);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
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