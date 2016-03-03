package com.istarindia.apps.cmsutils.reports.column;

public class MediaLinkHandler extends StatusColumnHandler {

	public StringBuffer getHTML(String link)
	{
		
		if(link!=null)
		{
			return new StringBuffer("<a href='"+link+"' target='_new'>Media Link</a>");
		}
		else
		{
			return new StringBuffer("Not Published yet");
		}	
		
	}
}
