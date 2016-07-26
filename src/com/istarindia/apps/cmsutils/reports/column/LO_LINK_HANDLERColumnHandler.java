/**
 * 
 */
package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

/**
 * @author vaibhav
 *
 */
public class LO_LINK_HANDLERColumnHandler extends ColumnHandler {

	/* (non-Javadoc)
	 * @see com.istarindia.apps.cmsutils.reports.column.ColumnHandler#getHTML(java.lang.String, com.istarindia.apps.dao.IstarUser, java.lang.String, int, int)
	 */
	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID , String itemType) {
		// TODO Auto-generated method stub
		return new StringBuffer("<a href='/content/content_admin/course_learning_objectives.jsp?lo_id=" + taskID + "'> Edit</a>  ");
	}

}
