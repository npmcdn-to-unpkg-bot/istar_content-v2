/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istarindia.cms.controller;   

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.services.TestService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kunal Chakravertti
 */
@WebServlet(urlPatterns = "/tests_controller")
public class TestController extends HttpServlet {

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
            String action = request.getParameter("_action") != null ? request.getParameter("_action") : "__NO_VALUE";
            TestService service = new TestService();
            List<Assessment> assessmentList = new ArrayList();
            List<LearningObjective> learningObjectiveList = new ArrayList();
            List<Integer> oldLearningObjectiveList = new ArrayList();
            List<Question> questionList = new ArrayList();
            List<Integer> quesList = new ArrayList();
            List<Integer> objIdList = new ArrayList();
            Assessment test = new Assessment();
            StringBuilder sb = new StringBuilder();
            String testTitle = "";
            String testType = "";
            int questionCount = 0;
            String testDateString = "";
            int testTimeHr = 0;
            int testTimeMin = 0;
            int testDurationHr = 0;
            int testDurationMin = 0;
            String testId = "";
            Map dataMap = new HashMap();
            Map map = new HashMap();

            if (request.getSession().getAttribute("_DATA_MAP") == null) {
                request.getSession().setAttribute("_DATA_MAP", dataMap);
            }

            switch (action) {
                case "findall":
                    assessmentList = service.getAllAssessments();
                    request.setAttribute("assessmentList", assessmentList);
                    request.getRequestDispatcher("tests/test_listing.jsp").forward(request, response);
                    break;

                case "create":
                    sb.append("<select name='test_time_min'>");
                    for (int x = 0; x < 60; x++) {
                        sb.append("<option value='" + x + "'>" + x + "</option>");
                    }
                    sb.append("</select>");
                    request.setAttribute("mindrop", sb.toString());
                    sb = new StringBuilder();

                    sb.append("<select name='test_duration_min'>");
                    for (int x = 0; x < 60; x++) {
                        sb.append("<option value='" + x + "'>" + x + "</option>");
                    }
                    sb.append("</select>");
                    request.setAttribute("durationmindrop", sb.toString());
                    sb = new StringBuilder();

                    sb.append("<select name='test_time_hr'>");
                    for (int x = 0; x < 24; x++) {
                        sb.append("<option value='" + x + "'>" + x + "</option>");
                    }
                    sb.append("</select>");
                    request.setAttribute("hourdrop", sb.toString());
                    sb = new StringBuilder();

                    sb.append("<select name='test_duration_hr'>");
                    for (int x = 0; x < 24; x++) {
                        sb.append("<option value='" + x + "'>" + x + "</option>");
                    }
                    sb.append("</select>");
                    request.setAttribute("durationhrdrop", sb.toString());

                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("tests/create_edit_test.jsp").forward(request, response);
                    break;

                case "foredit":
                    testId = request.getParameter("test_id") != null ? request.getParameter("test_id") : "0";
                    test = service.getAssessment(Integer.parseInt(testId));

                    sb.append("<select name='test_time_min'>");
                    for (int x = 0; x < 60; x++) {
                        if (test.getAssessmentMinutes() != null && test.getAssessmentMinutes() == x) {
                            sb.append("<option value='" + x + "' selected=selected>" + x + "</option>");
                        } else {
                            sb.append("<option value='" + x + "'>" + x + "</option>");
                        }

                    }
                    sb.append("</select>");
                    request.setAttribute("mindrop", sb.toString());
                    sb = new StringBuilder();

                    sb.append("<select name='test_duration_min'>");
                    for (int x = 0; x < 60; x++) {
                        if (test.getAssessmentDurationMinutes() != null && test.getAssessmentDurationMinutes() == x) {
                            sb.append("<option value='" + x + "' selected=selected>" + x + "</option>");
                        } else {
                            sb.append("<option value='" + x + "'>" + x + "</option>");
                        }

                    }
                    sb.append("</select>");
                    request.setAttribute("durationmindrop", sb.toString());
                    sb = new StringBuilder();

                    sb.append("<select name='test_time_hr'>");
                    for (int x = 0; x < 24; x++) {
                        if (test.getAssessmentHour() != null && test.getAssessmentHour() == x) {
                            sb.append("<option value='" + x + "' selected=selected>" + x + "</option>");
                        } else {
                            sb.append("<option value='" + x + "'>" + x + "</option>");
                        }

                    }
                    sb.append("</select>");
                    request.setAttribute("hourdrop", sb.toString());
                    sb = new StringBuilder();

                    sb.append("<select name='test_duration_hr'>");
                    for (int x = 0; x < 24; x++) {
                        if (test.getAssessmentDurationHours() != null && test.getAssessmentDurationHours() == x) {
                            sb.append("<option value='" + x + "' selected=selected>" + x + "</option>");
                        } else {
                            sb.append("<option value='" + x + "'>" + x + "</option>");
                        }
                    }
                    sb.append("</select>");
                    request.setAttribute("durationhrdrop", sb.toString());

                    request.setAttribute("testObject", test);
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("tests/create_edit_test.jsp").forward(request, response);
                    break;

                case "chooseobjectives":
                    testId = request.getParameter("test_id") != null ? request.getParameter("test_id") : "0";
                    testTitle = request.getParameter("test_title") != null ? request.getParameter("test_title") : "";
                    testType = request.getParameter("test_type") != null ? request.getParameter("test_type") : "";
                    questionCount = request.getParameter("question_count") != null ? Integer.parseInt(request.getParameter("question_count")) : 0;
                    testDateString = request.getParameter("test_date") != null ? request.getParameter("test_date") : "";
                    testTimeHr = request.getParameter("test_time_hr") != null ? Integer.parseInt(request.getParameter("test_time_hr")) : 0;
                    testTimeMin = request.getParameter("test_time_min") != null ? Integer.parseInt(request.getParameter("test_time_min")) : 0;
                    testDurationHr = request.getParameter("test_duration_hr") != null ? Integer.parseInt(request.getParameter("test_duration_hr")) : 0;
                    testDurationMin = request.getParameter("test_duration_min") != null ? Integer.parseInt(request.getParameter("test_duration_min")) : 0;
                    
                    //Setting in hashmap and storing in session
                    dataMap = (Map) request.getSession().getAttribute("_DATA_MAP");
                    dataMap.put("testTitle", testTitle);
                    dataMap.put("testType", testType);
                    dataMap.put("questionCount", questionCount);
                    dataMap.put("testDateString", testDateString);
                    dataMap.put("testTimeHr", testTimeHr);
                    dataMap.put("testTimeMin", testTimeMin);
                    dataMap.put("testDurationHr", testDurationHr);
                    dataMap.put("testDurationMin", testDurationMin);
                    dataMap.put("testId", testId);
                    request.getSession().setAttribute("_DATA_MAP", dataMap);

                    learningObjectiveList = service.getLearningObjectives();
                    if (!testId.equalsIgnoreCase("0")) {
                        test = service.getAssessment(Integer.parseInt(testId));
                        List<LearningObjective> objLs =  new ArrayList();
                        objLs = (List<LearningObjective>) test.getLearningObjectiveCollection();
                        for (int a = 0; a< objLs.size(); a++) {
                            oldLearningObjectiveList.add(objLs.get(a).getId());
                        }
                        request.setAttribute("oldLearningObjectiveList", oldLearningObjectiveList);
                    }
                    System.out.println("learningObjectiveList : " + oldLearningObjectiveList.size());
                    request.setAttribute("learningObjectiveList", learningObjectiveList);
                    request.getRequestDispatcher("tests/learning_objective.jsp").forward(request, response);
                    break;

                case "choosequestions":
                    dataMap = (Map) request.getSession().getAttribute("_DATA_MAP");
                    map = request.getParameterMap();
                    String fieldname = ""; //item.getFieldName();
                    Iterator it = map.entrySet().iterator();
                     testTitle = dataMap.get("testTitle").toString();
                    while (it.hasNext()) {
                        Map.Entry pairs = (Map.Entry) it.next();
                        String[] s = (String[]) pairs.getValue();
                        fieldname = pairs.getKey().toString();
                        if (fieldname.startsWith("obj_")) {
                            String idString = fieldname.substring(fieldname.indexOf("_") + 1, fieldname.length());
                            objIdList.add(Integer.parseInt(idString));
                        }
                    }
                    dataMap.put("objIdList", objIdList);
                    request.getSession().setAttribute("_DATA_MAP", dataMap);
                    questionList = service.getQuestionsFrmLrngObjtvs(objIdList);
                    request.setAttribute("questionList", questionList);
                    request.getRequestDispatcher("tests/choose_questions.jsp").forward(request, response);
                    break;

                case "save":
                    map = request.getParameterMap();
                    String fieldname1 = ""; //item.getFieldName();
                    Iterator it1 = map.entrySet().iterator();
                    while (it1.hasNext()) {
                        Map.Entry pairs = (Map.Entry) it1.next();
                        fieldname1 = pairs.getKey().toString();
                        if (fieldname1.startsWith("question_")) {
                            String idString = fieldname1.substring(fieldname1.indexOf("_") + 1, fieldname1.length());
                            quesList.add(Integer.parseInt(idString));
                        }
                    }
                    dataMap = (Map) request.getSession().getAttribute("_DATA_MAP");
                    testId = dataMap.get("testId").toString();
                    Integer id = Integer.parseInt(testId);
                    testTitle = dataMap.get("testTitle").toString();
                    testType = dataMap.get("testType").toString();
                    questionCount = Integer.parseInt(dataMap.get("questionCount").toString());
                    testDateString = dataMap.get("testDateString").toString();
                    testTimeHr = Integer.parseInt(dataMap.get("testTimeHr").toString());
                    testTimeMin = Integer.parseInt(dataMap.get("testTimeMin").toString());
                    testDurationHr = Integer.parseInt(dataMap.get("testDurationHr").toString());
                    testDurationMin = Integer.parseInt(dataMap.get("testDurationMin").toString());
                    objIdList = (List<Integer>) dataMap.get("objIdList");
                    if (!testId.equalsIgnoreCase("0")) {
                        //Edit
                        boolean flag = service.updateTest(id, testTitle, testType, questionCount, testDurationHr, testDurationMin, getDate(testDateString), testTimeHr, testTimeMin, objIdList, quesList);
                       
                    } else {
                        //Create
                        boolean flag = service.createTest(testTitle, testType, questionCount, testDurationHr, testDurationMin, getDate(testDateString), testTimeHr, testTimeMin, objIdList, quesList);
                    }
                    assessmentList = service.getAllAssessments();
                    request.setAttribute("assessmentList", assessmentList);
                    request.getRequestDispatcher("tests/test_listing.jsp").forward(request, response);
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

    private Date getDate(String stringDate) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = formatter.parse(stringDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
