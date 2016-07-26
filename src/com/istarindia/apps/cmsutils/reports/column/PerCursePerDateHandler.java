package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class PerCursePerDateHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID , String itemType) {
		
		return new StringBuffer("<a href='/content/logs/per_course_per_time.jsp?course_id="+taskID+"'> "+status+"</a>  ");
		
	}

}
