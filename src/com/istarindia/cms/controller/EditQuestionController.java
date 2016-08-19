/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istarindia.cms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.QuestionDAO;
import com.istarindia.apps.services.OptionService;
import com.istarindia.apps.services.QuestionService;

@WebServlet("/edit_question")
public class EditQuestionController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, IOException {

		int assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
		int question_id = Integer.parseInt(request.getParameter("question_id"));
		String[] learningObjectives = null;
		StringBuffer lo_ids = new StringBuffer();
		StringBuffer sql = new StringBuffer();

		learningObjectives = (String[]) request.getParameterMap().get("selected_items");
		for (int i = 0; learningObjectives != null && i < learningObjectives.length; i++) {
			lo_ids.append(learningObjectives[i]);
			if (i < learningObjectives.length - 1) {
				lo_ids.append(',');
			}
		}

		if (!request.getParameterMap().containsKey("only_learning_objevtives")) {
			sql.append(" delete from learning_objective_question where questionid = " + question_id);

			sql.append("; INSERT INTO learning_objective_question (learning_objectiveid, questionid) " + "SELECT lo. ID, " + question_id + " FROM learning_objective lo WHERE	lo.id IN (" + lo_ids + ")");

			Question question = (new QuestionDAO()).findById(question_id);
			String question_text = request.getParameter("question_text");
			String question_type = request.getParameter("question_type");
			int difficulty_level = Integer.parseInt(request.getParameter("difficulty_level").toString());
			int duration_in_sec = Integer.parseInt(request.getParameter("duration_in_sec"));

			QuestionService service = new QuestionService();
			service.updateQuestion(question_id, question_text, question_type, difficulty_level, duration_in_sec);

			OptionService opService = new OptionService();

			String[] answers = request.getParameterValues("answers");
			Integer[] optionValue = new Integer[5];

			if (answers != null) {
				for (int i = 0; i < 5; i++) {
					optionValue[i] = null;
				}
				for (int j = 0; j < answers.length; j++) {
					optionValue[Integer.parseInt(answers[j]) - 1] = 1;
				}
			}

			opService.updateNewOption(Integer.parseInt(request.getParameter("option1_id")), request.getParameter("option1"), question, optionValue[0]);
			opService.updateNewOption(Integer.parseInt(request.getParameter("option2_id")), request.getParameter("option2"), question, optionValue[1]);
			opService.updateNewOption(Integer.parseInt(request.getParameter("option3_id")), request.getParameter("option3"), question, optionValue[2]);
			opService.updateNewOption(Integer.parseInt(request.getParameter("option4_id")), request.getParameter("option4"), question, optionValue[3]);
			opService.updateNewOption(Integer.parseInt(request.getParameter("option5_id")), request.getParameter("option5"), question, optionValue[4]);
		
		} else if (request.getParameter("only_learning_objevtives").toString().equalsIgnoreCase("true")){
			sql.append("INSERT INTO learning_objective_question (learning_objectiveid, questionid) " + "SELECT lo. ID, " + question_id + " FROM learning_objective lo WHERE	lo.id IN (" + lo_ids + ")");
		}
		
		System.err.println(sql.toString());

		try {
			IstarUserDAO dao = new IstarUserDAO();

			Session session = dao.getSession();
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			int result = query.executeUpdate();
			session.beginTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("/content/lesson/edit_assessment.jsp?assessment_id=" + assessment_id + "&question_id=" + question_id);
	}

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
