package com.istarindia.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.services.OptionService;
import com.istarindia.apps.services.QuestionService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.cms.lessons.CMSList;
import com.istarindia.cms.lessons.CMSTextItem;

/**
 * Servlet implementation class CreateSlideController
 */
@WebServlet("/add_question")
public class AddQuestionController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddQuestionController() {
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
		QuestionService questionService = new QuestionService();
		OptionService optionService = new OptionService();
		AssessmentDAO dao = new AssessmentDAO();
		LearningObjectiveDAO learningObjectiveDAO = new LearningObjectiveDAO();
		Assessment assessment = dao.findById(Integer.parseInt(request.getParameter("assessment_id")));
		Integer task_id= Integer.parseInt(request.getParameter("task_id"));
		String[] learningObjectiveIds = request.getParameterValues("learningObjectives");
		Set<LearningObjective> learningObjectiveSet=new HashSet<LearningObjective>();
		if(learningObjectiveIds!=null){
		for (int i = 0; i < learningObjectiveIds.length; i++) {
			learningObjectiveSet.add(learningObjectiveDAO.findById(Integer.parseInt(learningObjectiveIds[i])));
		}} 
		
		Question question = questionService.createNewQuestionForAssessment(request.getParameter("question_text"),
				request.getParameter("question_type"), Integer.parseInt(request.getParameter("difficulty_level")),
				Integer.parseInt(request.getParameter("assessment_id")),learningObjectiveSet);
		
		optionService.createNewOption(request.getParameter("option1"), question, "demo"/*request.getParameter("marking_scheme1")*/);
		optionService.createNewOption(request.getParameter("option2"), question, "demo"/*request.getParameter("marking_scheme1")*/);
		optionService.createNewOption(request.getParameter("option3"), question, "demo"/*request.getParameter("marking_scheme1")*/);
		optionService.createNewOption(request.getParameter("option4"), question, "demo"/*request.getParameter("marking_scheme1")*/);
		optionService.createNewOption(request.getParameter("option5"), question, "demo"/*request.getParameter("marking_scheme1")*/);
		
		//TODO: UPDATE:::: CreateLessonTaskManager.pushTaskNotification(ppt, (IstarUser) request.getSession().getAttribute("user"),
		//		"A new Slide added with the template => "+ template +" created in the presentation wih ID ->"+ ppt.getId() );
	//	request.setAttribute("task_id", task_id);
		response.sendRedirect("/content/edit_lesson?lesson_id=" + assessment.getLesson().getId()+"&task_id="+task_id);

	}

	private CMSList getNewTree(HttpServletRequest request) {
		CMSList list = new CMSList();
		/*
		 * ArrayList<CMSTextItem> items =new ArrayList<CMSTextItem>();
		 * items.add(new CMSTextItem("")); items.add(new
		 * CMSTextItem(""));items.add(new CMSTextItem("")); items.add(new
		 * CMSTextItem(""));items.add(new CMSTextItem(""));
		 */
		list.setItems(new ArrayList<CMSTextItem>());
		for (Object key : request.getParameterMap().keySet()) {
			if (key.toString().startsWith("parent_")) {
				if (!request.getParameter(key.toString()).equalsIgnoreCase("")) {
					System.out.println("key>>>" + key.toString());
					CMSTextItem item = new CMSTextItem(request.getParameter(key.toString()));
					item.setList(getList(request, key));
					list.getItems().add(item);

					System.out.println("---->" + request.getParameter(key.toString()));
				}
			}
		}

		return list;
	}

	private CMSList getList(HttpServletRequest request, Object key) {
		CMSList list = new CMSList();
		/*
		 * ArrayList<CMSTextItem> items =new ArrayList<CMSTextItem>();
		 * items.add(new CMSTextItem("")); items.add(new
		 * CMSTextItem(""));items.add(new CMSTextItem(""));
		 */
		list.setItems(new ArrayList<CMSTextItem>());
		for (Object iterable_element : request.getParameterMap().keySet()) {
			if (iterable_element.toString().endsWith("_" + key.toString())) {
				CMSTextItem item = new CMSTextItem(request.getParameter(iterable_element.toString()));
				list.getItems().add(item);
				System.out.println("element here is -----" + iterable_element);
			}
		}
		// TODO Auto-generated method stub
		return list;
	}

	private CMSList getNewList(HttpServletRequest request) {
		CMSList list = new CMSList();
		ArrayList<CMSTextItem> items = new ArrayList<CMSTextItem>();
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		list.setItems(items);
		for (Object key : request.getParameterMap().keySet()) {
			if (key.toString().startsWith("list_item")) {
				if (!request.getParameter(key.toString()).equalsIgnoreCase("")) {
					System.out.println(key.toString());
					CMSTextItem item = new CMSTextItem(request.getParameter(key.toString()));
					list.getItems().add(item);
					System.out.println("---->" + request.getParameter(key.toString()));
				}
			}
		}

		return list;
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
