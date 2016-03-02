package com.istarindia.apps.cmsutils.reports.column;

public class StatusColumnHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status) {
		return new StringBuffer().append("-----------"+status);
	}

}
