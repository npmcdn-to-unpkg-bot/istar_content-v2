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
	public StatusColumnHandler getHandler(String columnHandler) {
		switch (columnHandler) {
		case "STATUS_HANDLER":
			return (new StatusColumnHandler());
		default:
			break;
		}
		return null;
		
	}
	

}
