package com.istarindia.apps.cmsutils.reports.column;

import com.istarindia.apps.dao.IstarUser;

public class TrainerColumnHandler  extends ColumnHandler{

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int trainerID) {
		if(status.equals("null")) {
			return new StringBuffer("<a href='/content/trainer/trainer_assessment_list.jsp?trainer_id=" + trainerID + "'> Reports</a>  ");	
		} else if(!status.equals("PENDING")) {
			return new StringBuffer("<a href='/content/trainer/trainer_assessment_report.jsp?assessment_id="+status+"&trainer_id=" + trainerID + "'> Reports</a>  ");
		} else return new StringBuffer("PENDING");
	}
}