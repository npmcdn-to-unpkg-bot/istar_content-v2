package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.cms.lessons.CMSList;
import com.istarindia.cms.lessons.CMSTextItem;
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
			service.addTextSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"),  ppt, request.getParameter("title"), request.getParameter("slideTransition"), 
					request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"));
			break;
		case "ONLY_TITLE_PARAGRAPH":
			service.addTextAndParaGraphSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"),  ppt, request.getParameter("title"), request.getParameter("slideTransition"), 
					request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), request.getParameter("paragraph"));
			break;
			
		case "ONLY_TITLE_LIST":
			CMSList list = getNewList(request);
			service.addTextListSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"),  ppt, request.getParameter("title"), request.getParameter("slideTransition"), 
					request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), list);
			break;
		default:
			
		case "ONLY_TITLE_LIST":
			CMSList list = getNewList(request);
			service.addTextListSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"),  ppt, request.getParameter("title"), request.getParameter("slideTransition"), 
					request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), list);
			break;
		default:
			break;
		}
		
		response.sendRedirect("/content/edit_lesson?lesson_id="+ppt.getLesson().getId());
		
	}

	private CMSList getNewList(HttpServletRequest request) {
		CMSList list = new CMSList();
		list.setItems(new ArrayList<CMSTextItem>());
		for (Object key : request.getParameterMap().keySet()) {
			if(key.toString().startsWith("list_item")) {
				System.out.println(key.toString());
				CMSTextItem item = new CMSTextItem(request.getParameter(key.toString()));
				list.getItems().add(item);
			}
		}
		
		return list;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
