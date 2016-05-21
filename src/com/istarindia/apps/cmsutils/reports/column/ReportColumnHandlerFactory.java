/**
 * 
 */
package com.istarindia.apps.cmsutils.reports.column;

/**
 * @author vaibhav
 *
 */
public class ReportColumnHandlerFactory {

	private static ReportColumnHandlerFactory instance = null;
	   protected ReportColumnHandlerFactory() {
	      // Exists only to defeat instantiation.
	   }
	   public static ReportColumnHandlerFactory getInstance() {
	      if(instance == null) {
	         instance = new ReportColumnHandlerFactory();
	      }
	      return instance;
	   }
	public ColumnHandler getHandler(String columnHandler) {
		switch (columnHandler) {
		case "STATUS_HANDLER":
			return (new StatusColumnHandler());
		case "MEDIA_LINK_HANDLER":
			return (new MediaLinkHandler());
		case "LESSON_LINK_HANDLER":
			return (new LessonLinkHandler());	
		case "TASK_HISTORY":
			return (new TaskHistoryHandler());	
		case "ASSESSMENT":
			return (new AssessmentHandler());
		case "THEME":
			return (new UiThemeHandler());
		case "TRAINER":
			return (new TrainerColumnHandler());
		case "HANDOUT_HANDLER":
			return (new HandoutColumnHandler());	
		case "EDIT_DELETE_HANDLER":
			return (new EDIT_DELETE_HANDLERColumnHandler());	
		case "VACANCY_HANDLER":
			return (new VACANCY_HANDLERColumnHandler());		
		case "ANSWER_DOUBT":
			return (new ANSWER_DOUBTColumnHandler());		
		default:
			break;
		}
		return null;
		
	}
	

}
