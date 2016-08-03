package com.istarindia.cms.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;

import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.SlideDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.cms.lessons.SlideService;

/**
 * Servlet implementation class DeleteSlideController
 */
@WebServlet("/delete_slide")
public class DeleteSlideController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteSlideController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
		SlideDAO dao = new SlideDAO();
		Slide slide= dao.findById(Integer.parseInt(request.getParameter("slide_id")));
		String comments = "Slide with ID ->" + slide.getId() + " has been deleted";

		Session session = dao.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ppt.getSlides().remove(slide);
			dao.delete(slide);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		Task task = new TaskDAO().findByItemId(ppt.getLesson().getId()).get(0);

		request.setAttribute("message_success", "Slide has been deleted successfully ");
		
		CMSRegistry.addTaskLogEntry(request, "DRAFT", comments, ppt.getLesson().getTaskID(), "LESSON", ppt.getLesson().getId(), "Slide is deleted");
	
		response.sendRedirect("/content/edit_lesson?task_id=" + task.getId());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
