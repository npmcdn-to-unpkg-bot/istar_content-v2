package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class UiThemeHandler  extends ColumnHandler{

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int themeID) {
		return new StringBuffer("<a href='/content/creative_admin/edit_theme.jsp?theme_id=" + themeID + "'> Edit</a>  ");	
	}

}
