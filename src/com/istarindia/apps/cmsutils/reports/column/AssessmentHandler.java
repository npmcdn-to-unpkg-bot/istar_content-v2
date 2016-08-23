
package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.Assessment;
import com.istarindia.apps.dao.AssessmentDAO;
import com.istarindia.apps.dao.IstarUser;

public class AssessmentHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID, String itemType) {
		AssessmentDAO assessmentDAO = new AssessmentDAO();
		Assessment assessment = assessmentDAO.findById(taskID);
		return new StringBuffer("<a href='/content/edit_lesson?task_id=" + assessment.getLesson().getTask().getId() + "'> Edit</a>  ");	
		
	}

	

}
