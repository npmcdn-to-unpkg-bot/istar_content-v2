package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class CourseLinkHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID) {
		if (reportID == 30){
			return new StringBuffer("<a href='/content/content_admin/modify_course_structure.jsp?course_id=" + taskID + "'> Edit</a>  ");
		} else if (reportID == 31){
			return new StringBuffer("<a href='/content/content_admin/modify_course_structure.jsp?module_id=" + taskID + "'> Edit</a>  ");
		} else if (reportID == 32){
			return new StringBuffer("<a href='/content/content_admin/modify_course_structure.jsp?session_id=" + taskID + "'> Edit</a>  ");
		} else if (reportID == 33){
			return new StringBuffer("<a href='/content/edit_lesson?task_id=" + taskID + "'> Edit</a>  ");
		} else return null;
	}

}
