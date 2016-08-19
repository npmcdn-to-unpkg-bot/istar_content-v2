package com.istarindia.cms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.SlideDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.CourseService;

/**
 * Servlet implementation class DuplicateSlideController
 */
@WebServlet("/duplicate_slide")
public class DuplicateSlideController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DuplicateSlideController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int presentation_id = Integer.parseInt(request.getParameter("ppt_id").toString());
		int slide_id = Integer.parseInt(request.getParameter("slide_id").toString());
		String order_holder_string = request.getParameter("order_holder").toString();
		
		Presentaion ppt = (new PresentaionDAO()).findById(presentation_id);
		SlideDAO dao = new SlideDAO();
		Slide slide= dao.findById(slide_id);
		Task task = new TaskDAO().findById(ppt.getLesson().getTask().getId());
		
		String comments = "";
		
		CourseService service = new CourseService();
		service.replicateSlide(presentation_id, slide_id);
		request.setAttribute("message_success", "Slide has been duplicate successfully ");
			
		comments = "Slide with ID ->" + slide.getId() + " has been duplicated";
		CMSRegistry.addTaskLogEntry(request, "DRAFT", comments, ppt.getLesson().getTask().getId(), "LESSON", ppt.getLesson().getId(), "Slide is duplicated");

		String slidesOrder[] = order_holder_string.split(",");
		service.updateSlideOrder(slide_id, slidesOrder);
		
		response.sendRedirect("/content/edit_lesson?task_id=" + task.getId());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
