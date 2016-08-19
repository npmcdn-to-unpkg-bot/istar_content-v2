package com.istarindia.cms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.services.LearningObjectiveService;

/**
 * Servlet implementation class LearningObjectiveController
 */
@WebServlet("/lo_controller")
public class LearningObjectiveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LearningObjectiveController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LearningObjectiveService service = new LearningObjectiveService();
		String title = request.getParameter("title");
		String subject = request.getParameter("subject");
		switch (request.getParameter("action").toString()) {
		case "create":
			if (service.createLearningObjectives(title, subject, "OTHERS")) {
				request.setAttribute("message_success", "New learing objective has been created!");
			} else {
				request.setAttribute("message_failure",
						"Another Learing objective exists with same name! Choose new name");
			}
			break;

		case "update":
			int lo_id = Integer.parseInt(request.getParameter("lo_id").toString());
			if (service.updateLearningObjectives(lo_id, title, subject, "OTHERS")) {
				request.setAttribute("message_success", "Learing objective details have been updated!");
			} else {
				request.setAttribute("message_failure",
						"Another Learing objective exists with the same name! Kindly choose a new one");
				request.getRequestDispatcher("/content_admin/course_learning_objectives.jsp?lo_id=" + lo_id)
						.forward(request, response);
			}
			break;

		default:
			request.setAttribute("message_failure", "Something went wrong! Please try again");
			request.getRequestDispatcher("/content_admin/course_learning_objectives.jsp").forward(request, response);
			break;

		}

		request.getRequestDispatcher("/content_admin/course_learning_objectives.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
