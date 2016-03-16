package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.SlideDAO;
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
		//http://127.0.0.1:8080/content/delete_slide?ppt_id=6&slide_id=25
		Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
		SlideDAO dao = new SlideDAO();
		Slide slide= dao.findById(Integer.parseInt(request.getParameter("slide_id")));
		Integer task_id = Integer.parseInt(request.getParameter("task_id"));

		
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
		
		
		response.sendRedirect("/content/edit_lesson?lesson_id=" + ppt.getLesson().getId()+"&task_id="+task_id);
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
