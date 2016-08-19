/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istarindia.cms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.TblSession;
import com.istarindia.apps.services.TblSessionService;

/**
 *
 * @author Kunal Chakravertti
 */
@WebServlet(urlPatterns = "/session_controller")
public class SessionController extends HttpServlet {

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
            TblSessionService sessionService = new TblSessionService();
            StringBuilder sb = new StringBuilder();
            String action = request.getParameter("_action") != null ? request.getParameter("_action") : "_NO_VALUE";
            List<TblSession> list = new ArrayList();
            String sessionId = "";
            String sessionType = "";
            String sessionContent = "";
            String sessionDate = "";
            String sessionHour = "";
            String sessionMinute = "";
            String sessionIsMonthly = "";
            String sessionIsWeekly = "";
            String noOfReviews = "";
            boolean isWeekly = false;
            boolean isMonthly = false;

            switch (action) {
                case "findall":

                    list = sessionService.findAll();
                    request.setAttribute("sessionList", list);
                    request.getRequestDispatcher("session/session_listing.jsp").forward(request, response);
                    break;
                    
                case "create":
                    sb.append("<select name='session_minute'>");
                    for (int x = 0; x < 60; x++) {
                        sb.append("<option value='" + x + "'>" + x + "</option>");
                    }
                    sb.append("</select>");
                    request.setAttribute("mindrop", sb.toString());
                    sb = new StringBuilder();
                    sb.append("<select name='session_hour'>");
                    for (int x = 0; x < 24; x++) {
                        sb.append("<option value='" + x + "'>" + x + "</option>");
                    }
                    sb.append("</select>");
                    request.setAttribute("hourdrop", sb.toString());
                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("session/create_edit_session.jsp").forward(request, response);
                    break;

                case "foredit":
                    sessionId = request.getParameter("session_id") != null ? request.getParameter("session_id") : "0";
                    TblSession tblSession = new TblSession();
                    tblSession = sessionService.finById(Integer.parseInt(sessionId));
                    System.out.println("tblSession : " + tblSession.getSessionDate());
                    sb.append("<select name='session_minute'>");
                    for (int x = 0; x < 60; x++) {
                        if (x == tblSession.getSessionMinute()) {
                            sb.append("<option value='" + x + "' selected=selected>" + x + "</option>");
                        } else {
                            sb.append("<option value='" + x + "'>" + x + "</option>");
                        }

                    }
                    sb.append("</select>");
                    request.setAttribute("mindrop", sb.toString());
                    sb = new StringBuilder();
                    sb.append("<select name='session_hour'>");
                    for (int x = 0; x < 24; x++) {
                        if (x == tblSession.getSessionHour()) {
                            sb.append("<option value='" + x + "' selected=selected>" + x + "</option>");
                        } else {
                            sb.append("<option value='" + x + "'>" + x + "</option>");
                        }

                    }
                    sb.append("</select>");
                    request.setAttribute("hourdrop", sb.toString());
                    request.setAttribute("sessionobject", tblSession);
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("session/create_edit_session.jsp").forward(request, response);
                    break;

                case "save":
                    sessionType = request.getParameter("session_type") != null ? request.getParameter("session_type") : "";
                    sessionContent = request.getParameter("class_content") != null ? request.getParameter("class_content") : "";
                    sessionDate = request.getParameter("session_date") != null ? request.getParameter("session_date") : "";
                    sessionHour = request.getParameter("session_hour") != null ? request.getParameter("session_hour") : "";
                    sessionMinute = request.getParameter("session_minute") != null ? request.getParameter("session_minute") : "";
                    sessionIsMonthly = request.getParameter("review_monthly") != null ? request.getParameter("review_monthly") : "";
                    sessionIsWeekly = request.getParameter("review_weekly") != null ? request.getParameter("review_weekly") : "";
                    noOfReviews = request.getParameter("no_reviews") != null ? request.getParameter("no_reviews") : "";
                    if (sessionIsMonthly.equalsIgnoreCase("on")) {
                        isMonthly = true;
                    }
                    if (sessionIsWeekly.equalsIgnoreCase("on")) {
                        isWeekly = true;
                    }

                    boolean flag = sessionService.createSession(sessionType, sessionContent, getDate(sessionDate), Integer.parseInt(sessionHour), Integer.parseInt(sessionMinute), isWeekly, isMonthly, Integer.parseInt(noOfReviews), 0);
               
                    list = sessionService.findAll();
                    request.setAttribute("sessionList", list);
                    request.getRequestDispatcher("session/session_listing.jsp").forward(request, response);
                    break;

                case "update":
                    sessionId = request.getParameter("session_id") != null ? request.getParameter("session_id") : "0";
                    sessionType = request.getParameter("session_type") != null ? request.getParameter("session_type") : "";
                    sessionContent = request.getParameter("class_content") != null ? request.getParameter("class_content") : "";
                    sessionDate = request.getParameter("session_date") != null ? request.getParameter("session_date") : "";
                    sessionHour = request.getParameter("session_hour") != null ? request.getParameter("session_hour") : "";
                    sessionMinute = request.getParameter("session_minute") != null ? request.getParameter("session_minute") : "";
                    sessionIsMonthly = request.getParameter("review_monthly") != null ? request.getParameter("review_monthly") : "";
                    sessionIsWeekly = request.getParameter("review_weekly") != null ? request.getParameter("review_weekly") : "";
                    noOfReviews = request.getParameter("no_reviews") != null ? request.getParameter("no_reviews") : "";
                    if (sessionIsMonthly.equalsIgnoreCase("on")) {
                        isMonthly = true;
                    }
                    if (sessionIsWeekly.equalsIgnoreCase("on")) {
                        isWeekly = true;
                    }
                
                    boolean flag1 = sessionService.updateSession(Integer.parseInt(sessionId), sessionType, sessionContent, getDate(sessionDate), Integer.parseInt(sessionHour), Integer.parseInt(sessionMinute), isWeekly, isMonthly, Integer.parseInt(noOfReviews), 0);
           
                    list = sessionService.findAll();
                    request.setAttribute("sessionList", list);
                    request.getRequestDispatcher("session/session_listing.jsp").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
