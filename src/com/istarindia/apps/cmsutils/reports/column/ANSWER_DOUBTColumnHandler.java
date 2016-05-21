/**
 * 
 */
package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

/**
 * @author vaibhav
 *
 */
public class ANSWER_DOUBTColumnHandler extends ColumnHandler {

	/* (non-Javadoc)
	 * @see com.istarindia.apps.cmsutils.reports.column.ColumnHandler#getHTML(java.lang.String, com.istarindia.apps.dao.IstarUser, java.lang.String, int, int)
	 */
	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID) {
		return new StringBuffer("<a href='/content/content_admin/answer_doubt.jsp?id="+taskID+"'> Answer</a>  ");
	}

}
