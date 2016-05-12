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

import com.istarindia.apps.dao.Address;
import com.istarindia.apps.dao.Organization;
import com.istarindia.apps.dao.QuestionDAO;
import com.istarindia.apps.dao.Trainer;
import com.istarindia.apps.dao.TrainerDAO;
import com.istarindia.apps.services.TrainerService;

/**
 * Servlet implementation class CreateTrainerController
 */
@WebServlet("/create_trainer")
public class CreateTrainerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTrainerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TrainerService service = new TrainerService();
		String name = new String();
		String gender = new String();
		String email =  new String();
		String password =  new String();
		String[] assessments = null;
		
		if (request.getParameterMap().containsKey("name")){
			name = request.getParameter("name");
		} else  {
			name = "Trainer";
		}
		if (request.getParameterMap().containsKey("gender")){
			gender = request.getParameter("gender");
		} else  {
			gender = "Male";
		}
		if (request.getParameterMap().containsKey("email")){
			email = request.getParameter("email");
		} else  {
			email = "noreply@istarindia.com";
		}
		if (request.getParameterMap().containsKey("password")){
			password = request.getParameter("password");
		} else  {
			password = "password";
		}
		if (request.getParameterMap().containsKey("assessments")){
			assessments = request.getParameterValues("assessments");
		}
		
		Boolean flag = service.createTrainer(email, gender, name, password, (double)1, (double)1, 1, "addressline1", "addressline2", 1, assessments);
		if (flag) {
			request.setAttribute("message_success", "User created successfully!");
			request.getRequestDispatcher("/trainer/create_user.jsp").forward(request, response);
		} else {
			request.setAttribute("message_failure", "Something missing!");
			request.getRequestDispatcher("/trainer/create_user.jsp").forward(request, response);
		}
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
