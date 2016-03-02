package com.istarindia.apps.cmsutils.reports.column;

public abstract class ColumnHandler {

	public ColumnHandler() {
		super();
	}

	public abstract StringBuffer getHTML(String string);
}