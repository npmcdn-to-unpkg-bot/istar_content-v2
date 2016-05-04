/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istarindia.cms.controller;

import com.istarindia.apps.dao.Batch;
import com.istarindia.apps.dao.BatchCourse;
import com.istarindia.apps.dao.BatchGroup;
import com.istarindia.apps.dao.BatchStudents;
import com.istarindia.apps.dao.Course;
import com.istarindia.apps.dao.Student;
import com.istarindia.apps.dao.Trainer;
import com.istarindia.apps.services.BatchService;
import com.istarindia.apps.services.CourseService;
import com.istarindia.apps.services.StudentService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
@WebServlet(urlPatterns = "/batch_controller")
public class BatchController extends HttpServlet {

    StringBuilder tableString = new StringBuilder();

    BatchService batchService = new BatchService();
    CourseService courseService = new CourseService();
    StudentService studentService = new StudentService();

    List<BatchGroup> batchGroupList = new ArrayList();
    List<Course> courseList = new ArrayList();
    List<Student> studentList = new ArrayList();
    List<Integer> stList = new ArrayList();
    List<Trainer> trainerList = new ArrayList();

    BatchGroup batchGroup = new BatchGroup();
    Batch batchObj = new Batch();

    String id = "";
    String courseId = "";
    String tableStyle = "style='width: 98%;margin-left: 1%;margin-right: 1%;'";
    String style = "style='margin: 10px;    margin: 10px;border: 3px solid #FFC107;'";

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
        try {
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("_action") != null ? request.getParameter("_action") : "_NO_VALUE";

            switch (action) {
                case "findallbatchgrps":
                    request.setAttribute("batchGroupList", getTableString(1));
                    request.getRequestDispatcher("batch/batchgroup_listing.jsp").forward(request, response);
                    break;

                case "createbatchgrp":
                    courseList = courseService.findAll();
                    request.setAttribute("action", "create");
                    request.setAttribute("courseList", courseList);
                    request.getRequestDispatcher("batch/create_edit_batch_group.jsp").forward(request, response);
                    break;

                case "editbatchgrp":
                    id = request.getParameter("id") != null ? request.getParameter("id") : "";
                    batchGroup = new BatchGroup();
                    batchGroup = batchService.findById(Integer.parseInt(id));
                    Set<BatchCourse> set = new HashSet();
                    BatchCourse batchCourse = new BatchCourse();
                    set = batchGroup.getBatchCourses();
                    if (set.isEmpty()) {
                        request.setAttribute("courseId", "0");
                    } else {
                        Iterator it = set.iterator();
                        batchCourse = (BatchCourse) it.next();
                        request.setAttribute("courseId", batchCourse.getCourse().getId() + "");
                    }
                    request.setAttribute("action", "edit");
                    courseList = new ArrayList();
                    courseList = courseService.findAll();
                    request.setAttribute("courseList", courseList);
                    request.setAttribute("batchGroup", batchGroup);
                    request.getRequestDispatcher("batch/create_edit_batch_group.jsp").forward(request, response);
                    break;

                case "savebatchgrp":
                    id = request.getParameter("id") != null ? request.getParameter("id") : "0";
                    courseId = request.getParameter("course") != null ? request.getParameter("course") : "0";
                    String oldCourseId = request.getParameter("oldcourse") != null ? request.getParameter("oldcourse") : "0";
                    String name = request.getParameter("name") != null ? request.getParameter("name") : "";
                    String studentString = request.getParameter("students") != null ? request.getParameter("students") : "0";
                    if (id.equalsIgnoreCase("")) {
                        batchService.createBatchGroup(name, Integer.parseInt(studentString), Integer.parseInt(courseId));
                    } else {
                        batchService.updateBatchGroup(Integer.parseInt(id), name, Integer.parseInt(studentString), Integer.parseInt(courseId), Integer.parseInt(oldCourseId));
                    }

                    request.setAttribute("batchGroupList", getTableString(1));
                    request.getRequestDispatcher("batch/batchgroup_listing.jsp").forward(request, response);
                    break;

                case "students":
                    id = request.getParameter("id") != null ? request.getParameter("id") : "0";
                    batchGroup = new BatchGroup();
                    stList = new ArrayList();
                    Set<BatchStudents> studentSet = new HashSet();
                    batchGroup = batchService.findById(Integer.parseInt(id));
                    studentSet = batchGroup.getBatchStudentses();
                    if (!studentSet.isEmpty()) {
                        Iterator it = studentSet.iterator();
                        while (it.hasNext()) {
                            BatchStudents batchStudents = (BatchStudents) it.next();
                            if (!stList.contains(batchStudents.getStudent().getId())) {
                                stList.add(batchStudents.getStudent().getId());
                            }
                        }

                    }

                    studentList = new ArrayList();
                    studentList = studentService.getAllStudents();
                    String batchGrpName = batchGroup.getName();
                    request.setAttribute("stList", stList);
                    request.setAttribute("batchGrpId", id);
                    request.setAttribute("studentList", studentList);
                    request.setAttribute("batchGrpName", batchGrpName);
                    request.getRequestDispatcher("batch/batch_grp_students.jsp").forward(request, response);
                    break;

                case "savestudents":
                    id = request.getParameter("id") != null ? request.getParameter("id") : "0";
                    Map map = request.getParameterMap();
                    String fieldname = "";
                    stList = new ArrayList();
                    Iterator it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pairs = (Map.Entry) it.next();
                        String[] s = (String[]) pairs.getValue();
                        fieldname = pairs.getKey().toString();
                        if (fieldname.startsWith("obj_")) {
                            String idString = fieldname.substring(fieldname.indexOf("_") + 1, fieldname.length());
                            stList.add(Integer.parseInt(idString));
                        }
                    }
                    batchService.updateBatchGrpStudents(Integer.parseInt(id), stList);

                    request.setAttribute("batchGroupList", getTableString(1));
                    request.getRequestDispatcher("batch/batchgroup_listing.jsp").forward(request, response);

                case "batch":
                    id = request.getParameter("id") != null ? request.getParameter("id") : "0";
                    batchGroup = new BatchGroup();
                    batchGroup = batchService.findById(Integer.parseInt(id));
                    Set<Batch> batchs = new HashSet();
                    List<Batch> batchList = new ArrayList();
                    batchs = batchGroup.getBatchs();
                    if (!batchs.isEmpty()) {
                        Iterator it1 = batchs.iterator();
                        while (it1.hasNext()) {
                            Batch batch = (Batch) it1.next();
                            batchList.add(batch);
                        }
                    }
                    request.setAttribute("batchList", batchList);
                    String batchGrpName1 = batchGroup.getName();
                    request.setAttribute("batchGrpId", id);
                    request.setAttribute("batchGrpName", batchGrpName1);
                    request.getRequestDispatcher("batch/batch_listing.jsp").forward(request, response);
                    break;

                case "createbatch":
                    id = request.getParameter("batchgrpid") != null ? request.getParameter("batchgrpid") : "0";
                    trainerList = batchService.getTrainerList();
                    request.setAttribute("batchGrpId", id);
                    request.setAttribute("action", "create");
                    request.setAttribute("trainerId", "0");
                    request.setAttribute("trainerList", trainerList);
                    request.getRequestDispatcher("batch/create_edit_batch.jsp").forward(request, response);
                    break;

                case "editbatch":
                    id = request.getParameter("batchgrpid") != null ? request.getParameter("batchgrpid") : "0";
                    String batchId = request.getParameter("id") != null ? request.getParameter("id") : "0";
                    batchObj = batchService.getBatchById(Integer.parseInt(batchId));
                    trainerList = batchService.getTrainerList();
                   // request.setAttribute("trainerId", batchObj.getTrainer() != null ? batchObj.getTrainer().getId()+"" : "0");
                    request.setAttribute("trainerList", trainerList);
                    request.setAttribute("batchGrpId", id);
                    request.setAttribute("action", "edit");
                    request.setAttribute("batchObj", batchObj);
                    request.setAttribute("batchId", batchId);
                    request.getRequestDispatcher("batch/create_edit_batch.jsp").forward(request, response);
                    break;

                case "savebatch":
                    id = request.getParameter("batchGrpId") != null ? request.getParameter("batchGrpId") : "0";
                    String batchId1 = request.getParameter("batchId") != null ? request.getParameter("batchId") : "0";
                    String batchName = request.getParameter("name") != null ? request.getParameter("name") : "";
                    String dateString = request.getParameter("scheduledate") != null ? request.getParameter("scheduledate") : "0";
                    String trainerId = request.getParameter("trainer") != null ? request.getParameter("trainer") : "0";

                    if (batchId1.equalsIgnoreCase("")) {
                        if (dateString.trim().length() == 0) {
                            batchService.createBatch(Integer.parseInt(id), batchName, null, Integer.parseInt(trainerId));
                        } else {
                            batchService.createBatch(Integer.parseInt(id), batchName, getDate(dateString), Integer.parseInt(trainerId));
                        }
                    } else {
                        if (dateString.trim().length() == 0) {
                            batchService.updateBatch(Integer.parseInt(id), Integer.parseInt(batchId1), batchName, null, Integer.parseInt(trainerId));
                        } else {
                            batchService.updateBatch(Integer.parseInt(id), Integer.parseInt(batchId1), batchName, getDate(dateString), Integer.parseInt(trainerId));
                        }
                    }
                    batchGroup = new BatchGroup();
                    batchGroup = batchService.findById(Integer.parseInt(id));
                    Set<Batch> batchs1 = new HashSet();
                    List<Batch> batchList1 = new ArrayList();
                    batchs1 = batchGroup.getBatchs();
                    if (!batchs1.isEmpty()) {
                        Iterator it1 = batchs1.iterator();
                        while (it1.hasNext()) {
                            Batch batch = (Batch) it1.next();
                            batchList1.add(batch);
                        }
                    }
                    String batchGrpName2 = batchGroup.getName();
                    request.setAttribute("batchGrpId", id);
                    request.setAttribute("batchList", batchList1);
                    request.setAttribute("batchGrpName", batchGrpName2);
                    request.getRequestDispatcher("batch/batch_listing.jsp").forward(request, response);
                    break;

                case "schedulebatch":
                    id = request.getParameter("batchgrpid") != null ? request.getParameter("batchgrpid") : "0";
                    request.setAttribute("batchGrpId", id);
                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("batch/create_edit_batch.jsp").forward(request, response);
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

    private String getTableString(int criteria) {

        switch (criteria) {
            case 1:
                batchGroupList = new ArrayList();
                tableString = new StringBuilder();
                batchGroupList = batchService.findAll();
                tableString.append("<div class='row pie-progress-charts margin-bottom-60'>");
                tableString.append("<div class='panel panel-yellow margin-bottom-40' " + style + "> <div class='panel-heading'> <h3 class='panel-title'><i class='fa fa-tasks'></i> " + "Batch-Group Listing" + " </h3> </div><div class='panel-body'></div>"
                        + "<table " + tableStyle + " class='table table-striped table-bordered display responsive  dataTable datatable_report' "
                        + "id='datatable_report_" + "" + "' data-graph_type='" + "" + "' " + "" + "  "
                        + " data-graph_title='" + "" + "' " + "data-graph_containter='report_container_" + ""
                        + "'>");
                tableString.append("<thead><tr>");
                tableString.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + "Name" + "</th>");
                tableString.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + "Max Students" + "</th>");
                tableString.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + "Course" + "</th>");
                tableString.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + "Edit" + "</th>");
                tableString.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + "View/Assign Students" + "</th>");
                tableString.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + "View/Edit Batch" + "</th>");
                tableString.append("</tr></thead>");
                tableString.append("<tbody>");
                for (int x = 0; x < batchGroupList.size(); x++) {
                    BatchGroup batchGroup = batchGroupList.get(x);
                    Set<BatchCourse> set = new HashSet();
                    BatchCourse batchCourse = new BatchCourse();

                    set = batchGroup.getBatchCourses();
                    if (!set.isEmpty()) {
                        Iterator it = set.iterator();
                        batchCourse = (BatchCourse) it.next();
                    }
                    tableString.append("<tr>");
                    tableString.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + batchGroup.getName() + "</td>");
                    tableString.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + batchGroup.getMaxStudents() + "</td>");
                    if (set.isEmpty()) {
                        tableString.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + "&nbsp;" + "</td>");
                    } else {
                        tableString.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + batchCourse.getCourse().getCourseName() + "</td>");
                    }
                    tableString.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + "<a href=/content/batch_controller?_action=editbatchgrp&id=" + batchGroup.getId() + " >Edit</a></td>");
                    tableString.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + "<a href=/content/batch_controller?_action=students&id=" + batchGroup.getId() + " >View/Assign Students</a></td>");
                    tableString.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + "<a href=/content/batch_controller?_action=batch&id=" + batchGroup.getId() + " >View/Edit Batch</a></td>");
                    tableString.append("</tr>");
                }
                tableString.append("</tbody>");
                tableString.append("</table>");
                tableString.append("<div id='report_container_" + "" + "'></div>");
                tableString.append("</div></div>");
                break;
            default:
                throw new AssertionError();
        }

        return tableString.toString();
    }

    private Date getDate(String stringDate) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            date = formatter.parse(stringDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
