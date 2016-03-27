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
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
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

		Integer specifier = 0;      //update later for tree                      //Integer.parseInt(request.getParameter("specifier"));
		String[] learningObjectiveIds = request.getParameterValues("learningObjectives");
		Set<LearningObjective> learningObjectiveSet=new HashSet<LearningObjective>();
		if(learningObjectiveIds!=null){
		for (int i = 0; i < learningObjectiveIds.length; i++) {
			learningObjectiveSet.add(learningObjectiveDAO.findById(Integer.parseInt(learningObjectiveIds[i])));
		}} 
		
		Question question = questionService.createNewQuestionForAssessment(request.getParameter("question_text"),
				request.getParameter("question_type"), Integer.parseInt(request.getParameter("difficulty_level")),
				Integer.parseInt(request.getParameter("assessment_id")), learningObjectiveSet, specifier);
		
		String[] answers = request.getParameterValues("answers");
		Integer[] optionValue = new Integer[5];
		if(answers!=null){
			for (int j = 0; j < answers.length; j++){
				optionValue[Integer.parseInt(answers[j])-1] = 1;
			}
		}

		optionService.createNewOption(request.getParameter("option1"), question, optionValue[0]);
		optionService.createNewOption(request.getParameter("option2"), question, optionValue[1]);
		optionService.createNewOption(request.getParameter("option3"), question, optionValue[2]);
		optionService.createNewOption(request.getParameter("option4"), question, optionValue[3]);
		optionService.createNewOption(request.getParameter("option5"), question, optionValue[4]);
		
		TaskDAO dao1 = new TaskDAO();
		Task t = new Task();
		t.setItemType("LESSON");
		t.setItemId(assessment.getLesson().getId());
		
		response.sendRedirect("/content/edit_lesson?lesson_id=" + assessment.getLesson().getId()+"&task_id="+dao1.findByExample(t).get(0).getId());

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
