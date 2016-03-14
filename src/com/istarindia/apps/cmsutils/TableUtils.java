/**
 * 
 */
package com.istarindia.apps.cmsutils;

import java.util.ArrayList;

/**
 * @author Vaibhav
 * This is the master class to generate tables which can be used and re-used.
 */
public class TableUtils {
	
	String tableNotes;
	public static StringBuffer getTableHeader(String title, String[] headers, ArrayList<ArrayList<String>> items, int count) {
		StringBuffer out = new StringBuffer();
		out.append("<div class='panel panel-yellow margin-bottom-40' style='margin: 10px;    margin: 10px;border: 3px solid #FFC107;'> <div class='panel-heading'> <h3 class='panel-title'>"
				+ "<i class='fa fa-tasks'></i> "+title+"</h3> </div>");
		out.append("<div class='panel-body'></div>");
		out.append("<table class='table table-striped table-bordered display responsivess' id='datatable_fixed_column' width='100%' "
				+ "cellspacing='0'> <thead> <tr> ");
		//		+ "<th>#</th> <th>First Name</th> <th class='hidden-sm'>Last Name</th> <th>Username</th> <th>Status</th> "
		
		for (String string : headers) {
			out.append("<th style='max-width:100px !important; word-wrap: break-word;'>"+string+"</th>");
		}
		
		out.append("</tr> </thead> ");
		
		out.append("<tfoot> ");
		out.append("<tr> ");
		for (String string : headers) {
			if(!string.equalsIgnoreCase("Task Action") && !string.equalsIgnoreCase("Url") && !string.equalsIgnoreCase("Description")){
				out.append("<th style='max-width:100px !important; word-wrap: break-word;'>"+string+"</th>");
			}
			
		}
		
		
                out.append("</tr>");
            out.append("</tfoot>");
		for (ArrayList<String> arrayList : items) {
			out.append("<tr> ");
			for (int i=0; i<= count ;i++) {
				out.append("<td style='max-width:100px !important; word-wrap: break-word;'>"+arrayList.get(i)+"</td>");
			}out.append("</tr>");
		}
		out.append("</table> </div>");
		return out;
	}
	
}