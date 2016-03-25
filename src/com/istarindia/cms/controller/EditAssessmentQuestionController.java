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
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
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
@WebServlet("/edit_assessment_question")
public class EditAssessmentQuestionController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try {
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("_action") != null ? request.getParameter("_action") : "__NO_VALUE";
            String questionId = request.getParameter("question_id") != null ? request.getParameter("question_id") : "0";
            AssessmentDAO dao = new AssessmentDAO();
            Assessment assessment = dao.findById(Integer.parseInt(request.getParameter("assessment_id")));

            TaskDAO dao1 = new TaskDAO();
            Task t = new Task();
            t.setItemType("LESSON");
            t.setItemId(assessment.getLesson().getId());
            switch (action) {
                case "edit":
                    request.setAttribute("__EDIT_QUESTION", "true");
                    request.setAttribute("__QUESTION_ID", questionId);
                    request.getRequestDispatcher("edit_lesson?lesson_id=" + assessment.getLesson().getId() + "&task_id=" + dao1.findByExample(t).get(0).getId()).forward(request, response);
                    //  response.sendRedirect("/content/edit_lesson?lesson_id=" + assessment.getLesson().getId() + "&task_id=" + dao1.findByExample(t).get(0).getId());
                    break;
                case "update":
                    QuestionService questionService = new QuestionService();
                    OptionService optionService = new OptionService();

                    LearningObjectiveDAO learningObjectiveDAO = new LearningObjectiveDAO();

                    Integer specifier = Integer.parseInt(request.getParameter("specifier"));
                    String[] learningObjectiveIds = request.getParameterValues("learningObjectives");
                    Set<LearningObjective> learningObjectiveSet = new HashSet<LearningObjective>();
                    if (learningObjectiveIds != null) {
                        for (int i = 0; i < learningObjectiveIds.length; i++) {
                            learningObjectiveSet.add(learningObjectiveDAO.findById(Integer.parseInt(learningObjectiveIds[i])));
                        }
                    }
                    boolean flag = questionService.editQuestionForAssessment(Integer.parseInt(request.getParameter("questionId")), request.getParameter("question_text"),
                            request.getParameter("question_type"), Integer.parseInt(request.getParameter("difficulty_level")),
                            Integer.parseInt(request.getParameter("assessment_id")), learningObjectiveSet, specifier);

                    Map map = new HashMap();
                    map = request.getParameterMap();
                    String fieldname = ""; //item.getFieldName();
                    String questionId1 = request.getParameter("questionId");
                    Iterator it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pairs = (Map.Entry) it.next();
                        String[] s = (String[]) pairs.getValue();
                        fieldname = pairs.getKey().toString();

                        if (fieldname.startsWith("option_")) {
                            String id = fieldname.substring(fieldname.indexOf("_") + 1, fieldname.length());
                            String[] answers = request.getParameterValues("answers");
                            optionService.updateNewOption(Integer.parseInt(id), request.getParameter(fieldname), questionService.findById(Integer.parseInt(questionId1)), 1);

                        }
                    }

                    request.setAttribute("__EDIT_QUESTION", "true");
                    request.setAttribute("__QUESTION_ID", questionId1);

                    request.getRequestDispatcher("edit_lesson?lesson_id=" + assessment.getLesson().getId() + "&task_id=" + dao1.findByExample(t).get(0).getId()).forward(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
