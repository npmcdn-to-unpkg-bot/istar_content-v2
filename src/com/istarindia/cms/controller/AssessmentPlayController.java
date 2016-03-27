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
import com.istarindia.apps.dao.StudentAssessment;
import com.istarindia.apps.dao.StudentAssessmentDAO;
import com.istarindia.apps.services.ReportService;
import com.istarindia.apps.services.StudentAssessmentService;
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
			
			Boolean[] options = new Boolean[5];
			for (int i=0; i < 5; i++ ){
				if(Arrays.asList(selected).contains(((Integer)(i+optionIds.get(0))).toString())){
					options[i] = true;
				}
				else options[i] = false;
			}
			
			StudentAssessment studentAssessment = new StudentAssessment();
			studentAssessment.setQuestion(question);
			studentAssessment.setStudentId(user.getId());
			studentAssessment.setAssessment(assessment);
			studentAssessment.setOption1(options[0]);
			studentAssessment.setOption2(options[1]);
			studentAssessment.setOption3(options[2]);
			studentAssessment.setOption4(options[3]);
			studentAssessment.setOption5(options[4]);
			
			StudentAssessmentDAO studentAssessmentDAO = new StudentAssessmentDAO(); 
			Session sAsession = studentAssessmentDAO.getSession();
			Transaction sAtx = null;
			try {
				sAtx = sAsession.beginTransaction();
				studentAssessmentDAO.save(studentAssessment);
				sAtx.commit();
			} catch (HibernateException e) {
				if (sAtx != null)
					sAtx.rollback();
				e.printStackTrace();
			} finally {
				sAsession.close();
			}
			
			switch(assessment.getAssessmentType()) {
				case "TREE":
					System.out.println("TREE");
					break;
					
				case "RANDOM":
					System.out.println("RANDOM");
					break;
					
				case "ADAPTIVE":
					System.out.println("ADAPTIVE");
					
					//TODO Score
					
					//TODO Progress
					IstarUserDAO buserdao = new IstarUserDAO();
					Session bsession = buserdao.getSession();
					AssessmentQuestionDAO aqdao = new AssessmentQuestionDAO();
					AssessmentQuestion aq = aqdao.findByMapping(assessment, question);
					String bsql = "select * from assessment_question where assessmentid="+assessment_id+" and order_id="+1+aq.getOrderId()+"";
					SQLQuery bquery = bsession.createSQLQuery(bsql);
					bquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
					List<HashMap<String, Object>> bresult = bquery.list();
					int tempq =0; 
					Question tq = new Question();
					QuestionDAO tqd = new QuestionDAO();
					int prev = report.getProgress();
					if (bresult != null ){
						for (HashMap<String, Object> bobject : bresult) {
							tempq = (int) bobject.get("question_id");
							tq = tqd.findById(tempq);
							if(tq.getDifficultyLevel()==question.getDifficultyLevel()){
								report.setProgress(tq.getId());
								break;
							}
						}
					} 
					if ( report.getProgress() == prev){
						
						//Evaluate all the selections in this level
						StudentAssessmentService sAservice = new StudentAssessmentService();
						Boolean ev = sAservice.performanceAtThisLevel(user.getId(), assessment_id, question.getDifficultyLevel());
						
						//If more than half are correct and score<difficulty(report) then move up and update score
						if(ev==true) {
							prev = report.getScore();
							if(report.getScore()<question.getDifficultyLevel()){
								for (int i=question.getDifficultyLevel()+1;i<=10;i++){
									IstarUserDAO cuserdao = new IstarUserDAO();
									Session csession = cuserdao.getSession();
									String csql = "select * from assessment_question where assessmentid="+assessment_id+" and order_id="+1+"";
									SQLQuery cquery = csession.createSQLQuery(csql);
									cquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
									List<HashMap<String, Object>> cresult = bquery.list();
									for (HashMap<String, Object> hashMap : cresult) {
										tq = tqd.findById((int)hashMap.get("questionid"));
										if(tq.getDifficultyLevel()==i){
											report.setScore(question.getDifficultyLevel());
											report.setProgress((int)hashMap.get("questionid"));
										}
									}
								}
								if (prev == report.getScore()) {
									report.setProgress(0);
								}
							}
						//If more then half are correct and score>difficulty(report) then end game and show difficulty(report)
							else {
								report.setProgress(0);
							}
						}
						//If more than half are wrong and score<difficulty(report) then end game and show score
						else {
							prev = report.getScore();
							if(report.getScore()<question.getDifficultyLevel()){
								report.setProgress(0);
							}
						//If more than half are wrong and score>difficulty(report) then move down and update score
							else {
								for (int i=question.getDifficultyLevel()-1;i>0;i--){
									IstarUserDAO euserdao = new IstarUserDAO();
									Session esession = euserdao.getSession();
									String esql = "select * from assessment_question where assessmentid="+assessment_id+" and order_id="+1+"";
									SQLQuery equery = esession.createSQLQuery(esql);
									equery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
									List<HashMap<String, Object>> eresult = equery.list();
									for (HashMap<String, Object> hashMap : eresult) {
										tq = tqd.findById((int)hashMap.get("questionid"));
										if(tq.getDifficultyLevel()==i){
											report.setScore(question.getDifficultyLevel());
											report.setProgress((int)hashMap.get("questionid"));
										}
									}
								}
								if (prev == report.getScore()) {
									report.setProgress(0);
								}
							}
						}
					}

					ReportDAO dao4 = new ReportDAO(); 
					Session session4 = dao4.getSession();
					Transaction tx4 = null;
					try {
						tx4 = session4.beginTransaction();
						dao4.attachDirty(report);
						tx4.commit();
					} catch (HibernateException e) {
						if (tx4 != null)
							tx4.rollback();
						e.printStackTrace();
					} finally {
						session4.close();
					}
					
					break;
				
				default: //STATIC
					System.out.println("STATIC");
					AssessmentQuestionDAO dao1 = new AssessmentQuestionDAO();

					// Score
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
					
					// Progress
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

				Report report = new Report();
				System.out.println();
				report.setAssessment(assessment);
				report.setUserId(user.getId());
				report.setScore(0);
				
				switch(assessment.getAssessmentType()) {

					case "TREE":
						System.out.println("TREE");
						break;
						
					case "RANDOM":
						System.out.println("RANDOM");
						break;
						
					case "ADAPTIVE":
						System.out.println("ADAPTIVE");
						
						IstarUserDAO userdao = new IstarUserDAO();
						Session asession = userdao.getSession();
						String asql = "select * from assessment_question where assessmentid="+assessment_id+" and order_id=1";
						SQLQuery aquery = asession.createSQLQuery(asql);
						aquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						List<HashMap<String, Object>> aresult = aquery.list();
						int temp[] =  new int[100];
						int i=0;
						int qid= 0 ;
						Question q = new Question();
						QuestionDAO qdao = new QuestionDAO();
						for (HashMap<String, Object> aobject : aresult) {
							qid=(int) aobject.get("questionid");
							System.out.println(qid);
							q = qdao.findById(qid);
							temp[i]=q.getDifficultyLevel();
							System.out.println(i+"--------------"+temp[i]);
							i++;
						}
						int j = 0;
					    for( int ii=0;  ii<temp.length;  ii++ )
					    {
					        if (temp[ii] != 0)
					            temp[j++] = temp[ii];
					    }
					    int [] newArray = new int[j];
					    System.arraycopy( temp, 0, newArray, 0, j );
						Arrays.sort(newArray);
						int progress = 0;
						if (newArray.length==0) {
							progress=0;
						} else if (newArray.length==1) {
							progress=newArray[0];
							System.out.println(progress);
						} else {
							progress=newArray[newArray.length/2];
						}
						System.out.println(newArray.length+"   ppppppppppppppppppppppppppppppppppppppppppppppppp   "+progress);
						for (HashMap<String, Object> zobject : aresult) {
							qid=(int) zobject.get("questionid");
							q = qdao.findById(qid);
							System.out.println(qid);
							System.out.println(newArray[0]);
							System.out.println(q.getDifficultyLevel());
							System.out.println(progress);
							if (progress == q.getDifficultyLevel()){
								report.setProgress(qid);
								break;
							}
						}
						
						break;
					
					default: //STATIC
						System.out.println("STATIC");
						
						IstarUserDAO dao1 = new IstarUserDAO();
						Session session1 = dao1.getSession();
						String sql = "select * from assessment_question where assessmentid="+assessment_id+" and order_id=1";
						SQLQuery query = session1.createSQLQuery(sql);
						query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						List<HashMap<String, Object>> result = query.list();
						int init_question_id = (int) result.get(0).get("questionid");
						report.setProgress(init_question_id);
					
						break;
				}

				ReportDAO rdao = new ReportDAO();
				Session rsession = rdao.getSession();
				Transaction rtx = null;
				try {
					rtx = rsession.beginTransaction();
					rdao.save(report);
					rtx.commit();
				} catch (HibernateException e) {
					if (rtx != null)
						rtx.rollback();
					e.printStackTrace();
				} finally {
					rsession.close();
				}
				report.setId(report.getId());
			}
			else {
				System.out.print("This is a strange place to end up in!!");
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
