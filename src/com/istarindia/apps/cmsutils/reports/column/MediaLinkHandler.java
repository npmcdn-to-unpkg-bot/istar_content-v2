package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class MediaLinkHandler extends ColumnHandler {

	

	@Override
	public StringBuffer getHTML(String link, IstarUser user, String taskType, int taskID, int reportID, String itemType) {
		if(!link.equalsIgnoreCase("null"))
		{
			return new StringBuffer("<a href='"+link+"' target='_new'>Media Link</a>");
			//return new StringBuffer(link);
		}
		else
		{
			return new StringBuffer("Not Published yet");
			//return new StringBuffer("---"+link);
		}	
	
	}
}
