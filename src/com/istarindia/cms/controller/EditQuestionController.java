/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istarindia.cms.controller;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.AssessmentOption;
import com.istarindia.apps.dao.AssessmentOptionDAO;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.QuestionDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.AssessmentOptionService;
import com.istarindia.apps.services.OptionService;
import com.istarindia.apps.services.QuestionService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    	
    	int question_id = Integer.parseInt(request.getParameter("question_id"));
    	Question question = (new QuestionDAO()).findById(question_id);
    	int assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
    	String question_text = request.getParameter("question_text");
    	String question_type = request.getParameter("question_type");
    	int difficulty_level = Integer.parseInt(request.getParameter("difficulty_level"));
    	int duration_in_sec = Integer.parseInt(request.getParameter("duration_in_sec"));
    	
    	QuestionService service = new QuestionService();
    	service.updateQuestion(question_id, question_text, question_type, difficulty_level,duration_in_sec);
    	
    	OptionService opService = new OptionService();

		String[] answers = request.getParameterValues("answers");
		Integer[] optionValue = new Integer[5];
		
		if(answers!=null){
			for (int i= 0 ; i<5;i++){
				optionValue[i] = null;
			}
			for (int j = 0; j < answers.length; j++){
				optionValue[Integer.parseInt(answers[j])-1] = 1;
			}
		}

		opService.updateNewOption(Integer.parseInt(request.getParameter("option1_id")), request.getParameter("option1"), question, optionValue[0]);
		opService.updateNewOption(Integer.parseInt(request.getParameter("option2_id")), request.getParameter("option2"), question, optionValue[1]);
		opService.updateNewOption(Integer.parseInt(request.getParameter("option3_id")), request.getParameter("option3"), question, optionValue[2]);
		opService.updateNewOption(Integer.parseInt(request.getParameter("option4_id")), request.getParameter("option4"), question, optionValue[3]);
		opService.updateNewOption(Integer.parseInt(request.getParameter("option5_id")), request.getParameter("option5"), question, optionValue[4]);

		response.sendRedirect("/content/lesson/edit_assessment.jsp?assessment_id=" + assessment_id);
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
