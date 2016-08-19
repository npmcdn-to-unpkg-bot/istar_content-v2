
package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class AssessmentHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID, String itemType) {
		
		return new StringBuffer("<a href='/content/lesson/edit_assessment.jsp?assessment_id=" + taskID + "'> Edit</a>  ");	
		
	}

	

}
