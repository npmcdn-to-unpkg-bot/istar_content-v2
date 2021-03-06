package com.istarindia.apps.cmsutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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

import com.istarindia.apps.MediaUtils;
import com.istarindia.apps.SlideTransition;
import com.istarindia.apps.cmsutils.reports.ReportUtils;
import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.AssessmentOption;
import com.istarindia.apps.dao.Cmsession;
import com.istarindia.apps.dao.DBUTILS;
import com.istarindia.apps.dao.Game;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.IstarUser;
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

/**
 * @author Vaibhav
 *
 */
public class LessonUtils {
	
    public StringBuffer getFormForAssessmentQuestionEdit(int assessment_id, int question_id,HttpServletRequest request) {
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
        
        ArrayList<LearningObjective> selectedItems = new ArrayList<LearningObjective>(getSelectedLOsForQuestion(question_id));
        ArrayList<LearningObjective> selectableItems = new ArrayList<LearningObjective>(getRemainingLOsInLesson(assessment.getLesson().getId(), question_id));

            Integer number_of_questions = new Integer(0);
            AssessmentService assessmentService = new AssessmentService();
            number_of_questions = assessmentService.getNumberOfQuestionsInAssessment(assessment.getId());
        	out.append("<div class=' col-md-12 '> <div class='panel panel-sea'> <div class='panel-heading'> "
        			+ "<h3 class='panel-title'> <i class='fa fa-tasks'></i>Assessment Details </h3> </div> "
        			+ "<div class='panel-body'> "
        			+ "<form action='/content/update_assessment' id='sky-form4' class='sky-form' novalidate='novalidate'> "
        			+ "<input type='hidden' name='assessment_id' value='"+assessment.getId()+"'>"
        			+ "<input type='hidden' name='title' value='"+assessment.getAssessmenttitle()+"'>"
        			+ "<input type='hidden' name='duration' value='"+assessment.getAssessmentdurationminutes()+"'> "
        			+ "<input type='hidden' name='update_assessment' value='true'> "
        			+ "<fieldset class='row'> <section class='col-md-4'> "
        			+ "<label>Assessment Type</label> <label class='input'> "
        			+ "<select class='form-control valid' name='assessment_type'> "
        			+ "<option value='STATIC'>STATIC</option> <option value='ADAPTIVE'>ADAPTIVE</option> "
        			+ "<option value='TREE'>TREE</option> <option value='RANDOM'>RANDOM</option> "
        			+ "<option value='"+assessment.getAssessmentType()+"' selected='selected'>"+assessment.getAssessmentType()+"</option> </select> </label> </section> "
        			+ "<section class='col-md-3'> <label>Maximum number of Questions</label> <label class='input'> "
        			+ "<input value='"+assessment.getNumber_of_questions()+"' type='number' name='number_of_questions' placeholder='Number of Questions'> <b class='tooltip tooltip-bottom-right'>Number of Questions</b> </label> </section> "
        			+ "<section class='col-md-2'><button type='submit'  style='margin-top: 12%;' class='btn-u'>Update assessment Details</button></section> "
        			+ "<section class='col-md-3'><a type='button' href='/content/edit_lesson?task_id="+assessment.getLesson().getTask().getId()+"' style='margin-top: 7%; float:right' class='btn-u'>Add new question</a></section> </fieldset>  </form> </div> </div> </div>");

                //Fix upload-assessment part anad  uncomment the below part
            	//out.append("<div class=' col-md-12 '> <div class='panel panel-sea'> " + "<div class='panel-heading'> <h3 class='panel-title'> " + "<i class='fa fa-tasks'></i>Upload Questions </h3> </div> <div class='panel-body'>" + "<form action='assessment_upload' class='sky-form' method='post' enctype='multipart/form-data'> " + "<fieldset> <section><p>File input &nbsp;&nbsp;&nbsp;&nbsp; (Download sample format from" + "<a href='assets/excel/format.xls' style='color: RED'> here</a> )</p>" 				+ "<label for='file' class='input input-file'> <div class='button'>" + "<input type='file' id='file' name='file' onchange='DisplayFilePath()'>Browse</div> " + "<input type='text' id='formfield' readonly> </label> </section> </fieldset> <footer> " + "<button type='submit' class='btn-u'>Submit</button> </footer> </form> </div> </div></div>");
            	
            	
                out.append("<div class=' col-md-12 '>"
                        + "<div class='panel panel-sea'>"
                        + "<div class='panel-heading'>"
                        + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                        + "</i>Edit/Update Question details</h3></div>"
                        + "<div class='panel-body'> "
                        + "<form action='/content/edit_question' id='sky-form4' class='sky-form' method='POST'> "
                        + "<input type='hidden' name='assessment_id' value=" + assessment_id + "> "
                        + "<input type='hidden' name='question_id' value=" + question_id + "> "
                        + "<fieldset>");

                out.append("<section><label>Learning Objectives selected for the question</label><div class='row'>");
                if(selectedItems.size() > 0) {
        	        for (LearningObjective obj : selectedItems) {
	                    out.append("<div class='col col-12'><label class='checkbox'>"
	                            + "<input type='checkbox' name='selected_items' checked='checked' value="
	                            + obj.getId() + "><i></i>" + obj.getTitle() + "</label></div>");
	                }
                } else {
                	out.append("<div class='col col-12'><label>NONE</label></div>");
                }
                out.append("</div></section></br>");
                
                out.append("<section><label>Remaining learning Objectives in the lesson [which are not selected for the question]</label><div class='row'>");
                if(selectableItems.size() > 0) {
        	        for (LearningObjective obj : selectableItems) {
	                    out.append("<div class='col col-12'><label class='checkbox'>"
	                            + "<input type='checkbox' name='selected_items'  value="
	                            + obj.getId() + "><i></i>" + obj.getTitle() + "</label></div>");
	                } 
                } else {
                	out.append("<div class='col col-12'><label>NONE</label></div>");
                }
                out.append("</div></section></br>");
                
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
                        
                        + "</div>"
                        + "<section><label>Question Text</label> "
                        + "<label class='input'> <TEXTAREA class='init_textarea' id='question_text' NAME='question_text' ROWS='5' cols='75' >"+ StringEscapeUtils.escapeHtml4(question.getQuestionText()) +"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='1' "+markingScheme[0]+"><i></i>Option 1</label><label class='input'> "
                        + "<TEXTAREA class='init_textarea' id='option1' NAME='option1' ROWS='2' cols='25' value='"+options.get(0)+"'>"+StringEscapeUtils.escapeHtml4(options.get(0).getText())+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='2' "+markingScheme[1]+"><i></i>Option 2</label><label class='input'> "
                        + "<TEXTAREA class='init_textarea' id='option2' NAME='option2' ROWS='2' cols='25' value='"+options.get(1)+"'>"+StringEscapeUtils.escapeHtml4(options.get(1).getText())+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='3' "+markingScheme[2]+"><i></i>Option 3</label> <label class='input'> "
                        + "<TEXTAREA class='init_textarea' id='option3' NAME='option3' ROWS='2' cols='25' value='"+options.get(2)+"'>"+StringEscapeUtils.escapeHtml4(options.get(2).getText())+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='4' "+markingScheme[3]+"><i></i>Option 4</label> <label class='input'> "
                        + "<TEXTAREA class='init_textarea' id='option4' NAME='option4' ROWS='2' cols='25' value='"+options.get(3)+"'>"+StringEscapeUtils.escapeHtml4(options.get(3).getText())+"</TEXTAREA> </label> </section> "
                        + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='5' "+markingScheme[4]+"><i></i>Option 5</label> <label class='input'> "
                        + "<TEXTAREA class='init_textarea' id='option5' NAME='option5' ROWS='2' cols='25' value='"+options.get(4)+"'>"+StringEscapeUtils.escapeHtml4(options.get(4).getText())+"</TEXTAREA> </label> </section> "
                        + "<input type='hidden' name='option1_id' value=" + options.get(0).getId() + "> "
                        + "<input type='hidden' name='option2_id' value=" + options.get(1).getId() + "> "
                        + "<input type='hidden' name='option3_id' value=" + options.get(2).getId() + "> "
                        + "<input type='hidden' name='option4_id' value=" + options.get(3).getId() + "> "
                        + "<input type='hidden' name='option5_id' value=" + options.get(4).getId() + "> "
                        + "</fieldset> "
                        + "<footer> <button type='submit' id='checkBtn' class='btn-u'>Proceed</button> <label id='err' style='color:red'></label></footer></form></div></div></div>");
               
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
                out.append("<a class='btn btn-success btn-xs' href='/content/edit_lesson?task_id="+assessment.getLesson().getTask().getId()+"&assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-check'></i>Edit</a>");

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
    
