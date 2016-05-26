
package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.*;

public class AssessmentHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID) {
		
		return new StringBuffer("<a href='/content/lesson/edit_assessment.jsp?assessment_id=" + taskID + "'> Edit</a>  ");	
		
	}

	

}
