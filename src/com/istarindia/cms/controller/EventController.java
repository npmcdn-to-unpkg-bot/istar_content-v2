/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istarindia.cms.controller;

import com.istarindia.apps.dao.Event;
import com.istarindia.apps.services.EventService;
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

/**
 *
 * @author Kunal Chakravertti
 */
@WebServlet(urlPatterns = "/event_controller")
public class EventController extends HttpServlet {

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
            EventService eventService = new EventService();
            List<Event> eventList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            switch (action) {
                case "findall":
                    eventList = eventService.findEventsByCriteria(0); // List all Events
                    request.setAttribute("eventList", eventList);
                    request.getRequestDispatcher("events/event_listing.jsp").forward(request, response);
                    break;
                case "findbydate":
                    eventList = eventService.findEventsByCriteria(1); // List Events for current date
                    request.setAttribute("eventList", eventList);
                    request.getRequestDispatcher("events/event_listing.jsp").forward(request, response);
                    break;
                case "findbyweek":
                    eventList = eventService.findEventsByCriteria(2); // List Events for current week
                    request.setAttribute("eventList", eventList);
                    request.getRequestDispatcher("events/event_listing.jsp").forward(request, response);
                    break;

                case "findbymonth":
                    eventList = eventService.findEventsByCriteria(3); // List Events for current month
                    request.setAttribute("eventList", eventList);
                    request.getRequestDispatcher("events/event_listing.jsp").forward(request, response);
                    break;

                case "create":

                    sb.append("<select name='event_minute'>");
                    for (int x = 0; x < 60; x++) {
                        sb.append("<option value='" + x + "'>" + x + "</option>");
                    }
                    sb.append("</select>");
                    request.setAttribute("mindrop", sb.toString());
                    sb = new StringBuilder();
                    sb.append("<select name='event_hour'>");
                    for (int x = 0; x < 24; x++) {
                        sb.append("<option value='" + x + "'>" + x + "</option>");
                    }
                    sb.append("</select>");
                    request.setAttribute("hourdrop", sb.toString());
                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("events/create_edit_event.jsp").forward(request, response);
                    break;

                case "save":
                    String eventType = request.getParameter("event_type") != null ? request.getParameter("event_type") : "";
                    String eventDate = request.getParameter("event_date") != null ? request.getParameter("event_date") : "";
                    String eventHour = request.getParameter("event_hour") != null ? request.getParameter("event_hour") : "";
                    String eventMinute = request.getParameter("event_minute") != null ? request.getParameter("event_minute") : "";
                    boolean flag = eventService.createEvent(eventType, getDate(eventDate), Integer.parseInt(eventHour), Integer.parseInt(eventMinute), 1);
                    eventList = eventService.findEventsByCriteria(0);
                    request.setAttribute("eventList", eventList);
                    request.getRequestDispatcher("events/event_listing.jsp").forward(request, response);
                    break;
                case "foredit":
                    String eventId = request.getParameter("event_id") != null ? request.getParameter("event_id") : "0";
                    Event event = new Event();
                    event = eventService.findEventById(Integer.parseInt(eventId));
                    sb.append("<select name='event_minute'>");
                    for (int x = 0; x < 60; x++) {
                        if (x == event.getEventminute()) {
                            sb.append("<option value='" + x + "' selected=selected>" + x + "</option>");
                        } else {
                            sb.append("<option value='" + x + "'>" + x + "</option>");
                        }

                    }
                    sb.append("</select>");
                    request.setAttribute("mindrop", sb.toString());
                    sb = new StringBuilder();
                    sb.append("<select name='event_hour'>");
                    for (int x = 0; x < 24; x++) {
                        if (x == event.getEventhour()) {
                            sb.append("<option value='" + x + "' selected=selected>" + x + "</option>");
                        } else {
                            sb.append("<option value='" + x + "'>" + x + "</option>");
                        }

                    }
                    sb.append("</select>");
                    request.setAttribute("hourdrop", sb.toString());
                    request.setAttribute("eventobject", event);
                    request.setAttribute("action", "edit");
                    request.setAttribute("eventId", eventId);
                    request.getRequestDispatcher("events/create_edit_event.jsp").forward(request, response);
                    break;
                case "update":
                    String eventIdForUpdate = request.getParameter("event_id") != null ? request.getParameter("event_id") : "0";
                    String eventTypeForUpdate = request.getParameter("event_type") != null ? request.getParameter("event_type") : "";
                    String eventDateForUpdate = request.getParameter("event_date") != null ? request.getParameter("event_date") : "";
                    String eventHourForUpdate = request.getParameter("event_hour") != null ? request.getParameter("event_hour") : "";
                    String eventMinuteForUpdate = request.getParameter("event_minute") != null ? request.getParameter("event_minute") : "";
                    boolean flagForUpdate = eventService.updateEvent(Integer.parseInt(eventIdForUpdate), eventTypeForUpdate, getDate(eventDateForUpdate), Integer.parseInt(eventHourForUpdate), Integer.parseInt(eventMinuteForUpdate), 1);
                    eventList = eventService.findEventsByCriteria(0);
                    request.setAttribute("eventList", eventList);
                    request.getRequestDispatcher("events/event_listing.jsp").forward(request, response);
                    break;
                case "delete":
                    String eventIdForDelete = request.getParameter("event_id") != null ? request.getParameter("event_id") : "0";
                    boolean flagForDelete = eventService.deleteEvent(Integer.parseInt(eventIdForDelete));
                    eventList = eventService.findEventsByCriteria(0);
                    request.setAttribute("eventList", eventList);
                    request.getRequestDispatcher("events/event_listing.jsp").forward(request, response);
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
