package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.dao.*;
import com.istarindia.apps.dao.TaskDAO;

public class LessonLinkHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID) {
		
		Task t = new TaskDAO().findById(taskID);
		Lesson l = new LessonDAO().findById(t.getItemId());
		System.out.println("======="+l.getId());
		if(new PresentaionDAO().findByProperty("lesson", l).size()>0)
		{
			Presentaion ppt = (Presentaion)new PresentaionDAO().findByProperty("lesson", l).get(0);
			return new StringBuffer("<a href='/content/lesson/preview.jsp?ppt_id=" + ppt.getId() + "'> Preview</a>  ");	
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
