package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class HandoutColumnHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID) {
		
		return new StringBuffer("<a href='/content/content_creator/edit_handout.jsp?handout_id=" + taskID + "'> Edit</a>  ");
		//return null;
	}

}
