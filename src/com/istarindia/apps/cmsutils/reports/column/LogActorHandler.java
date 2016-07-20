package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class LogActorHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID) {
		// TODO Auto-generated method stub
		return new StringBuffer("<a href='/content/logs/admin_per_course.jsp?user_id="+taskID+"'> "+status+"</a>  ");
	}

}
