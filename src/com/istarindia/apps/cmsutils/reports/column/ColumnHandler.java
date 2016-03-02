package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public abstract class ColumnHandler {

	public ColumnHandler() {
		super();
	}

	public abstract StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID) ;
}