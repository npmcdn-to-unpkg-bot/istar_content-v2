package com.istarindia.apps.cmsutils.reports.column;

import java.util.List;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;

public class CourseLinkHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID, int reportID , String itemType) {
		if (reportID == 30){
			return new StringBuffer("<a href='/content/content_admin/modify_course.jsp?course_id=" + taskID + "'> Edit</a>  ");
		} else if (reportID == 31){
			return new StringBuffer("<a href='/content/content_admin/modify_module.jsp?module_id=" + taskID + "'> Edit</a>  ");
		} else if (reportID == 32){
			return new StringBuffer("<a href='/content/content_admin/modify_session.jsp?session_id=" + taskID + "'> Edit</a>  ");
		} else if (reportID == 33){
			Task task = (new TaskDAO()).findById(taskID);
			Lesson lesson = new LessonDAO().findById(task.getItemId());
			List<Presentaion> list = new PresentaionDAO().findByProperty("lesson", lesson);
			StringBuffer element = new StringBuffer();
			
			element.append("<div class='btn-group'>  <button style='width: 115%;' type='button' class='btn btn-warning dropdown-toggle' data-toggle='dropdown' aria-expanded='false'> Choose Action <i class='fa fa-angle-down'></i> </button>  ");
			element.append("<ul class='dropdown-menu' role='menu'>");
			
			if(!task.getStatus().equalsIgnoreCase("PUBLISHED")) {
				element.append("<li><a href='/content/edit_lesson?task_id=" + taskID + "'> Edit</a> </li>");
				element.append("<li><a href='/content/change_status?task_id="+taskID+"&new_status=DELETED&source_link=modify_course_structure'>Delete</a></li> ");
			} 
			
			if(list.size()>0)
			{
				element.append("<li><a onclick='openWin(\"/content/lesson/preview.jsp?ppt_id=" + list.get(0).getId() + "\")'  href='#' >Mobile Preview</a></li>  ");
				element.append("<li><a target='_blank' href='/content/lesson/preview_desktop.jsp?ppt_id=" + list.get(0).getId() +"' >Speaker Preview</a></li> ");
				element.append("</ul> </div>");
			}
			
			return element;
			
		} else return null;
	}
}
