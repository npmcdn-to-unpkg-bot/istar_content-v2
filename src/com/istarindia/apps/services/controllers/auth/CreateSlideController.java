package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.cms.lessons.SlideService;

/**
 * Servlet implementation class CreateSlideController
 */
@WebServlet("/create_slide")
public class CreateSlideController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSlideController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String template = request.getParameter("template");
		SlideService service = new SlideService();
		Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
		switch (template) {
		case "ONLY_TITLE":
			service.addTextSlideToLesson(ppt, request.getParameter("title"), request.getParameter("slideTransition"), 
					request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"));
			break;
		case "ONLY_TITLE_PARAGRAPH":
			service.addTextAndParaGraphSlideToLesson(ppt, request.getParameter("title"), request.getParameter("slideTransition"), 
					request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), request.getParameter("paragraph"));
			break;
		default:
			break;
		}
		
		response.sendRedirect("/content/edit_lesson?lesson_id="+ppt.getLesson().getId());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
