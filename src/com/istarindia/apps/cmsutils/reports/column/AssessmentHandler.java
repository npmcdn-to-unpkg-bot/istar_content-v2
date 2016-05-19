
package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.*;

public class AssessmentHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID) {
		
		Task t = new TaskDAO().findById(taskID);
		Lesson l = new LessonDAO().findById(t.getItemId());
		System.out.println("======="+l.getId());
		if(new AssessmentDAO().findByProperty("lesson", l).size()>0)
		{
				Assessment assessment = l.getAssessment();
				return new StringBuffer("<a href='/content/lesson/assessment_play.jsp?assessment_id=" + assessment.getId() + "'> Play</a>  ");	
		}
		else
		{
			return new StringBuffer("Not applied");	
		}	
		
		//Presentaion ppt =(Presentaion) new PresentaionDAO().findByProperty("lesson", new LessonDAO().findById(new TaskDAO().findById(taskID).getItemId())).get(0);
		
		
	}

	

}
