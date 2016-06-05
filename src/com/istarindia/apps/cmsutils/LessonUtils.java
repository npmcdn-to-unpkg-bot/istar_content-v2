package com.istarindia.apps.cmsutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.ImageUtils;
import com.istarindia.apps.SlideTransition;
import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.AssessmentOption;
import com.istarindia.apps.dao.Cmsession;
import com.istarindia.apps.dao.Game;
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
import com.istarindia.apps.dao.Video;
import com.istarindia.apps.dao.VideoDAO;
import com.istarindia.apps.services.AssessmentService;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.QuestionService;
import com.istarindia.cms.lessons.CMSList;
import com.istarindia.cms.lessons.CMSSlide;
import com.istarindia.cms.lessons.CMSTextItem;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Vaibhav
 *
 */
public class LessonUtils {
	
    public StringBuffer getFormForAssessmentQuestionEdit(int assessment_id, int question_id) {
        StringBuffer out = new StringBuffer();
        
        Assessment assessment = (new AssessmentDAO()).findById(assessment_id);
        Question question = (new QuestionDAO()).findById(question_id);
        
        QuestionService service = new QuestionService();
        
        ArrayList<AssessmentOption> options = (ArrayList<AssessmentOption>) service.getAllOptionsInTheQuestion(question_id);
        String markingScheme[] = new String[5];
        
        int z = 0;
        for (AssessmentOption option : options) {

			try {
				if(option.getMarkingScheme()==1) {
					markingScheme[z] = "checked=checked";	//correct answer with marking scheme 1
				} else {
					markingScheme[z] = "";					//wrong answer with marking scheme 0
				}
			} catch (Exception e) {
				markingScheme[z] = "";						//wrong answer with marking scheme NULL
			}
        	z++;
		}
        
        //TODO: get learning objectives of  the assessment
        //ArrayList<LearningObjective> items = new ArrayList<LearningObjective>(getLearningObjectivesOfAllSiblings(lesson.getId()));

            Integer number_of_questions = new Integer(0);
            AssessmentService assessmentService = new AssessmentService();
            number_of_questions = assessmentService.getNumberOfQuestionsInAssessment(assessment.getId());
            //if (number_of_questions < assessment.getNumber_of_questions()) {
            	
                //Fix upload-assessment part anad  uncomment the below part
            	/*out.append("<div class=' col-md-12 '> <div class='panel panel-sea'> "
                		+ "<div class='panel-heading'> <h3 class='panel-title'> "
                		+ "<i class='fa fa-tasks'></i>Upload Questions </h3> </div>  <div class='panel-body'>"
                		+ "<form action='assessment_upload' class='sky-form' method='post' enctype='multipart/form-data'>  "
                		+ "<fieldset> <section><p>File input &nbsp;&nbsp;&nbsp;&nbsp; (Download sample format from"
                		+ "<a href='assets/excel/format.xls' style='color: RED'> here</a> )</p>"
						+ "<label for='file' class='input input-file'> <div class='button'>"
                		+ "<input type='file' id='file' name='file' onchange='DisplayFilePath()'>Browse</div> "
                		+ "<input type='text' id='formfield' readonly> </label> </section> </fieldset> <footer> "
                		+ "<button type='submit' class='btn-u'>Submit</button> </footer> </form> </div> </div></div>");
            	*/
            	
                out.append("<div class=' col-md-12 '>"
                        + "<div class='panel panel-sea'>"
                        + "<div class='panel-heading'>"
                        + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                        + "</i>Question Details</h3></div>"
                        + "<div class='panel-body'> "
                        + "<form action='/content/edit_question' id='sky-form4' class='sky-form' method='POST'> "
                        + "<input type='hidden' name='assessment_id' value=" + assessment_id + "> "
                        + "<input type='hidden' name='question_id' value=" + question_id + "> "
                        + "<fieldset>");
                
                //enable below block after learning objectives are added
                /*
                out.append("<section><label>List of Learning Objectives in this Session</label> <div class='row'>");
                
                
                for (LearningObjective obj : items) {

                    out.append("<div class='col col-12'><label class='checkbox'>"
                            + "<input type='checkbox' name='learningObjectives' checked='checked' value="
                            + obj.getId() + "><i></i>" + obj.getTitle() + "</label></div></div></section></br>");
                }*/
                
                out.append("<div class='row'><section class='col col-md-4'><label>Question Type</label> "
                        + "<label class='input'>"
                        + "<select class='form-control valid' id='qType' name='question_type' style='margin-right: 50px' '>");

                try {
					if(question.getQuestionType().equalsIgnoreCase("1")){
					out.append( "<option value='1' selected='selected'>Single correct option</option>"
					        + "<option value='2'>Multiple correct options</option>");
					        }

					else if(question.getQuestionType().equalsIgnoreCase("2")){
					out.append( "<option value='1'>Single correct option</option>"
					        + "<option value='2' selected='selected'>Multiple correct options</option>");
					        }
				} catch (Exception e) {
					out.append( "<option value='1'>Single correct option</option>"
					        + "<option value='2'>Multiple correct options</option>");
				}
                
                out.append( "</select></label> </section>"
                        + "<section class='col col-md-4'><label>Difficulty Level</label> "
                        + "<label class='input'>"
                        + "<select class='form-control valid' name='difficulty_level' style='margin-right: 50px' >" 
                        + "<option value='"+question.getDifficultyLevel()+"' selected='selected'>"+question.getDifficultyLevel()+" (Selected)</option>"
                        + "<option value='1'>1(EASIEST)</option> <option value='2'>2</option> <option value='3'>3</option>"
                        + "<option value='4'>4</option> <option value='5'>5</option> <option value='6'>6</option>"
                        + "<option value='7'>7</option> <option value='8'>8</option> <option value='9'>9</option>"
                        + "<option value='10'>10(HARDEST)</option>"
                        + "</select></label> </section>"
                        + "<section class='col col-md-4'><label>Duration for Question (In Sec)</label> "
                        + "<label class='input'>"
                        + "<input type='number' value='"+question.getDurationInSec()+"' name='duration_in_sec' placeholder='Duration for Question'> <b class='tooltip tooltip-bottom-right'>"
                        + "Duration to Attempt Question</b>"
                        + "</label> </section>"
                        /*  + "<section class='col col-md-4'><label>Depth</label> <label class='input'> "
                         + "<select class='form-control valid' name='specifier' style='margin-right: 50px'>"
                         + "<option value='1'>1</option><option value='2'>2</option></select></label></section>"*/ //Add this later for tree type assessment

                        + "</div>"
                        + "<section><label>Question Text</label> "
                        + "<label class='input'> <TEXTAREA NAME='question_text' ROWS='5' cols='75' value='"+question.getQuestionText()+"'>"+question.getQuestionText()+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='1' "+markingScheme[0]+"><i></i>Option 1</label><label class='input'> "
                        + "<TEXTAREA NAME='option1' ROWS='2' cols='25' value='"+options.get(0)+"'>"+options.get(0).getText()+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='2' "+markingScheme[1]+"><i></i>Option 2</label><label class='input'> "
                        + "<TEXTAREA NAME='option2' ROWS='2' cols='25' value='"+options.get(1)+"'>"+options.get(1).getText()+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='3' "+markingScheme[2]+"><i></i>Option 3</label> <label class='input'> "
                        + "<TEXTAREA NAME='option3' ROWS='2' cols='25' value='"+options.get(2)+"'>"+options.get(2).getText()+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='4' "+markingScheme[3]+"><i></i>Option 4</label> <label class='input'> "
                        + "<TEXTAREA NAME='option4' ROWS='2' cols='25' value='"+options.get(3)+"'>"+options.get(3).getText()+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='5' "+markingScheme[4]+"><i></i>Option 5</label> <label class='input'> "
                        + "<TEXTAREA NAME='option5' ROWS='2' cols='25' value='"+options.get(4)+"'>"+options.get(4).getText()+"</TEXTAREA> </label> </section> "
                        + "<input type='hidden' name='option1_id' value=" + options.get(0).getId() + "> "
                        + "<input type='hidden' name='option2_id' value=" + options.get(1).getId() + "> "
                        + "<input type='hidden' name='option3_id' value=" + options.get(2).getId() + "> "
                        + "<input type='hidden' name='option4_id' value=" + options.get(3).getId() + "> "
                        + "<input type='hidden' name='option5_id' value=" + options.get(4).getId() + "> "
                        + "</fieldset> "
                        + "<footer> <button type='submit' id='checkBtn' class='btn-u'>Proceed</button> <label id='err' style='color:red'></label></footer></form></div></div></div>");
            //}
            
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
                out.append("<tr>");
                out.append("<td>" + data.get(i).get(0) + "</td>");
                out.append("<td>" + data.get(i).get(1) + "</td>");
                out.append("<td>");
                out.append("<a class='btn btn-success btn-xs' href='/content/edit_question?assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-check'></i>Edit</a>");

                out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger btn-xs' "
                        + "href='/content/delete_question?assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-remove'></i>Delete</a>");

