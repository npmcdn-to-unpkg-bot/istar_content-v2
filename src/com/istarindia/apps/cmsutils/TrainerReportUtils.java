/**
 * 
 */
package com.istarindia.apps.cmsutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.istarindia.apps.cmsutils.reports.IStarColumn;
import com.istarindia.apps.cmsutils.reports.ReportUtils;
import com.istarindia.apps.dao.AssessmentOption;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.QuestionDAO;
import com.istarindia.apps.dao.RecruiterCompany;
import com.istarindia.apps.dao.RecruiterCompanyDAO;
import com.istarindia.apps.dao.Vacancy;
import com.istarindia.apps.dao.VacancyDAO;
import com.istarindia.apps.dao.VacancyWorkflow;
import com.istarindia.apps.dao.VacancyWorkflowDAO;
import com.istarindia.apps.dao.VacanyStatus;
import com.istarindia.apps.dao.VacanyStatusDAO;

/**
 * @author vaibhav
 *
 */
public class TrainerReportUtils {

	public StringBuffer getStudentsDetailedResultCourse(int studentID, int assessment_id){
		StringBuffer out = new StringBuffer();
		
		HashMap<LearningObjective, Integer> los = new HashMap<>();
		HashMap<String, Integer> cos = new HashMap<>();
		String sql = "SELECT learning_objective_question.learning_objectiveid FROM learning_objective_question "
				+ " WHERE learning_objective_question.questionid IN ( SELECT assessment_question.questionid FROM "
				+ " assessment_question WHERE assessment_question.assessmentid = "+assessment_id+" ) ";
		ReportUtils rutils = new ReportUtils();
		ArrayList<IStarColumn> keys = new ArrayList();
		IStarColumn kk = new IStarColumn();
		kk.setName("learning_objectiveid");
		kk.setDisplayName("learning_objectiveid");
		
		keys.add(kk);
		ArrayList<ArrayList<String>> items = rutils.getReportData(sql, keys, new HashMap());
		
		for (ArrayList<String> arrayList : items) {
			String loID = arrayList.get(0);
			LearningObjective looo = new LearningObjectiveDAO().findById(Integer.parseInt(loID));
			los.put(looo,0);
			cos.put(looo.getSubject(), 0);
		}
		
		
		for (String arrayList : cos.keySet()) {
			out.append("<tr><td>" + arrayList + "</td>");
			out.append("<td>" + cos.get(arrayList) + "</td>");
			out.append("</tr>");
		}
		
		
		
		return out;
	}
	
