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
@WebServlet("/addAssessmentQuestion")
public class AddAssessmentQuestionController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAssessmentQuestionController() {
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
		int assessment_id = Integer.parseInt(request.getParameter("assessment_id"));
		Assessment assessment = dao.findById(assessment_id);

		Integer specifier = 1;      //update later for tree                      //Integer.parseInt(request.getParameter("specifier"));
		Set<LearningObjective> learningObjectiveSet=new HashSet<LearningObjective>();
		
		//Uncomment below after adding LOs
		/*
		String[] learningObjectiveIds = request.getParameterValues("learningObjectives");
		if(learningObjectiveIds!=null){
		for (int i = 0; i < learningObjectiveIds.length; i++) {
			learningObjectiveSet.add(learningObjectiveDAO.findById(Integer.parseInt(learningObjectiveIds[i])));
		}} 
		*/
		
		
		Question question = questionService.createNewQuestionForAssessment(request.getParameter("question_text"),
				request.getParameter("question_type"), Integer.parseInt(request.getParameter("difficulty_level")),
				Integer.parseInt(request.getParameter("assessment_id")), learningObjectiveSet, specifier,Integer.parseInt(request.getParameter("duration_in_sec"))); //replace 60 by the timeout from input form
		
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
		
		response.sendRedirect("/content/lesson/edit_assessment.jsp?assessment_id=" + assessment_id);

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
