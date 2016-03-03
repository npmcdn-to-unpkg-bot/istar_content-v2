package com.istarindia.apps.cmsutils.reports.column;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.task.CreateLessonTaskManager;
import com.istarindia.apps.services.task.TaskStage;

public class StatusColumnHandler extends ColumnHandler {

	@Override
	public StringBuffer getHTML(String status, IstarUser user, String taskType, int taskID) {
		StringBuffer out = new StringBuffer();
		HashMap<String, String> items = new HashMap<>();
		
		TaskDAO dao = new TaskDAO();
		Task task = dao.findById(taskID);
		for(TaskStage stage : getAllVaildStages(task.getStatus())) {
			String[] role_array = stage.getValidRole().split(",");
			for(String roles : role_array)
			{ 
				if(user.getUserType().equals(roles))
				{
					if(stage.getName().equalsIgnoreCase(StatusTypes.REVIEW))
					{
						items.put("<li><a href='/content/review_task?task_id="+taskID+"'>"+stage.getName()+"</a></li> ", "<li><a href='/content/review_task?task_id="+taskID+"'>"+stage.getName()+"</a></li> ");

					}
					else if(stage.getName().equalsIgnoreCase(StatusTypes.EDIT))
					{
						items.put("<li><a href='/content/edit_lesson?lesson_id="+task.getItemId()+"'>"+stage.getName()+"</a></li> ", "<li><a href='/content/edit_lesson?lesson_id="+task.getItemId()+"'>"+stage.getName()+"</a></li> ");

					}
					else if(stage.getName().equalsIgnoreCase(StatusTypes.UNPUBLISHED))
					{	
						items.put("<li><a href='/content/change_status?task_id="+task.getId()+"&new_status="+StatusTypes.CREATED+"'>"+stage.getName()+"</a></li> ", "<li><a href='/content/change_status?task_id="+task.getId()+"&new_status="+StatusTypes.CREATED+"'>"+stage.getName()+"</a></li> ");						
					}
					else
					{
						items.put("<li><a href='/content/change_status?task_id="+task.getId()+"&new_status="+stage.getName()+"'>"+stage.getName()+"</a></li> ", "<li><a href='/content/change_status?task_id="+task.getId()+"&new_status="+stage.getName()+"'>"+stage.getName()+"</a></li> ");
					
					}	
				}
			}
		
		
		
	}
		out.append("<div class='btn-group'> <button type='button' class='btn btn-warning dropdown-toggle' data-toggle='dropdown' aria-expanded='true'> Change Task State <i class='fa fa-angle-down'></i> </button> <ul class='dropdown-menu' role='menu'>");

		for (String iterable_element : items.keySet()) {
			out.append(iterable_element);
		}
		out.append("</ul> </div>");
		return out;
	}		

	private List<TaskStage> getAllVaildStages(String status) {
		
		ArrayList<TaskStage> items = new ArrayList<TaskStage>();
		for (TaskStage taskStage : CreateLessonTaskManager.taskStages) {
			if (status.equalsIgnoreCase(taskStage.getName())) {
				try {
					for (int tt : taskStage.validNextStages) {
						items.add(CreateLessonTaskManager.taskStages.get(tt));
					}
				} catch (Exception e) {
					System.out.println("--------"+status);
				}
			}
		}

		return items;
	}
}
