package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class TaskHistoryHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID) {
		// TODO Auto-generated method stub
		return new StringBuffer("<a href='/content/task/task_history.jsp?task_id=" + taskID + "'> View Task History</a>  ");

	}

}