	public StringBuffer getStudents() {
		StringBuffer out = new StringBuffer();
		VacanyStatusDAO vDAO = new VacanyStatusDAO();
		List<VacanyStatus> listOfTest = vDAO.findAll();

		for (VacanyStatus vacanyStatus : listOfTest) {

			try {
				int studentID = vacanyStatus.getUserId();
				IstarUser user = (new IstarUserDAO()).findById(studentID);
				String name = user.getName();
				String email = user.getEmail();

				VacancyWorkflowDAO wfDAO = new VacancyWorkflowDAO();
				VacancyWorkflow vwfd = wfDAO.findById(vacanyStatus.getVacancyWorkflowId());

				Vacancy v = (new VacancyDAO()).findById(vwfd.getVacancyId());
				
				
				// FInd Data in reports to check wether he has completed or not
				// String sql1, ArrayList<IStarColumn> keys, HashMap<String, String>
				// conditions

				String sql = "select report.score as score, assessment.number_of_questions as number_of_questions from report, "
						+ "assessment where report.assessment_id=" + vwfd.getAssessmentId() + "   and report.user_id="
						+ studentID + " and assessment.id=" + vwfd.getAssessmentId();
				ReportUtils rutils = new ReportUtils();
				ArrayList<IStarColumn> keys = new ArrayList();
				IStarColumn kk = new IStarColumn();
				kk.setName("score");
				kk.setDisplayName("score");

				IStarColumn kk1 = new IStarColumn();
				kk1.setName("number_of_questions");
				kk1.setDisplayName("number_of_questions");
				keys.add(kk);
				keys.add(kk1);
				ArrayList<ArrayList<String>> items = rutils.getReportData(sql, keys, new HashMap());

				String completionStatus = "NOT COMPLETED";
				int score = 0;
				int total = 0;
				if (items.size() != 0) {
					completionStatus = "COMPLETED";
					try {
						score = Integer.parseInt(items.get(0).get(0));
						total = Integer.parseInt(items.get(0).get(1));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				out.append("<tr><td>" + studentID + "</td>");
				out.append("<td>" + name + "</td>");
				out.append("<td>" + email + "</td>");
				out.append("<td>" + v.getProfileTitle() + " --- " + vwfd.getAssessmentId() + "</td>");

				out.append("<td>" + completionStatus + "</td>");
				out.append("<td><a href='detailed_result.jsp?student_id=" + studentID + "&assessment_id="
						+ vwfd.getAssessmentId() + "' >View Details Results " + score + " / " + total + "</a></td>");
				out.append("<td><a href='detailed_result_course.jsp?student_id=" + studentID + "&assessment_id="
						+ vwfd.getAssessmentId() + "' >View Details Results " + score + " / " + total + "</a></td>");
				out.append("</tr>");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return out;
	}

	public StringBuffer getStudentsDetailedResult(int studentID, int assessment_id) {
		StringBuffer out = new StringBuffer();

		String sql = "SELECT 	student_assessment.question_id AS idd , assessment.number_of_questions  as number_of_questions  , "
				+ "	student_assessment.correct as correct FROM 	assessment, 	student_assessment WHERE "
				+ "	student_assessment.student_id = "+studentID+" AND student_assessment.assessment_id = "+assessment_id+"  "
						+ "AND assessment.ID = "+assessment_id;

		ReportUtils rutils = new ReportUtils();
		ArrayList<IStarColumn> keys = new ArrayList();

		IStarColumn kk = new IStarColumn();

		kk.setName("idd");
		kk.setDisplayName("idd");

		IStarColumn kk1 = new IStarColumn();
		kk1.setName("number_of_questions");
		kk1.setDisplayName("number_of_questions");

		IStarColumn kk2 = new IStarColumn();
		kk2.setName("correct");
		kk2.setDisplayName("correct");

		keys.add(kk);
		keys.add(kk1);
		keys.add(kk2);
		ArrayList<ArrayList<String>> items = rutils.getReportData(sql, keys, new HashMap());
		for (ArrayList<String> arrayList : items) {

			QuestionDAO qDAO = new QuestionDAO();
			Question q = qDAO.findById(Integer.parseInt(arrayList.get(0)));
			out.append("<tr>");
			out.append("<td>" + arrayList.get(0) + "</th>");
			out.append("<td>" + q.getQuestionText() + "</th>");

			String rightOption = "NONE";
			for (AssessmentOption string : q.getAssessmentOptions()) {
				try {
					if (string.getMarkingScheme() == 1) {
						rightOption = string.getText();
						// out.append("<td>"+string.getText()+"</th>");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					/// out.append("<td>- </th>");
				}
			}

			out.append("<td>" + rightOption + "</th>");

			//out.append("<td>His/her Answer</th>");
			System.err.println(arrayList.get(2));
			if (arrayList.get(2).equalsIgnoreCase("true")) {
				out.append("<td>CORRECT</th>");
			} else
				out.append("<td>In-CORRECT</th>");

		}
		out.append("</tr>");
	return out;

	}

	public static void main(String[] args) {
		TrainerReportUtils t = new TrainerReportUtils();
		t.getStudents();
	}
	
	
	
	public StringBuffer getCompanies(){
		StringBuffer out = new StringBuffer();

		RecruiterCompanyDAO dao = new RecruiterCompanyDAO();
		List<RecruiterCompany> items = dao.findAll();
		
		for (RecruiterCompany recruiterCompany : items) {
			out.append("<tr><td>" + recruiterCompany.getId() + "</td>");
			out.append("<td>" + recruiterCompany.getName() + "</td>");
			out.append("<td><img style='height:30px' src='"+recruiterCompany.getImage()+"'/></td>");
			out.append("<td> <a href='edit_company.jsp?id="+recruiterCompany.getId()+"' > Edit</td>");
			out.append("<td> <a href='view_vacancies.jsp?id="+recruiterCompany.getId()+"' > View Vacancies</td>");
			out.append("</tr>");
		}
		
	
		return out;
	
	}
	
	public StringBuffer getVacancies(int id){
		StringBuffer out = new StringBuffer();

		VacancyDAO dao = new VacancyDAO();
		List<Vacancy> items = dao.findAll();
		
		for (Vacancy vacancy : items) {
			out.append("<tr><td>" + vacancy.getId() + "</td>");
			out.append("<td>" + vacancy.getProfileTitle() + "</td>");
			out.append("<td>" + vacancy.getDescription() + "</td>");
			out.append("<td> <a href='edit_vacancies.jsp?id="+vacancy.getId()+"' > Edit Vacancy</td>");
			out.append("</tr>");
		}
		
	
		return out;
	
	}
	
	
	public StringBuffer getAllQuestions(){
		StringBuffer out = new StringBuffer();
		
		QuestionDAO dao = new QuestionDAO();
		List<Question> items = dao.findAll();
		
		for (Question q : items) {
			out.append("<tr><td><a href='edit_question.jsp?id=" + q.getId() + "'> " + q.getId() + "</a></td>");
			out.append("<td style='max-width: 100px !important; word-break: break-all;'>" + q.getQuestionText() + "</td>");
			
			Set<AssessmentOption> opts = q.getAssessmentOptions();
			
			for (AssessmentOption assessmentOption : opts) {
				try {
					if(assessmentOption.getMarkingScheme() ==1) {
						out.append("<td style='max-width: 100px !important; word-break: break-all;background: green;color: white;'>" + assessmentOption.getText() + "</td>");
					} else {
						out.append("<td style='max-width: 100px !important; word-break: break-all;'>" + assessmentOption.getText() + "</td>");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			
			
			
			
			
			out.append("</tr>");
		}
		
		return out;
	}
}
