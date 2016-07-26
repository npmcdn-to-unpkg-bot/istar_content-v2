/**
 * 
 */
package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

/**
 * @author vaibhav
 *
 */
public class EDIT_DELETE_HANDLERColumnHandler extends ColumnHandler {

	/* (non-Javadoc)
	 * @see com.istarindia.apps.cmsutils.reports.column.ColumnHandler#getHTML(java.lang.String, com.istarindia.apps.dao.IstarUser, java.lang.String, int)
	 */
	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID , String itemType) {
		return new StringBuffer("<a href='/content/edit_entity?report_id="+reportID+"&id="+ taskID+ "'> Edit</a>  ");
	}

}
