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
		
		String message = "" ;
		String status = new String();
		IstarUser user = (IstarUser) request.getSession().getAttribute("user");
		
		if(!request.getParameterMap().containsKey("is_edit")) {
			message = "A new theme is created wih ID ->" + theme_id + " by " + user.getEmail() + " ; Name -> " + theme.getName();
			status = "CREATED";
		} else {
			message = theme.getName() + " Theme with ID ->" + theme_id + " has been updated by " + user.getEmail();
			status = "UPDATED";
		}
		
		pushThemeNotification(theme_id, user , message, status);
		
		response.sendRedirect("/content/creative_admin/view_themes.jsp");
		
	}
	
	public static void pushThemeNotification(int theme_id, IstarUser user, String message, String status) {

		TaskLogDAO dao = new TaskLogDAO();
		TaskLog log = new TaskLog();
		log.setActorId(user.getId());
		log.setChangedStatus(status);
		log.setComments(message);
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