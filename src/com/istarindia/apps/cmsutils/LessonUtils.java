package com.istarindia.apps.cmsutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.directive.Foreach;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.SlideTransition;
import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentQuestion;
import com.istarindia.apps.dao.AssessmentQuestionDAO;
import com.istarindia.apps.dao.Cmsession;
import com.istarindia.apps.dao.CmsessionDAO;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.QuestionDAO;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.cms.lessons.CMSList;
import com.istarindia.cms.lessons.CMSSlide;
import com.istarindia.cms.lessons.CMSTextItem;

/**
 * @author Vaibhav
 *
 */
public class LessonUtils {

	public StringBuffer getEditForm(Lesson lesson) {
		StringBuffer out = new StringBuffer();
		if (lesson.getPresentaion() != null) {
			Presentaion ppt = lesson.getPresentaion();

			out.append("<div class=' col-md-12 '>");
			out.append("<div class='panel panel-sea'>");
			out.append("<div class='panel-heading'>");
			out.append("<h3 class='panel-title'>");
			out.append("<i class='fa fa-tasks'></i>New Slide");
			out.append("</h3>");
			out.append("</div>");
			out.append("<div class='panel-body'>");
			out.append("<form class='form-inline' role='form' action='/content/fill_tempate.jsp'>");
			out.append("<input name='ppt_id' value='" + ppt.getId() + "' type='hidden'/>");

			out.append("<div class='form-group'>");
			out.append("<select class='form-control' name='slide_type' style='margin-right: 50px'>");

			for (String template : CMSRegistry.slideTemplates) {
				out.append("<option value='" + template + "'>" + template + "</option>");
			}
			out.append("</select>");

			out.append("</div>");
			out.append("<div class='form-group'  style='margin-right: 50px'>");
			out.append("<button type='submit' class='btn-u btn-u-default'>Add Slide</button>");
			out.append("</div>");
			out.append("<div class='checkbox'  style='margin-right: 50px'>");
			out.append("<a onclick='openWin(\"/content/lesson/preview.jsp?ppt_id=" + ppt.getId()
					+ "\")'  href='#' class='btn-u btn-u-default'>Mobile Preview</a>");
			out.append("</div>");
			out.append("<a onclick='openWinSpeaker(\"/content/lesson/speaker_preview.jsp?ppt_id=" + ppt.getId()
					+ "\")'  href='#' class='btn-u btn-u-default'>Speaker Preview</a>");
			out.append("</form>");
			out.append("</div>");
			out.append("</div>");
			out.append("</div>");

			out.append("<div class=' col-md-12 '>");
			out.append("<div class='panel panel-sea margin-bottom-40'>");
			out.append("<div class='panel-heading'>");
			out.append("<h3 class='panel-title'><i class='fa fa-edit'></i> List of Slides</h3>");
			out.append("</div>");
			out.append("<table class='table table-striped'>");
			out.append("<thead>");
			out.append("<tr>");
			out.append("<th>#</th>");
			out.append("<th>Slide Title</th>");
			out.append("<th>Action</th>");
			out.append("</tr>");
			out.append("</thead>");
			out.append("<tbody>");
			DBUtils db = new DBUtils();
			ArrayList<ArrayList<String>> data = db.getSlides(ppt.getId());
			for (int i = 0; i < data.size(); i++) {
				out.append("<tr>");
				out.append("<td>" + data.get(i).get(0) + "</td>");
				out.append("<td>" + data.get(i).get(1) + "</td>");
				out.append("<td>");
				out.append("<a class='btn btn-success btn-xs' href='/content/fill_tempate.jsp?ppt_id=" + ppt.getId()
						+ "&slide_id=" + data.get(i).get(0) + "&slide_type=" + data.get(i).get(2) + "'>"
						+ "<i class='fa fa-check'></i>Edit</a>");

				out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger btn-xs' "
						+ "href='/content/delete_slide?ppt_id=" + ppt.getId() + "&slide_id=" + data.get(i).get(0) + "'>"
						+ "<i class='fa fa-remove'></i>Delete</a>");

				out.append("</td>");
				out.append("</tr>");
			}

			out.append("</tbody>");
			out.append("</table>");
			out.append("</div>");
			out.append("</div>");
		} else if (lesson.getGame() != null) {
			out.append("This is where we wil have a form to generate a Asssesment Screen Input....");
		} else if (lesson.getAssessment() != null) {
			// out.append("This is where we wil have a form to generate a
			// Asssesment Screen Input....");

			Assessment assessment = lesson.getAssessment();
			ArrayList<LearningObjective> items = new ArrayList<LearningObjective>(
					getLearningObjectivesOfAllSiblings(lesson.getId()));

			if (assessment.getAssessmentType() == null) {

				out.append("<div class=' col-md-12 '>" + "<div class='panel panel-sea'>" + "<div class='panel-heading'>"
						+ "<h3 class='panel-title'><i class='fa fa-tasks'>" + "</i>Assessment Details</h3></div>"
						+ "<div class='panel-body'> "
						+ "<form action='/content/update_assessment' id='sky-form4' class='sky-form' method='POST'> "
						+ "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
						+ "<fieldset><div class='row'><section class='col col-6'><label>Assessment Type</label> "
						+ "<label class='input'><select class='form-control valid' name='assessment_type' style='margin-right: 50px'>"
						+ "<option value='STATIC'>STATIC</option>" + "<option value='ADAPTIVE'>ADAPTIVE</option>"
						+ "<option value='TREE'>TREE</option>"
						+ "<option value='RANDOM'>RANDOM</option></select></label> </section> "
						+ "<section class='col col-6'> <label>Number of Questions</label> <label class='input'> "
						+ "<input value='' type='number' name='number_of_questions' placeholder='Number of questions'> <input value='' type='hidden' name='number_of_questions' placeholder='Number of questions'> </label> </section> ");

				out.append(" </div>");
				out.append("</fieldset> "
						+ "<footer> <button type='submit' style='float: right' class='btn-u'>Proceed</button> </footer></form></div></div></div>");
			} 
			else {
				out.append("<div class=' col-md-12 '>" + "<div class='panel panel-sea'>" + "<div class='panel-heading'>"
						+ "<h3 class='panel-title'><i class='fa fa-tasks'>" + "</i>Question Details</h3></div>"
						+ "<div class='panel-body'> "
						+ "<form action='/content/add_question' id='sky-form4' class='sky-form' method='POST'> "
						+ "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
						+ "<fieldset>");

				out.append(
						"<section><label class='label'>List of Learning Objectives in this Session</label> <div class='row'>");

				for (LearningObjective obj : items) {

					out.append("<div class='col col-12'><label class='checkbox'>"
							+ "<input type='checkbox' name='learningObjectives' checked='checked' value=" + obj.getId()
							+ "><i></i>" + obj.getTitle() + "</label></div>");
				}
				out.append("</div></section><section><label>Question Type</label> " + "<label class='input'>"
						+ "<select class='form-control valid' name='question_type' style='margin-right: 50px'>"
						+ "<option value='1'>Type1</option>" + "<option value='2'>Type2</option>"
						+ "<option value='3'>Type3</option>"
						+ "<option value='4'>Type4</option></select></label> </section>"
						+ "<section><label>Difficulty Level</label> " + "<label class='input'>"
						+ "<select class='form-control valid' name='difficulty_level' style='margin-right: 50px'>"
						+ "<option value='1'>1(Hardest)</option>" + "<option value='2'>2</option>"
						+ "<option value='3'>3</option>" + "<option value='4'>4</option>"
						+ "<option value='5'>5</option>" + "<option value='6'>6</option>"
						+ "<option value='7'>7</option>" + "<option value='8'>8</option>"
						+ "<option value='9'>9</option>"
						+ "<option value='10'>10(Easiest)</option></select></label> </section>"
						+ "<section><label>Question Text</label> "
						+ "<label class='input'> <TEXTAREA NAME='question_text' ROWS='5' cols='75'></TEXTAREA> </label> </section> "
						+ "<section> <label>Option 1</label> <label class='input'> "
						+ "<TEXTAREA NAME='option1' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
						+ "<section> <label>Option 2</label> <label class='input'> "
						+ "<TEXTAREA NAME='option2' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
						+ "<section> <label>Option 3</label> <label class='input'> "
						+ "<TEXTAREA NAME='option3' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
						+ "<section> <label>Option 4</label> <label class='input'> "
						+ "<TEXTAREA NAME='option4' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
						+ "<section> <label>Option 5</label> <label class='input'> "
						+ "<TEXTAREA NAME='option5' ROWS='2' cols='25'></TEXTAREA> </label> </section> </fieldset> "
						+ "<footer> <button type='submit' class='btn-u'>Proceed</button> </footer></form></div></div></div>");

				out.append("<div class=' col-md-12 '>");
				out.append("<div class='panel panel-sea margin-bottom-40'>");
				out.append("<div class='panel-heading'>");
				out.append("<h3 class='panel-title'><i class='fa fa-edit'></i> List of Questions</h3>");
				out.append("</div>");
				out.append("<table class='table table-striped'>");
				out.append("<thead>");
				out.append("<tr>");
				out.append("<th>#</th>");
				out.append("<th>Question Title</th>");
				out.append("<th>Action</th>");
				out.append("</tr>");
				out.append("</thead>");
				out.append("<tbody>");
				DBUtils db = new DBUtils();
				ArrayList<ArrayList<String>> data = db.getQuestions(assessment.getId());
				for (int i = 0; i < data.size(); i++) {
					
					AssessmentQuestionDAO aqdao = new AssessmentQuestionDAO();
					AssessmentQuestion aq = new AssessmentQuestion();
					aq.setQuestion(new QuestionDAO().findById(Integer.parseInt(data.get(i).get(0))));
					
					out.append("<tr>");
					out.append("<td>" + data.get(i).get(0) + "</td>");
					out.append("<td>" + data.get(i).get(1) + "</td>");
					out.append("<td>");
					out.append("<a class='btn btn-success btn-xs' href='#' >" + "<i class='fa fa-check'></i>Edit</a>");

					out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger btn-xs' "
							+ "href='/content/delete_question?question_id="+data.get(i).get(0)+"&assessment_id="+aqdao.findByExample(aq).get(0)+"'>" + "<i class='fa fa-remove'></i>Delete</a>");

					out.append("</td>");
					out.append("</tr>");
				}

				out.append("</tbody>");
				out.append("</table>");
				out.append("</div>");
				out.append("</div>");
			}
		}

		return out;
	}

	private ArrayList<LearningObjective> getLearningObjectivesOfAllSiblings(Integer id) {
		// TODO Auto-generated method stub
		ArrayList<LearningObjective> list = new ArrayList<>();
		IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		Lesson lesson = new LessonDAO().findById(id);
		Cmsession cmsession = lesson.getCmsession();
		List<Lesson> siblings = cmsession.getAllLessons(cmsession.getId());

		for (Lesson lessonItem : siblings) {
			String sql = "select * from learning_objective_lesson where lessonid=" + lessonItem.getId()
					+ " order by learning_objectiveid";
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<HashMap<String, Object>> results = query.list();
			LearningObjectiveDAO learningObjectiveDao = new LearningObjectiveDAO();
			LearningObjective learningObjective = new LearningObjective();
			for (HashMap<String, Object> object : results) {
				try {
					learningObjective = learningObjectiveDao.findById((Integer) object.get("learning_objectiveid"));
					if (!list.contains(learningObjective)) {
						list.add(learningObjective);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public StringBuffer getReviewForm(Lesson lesson) {
		StringBuffer out = new StringBuffer();
		if (lesson.getPresentaion() != null) {
			Presentaion ppt = lesson.getPresentaion();

			out.append("<div class=' col-md-12 '>");
			out.append("<div class='panel panel-sea margin-bottom-40'>");
			out.append("<div class='panel-heading'>");
			out.append("<h3 class='panel-title'><i class='fa fa-edit'></i> List of Slides to Review</h3>");
			out.append("</div>");
			out.append("<table class='table table-striped'>");
			out.append("<thead>");
			out.append("<tr>");
			out.append("<th>#</th>");
			out.append("<th>Slide Title</th>");
			out.append("<th>Action</th>");
			out.append("</tr>");
			out.append("</thead>");
			out.append("<tbody>");
			DBUtils db = new DBUtils();
			ArrayList<ArrayList<String>> data = db.getSlides(ppt.getId());
			for (int i = 0; i < data.size(); i++) {
				out.append("<tr>");
				out.append("<td>" + data.get(i).get(0) + "</td>");
				out.append("<td>" + data.get(i).get(1) + "</td>");
				out.append("<td>");
				out.append("<a class='btn btn-success btn-xs' href='/content/fill_tempate_review.jsp?ppt_id="
						+ ppt.getId() + "&slide_id=" + data.get(i).get(0) + "&slide_type=" + data.get(i).get(2) + "'>"
						+ "<i class='fa fa-check'></i>View Slide</a>");

				out.append("</td>");
				out.append("</tr>");
			}

			out.append("</tbody>");
			out.append("</table>");
			out.append("</div>");
			out.append("</div>");
		} else if (lesson.getGame() != null) {

		} else if (lesson.getAssessment() != null) {

		}

		return out;
	}

	public StringBuffer getEditProfileEdit(CMSSlide slide, Presentaion ppt, Boolean newSlide) {
		ImageDAO dao = new ImageDAO();
		ArrayList<Image> images = (ArrayList<Image>) dao.findByProperty("sessionid",
				ppt.getLesson().getCmsession().getId());

		CMSList newList = new CMSList();
		newList.setList_type(slide.getList().getList_type());
		newList.setItems(new ArrayList<>());
		for (CMSTextItem item : slide.getList().getItems()) {
			if (item.getText().trim().equalsIgnoreCase("")) {
			} else {
				newList.getItems().add(item);
				try {
					if (item.getList().getItems().size() != 0) {
						// System.err.println("111");
						CMSList childList = new CMSList();
						ArrayList<CMSTextItem> items3 = new ArrayList<>();
						childList.setItems(items3);
						for (CMSTextItem childItem : item.getList().getItems()) {
							if (!childItem.getText().trim().startsWith("$slide")) {
								childList.getItems().add(childItem);
							}
						}
						item.setList(childList);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
		}
		if (!newSlide) {
			slide.setList(newList);
		}

		StringBuffer out = new StringBuffer();
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		VelocityContext context = new VelocityContext();
		context.put("slide", slide);
		context.put("images", images);
		//context.put("tag_string", tagString);
		context.put("list_types", CMSRegistry.listTypes);
		Template t = ve.getTemplate(slide.getTemplateName() + "_edit.vm");
		StringWriter writer = new StringWriter();
		try {
			t.merge(context, writer);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.append(writer.toString());
		return out;

	}

	public CMSSlide convertSlide(Slide slide) {
		CMSSlide cMSlide = new CMSSlide();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CMSSlide.class);
			InputStream in = IOUtils.toInputStream(slide.getSlideText(), "UTF-8");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			cMSlide = (CMSSlide) jaxbUnmarshaller.unmarshal(in);
			cMSlide.setTeacherNotes(slide.getTeacherNotes());
			cMSlide.setStudentNotes(slide.getStudentNotes());
			System.out.println(slide);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cMSlide;

	}

	public static StringBuffer getCreatePane() {
		return new StringBuffer("AAAA");
	}

	public static StringBuffer getGeneralCreatePane(CMSSlide slide, String pptID) {
		StringBuffer out = new StringBuffer();
		out.append("<input type='hidden' name='template' value='" + slide.getTemplateName() + "'> ");
		out.append("<input type='hidden' name='ppt_id' value=" + pptID + ">");

		out.append("<fieldset><section><label class='label'>Select Slide Transition</label> <label class='select'>");
		out.append("<select name='slideTransition' value='" + slide.getTransition() + "'>");
		
		for (String type : SlideTransition.SlideTransitionTypes) {
	 		if (type.equalsIgnoreCase(slide.getTransition())) {
	 			out.append("<option selected='selected' value='"+type+"'>"+type+"</option>");
	 		}else {
	 			out.append("<option selected='selected' value='"+type+"'>"+type+"</option>");
	 		}
		}
		out.append("</select>");
		out.append("</section></fieldset>");

		out.append(
				"<fieldset><section><label class='label'>Select Background Transition</label> <label class='select'>");
		out.append("<select name='backgroundTransition' value='" + slide.getBackgroundTransition() + "'>");
		
		for (String type : SlideTransition.BackgroundTransition) {
	 		if (type.equalsIgnoreCase(slide.getBackgroundTransition())) {
	 			out.append("<option selected='selected' value='"+type+"'>"+type+"</option>");
	 		}else {
	 			out.append("<option selected='selected' value='"+type+"'>"+type+"</option>");
	 		}
		}
		out.append("</select>");
		out.append("</section></fieldset>");

		out.append(
				"<fieldset><section><label class='label'>Select Slide Background color</label> <label class='select'>");
		out.append("<input type='color' id='slide_color' name='backgroundColor' value='"+slide.getBackground()+"'>");

		
		out.append("</section>");
		out.append("</fieldset>");
		
		out.append("<fieldset>");
		out.append("<section>");
		out.append("<label class='label'>Teacher Notes</label> ");
		out.append("<label class='textarea'> <textarea rows='3' name='teacher_notes' ");
		out.append("data-parsley-required='true' data-parsley-length='[5,250]' ");
		out.append("data-parsley-required-message='Please provide teacher notes' ");
		out.append("data-parsley-length-message='It should be 5-250 characters long'>");
		out.append(slide.getTeacherNotes() + "</textarea>");
		out.append("</label>");
		out.append("<div class='note'> <strong>Note:</strong> This is where we will put in the teacher notes.</div>");
		out.append("</section>");
		out.append("</fieldset>");
		
		
		out.append("<fieldset>");
		out.append("<section>");
		out.append("<label class='label'>Student Notes</label> ");
		out.append("<label class='textarea'> <textarea rows='3' name='student_notes' ");
		out.append("data-parsley-required='true' data-parsley-length='[5,250]' ");
		out.append("data-parsley-required-message='Please provide student notes' ");
		out.append("data-parsley-length-message='It should be 5-250 characters long'>");
		out.append(slide.getStudentNotes() + "</textarea>");
		out.append("</label>");
		out.append("<div class='note'> <strong>Note:</strong> This is where we will put in the student notes.</div>");
		out.append("</section>");
		out.append("</fieldset>");
		
		out.append("<footer><button type='submit' class='btn-u'>Save Changes</button></footer>");

		return out;
	}
}