	private Collection<? extends LearningObjective> getRemainingLOsInLesson(int lesson_id, int question_id) {
        // TODO Auto-generated method stub
        ArrayList<LearningObjective> question_lo_list = new ArrayList<>();
        String sql = "SELECT lo. ID, lo.title, lo.subject FROM learning_objective lo, learning_objective_lesson lol WHERE lol.lessonid = " + lesson_id + " AND lol.learning_objectiveid = lo. ID AND lo. ID NOT IN ( SELECT lo. ID FROM learning_objective lo, learning_objective_question loq WHERE loq.learning_objectiveid = lo. ID AND loq.questionid = " + question_id + " )";
        DBUTILS util = new DBUTILS();
		List<HashMap<String, Object>> results = util.executeQuery(sql);            
		for (HashMap<String, Object> object : results) {
        	LearningObjective lo = new LearningObjective();
            lo.setId((Integer)object.get("id"));
            lo.setTitle(object.get("title").toString());
            try {
				lo.setSubject(object.get("subject").toString());
			} catch (Exception e) {
				lo.setSubject("NONE");
			}
            question_lo_list.add(lo);
        }
        return question_lo_list;
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
            
            out.append( "<table class='table table-striped' id='sortable'>");
            out.append("<tbody id=''>");
            out.append(" <tr>");
            out.append(" <td>");
            out.append("<form class='form-inline' role='form' action='/content/fill_template.jsp'>");
            out.append("<input name='ppt_id' value='" + ppt.getId() + "' type='hidden'/>");

            out.append("<div class='form-group'>");
            out.append("<select class='form-control' name='slide_type' style='margin-right: 0px'>");

            for (String template : CMSRegistry.slideTemplates) {
                out.append("<option value='" + template + "'>" + template + "</option>");
            }
            out.append("</select>");

            out.append("</div>");
            out.append(" </td><td>");
            
            out.append("<div class='form-group'  style='margin-right: 0px'>");
            out.append("<button type='submit' class='btn-u btn-u-default'style='margin-left: 10px'>Add Slide</button>");
            out.append("</div></form>");
            out.append(" </td><td>");

            out.append("<div class='btn-group '>  <button style='width: 100%;' type='button' class='btn btn-warning dropdown-toggle' data-toggle='dropdown' aria-expanded='true'> Preview Lesson <i class='fa fa-angle-down'></i> </button>  <ul class='dropdown-menu' role='menu'><li>");
            out.append("<a onclick='openWin(\"/content/lesson/preview.jsp?ppt_id=" + ppt.getId() + "\")'  href='#' >Mobile Preview</a>");
            out.append("</li>  <li>");
            out.append("<a target='_blank' href='/content/lesson/preview_desktop.jsp?ppt_id=" + ppt.getId()+"' >Speaker Preview</a>");
            out.append("</li> </ul> </div>");

            out.append(" </td><td>");
            out.append("<form id='update_order' action='/content/update_course?ppt_id="+ ppt.getId()+"'>"
            		+ "<input id='action' name='action' type='hidden' value='reorder'> "
            		+ "<input id='order_holder' name='order_holder' type='hidden'>"
            		+ "<input id='entity_type' name='entity_type' type='hidden' value='slides'>"
            		+ "<input id='task_id' name='task_id' type='hidden' value="+taskID+">"
                    + "<input id='lesson_name' name='lesson_name' type='hidden' value="+lesson.getTitle()+">"
            		+ "<button style=' ' type='submit' class='btn-u' id='kammm'>Save Slide Order</button></form>");
            out.append(" </td><td>");
			
            try {
				
				String themeID =  lesson.getLesson_theme_desktop();
				out.append("<div>");
	            out.append("<a style='float: none; margin-right: 0px;' target='_blank' "
	            		+ ""
	            		+ "href='/content/creative_admin/edit_theme.jsp?theme_id="+themeID+"&ppt_id="+lesson.getPresentaion().getId()+"' "
	            		+ " class='btn-u btn-u-default'>Edit Theme</a>");
	            out.append("</div>");
	            out.append(" </td></tr>");
	            out.append(" </tbody>");
	            out.append("</table>");

			} catch (Exception e) {
			}
            
            
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
            out.append("<th>#Id</th>");
            out.append("<th>Slide Title</th>");
            out.append("<th>Slide Template</th>");
            out.append("<th colspan='3'>Choose action</th>");
            out.append("<th>#Order</th>");
            out.append("</tr>");
            out.append("</thead>");
            out.append("<tbody id='slidess_ord'>");
            DBUtils db = new DBUtils();
            ArrayList<ArrayList<String>> data = db.getSlides(ppt.getId());
            for (int i = 0; i < data.size(); i++) {
                out.append("<tr class='mammama' id='"+data.get(i).get(0)+"'>");
                out.append("<td id='id'>" + data.get(i).get(0) + "</td>");
                out.append("<td>" + data.get(i).get(2) + "</td>");
                out.append("<td>" + data.get(i).get(3) + "</td>");

                out.append("<td><a class='btn btn-success btn-xs' href='/content/fill_template.jsp?ppt_id=" + ppt.getId()
                        + "&slide_id=" + data.get(i).get(0) + "&slide_type=" + data.get(i).get(3) + "'>"
                        + "<i class='fa fa-pencil-square-o fa-fw'></i>Edit</a></td>");
                
                out.append("<td><a class='btn btn-danger btn-xs' "
                        + "href='#' id='delete-slide-btn' data-toggle='modal' data-target='#confirm-delete-slide-modal' data-slide-id='"+data.get(i).get(0)+"'>"
                        + "<i class='fa fa-trash-o fa-fw'></i>Delete</a></td>");
                
                out.append("<td><a class='btn btn-warning btn-xs' "
                        + "href='#' id='duplicate-slide-btn' data-toggle='modal' data-target='#confirm-duplicate-slide-modal' data-slide-id='"+data.get(i).get(0)+"'>"
                        + "<i class='fa fa-copy fa-fw'></i>Duplicate</a></td>");
                
                out.append("<td>" + data.get(i).get(1) + "</td>");
                out.append("</tr>");
            }

            out.append(" </tbody>");
            out.append("</table>");
            out.append("</div>");
            out.append("</div>");

            //confirm delete slide modal:
			out.append( "  <div class='modal fade' id='confirm-delete-slide-modal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>");
			out.append( "  <div  class='modal-dialog'> <div class='modal-content'> <div class='modal-header'> Confirm! </div>");
			out.append( " <div class='modal-body'> Are you sure you want to continue?  </div> <div class='modal-footer'>  ");
			out.append(" <button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button> ");
			out.append( "<a href='/content/delete_slide?ppt_id=" + ppt.getId() + "&slide_id=' id='confirm-delete-slide-btn' class='btn btn-success success'>Delete</a> </div> </div> </div> </div>");

            //confirm duplicate slide modal:
			out.append( "  <div class='modal fade' id='confirm-duplicate-slide-modal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>");
			out.append( "  <div  class='modal-dialog'> <div class='modal-content'> <div class='modal-header'> Confirm! </div>");
			out.append( " <div class='modal-body'> Are you sure you want to duplicate the slide?  </div> <div class='modal-footer'>  ");
			out.append(" <button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button> ");
			out.append( "<a href='/content/duplicate_slide?ppt_id=" + ppt.getId() + "&slide_id=' id='confirm-duplicate-slide-btn' class='btn btn-success success'>Duplicate</a> </div> </div> </div> </div>");
			
        } else if (lesson.getGame() != null) {
            Game game = lesson.getGame();
//            out.append("This is where we wil have a form to generate a Asssesment Screen Input....");

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
        	
            Assessment assessment = lesson.getAssessment();
            ArrayList<LearningObjective> items = new ArrayList<LearningObjective>(getLearningObjectivesOfLesson(lesson.getId()));

            if (assessment.getAssessmentType() == null) {

                out.append("<div class=' col-md-12 '>"
                        + "<div class='panel panel-sea'>"
                        + "<div class='panel-heading'>"
                        + "<h3 class='panel-title'><i class='fa fa-tasks'>"
                        + "</i>Add new Question</h3></div>"
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
            	out.append("<div class=' col-md-12 '> <div class='panel panel-sea'> <div class='panel-heading'> "
            			+ "<h3 class='panel-title'> <i class='fa fa-tasks'></i>Assessment Details </h3> </div> <div class='panel-body'> "
            			+ "<form action='/content/update_assessment' id='sky-form4' class='sky-form' novalidate='novalidate'> "
            			+ "<input type='hidden' name='assessment_id' value='"+assessment.getId()+"'>"
            			+ "<input type='hidden' name='title' value='"+assessment.getAssessmenttitle()+"'>"
            			+ "<input type='hidden' name='duration' value='"+assessment.getAssessmentdurationminutes()+"'> "
            			+ "<input type='hidden' name='update_assessment' value='true'> <fieldset class='row'> "
            			+ "<section class='col-md-5'> <label>Assessment Type</label> <label class='input'> "
            			+ "<select class='form-control valid' name='assessment_type'> <option value='STATIC'>STATIC</option> "
            			+ "<option value='ADAPTIVE'>ADAPTIVE</option> <option value='TREE'>TREE</option> "
            			+ "<option value='RANDOM'>RANDOM</option> "
            			+ "<option value='"+assessment.getAssessmentType()+"' selected='selected'>"+assessment.getAssessmentType()+"</option> </select> </label> </section> "
            			+ "<section class='col-md-3'> <label>Maximum number of Questions</label> <label class='input'> "
            			+ "<input value='"+assessment.getNumber_of_questions()+"' type='number' name='number_of_questions' placeholder='Number of Questions'> <b class='tooltip tooltip-bottom-right'>Number of Questions</b> </label> </section> "
            			+ "<section class='col-md-2'><button type='submit'  style='margin-top: 12%;' class='btn-u'>Update assessment Details</button></section> </fieldset>  </form> </div> </div> </div>");
            	
            	if (number_of_questions < assessment.getNumber_of_questions()) {
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
                            + "</i>Add new Question</h3></div>"
                            + "<div class='panel-body'> "
                            + "<form action='/content/add_question' id='sky-form4' class='sky-form' method='POST'> "
                            + "<input type='hidden' name='assessment_id' value=" + assessment.getId() + "> "
                            + "<fieldset>");

                    out.append("<section><label>List of Learning Objectives in this lesson [Note: Select more in the lesson and update; to access more Learning Objectives here]</label> <div class='row'>");

                    for (LearningObjective obj : items) {
                        out.append("<div class='col col-12'><label class='checkbox'>"
                                + "<input type='checkbox' name='learningObjectives' value="
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
                            + "<label class='input'> <TEXTAREA class='init_textarea' id='question_text' NAME='question_text' ROWS='5' cols='75'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='1'><i></i>Option 1</label><label class='input'> "
                            + "<TEXTAREA class='init_textarea' id='option1' NAME='option1' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='2'><i></i>Option 2</label><label class='input'> "
                            + "<TEXTAREA class='init_textarea' id='option2' NAME='option2' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='3'><i></i>Option 3</label> <label class='input'> "
                            + "<TEXTAREA class='init_textarea' id='option3' NAME='option3' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='4'><i></i>Option 4</label> <label class='input'> "
                            + "<TEXTAREA class='init_textarea' id='option4' NAME='option4' ROWS='2' cols='25'></TEXTAREA> </label> </section> "
                            + "<section> <label class='checkbox'><input class='correctOption' type='checkbox' name='answers'  value='5'><i></i>Option 5</label> <label class='input'> "
                            + "<TEXTAREA class='init_textarea' id='option5' NAME='option5' ROWS='2' cols='25'></TEXTAREA> </label> </section> </fieldset> "
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
                    out.append("<a  class='btn btn-success btn-xs' href='/content/edit_lesson?task_id="+lesson.getTask().getId()+"&assessment_id=" + assessment.getId() + "&question_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-check'></i>Edit</a>");

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

    private Collection<? extends LearningObjective> getLearningObjectivesOfLesson(Integer id) {
        // TODO Auto-generated method stub
        ArrayList<LearningObjective> list = new ArrayList<>();
        IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
        Lesson lesson = new LessonDAO().findById(id);
        Cmsession cmsession = lesson.getCmsession();
        
        String sql = "select * from learning_objective_lesson where lessonid=" + lesson.getId() + " order by learning_objectiveid";
        DBUTILS util = new DBUTILS();
		List<HashMap<String, Object>> results = util.executeQuery(sql);            
		LearningObjectiveDAO learningObjectiveDao = new LearningObjectiveDAO();
        LearningObjective learningObjective = new LearningObjective();
        for (HashMap<String, Object> object : results) {
            try {
                learningObjective = learningObjectiveDao.findById((Integer) object.get("learning_objectiveid"));
                if (!list.contains(learningObjective)) {
                    list.add(learningObjective);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            out.append("<th>Template</th>");
            out.append("<th>Action</th>");
            out.append("</tr>");
            out.append("</thead>");
            out.append("<tbody>");
            DBUtils db = new DBUtils();
            ArrayList<ArrayList<String>> data = db.getSlides(ppt.getId());
            for (int i = 0; i < data.size(); i++) {
                out.append("<tr>");
                out.append("<td>" + data.get(i).get(0) + "</td>");
                out.append("<td>" + data.get(i).get(2) + "</td>");
                out.append("<td>" + data.get(i).get(3) + "</td>");
                out.append("<td>");
                out.append("<a  target='_blank' class='btn btn-success btn-xs' href='/content/review_slide.jsp?ppt_id="
                        + ppt.getId() + "&slide_id=" + data.get(i).get(0) + "'>"
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
                    + "<label class='input'><input readonly='readonly' value=" + assessment.getNumber_of_questions() + "> </label> </section> ");
            
            //out.append( "<section class='col col-2' style='margin-top: 2.6%'> <a href=# onclick='openWin(\"/content/lesson/preview_assessment.jsp?assessment_id=" + assessment.getId() + "\")' rel='float-shadow' class='btn-u btn-u-default float-shadow'>Preview</a> </section>");

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
                out.append("<a target='_blank'  class='btn btn-success btn-xs' href='/content/lesson/review_question.jsp?question_id=" + data.get(i).get(0) + "&lesson_id=" + lesson.getId() + "'>"
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

    public StringBuffer getEditProfileEdit(CMSSlide slide, Presentaion ppt, Boolean newSlide, HttpServletRequest request) throws IOException {
        CMSList newList = new CMSList();
        newList.setList_type(slide.getList().getList_type());
        newList.setItems(new ArrayList<>());
        
        for (CMSTextItem item : slide.getList().getItems()) {
            if (!( item.getText().trim().equalsIgnoreCase("")    &&   (item.getDescription().trim().equalsIgnoreCase("")  ||  item.getDescription().trim().equalsIgnoreCase("NO_DESC")  ||  item.getDescription().trim().startsWith("Lorem Ipsum is simply")))) {
            	newList.getItems().add(item);
                try {
                    if (item.getList().getItems().size() != 0) {
                        CMSList childList = new CMSList();
                        ArrayList<CMSTextItem> items3 = new ArrayList<>();
                        childList.setItems(items3);
                        for (CMSTextItem childItem : item.getList().getItems()) {
                        	if (!childItem.getText().trim().startsWith("$slide")) {
                            	childList.getItems().add(childItem);
                            } else if(childItem.getDescription().trim().equalsIgnoreCase("NO_DESC")){
                        		childList.getItems().add(childItem);
                        	}
                        }
                        item.setList(childList);
                    }
                } catch (Exception e) {
                	//TODO: nothing
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
        
        try {
			if(slide.getTemplateName().toUpperCase().contains("IMAGE")) {
				ImageDAO imageDAO = new ImageDAO();
			    Image selected_image = new Image();
			    MediaUtils mediaUtils = new MediaUtils();
			    ArrayList<Image> images = mediaUtils.findAllPublishedImagesInSessin(ppt.getLesson().getCmsession().getId());
			    context.put("images", images);
			   
			    try {
			        String selected_image_url = slide.getImage().getUrl();
			        if (!imageDAO.findByUrl(selected_image_url).isEmpty()) {
			        	selected_image = imageDAO.findByUrl(selected_image_url).get(0);
			        }
			        context.put("selected_image", selected_image);
			    } catch (Exception e ) {
			    	//No selected-image record in the slide
					e.printStackTrace();
			    }
			    
			} else if (slide.getTemplateName().toUpperCase().contains("VIDEO")) {
				VideoDAO videoDAO = new VideoDAO();
			    Video selected_video = new Video();
			    MediaUtils mediaUtils = new MediaUtils();
			    ArrayList<Video> videos = mediaUtils.findAllPublishedVideosInSessin(ppt.getLesson().getCmsession().getId());
			    context.put("videos", videos);
			    
			    try {
			        String selected_video_url = slide.getImage().getUrl();
			        if (!videoDAO.findByUrl(selected_video_url).isEmpty()) {
			        	selected_video = videoDAO.findByUrl(selected_video_url).get(0);
			        }
			        context.put("selected_video", selected_video);
			    } catch (Exception e ) {
			    	//No selected-video record in the slide
					//ex.printStackTrace();
			    }
			    
			}
		} catch (Exception ex) {
			//New slide
			//ex.printStackTrace();
		}
        
        context.put("slide", slide);
        context.put("list_types", CMSRegistry.listTypes);
        
        Template t =  ve.getTemplate(slide.getTemplateName() + "_edit.vm");
        
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
		
    	String sql = "select * from slide where id=" + slide.getId() ;
		DBUTILS util = new DBUTILS();
		HashMap<String, Object> slide_version = util.executeQuery(sql).get(0);	
        
		String slideText = slide_version.get("slide_text").toString();
        String template =  slide_version.get("template").toString();
        String teacherNotes = slide_version.get("teacher_notes").toString();
        String studentNotes = slide_version.get("student_notes").toString();
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CMSSlide.class);
            InputStream in = IOUtils.toInputStream(slideText, "UTF-8");
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            cMSlide = (CMSSlide) jaxbUnmarshaller.unmarshal(in);
            cMSlide.setTemplateName(template);
            cMSlide.setTeacherNotes(teacherNotes);
            cMSlide.setStudentNotes(studentNotes);
        } catch (JAXBException e) {
            //System.err.println(version.getId());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cMSlide;
    }

    public CMSSlide convertSlide(int version_id) {
		CMSSlide cMSlide = new CMSSlide();
		
    	IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		String sql = "select * from slide_version where id=" + version_id ;
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		DBUTILS util = new DBUTILS();
		HashMap<String, Object> slide_version = util.executeQuery(sql).get(0);	
        
		String slideText = slide_version.get("slide_text").toString();
        String template =  slide_version.get("template").toString();
        String teacherNotes = slide_version.get("teacher_notes").toString();
        String studentNotes = slide_version.get("student_notes").toString();
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CMSSlide.class);
            InputStream in = IOUtils.toInputStream(slideText, "UTF-8");
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            cMSlide = (CMSSlide) jaxbUnmarshaller.unmarshal(in);
            cMSlide.setTemplateName(template);
            cMSlide.setTeacherNotes(teacherNotes);
            cMSlide.setStudentNotes(studentNotes);
        } catch (JAXBException e) {
            //System.err.println(version.getId());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cMSlide;

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
            DBUTILS util = new DBUTILS();
    		List<HashMap<String, Object>> results = util.executeQuery(sql);            for (HashMap<String, Object> object : results) {
            	LearningObjective lo = new LearningObjective();
                lo.setId((Integer)object.get("id"));
                lo.setTitle(object.get("title").toString());
                try {
					lo.setSubject(object.get("subject").toString());
				} catch (Exception e) {
					lo.setSubject("NONE");
				}
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
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            DBUTILS util = new DBUTILS();
    		List<HashMap<String, Object>> results = util.executeQuery(sql);            for (HashMap<String, Object> object : results) {
            	LearningObjective lo = new LearningObjective();
                lo.setId((Integer)object.get("id"));
                lo.setTitle(object.get("title").toString());
                try {
					lo.setSubject(object.get("subject").toString());
				} catch (Exception e) {
					lo.setSubject("NONE");
				}
                session_lo_list.add(lo);
            }
        return session_lo_list;
    }

   	private ArrayList<LearningObjective> getSelectedLOsForQuestion(int question_id) {
        // TODO Auto-generated method stub
        ArrayList<LearningObjective> question_lo_list = new ArrayList<>();
        String sql = "select lo.id, lo.title, lo.subject from learning_objective lo, learning_objective_question loq "
        			 + "where loq.learning_objectiveid=lo.id and loq.questionid = "+question_id;
        DBUTILS util = new DBUTILS();
		List<HashMap<String, Object>> results = util.executeQuery(sql);            
		for (HashMap<String, Object> object : results) {
        	LearningObjective lo = new LearningObjective();
            lo.setId((Integer)object.get("id"));
            lo.setTitle(object.get("title").toString());
            try {
				lo.setSubject(object.get("subject").toString());
			} catch (Exception e) {
				lo.setSubject("NONE");
			}
            question_lo_list.add(lo);
        }
        return question_lo_list;
    }

   	public List<HashMap<String, String>> getSlideComments(int slide_id) {
   		List<HashMap<String, String>> logs = new ArrayList<>();
   		
   		IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
        String sql = "select * from task_log t where t.item_type='SLIDE' and item_id = "+ slide_id + "order by t.created_at desc";
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        DBUTILS util = new DBUTILS();
		List<HashMap<String, Object>> results = util.executeQuery(sql);
		for (HashMap<String, Object> object : results) {
        	if(!object.get("comments").toString().trim().isEmpty()) {
	        	HashMap<String, String> log = new HashMap<>();
	            log.put("comment", object.get("comments").toString());
	            log.put("changed_status", object.get("changed_status").toString());
	            log.put("created_at", object.get("created_at").toString());
	            log.put("actor_id", object.get("actor_id").toString());
	            log.put("actor_name", dao.findById(Integer.parseInt( object.get("actor_id").toString())).getName());
	            logs.add(log);
        	}
        }
   		
   		return logs;
   		
   	}

   	public List<HashMap<String, String>> getQuestionComments(int question_id) {
   		List<HashMap<String, String>> logs = new ArrayList<>();
   		
   		IstarUserDAO dao = new IstarUserDAO();
        Session session = dao.getSession();
        String sql = "select * from task_log t where t.item_type='QUESTION' and item_id = "+ question_id + "order by t.created_at desc";
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        DBUTILS util = new DBUTILS();
		List<HashMap<String, Object>> results = util.executeQuery(sql);
		for (HashMap<String, Object> object : results) {
        	if(!object.get("comments").toString().trim().isEmpty()) {
	        	HashMap<String, String> log = new HashMap<>();
	            log.put("comment", object.get("comments").toString());
	            log.put("changed_status", object.get("changed_status").toString());
	            log.put("created_at", object.get("created_at").toString());
	            log.put("actor_name", dao.findById(Integer.parseInt( object.get("actor_id").toString())).getName());
	            logs.add(log);
        	}
        }
   		
   		return logs;
   		
   	}

}
