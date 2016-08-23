package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;

public class LessonLinkHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID , String itemType) {
		
		Task t = new TaskDAO().findById(taskID);
		Lesson l = t.getLesson();
		if(new PresentaionDAO().findByProperty("lesson", l).size()>0)
		{
			Presentaion ppt = (Presentaion)new PresentaionDAO().findByProperty("lesson", l).get(0);
			
			StringBuffer button = new StringBuffer();
			button.append("<div class='btn-group '>  <button style='width: 115%;' type='button' class='btn btn-warning dropdown-toggle' data-toggle='dropdown' aria-expanded='true'> Choose Preview <i class='fa fa-angle-down'></i> </button>  <ul class='dropdown-menu' role='menu'><li>");
			button.append("<a onclick='openWin(\"/content/lesson/preview.jsp?ppt_id=" + ppt.getId() + "\")'  href='#' >Mobile Preview</a>");
			button.append("</li>  <li>");
			button.append("<a target='_blank' href='/content/lesson/preview_desktop.jsp?ppt_id=" + ppt.getId()+"' >Speaker Preview</a>");
			button.append("</li> </ul> </div>");
			
			return button;	
 
		} else if(new AssessmentDAO().findByProperty("lesson", l).size()>0)
		{
			Assessment assessment = (Assessment)new AssessmentDAO().findByProperty("lesson", l).get(0);
			return new StringBuffer("<a href='/content/lesson/preview_assessment.jsp?assessment_id=" + assessment.getId() + "'> Preview</a>  ");	
		}
		else
		{
			return new StringBuffer("Preview Not Available");	
		}	
		
		//Presentaion ppt =(Presentaion) new PresentaionDAO().findByProperty("lesson", new LessonDAO().findById(new TaskDAO().findById(taskID).getItemId())).get(0);
		
		
	}

}
