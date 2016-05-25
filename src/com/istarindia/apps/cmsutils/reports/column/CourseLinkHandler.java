package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.StatusTypes;
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
			return new StringBuffer("<div class='btn-group'> <button type='button' class='btn btn-warning dropdown-toggle' data-toggle='dropdown' aria-expanded='false'> "
					+ "Action <i class='fa fa-angle-down'></i> </button> <ul class='dropdown-menu' role='menu'><li><a href='/content/edit_lesson?task_id=" + taskID + "'> "
					+ "Edit</a> </li><li><a href='/content/change_status?task_id="+taskID+"&new_status=DELETED&source_link=modify_course_structure'>Delete</a></li> </ul> </div>");
		} else return null;
	}
}