                out.append("</td>");
                out.append("</tr>");
            }

            out.append("</tbody>");
            out.append("</table>");
            out.append("</div>");
            out.append("</div>");
            
    return out;
}
    
    
    public StringBuffer getAssessmentEditForm(int assessment_id) {
        StringBuffer out = new StringBuffer();
        
            Assessment assessment = (new AssessmentDAO()).findById(assessment_id);
           
            //TODO: get learning objectives of  the assessment
            //ArrayList<LearningObjective> items = new ArrayList<LearningObjective>(getLearningObjectivesOfAllSiblings(lesson.getId()));

            if (assessment.getAssessmentType() == null) {

                out.append("<div class=' col-md-12 '>"
                        + "<div class='panel panel-sea'>"
                        + "<div class='panel-heading'>"
                        + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                        + "</i>Assessment Details</h3></div>"
                        + "<div class='panel-body'> "
                        + "<form action='/content/update_assessment' id='sky-form4' class='sky-form' method='POST'> "
                        + "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
                        + "<fieldset><div class='row'><section class='col col-6'><label>Assessment Type</label> "
                        + "<label class='input'><select class='form-control valid' name='assessment_type' style='margin-right: 50px'>"
                        + "<option value='STATIC'>STATIC</option>"
                        + "<option value='ADAPTIVE'>ADAPTIVE</option>"
                        + "<option value='TREE'>TREE</option>"
                        + "<option value='RANDOM'>RANDOM</option></select></label> </section> "
                        + "<section class='col col-6'> <label>Number of Questions</label> <label class='input'> "
                        + "<input value='' type='number' name='number_of_questions' placeholder='Number of questions'>  </label> </section> ");
                out.append(" </div>");
                out.append("</fieldset>  <footer> <button type='submit' style='float: right' class='btn-u'>"
                		+ "Proceed</button> </footer></form></div></div></div>");
            } else {
                Integer number_of_questions = new Integer(0);
                AssessmentService assessmentService = new AssessmentService();
                number_of_questions = assessmentService.getNumberOfQuestionsInAssessment(assessment.getId());
                if (number_of_questions < assessment.getNumber_of_questions()) {
                	
                    //Fix upload-assessment part anad  uncomment the below part
                	/*out.append("<div class=' col-md-12 '> <div class='panel panel-sea'> "
                    		+ "<div class='panel-heading'> <h3 class='panel-title'> "
                    		+ "<i class='fa fa-tasks'></i>Upload Questions </h3> </div>  <div class='panel-body'>"
                    		+ "<form action='assessment_upload' class='sky-form' method='post' enctype='multipart/form-data'>  "
                    		+ "<fieldset> <section><p>File input &nbsp;&nbsp;&nbsp;&nbsp; (Download sample format from"
                    		+ "<a href='assets/excel/format.xls' style='color: RED'> here</a> )</p>"
							+ "<label for='file' class='input input-file'> <div class='button'>"
                    		+ "<input type='file' id='file' name='file' onchange='DisplayFilePath()'>Browse</div> "
                    		+ "<input type='text' id='formfield' readonly> </label> </section> </fieldset> <footer> "
                    		+ "<button type='submit' class='btn-u'>Submit</button> </footer> </form> </div> </div></div>");
                	*/
                	
                    out.append("<div class=' col-md-12 '>"
                            + "<div class='panel panel-sea'>"
                            + "<div class='panel-heading'>"
                            + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                            + "</i>Question Details</h3></div>"
                            + "<div class='panel-body'> "
                            + "<form action='/content/addAssessmentQuestion' id='sky-form4' class='sky-form' method='POST'> "
                            + "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
                            + "<fieldset>");
                    
                    //enable below block after learning objectives are added
                    /*
                    out.append("<section><label>List of Learning Objectives in this Session</label> <div class='row'>");
                    
                    
                    for (LearningObjective obj : items) {

                        out.append("<div class='col col-12'><label class='checkbox'>"
                                + "<input type='checkbox' name='learningObjectives' checked='checked' value="
                                + obj.getId() + "><i></i>" + obj.getTitle() + "</label></div></div></section></br>");
                    }*/
                    
                    out.append("<div class='row'><section class='col col-md-4'><label>Question Type</label> "
                            + "<label class='input'>"
                            + "<select class='form-control valid' id='qType' name='question_type' style='margin-right: 50px'>"
                            + "<option value='1'>Single correct option</option>"
                            + "<option value='2'>Multiple correct options</option></select></label> </section>"
                            + "<section class='col col-md-4'><label>Difficulty Level</label> "
                            + "<label class='input'>"
                            + "<select class='form-control valid' name='difficulty_level' style='margin-right: 50px'>"
                            + "<option value='1'>1(EASIEST)</option> <option value='2'>2</option> <option value='3'>3</option>"
                            + "<option value='4'>4</option> <option value='5'>5</option> <option value='6'>6</option>"
                            + "<option value='7'>7</option> <option value='8'>8</option> <option value='9'>9</option>"
                            + "<option value='10'>10(HARDEST)</option></select></label> </section>"
                            + "<section class='col col-md-4'><label>Duration for Question (In Sec)</label> "
                            + "<label class='input'>"
                            + "<input type='number' value=60 name='duration_in_sec' placeholder='Duration for Question'> <b class='tooltip tooltip-bottom-right'>"
                            + "Duration to Attempt Question</b>"
                            + "</label> </section>"
                            /*  + "<section class='col col-md-4'><label>Depth</label> <label class='input'> "
                             + "<select class='form-control valid' name='specifier' style='margin-right: 50px'>"
                             + "<option value='1'>1</option><option value='2'>2</option></select></label></section>"*/ //Add this later for tree type assessment

                            + "</div>"
                            + "<section><label>Question Text</label> "
                            + "<label class='input'> <TEXTAREA NAME='question_text' ROWS='5' cols='75'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='1'><i></i>Option 1</label><label class='input'> "
                            + "<TEXTAREA NAME='option1' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='2'><i></i>Option 2</label><label class='input'> "
                            + "<TEXTAREA NAME='option2' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='3'><i></i>Option 3</label> <label class='input'> "
                            + "<TEXTAREA NAME='option3' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='4'><i></i>Option 4</label> <label class='input'> "
                            + "<TEXTAREA NAME='option4' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='5'><i></i>Option 5</label> <label class='input'> "
                            + "<TEXTAREA NAME='option5' ROWS='2' cols='25'></TEXTAREA> </label> </section> </fieldset> "
                            + "<footer> <button type='submit' id='checkBtn' class='btn-u'>Proceed</button> <label id='err' style='color:red'></label></footer></form></div></div></div>");
                }
                
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
                    out.append("<tr>");
                    out.append("<td>" + data.get(i).get(0) + "</td>");
                    out.append("<td>" + data.get(i).get(1) + "</td>");
                    out.append("<td>");
                    out.append("<a class='btn btn-success btn-xs' href='/content/lesson/edit_assessment.jsp?assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-check'></i>Edit</a>");

                    out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger btn-xs' "
                            + "href='/content/delete_question?assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-remove'></i>Delete</a>");

                    out.append("</td>");
                    out.append("</tr>");
                }

                out.append("</tbody>");
                out.append("</table>");
                out.append("</div>");
                out.append("</div>");
            }


        return out;
    }

    public StringBuffer getEditForm(Lesson lesson, int taskID) {
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
            out.append("<form id='update_order' action='/content/update_course?ppt_id="+ ppt.getId()+"'>"
            		+ "<input id='action' name='action' type='hidden' value='reorder'> "
            		+ "<input id='order_holder' name='order_holder' type='hidden'>"
            		+ "<input id='entity_type' name='entity_type' type='hidden' value='slides'>"
            		+ "<input id='task_id' name='task_id' type='hidden' value="+taskID+">"
            		+ "<button type='submit' class='btn-u' id='kammm'>Save Slide Order</button></form>");
            out.append("</div>");
            out.append("</div>");
            out.append("</div>");

            out.append("<div class=' col-md-12 '>");
            out.append("<div class='panel panel-sea margin-bottom-40'>");
            out.append("<div class='panel-heading'>");
            out.append("<h3 class='panel-title'><i class='fa fa-edit'></i> List of Slides</h3>");
            out.append("</div>");
            out.append( "<table class='table table-striped' id='sortable'>");
            out.append("<thead>");
            out.append("<tr>");
            out.append("<th>#</th>");
            out.append("<th>Slide Title</th>");
            out.append("<th>Action</th>");
            out.append("<th>Slide Template</th>");
            out.append("<th>Order Number</th>");
            out.append("</tr>");
            out.append("</thead>");
            out.append("<tbody id='slidess_ord'>");
            DBUtils db = new DBUtils();
            ArrayList<ArrayList<String>> data = db.getSlides(ppt.getId());
            for (int i = 0; i < data.size(); i++) {
                out.append("<tr class='mammama' id='"+data.get(i).get(0)+"'>");
                out.append("<td id='id'>" + data.get(i).get(0) + "</td>");
                out.append("<td>" + data.get(i).get(2) + "</td>");
                out.append("<td>");
                out.append("<a class='btn btn-success btn-xs' href='/content/fill_tempate.jsp?ppt_id=" + ppt.getId()
                        + "&slide_id=" + data.get(i).get(0) + "&slide_type=" + data.get(i).get(3) + "'>"
                        + "<i class='fa fa-check'></i>Edit</a>");

                out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger btn-xs' "
                        + "href='/content/delete_slide?ppt_id=" + ppt.getId() + "&slide_id=" + data.get(i).get(0) + "'>"
                        + "<i class='fa fa-remove'></i>Delete</a>");

                out.append("</td>");
                out.append("<td>" + data.get(i).get(3) + "</td>");
                out.append("<td>" + data.get(i).get(1) + "</td>");
                out.append("</tr>");
            }

            out.append(" </tbody>");
            out.append("</table>");
            out.append("</div>");
            out.append("</div>");
        } else if (lesson.getGame() != null) {
            Game game = lesson.getGame();
            out.append("This is where we wil have a form to generate a Asssesment Screen Input....");

            out.append("<div class=' col-md-12 '>"
                    + "<div class='panel panel-sea'>"
                    + "<div class='panel-heading'>"
                    + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                    + "</i>Assessment Details</h3></div>"
                    + "<div class='panel-body'> "
                    + "<form action='/content/create_game' id='sky-form4' class='sky-form' method='POST'> "
                    + "<input type='hidden' name='game_id' value=" + game.getId() + "> "
                    + "<input type='hidden' name='lesson_id' value=" + lesson.getId() + "> "
                    + "<input type='hidden' name='task_id' value=" + taskID + "> "
                    + "<fieldset><div class='row'>"
                    + "<section class='col col-6'> <label>Game XML</label> <label class='input'> ");
            if (game.getGameObject() != null) {
                out.append("<textarea name='game_xml' style='width:1100px;height:500px'>" + game.getGameObject() + "</textarea>  </label> </section> ");
            } else {
                out.append("<textarea name='game_xml' style='width:1100px;height:500px'></textarea>  </label> </section> ");
            }
            out.append(" </div>");
            out.append("</fieldset> "
                    + "<footer> <button type='submit' style='float: right' class='btn-u'>Proceed</button> </footer></form></div></div></div>");

        } else if (lesson.getAssessment() != null) {
            //out.append("This is where we wil have a form to generate a Asssesment Screen Input....");

            Assessment assessment = lesson.getAssessment();
            ArrayList<LearningObjective> items = new ArrayList<LearningObjective>(getLearningObjectivesOfAllSiblings(lesson.getId()));

            if (assessment.getAssessmentType() == null) {

                out.append("<div class=' col-md-12 '>"
                        + "<div class='panel panel-sea'>"
                        + "<div class='panel-heading'>"
                        + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                        + "</i>Assessment Details</h3></div>"
                        + "<div class='panel-body'> "
                        + "<form action='/content/update_assessment' id='sky-form4' class='sky-form' method='POST'> "
                        + "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
                        + "<fieldset><div class='row'><section class='col col-6'><label>Assessment Type</label> "
                        + "<label class='input'><select class='form-control valid' name='assessment_type' style='margin-right: 50px'>"
                        + "<option value='STATIC'>STATIC</option>"
                        + "<option value='ADAPTIVE'>ADAPTIVE</option>"
                        + "<option value='TREE'>TREE</option>"
                        + "<option value='RANDOM'>RANDOM</option></select></label> </section> "
                        + "<section class='col col-6'> <label>Number of Questions</label> <label class='input'> "
                        + "<input value='' type='number' name='number_of_questions' placeholder='Number of questions'>  </label> </section> ");

                out.append(" </div>");
                out.append("</fieldset> "
                        + "<footer> <button type='submit' style='float: right' class='btn-u'>Proceed</button> </footer></form></div></div></div>");
            } else {
                Integer number_of_questions = new Integer(0);
                AssessmentService assessmentService = new AssessmentService();
                number_of_questions = assessmentService.getNumberOfQuestionsInAssessment(assessment.getId());
                if (number_of_questions < assessment.getNumber_of_questions()) {
                    out.append("<div class=' col-md-12 '> <div class='panel panel-sea'> "
                    		+ "<div class='panel-heading'> <h3 class='panel-title'> "
                    		+ "<i class='fa fa-tasks'></i>Upload Questions </h3> </div>  <div class='panel-body'>"
                    		+ "<form action='assessment_upload' class='sky-form' method='post' enctype='multipart/form-data'>  "
                    		+ "<fieldset> <section><p>File input &nbsp;&nbsp;&nbsp;&nbsp; (Download sample format from"
                    		+ "<a href='assets/excel/format.xls' style='color: RED'> here</a> )</p>"
							+ "<label for='file' class='input input-file'> <div class='button'>"
                    		+ "<input type='file' id='file' name='file' onchange='DisplayFilePath()'>Browse</div> "
                    		+ "<input type='text' id='formfield' readonly> </label> </section> </fieldset> <footer> "
                    		+ "<button type='submit' class='btn-u'>Submit</button> </footer> </form> </div> </div></div>");
                	
                    out.append("<div class=' col-md-12 '>"
                            + "<div class='panel panel-sea'>"
                            + "<div class='panel-heading'>"
                            + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                            + "</i>Question Details</h3></div>"
                            + "<div class='panel-body'> "
                            + "<form action='/content/add_question' id='sky-form4' class='sky-form' method='POST'> "
                            + "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
                            + "<fieldset>");

                    out.append("<section><label>List of Learning Objectives in this Session</label> <div class='row'>");

                    for (LearningObjective obj : items) {

                        out.append("<div class='col col-12'><label class='checkbox'>"
                                + "<input type='checkbox' name='learningObjectives' checked='checked' value="
                                + obj.getId() + "><i></i>" + obj.getTitle() + "</label></div>");
                    }
                    out.append("</div></section></br>"
                            + "<div class='row'><section class='col col-md-4'><label>Question Type</label> "
                            + "<label class='input'>"
                            + "<select class='form-control valid' id='qType' name='question_type' style='margin-right: 50px'>"
                            + "<option value='1'>Single correct option</option>"
                            + "<option value='2'>Multiple correct options</option></select></label> </section>"
                            + "<section class='col col-md-4'><label>Difficulty Level</label> "
                            + "<label class='input'>"
                            + "<select class='form-control valid' name='difficulty_level' style='margin-right: 50px'>"
                            + "<option value='1'>1(EASIEST)</option> <option value='2'>2</option> <option value='3'>3</option>"
                            + "<option value='4'>4</option> <option value='5'>5</option> <option value='6'>6</option>"
                            + "<option value='7'>7</option> <option value='8'>8</option> <option value='9'>9</option>"
                            + "<option value='10'>10(HARDEST)</option></select></label> </section>"
                            + "<section class='col col-md-4'><label>Duration for Question (In Sec)</label> "
                            + "<label class='input'>"
                            + "<input type='number' value=60 name='duration_in_sec' placeholder='Duration for Question'> <b class='tooltip tooltip-bottom-right'>"
                            + "Duration to Attempt Question</b>"
                            + "</label> </section>"
                            /*  + "<section class='col col-md-4'><label>Depth</label> <label class='input'> "
                             + "<select class='form-control valid' name='specifier' style='margin-right: 50px'>"
                             + "<option value='1'>1</option><option value='2'>2</option></select></label></section>"*/ //Add this later for tree type assessment

                            + "</div>"
                            + "<section><label>Question Text</label> "
                            + "<label class='input'> <TEXTAREA NAME='question_text' ROWS='5' cols='75'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='1'><i></i>Option 1</label><label class='input'> "
                            + "<TEXTAREA NAME='option1' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='2'><i></i>Option 2</label><label class='input'> "
                            + "<TEXTAREA NAME='option2' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='3'><i></i>Option 3</label> <label class='input'> "
                            + "<TEXTAREA NAME='option3' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='4'><i></i>Option 4</label> <label class='input'> "
                            + "<TEXTAREA NAME='option4' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='5'><i></i>Option 5</label> <label class='input'> "
                            + "<TEXTAREA NAME='option5' ROWS='2' cols='25'></TEXTAREA> </label> </section> </fieldset> "
                            + "<footer> <button type='submit' id='checkBtn' class='btn-u'>Proceed</button> <label id='err' style='color:red'></label></footer></form></div></div></div>");
                }
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
                    out.append("<tr>");
                    out.append("<td>" + data.get(i).get(0) + "</td>");
                    out.append("<td>" + data.get(i).get(1) + "</td>");
                    out.append("<td>");
                    out.append("<a class='btn btn-success btn-xs' href='/content/edit_assessment_question?assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "&_action=edit" + "'>" + "<i class='fa fa-check'></i>Edit</a>");

                    out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger btn-xs' "
                            + "href='/content/delete_question?assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-remove'></i>Delete</a>");

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

    //Added by Kunal on 24/03/2016 for editing assessment question
    /**
     *
     * @param lesson
     * @param questionId
     * @return
     */
    public String getFormForQuestionEdit(Lesson lesson, String questionId) {
        StringBuilder out = new StringBuilder();
        if (lesson.getPresentaion() != null) {

        } else if (lesson.getGame() != null) {

        } else if (lesson.getAssessment() != null) {
            Assessment assessment = lesson.getAssessment();
            ArrayList<LearningObjective> items = new ArrayList<LearningObjective>(getLearningObjectivesOfAllSiblings(lesson.getId()));

            if (assessment.getAssessmentType() == null) {

                out.append("<div class=' col-md-12 '>"
                        + "<div class='panel panel-sea'>"
                        + "<div class='panel-heading'>"
                        + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                        + "</i>Assessment Details</h3></div>"
                        + "<div class='panel-body'> "
                        + "<form action='/content/update_assessment' id='sky-form4' class='sky-form' method='POST'> "
                        + "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
                        + "<fieldset><div class='row'><section class='col col-6'><label>Assessment Type</label> "
                        + "<label class='input'><select class='form-control valid' name='assessment_type' style='margin-right: 50px'>"
                        + "<option value='STATIC'>STATIC</option>"
                        + "<option value='ADAPTIVE'>ADAPTIVE</option>"
                        + "<option value='TREE'>TREE</option>"
                        + "<option value='RANDOM'>RANDOM</option></select></label> </section> "
                        + "<section class='col col-6'> <label>Number of Questions</label> <label class='input'> "
                        + "<input value='' type='number' name='number_of_questions' placeholder='Number of questions'>  </label> </section> ");

                out.append(" </div>");
                out.append("</fieldset> "
                        + "<footer> <button type='submit' style='float: right' class='btn-u'>Proceed</button> </footer></form></div></div></div>");
            } else {
                Integer number_of_questions = new Integer(0);
                AssessmentService assessmentService = new AssessmentService();
                number_of_questions = assessmentService.getNumberOfQuestionsInAssessment(assessment.getId());
                QuestionService questionService = new QuestionService();
                Question question = new Question();
                question = questionService.findById(Integer.parseInt(questionId));

                out.append("<div class=' col-md-12 '>"
                        + "<div class='panel panel-sea'>"
                        + "<div class='panel-heading'>"
                        + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                        + "</i>Question Details</h3></div>"
                        + "<div class='panel-body'> "
                        + "<form action='/content/edit_assessment_question?_action=update' id='sky-form4' class='sky-form' method='POST'> "
                        + "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
                        + "<fieldset>");

                out.append("<section><label>List of Learning Objectives in this Session</label> <div class='row'>");
                out.append("<input type='hidden' name='questionId' value='" + question.getId() + "' </input>");

                for (LearningObjective obj : items) {

                    out.append("<div class='col col-12'><label class='checkbox'>"
                            + "<input type='checkbox' name='learningObjectives' checked='checked' value="
                            + obj.getId() + "><i></i>" + obj.getTitle() + "</label></div>");
                }
                Set optionSet = new HashSet();
                optionSet = question.getAssessmentOptions();
                List<Integer> correctOptionList = new ArrayList();
                Iterator it = optionSet.iterator();
                String[][] optionSetStringArray = new String[optionSet.size()][2];
                int counter = 0;
                while (it.hasNext()) {
                    AssessmentOption assessmentOption = (AssessmentOption) it.next();
                    if (assessmentOption.getMarkingScheme() != null) {
                        if (assessmentOption.getMarkingScheme() == 1) {
                            correctOptionList.add(assessmentOption.getId());
                        }
                    }
                    try {
                        optionSetStringArray[counter][0] = assessmentOption.getId() + "";
                        optionSetStringArray[counter][1] = assessmentOption.getText();
                        counter++;
                    } catch (Exception e) {
                    }

                }

                java.util.Arrays.sort(optionSetStringArray, new java.util.Comparator() {
                    @Override
                    public int compare(Object a, Object b) {
                        String[] row1 = (String[]) a;
                        String[] row2 = (String[]) b;

                        return row1[0].compareTo(row2[0]);
                    }
                });

                out.append("</div></section></br>"
                        + "<div class='row'><section class='col col-md-4'><label>Question Type</label> "
                        + "<label class='input'>"
                        + "<select class='form-control valid' name='question_type' style='margin-right: 50px'>");
                if (question.getQuestionType().equalsIgnoreCase("1")) {
                    out.append("<option value='1' selected=selected>Single correct option</option><option value='2'>Multiple correct options</option>");
                } else {
                    out.append("<option value='2' selected=selected>Multiple correct options</option><option value='2'>Single correct option</option>");
                }
                out.append("</select></label> </section>"
                        + "<section class='col col-md-4'><label>Difficulty Level</label>"
                        + "<label class='input'>"
                        + "<select class='form-control valid' name='difficulty_level' style='margin-right: 50px'>");
                out.append(getDifficultyLevelDropDown(question.getDifficultyLevel()));

                out.append("</select></label> </section>");

                out.append("<section class='col col-md-4'><label>Question Duration</label> <label class='input'> <input value='"+question.getDurationInSec());
                
                out.append("'type='number' name='duration_in_sec' placeholder='Duration of Question'> "
                		+ "<b class='tooltip tooltip-bottom-right'>The duration of the Question</b> </label> </section>");
                
                
/* 
                out.append("<section class='col col-md-4'><label>Depth</label> <label class='input'> ");
                out.append(getSpecifierDropDown(question.getSpecifier()));
                
*/ //Enable for tree assessment type in future
                
                
                out.append("<section><label>Question Text</label> "
                        + "<label class='input'> <TEXTAREA NAME='question_text' ROWS='5' cols='75'>" + question.getQuestionText() + "</TEXTAREA> </label> </section> ");

                
                int increment = 1;
                for (int a = 0; a < optionSetStringArray.length; a++) {
                    int idVerifier = Integer.parseInt(optionSetStringArray[a][0]);
                    if (correctOptionList.contains(idVerifier)) {
                        out.append("<section> <label class='checkbox'><input type='checkbox' name='answers_" + optionSetStringArray[a][0] + "' checked=checked/><i></i>Option " + increment + "</label><label class='input'> <TEXTAREA NAME='option_" + optionSetStringArray[a][0] + "'  ROWS='2' cols='25'>" + optionSetStringArray[a][1] + "</TEXTAREA> </label> </section>");
                    } else {
                        out.append("<section> <label class='checkbox'><input type='checkbox' name='answers_" + optionSetStringArray[a][0] + "' /><i></i>Option " + increment + "</label><label class='input'> <TEXTAREA NAME='option_" + optionSetStringArray[a][0] + "'  ROWS='2' cols='25'>" + optionSetStringArray[a][1] + "</TEXTAREA> </label> </section>");
                    }
                    
                    increment++;
                }

                out.append("</fieldset>"
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
                    out.append("<tr>");
                    out.append("<td>" + data.get(i).get(0) + "</td>");
                    out.append("<td>" + data.get(i).get(1) + "</td>");
                    out.append("<td>");
                    out.append("<a class='btn btn-success btn-xs' href='/content/edit_assessment_question?assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "&_action=edit" + "'>" + "<i class='fa fa-check'></i>Edit</a>");

                    out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger btn-xs' "
                            + "href='/content/delete_question?assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-remove'></i>Delete</a>");

                    out.append("</td>");
                    out.append("</tr>");
                }

                out.append("</tbody>");
                out.append("</table>");
                out.append("</div>");
                out.append("</div>");
            }
        }
        return out.toString();
    }
//Added by Kunal on 24/03/2016 for getting Specifier Drop down

    /**
     *
     * @param specifier
     * @return
     */
    private String getSpecifierDropDown(int specifier) {
        StringBuilder sb = new StringBuilder();

        sb.append("<select class='form-control valid' name='specifier' style='margin-right: 50px'>" + "\n");
        if (specifier == 1) {
            sb.append("<option value='1' selected=selected>1</option>" + "\n");
            sb.append("<option value='2'>2</option>" + "\n");

        } else {
            sb.append("<option value='2' selected=selected>2</option>" + "\n");
            sb.append("<option value='1'>1</option>" + "\n");
        }
        sb.append("</select></label></section></div>");

        return sb.toString();

    }

// Added by Kunal on 24/03/2016 for getting Difficulty Level Drop down
    /**
     *
     * @param level
     * @return
     */
    private String getDifficultyLevelDropDown(int level) {
        StringBuilder sb = new StringBuilder();
        if (level == 1) {
            sb.append("<option value='1' selected=selected>1(EASIEST)</option>");
            for (int x = 2; x < 10; x++) {
                sb.append("<option value='" + x + "'>" + x + "</option>");

            }
            sb.append("<option value='10'>10(HARDEST)</option>");
        } else if (level == 10) {
            sb.append("<option value='10' selected=selected>10(HARDEST)</option>");
            sb.append("<option value='1'>1(EASIEST)</option>");
            for (int x = 2; x < 10; x++) {
                sb.append("<option value='" + x + "'>" + x + "</option>");

            }

        } else {
            sb.append("<option value='1'>1(EASIEST)</option>");
            for (int x = 2; x < 10; x++) {
                if (x == level) {
                    sb.append("<option value='" + x + "' selected>" + x + "</option>");
                } else {
                    sb.append("<option value='" + x + "'>" + x + "</option>");
                }

            }
            sb.append("<option value='10'>10(HARDEST)</option>");
        }
        return sb.toString();

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

            Assessment assessment = lesson.getAssessment();

            out.append("<div class=' col-md-12 '>"
                    + "<div class='panel panel-sea'>"
                    + "<div class='panel-heading'>"
                    + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                    + "</i>Assessment Details</h3></div>"
                    + "<div class='panel-body'> "
                    + "<form action='/content/update_assessment' id='sky-form4' class='sky-form' method='POST'> "
                    + "<fieldset><div class='row'><section class='col col-5'><label>Assessment Type</label> "
                    + "<label class='input'><input readonly='readonly' value=" + assessment.getAssessmentType() + ">"
                    + "</label> </section> "
                    + "<section class='col col-5'> <label>Number of Questions</label> "
                    + "<label class='input'><input readonly='readonly' value=" + assessment.getNumber_of_questions() + "> </label> </section> "
                    + "<section class='col col-2' style='margin-top: 2.6%'> <a href=# onclick='openWin(\"/content/lesson/preview_assessment.jsp?assessment_id=" + assessment.getId() + "\")' rel='float-shadow' class='btn-u btn-u-default float-shadow'>Preview</a> </section>");

            out.append(" </div>");
            out.append("</fieldset> "
                    + "</form></div></div></div>");

            out.append("<div class=' col-md-12 '>");
            out.append("<div class='panel panel-sea margin-bottom-40'>");
            out.append("<div class='panel-heading'>");
            out.append("<h3 class='panel-title'><i class='fa fa-edit'></i> List of Added Questions</h3>");
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
                out.append("<tr>");
                out.append("<td>" + data.get(i).get(0) + "</td>");
                out.append("<td>" + data.get(i).get(1) + "</td>");
                out.append("<td>");
                out.append("<a class='btn btn-success btn-xs' href='/content/lesson/review_question.jsp?question_id=" + data.get(i).get(0) + "&lesson_id=" + lesson.getId() + "'>"
                        + "<i class='fa fa-check'></i>View question</a>");
                out.append("</td>");
                out.append("</tr>");
            }

            out.append("</tbody>");
            out.append("</table>");
            out.append("</div>");
            out.append("</div>");

        }

        return out;
    }

    public StringBuffer getEditProfileEdit(CMSSlide slide, Presentaion ppt, Boolean newSlide,HttpServletRequest request) throws IOException {
        ImageDAO dao = new ImageDAO();
        ImageUtils dao1 = new ImageUtils();
        ArrayList<Image> images = (ArrayList<Image>) dao.findByProperty("sessionid", ppt.getLesson().getCmsession().getId());
        ArrayList<Video> videos = (ArrayList<Video>) (new VideoDAO()).findByProperty("sessionId",
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

        context.put("videos", videos);//context.put("tag_string", tagString);
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
            System.err.println(slide.getId());
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
                out.append("<option selected='selected' value='" + type + "'>" + type + "</option>");
            } else {
                out.append("<option selected='selected' value='" + type + "'>" + type + "</option>");
            }
        }
        out.append("</select>");
        out.append("</section></fieldset>");

        out.append(
                "<fieldset><section><label class='label'>Select Background Transition</label> <label class='select'>");
        out.append("<select name='backgroundTransition' value='" + slide.getBackgroundTransition() + "'>");

        for (String type : SlideTransition.BackgroundTransition) {
            if (type.equalsIgnoreCase(slide.getBackgroundTransition())) {
                out.append("<option selected='selected' value='" + type + "'>" + type + "</option>");
            } else {
                out.append("<option selected='selected' value='" + type + "'>" + type + "</option>");
            }
        }
        out.append("</select>");
        out.append("</section></fieldset>");

        out.append(
                "<fieldset><section><label class='label'>Select Slide Background color</label> <label class='select'>");
        out.append("<input type='color' id='slide_color' name='backgroundColor' value='" + slide.getBackground() + "'>");

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
    

    public ArrayList<LearningObjective> getSelectedLOsOftheLesson(int lesson_id) {
        // TODO Auto-generated method stub
        ArrayList<LearningObjective> lesson_lo_list = new ArrayList<>();
        IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
            String sql = "SELECT lol.learning_objectiveid as id, lo.title as title, lo.subject as subject "
            		+ " FROM learning_objective_lesson lol, learning_objective lo "
            		+ " WHERE lol.learning_objectiveid = lo.id and lol.lessonid = "+lesson_id;
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List<HashMap<String, Object>> results = query.list();
            for (HashMap<String, Object> object : results) {
            	LearningObjective lo = new LearningObjective();
                lo.setId((Integer)object.get("id"));
                lo.setTitle(object.get("title").toString());
                lo.setSubject(object.get("subject").toString());
                lesson_lo_list.add(lo);
            }
        return lesson_lo_list;
    }

    public ArrayList<LearningObjective> getUnselectedLOsInTheSameSession(int lesson_id) {
        // TODO Auto-generated method stub
        ArrayList<LearningObjective> session_lo_list = new ArrayList<>();
        IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
            String sql = "SELECT lol.learning_objectiveid as id, lo.title as title, lo.subject as subject "
            		+ " FROM learning_objective_lesson lol, lesson lsn, learning_objective lo "
            		+ " WHERE lol.learning_objectiveid= lo.id and lol.lessonid = lsn. ID AND lsn.session_id IN "
            		+ 	" ( SELECT C . ID FROM cmsession C, lesson l "
            		+ 	" WHERE C . ID = l.session_id AND l. ID = "+lesson_id+" )  AND lol.learning_objectiveid not in "
            		+ 	  " (SELECT lol.learning_objectiveid FROM learning_objective_lesson lol "
            		+ 	  " WHERE lol.lessonid = "+lesson_id+") group by lol.learning_objectiveid, lo.title, lo.subject";
            System.err.println(sql);
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List<HashMap<String, Object>> results = query.list();
            for (HashMap<String, Object> object : results) {
            	LearningObjective lo = new LearningObjective();
                lo.setId((Integer)object.get("id"));
                lo.setTitle(object.get("title").toString());
                lo.setSubject(object.get("subject").toString());
                session_lo_list.add(lo);
            }
        return session_lo_list;
    }
    
}
