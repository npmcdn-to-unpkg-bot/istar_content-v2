package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.AssessmentOption;
import com.istarindia.apps.dao.AssessmentOptionDAO;
import com.istarindia.apps.dao.AssessmentQuestion;
import com.istarindia.apps.dao.AssessmentQuestionDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.QuestionDAO;
import com.istarindia.apps.dao.Report;
import com.istarindia.apps.dao.ReportDAO;
import com.istarindia.apps.services.ReportService;
import com.istarindia.apps.services.AssessmentOptionService;
import com.istarindia.apps.services.AssessmentQuestionService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class CreateSlideController
 */
@WebServlet("/assessment_play")
public class AssessmentPlayController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AssessmentPlayController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		printParams(request);
		int assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
		Assessment assessment = new AssessmentDAO().findById(assessment_id);
		ReportService service = new ReportService();
		IstarUser user = (IstarUser)request.getSession().getAttribute("user");
		
		if(request.getParameterMap().containsKey("question_id")) {
			int question_id = Integer.parseInt(request.getParameter("question_id"));
			Report report = service.getAssessmentReportOfUser(user.getId(), assessment);
			int progress = question_id;
	
			Question question = new QuestionDAO().findById(question_id);
			
			int score = -1;
			AssessmentOptionService assessmentOptionService = new AssessmentOptionService();
			ArrayList<Integer> optionIds = new ArrayList<>();
			optionIds = assessmentOptionService.getOptionIdsForQuestion(question_id);
			String[] selected = request.getParameterValues("selected");
			
			switch(assessment.getAssessmentType()) {
				case "ADAPTIVE":
					System.out.println("ADAPTIVE");
					break;
				
				case "TREE":
					System.out.println("TREE");
					break;
					
				case "RANDOM":
					System.out.println("RANDOM");
					break;
					
				default: //STATIC
					System.out.println("STATIC");
					AssessmentQuestionDAO dao1 = new AssessmentQuestionDAO();

					//TODO Score
					int total = 0;
					for (int i=0; i<5; i++){
						if(new AssessmentOptionDAO().findById(optionIds.get(i)).getMarkingScheme()!=null){
							total++;
							if(!Arrays.asList(selected).contains(optionIds.get(i).toString())){
								score = 0 ;
							}
						}
						else {
							if(Arrays.asList(selected).contains(optionIds.get(i).toString())){
								score = 0 ;
							}
						}
					}
					if (score<0) score = 1;
					report.setScore(score+report.getScore());
					
					//TODO Progress
					AssessmentQuestionService assessmentQuestionService = new AssessmentQuestionService();
					progress = assessmentQuestionService.getNextQuestionStatic(assessment_id, question_id);
					
					report.setProgress(progress);
					
					ReportDAO dao2 = new ReportDAO(); 
					Session session = dao2.getSession();
					Transaction tx = null;
					try {
						tx = session.beginTransaction();
						dao2.attachDirty(report);
						tx.commit();
					} catch (HibernateException e) {
						if (tx != null)
							tx.rollback();
						e.printStackTrace();
					} finally {
						session.close();
					}
					break;
			}
			request.setAttribute("question_id", progress);
		}
		else {
			
			if (service.getAssessmentReportOfUser(user.getId(), assessment) == null){
				//Move it to service
				Report report = new Report();
				System.out.println();
				report.setAssessment(assessment);
				report.setUserId(user.getId());
				report.setScore(0);
				
				IstarUserDAO dao1 = new IstarUserDAO();
				Session session1 = dao1.getSession();
				String sql = "select * from assessment_question where assessmentid="+assessment_id+" and order_id=1";
				SQLQuery query = session1.createSQLQuery(sql);
				query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				List<HashMap<String, Object>> result = query.list();
				int init_question_id = (int) result.get(0).get("questionid");
				report.setProgress(init_question_id);
				
				ReportDAO dao = new ReportDAO();
				Session session = dao.getSession();
				Transaction tx = null;
				try {
					tx = session.beginTransaction();
					dao.save(report);
					tx.commit();
				} catch (HibernateException e) {
					if (tx != null)
						tx.rollback();
					e.printStackTrace();
				} finally {
					session.close();
				}
				report.setId(report.getId());
			}
			else {
				
			}
		}

		request.setAttribute("assessment_id", assessment_id);
		//response.sendRedirect("/content/lesson/assessment_play.jsp?assessment_id=" + assessment_id);
		request.getRequestDispatcher("/lesson/assessment_play.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
