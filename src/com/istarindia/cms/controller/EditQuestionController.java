/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istarindia.cms.controller;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.AssessmentOptionService;
import com.istarindia.apps.services.OptionService;
import com.istarindia.apps.services.QuestionService;
import java.io.IOException;
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

/**
 *
 * @author Kunal Chakravertti
 */
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
    	int assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
    	String question_text = request.getParameter("question_text");
    	String question_type = request.getParameter("question_type");
    	int difficulty_level = Integer.parseInt(request.getParameter("difficulty_level"));
    	int duration_in_sec = Integer.parseInt(request.getParameter("duration_in_sec"));
    	
    	QuestionService service = new QuestionService();
    	service.updateQuestion(question_id, question_text, question_type, difficulty_level,duration_in_sec);
    	
    	OptionService oservice = new OptionService();
    	
    	oservice.updateNewOption(optionId, optionText, question, optionValue);
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
