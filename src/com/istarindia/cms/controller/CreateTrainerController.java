package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.Address;
import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.AssessmentEventDAO;
import com.istarindia.apps.dao.Event;
import com.istarindia.apps.dao.EventDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Organization;
import com.istarindia.apps.dao.QuestionDAO;
import com.istarindia.apps.dao.Student;
import com.istarindia.apps.dao.StudentInvites;
import com.istarindia.apps.dao.StudentInvitesDAO;
import com.istarindia.apps.dao.Trainer;
import com.istarindia.apps.dao.TrainerDAO;
import com.istarindia.apps.dao.Vacancy;
import com.istarindia.apps.dao.VacancyDAO;
import com.istarindia.apps.dao.VacancyWorkflow;
import com.istarindia.apps.dao.VacancyWorkflowDAO;
import com.istarindia.apps.dao.VacanyStatus;
import com.istarindia.apps.dao.VacanyStatusDAO;
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
		String assessments = null;
		
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
			assessments = request.getParameter("assessments");
		}
		
		Integer trainerID = service.createTrainer(email, gender, name, password, (double)1, (double)1, 1, "addressline1", "addressline2", 1, assessments);
		
		
		String[]  vacancy_id = request.getParameterValues("vacancy_id");
		
		for(String vac : vacancy_id)
		{
			int vac_id = Integer.parseInt(vac);
			VacancyWorkflowDAO dao = new VacancyWorkflowDAO();
			
			VacancyWorkflow vw = dao.findById(Integer.parseInt(vac));
			
			EventDAO evdao = new EventDAO();
			Event event = new Event();
			event.setType("RECRUITER_ASSESSMENT");
			event.setUpdatedat(new Date());
			event.setCreatedat(new Date());
			event.setEventhour(0);
			event.setEventminute(new AssessmentDAO().findById(vw.getAssessmentId()).getAssessmentdurationminutes());
			event.setActor_id(trainerID);
			IstarUser user = ((IstarUser)request.getSession().getAttribute("user"));
			event.setCreator_id(user.getId());
			
				Session s = null;
		        Transaction t = null;
		        try {
		            s = evdao.getSession();
		            t = s.beginTransaction();
		           
		           evdao.save(event);
		            t.commit();
		        } catch (HibernateException e) {
		            if (t != null) {
		                t.rollback();
		            }
		            e.printStackTrace();
		        } finally {
		            s.close();
		        }
			
		        
		        
		        VacanyStatusDAO vsdao = new VacanyStatusDAO();
		        VacanyStatus vs = new VacanyStatus();
		        vs.setEvent_id(event.getId());
		        vs.setUserId(trainerID);
		        vs.setVacancyWorkflowId(vw.getId());
		        vs.setStatus(vw.getStage());	
			
		        Session s1 = null;
		        Transaction t1 = null;
		        try {
		            s1 = vsdao.getSession();
		            t1 = s1.beginTransaction();		           
		            vsdao.save(vs);
		            t1.commit();
		        } catch (HibernateException e) {
		            if (t1 != null) {
		                t1.rollback();
		            }
		            e.printStackTrace();
		        } finally {
		            s1.close();
		        }
			
		}
		
	
		if (true) {
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
