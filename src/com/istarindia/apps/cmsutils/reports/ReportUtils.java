/**
 * 
 */
package com.istarindia.apps.cmsutils.reports;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.crypto.interfaces.PBEKey;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.id.IntegralDataTypeHolder;

import com.istarindia.apps.cmsutils.reports.column.ReportColumnHandlerFactory;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.services.CMSRegistry;

/**
 * @author Vaibhav
 *
 */
public class ReportUtils {

	
	
	public HashMap< String , String> getReport(int reportID, int idd) {
		ReportCollection reportCollection = new ReportCollection();
		Report report = new Report();
		try {
			URL url = (new CMSRegistry()).getClass().getClassLoader().getResource("/report_list.xml");
			File file = new File(url.toURI());
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportCollection.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			reportCollection = (ReportCollection) jaxbUnmarshaller.unmarshal(file);

		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		for (Report r : reportCollection.getReports()) {
			if (r.getId() == reportID) {
				report = r;
			}
		}
		
		String sql = "select ";
		HashSet<String> seettt = new HashSet<>();
		
		
		for (IStarColumn string : report.getColumns()) {
			seettt.add(string.getName());
		}
		for (String iterable_element : seettt) {
			sql = sql + iterable_element +", ";
		}
		sql = sql.replaceAll(", $", "");
		sql = sql+" from "+ report.getTableName() + " where id="+ idd;
		
		IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		List<HashMap<String, Object>> results = query.list();
	
		ArrayList<String> columns = new ArrayList<>();
		HashMap< String , String> cols = new HashMap<>();
		for (HashMap<String, Object> hashMap : results) {
			for (String string : hashMap.keySet()) {
				
				for (IStarColumn istarcol : report.getColumns()) {
					if(istarcol.getName().equalsIgnoreCase(string)) {
						//columns.add(hashMap.get(string).toString());
						cols.put(string, hashMap.get(string).toString());
					}
				}
			}
		}
		
		//columns.add(results.get(0).get("id").toString());
		return cols;
	}
	
	public ArrayList<ArrayList<String>> getReportData(String sql1, ArrayList<IStarColumn> keys, HashMap<String, String> conditions) {
		ArrayList<ArrayList<String>> table = new ArrayList<>();
		IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		SQLQuery query = session.createSQLQuery(sql1);
		System.err.println(sql1);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		for(String key : conditions.keySet()) {
			try {
				if(sql1.contains(key)) {
					System.out.println("key->"+ key + "   value-> "+conditions.get(key));
					query.setParameter(key, Integer.parseInt(conditions.get(key)));
				} 
			} catch (Exception e) {
				query.setParameter(key, conditions.get(key));
			}
		}
		
		List<HashMap<String, Object>> results = query.list();
		for (HashMap<String, Object> object : results) {
			ArrayList<String> row = new ArrayList<>();
			for (IStarColumn string : keys) {		
				row.add(object.get(string.getName()) + "");
			}

			table.add(row);
		}
		System.err.println("Result Count"+ results.size() );
		return table;

	}

	public StringBuffer getReport(int reportID, HashMap<String, String> conditions, IstarUser user, String taskType) {
		//System.err.println("=====================================>>>"+reportID);
		ReportCollection reportCollection = new ReportCollection();
		Report report = new Report();
		try {
			URL url = (new CMSRegistry()).getClass().getClassLoader().getResource("/report_list.xml");
			File file = new File(url.toURI());
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportCollection.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			reportCollection = (ReportCollection) jaxbUnmarshaller.unmarshal(file);

		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		for (Report r : reportCollection.getReports()) {
			if (r.getId() == reportID) {
				report = r;
			}
		}

		StringBuffer out = new StringBuffer();
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		try {
			data = getReportData(report.getSql(), report.getColumns(), conditions);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("reportID===============>>>"+ reportID);
		}
		
		String style="style='margin: 10px;    margin: 10px;border: 3px solid #FFC107;'";
		String tableStyle ="style='width: 98%;margin-left: 1%;margin-right: 1%;'";
		if(report.getType_of_report().equalsIgnoreCase("table")) {
			style="style='margin: 10px;    margin: 10px;border: 3px solid #FFC107;'";
		} else {
			style="style='margin: 10px;    margin: 10px;border: 3px solid #FFC107;'";
			tableStyle = "style='display:none'";
		}
		out.append("<div class='row pie-progress-charts margin-bottom-60'>");
		out.append("<div class='panel panel-yellow margin-bottom-40' "+style+"> <div class='panel-heading'> <h3 class='panel-title'><i class='fa fa-tasks'></i> "+report.getTitle()+" </h3> </div><div id='datatable_report_panel_body' class='panel-body'></div>"
				+ "<table "+tableStyle+" class='table table-striped table-bordered display responsive  dataTable datatable_report' "
				+ "id='datatable_report_" + reportID + "' data-graph_type='" + report.getType_of_report() + "' " + "" + "  "
						+ " data-graph_title='" + report.getTitle() + "' " + "data-graph_containter='report_container_" + reportID +
						"'>");
		out.append("<thead><tr>");
		for (IStarColumn column : report.getColumns()) {
			if(column.isVisible) {
				out.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + column.getDisplayName() + "</th>");
			}
		}
		out.append("</tr></thead>");
		if(report.getType_of_report().equalsIgnoreCase("table")) {
		out.append("<tfoot><tr>");
		for (IStarColumn column : report.getColumns()) {
			if(column.isVisible) {
				out.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + column.getDisplayName() + "</th>");
			}
		}
		out.append("</tr></tfoot>");
		}
		out.append("<tbody id='datatable_report_"+reportID+"_body'>");
		String ROWID = "";
		for (ArrayList<String> row : data) {
			out.append("<tr id="+row.get(1)+">");
			int i=0;
			for (IStarColumn column : report.getColumns()) {
				try {
					if(column.getName().equalsIgnoreCase("task_id") || column.getName().equalsIgnoreCase("id") || column.getName().equalsIgnoreCase("handout_id")) {
						ROWID = row.get(i);
					}
					if(column.isVisible) {
						if(column.getColumnHandler().equalsIgnoreCase("NONE")) {
							out.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + row.get(i) + "</th>");
						} else {
							out.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + ReportColumnHandlerFactory.getInstance().getHandler(column.getColumnHandler()).getHTML(row.get(i), user, taskType, Integer.parseInt(ROWID), reportID) + "</th>");
						}i++;
					}
				} catch (Exception e) {
					System.err.println(column.getName());
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			ROWID = "";
			out.append("</tr>");
		}
		out.append("</tbody>");
		out.append("</table>");
		out.append("<div id='report_container_" + reportID+"'></div>");
		out.append("</div></div>");
		return out;

	}
	
	public StringBuffer getEditReport(int reportID, HashMap<String, String> conditions, IstarUser user, String taskType, HashMap< String , String> value) {
		//System.err.println("=====================================>>>"+reportID);
		ReportCollection reportCollection = new ReportCollection();
		Report report = new Report();
		try {
			URL url = (new CMSRegistry()).getClass().getClassLoader().getResource("/report_list.xml");
			File file = new File(url.toURI());
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportCollection.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			reportCollection = (ReportCollection) jaxbUnmarshaller.unmarshal(file);

		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		for (Report r : reportCollection.getReports()) {
			if (r.getId() == reportID) {
				report = r;
			}
		}

		StringBuffer out = new StringBuffer();
		
		String style="style='margin: 10px;    margin: 10px;border: 3px solid #FFC107;'";
		String tableStyle ="style='width: 98%;margin-left: 1%;margin-right: 1%;'";
		if(report.getType_of_report().equalsIgnoreCase("table")) {
			style="style='margin: 10px;    margin: 10px;border: 3px solid #FFC107;'";
		} else {
			style="style='margin: 10px;    margin: 10px;border: 3px solid #FFC107;'";
			tableStyle = "style='display:none'";
		}
		out.append("<div class='row pie-progress-charts margin-bottom-60'>");
		out.append("<div class='panel panel-yellow margin-bottom-40' "+style+"> <div class='panel-heading'> <h3 class='panel-title'><i class='fa fa-tasks'></i> "+report.getTitle()+" </h3> </div><div class='panel-body'></div>"
				+ "<table "+tableStyle+" class='table table-striped table-bordered display responsive  dataTable1 datatable_report1' "
				+ "id='datatable_report_" + reportID + "' data-graph_type='" + report.getType_of_report() + "' " + "" + "  "
						+ " data-graph_title='" + report.getTitle() + "' " + "data-graph_containter='report_container_" + reportID +
						"'>");
		out.append("<thead><tr>");
		
			
		for (IStarColumn column : report.getColumns()) {
			if(column.isVisible) {
				out.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + column.getDisplayName() + "</th>");
			}
		}
		
		out.append("</tr></thead>");
		if(report.getType_of_report().equalsIgnoreCase("table")) {
		out.append("<tfoot><tr>");
		for (IStarColumn column : report.getColumns()) {
			if(column.isVisible) {
				out.append("<th style='max-width:100px !important; word-wrap: break-word;'>" + column.getDisplayName() + "</th>");
			}
		}
		out.append("</tr></tfoot>");
		}
		out.append("<tbody>");
		String ROWID = "";
		HashMap< String , String> row  = value;
	//	System.err.println("l;dfjhcaldskjhflsdkjh"+value.toString());
		out.append("<form name='update_entity' id='update_en' action='/content/edit_entity' method='POST'>");
		out.append("<input type='hidden' name='report_id' value='"+reportID+"' >");
		out.append("<input type='hidden' name='entity_name' value='"+report.getTableName().trim()+"' >");
		
		
		out.append("<tr>");
			int i=0;
			for (IStarColumn column : report.getColumns()) {
				try {
					if(column.getName().equalsIgnoreCase("task_id") || column.getName().equalsIgnoreCase("id")) {
						ROWID = row.get(i);
						
					}
					//for(String value: )
					
					if(column.isVisible) {
						if(column.getColumnHandler().equalsIgnoreCase("NONE") && column.is_updatetable) {
							out.append("<td style='max-width:100px !important; word-wrap: break-word;'>");
							if(row.get(column.getName()).length()<50)
							{
								out.append("<input value='"+row.get(column.getName())+"' type='text' name='"+column.getName()+"' ></th>");
							}
							else
							{
								 out.append("<textarea rows='10' cols='40' name="+column.getName()+" >"+row.get(column.getName())+" </textarea>");
							}		
								
						}
						else if(column.getColumnHandler().equalsIgnoreCase("NONE") ) {
							out.append("<td style='max-width:100px !important; word-wrap: break-word;'>"+row.get(column.getName())+"</th>");
						}
						else {
							out.append("<td style='max-width:100px !important; word-wrap: break-word;'>" + ReportColumnHandlerFactory.getInstance().getHandler(column.getColumnHandler()).getHTML(row.get(i), user, taskType, Integer.parseInt(ROWID), reportID) + "</th>");
						}i++;
					}
				} catch (Exception e) {
					System.err.println("column------>"+ column.getName());
					e.printStackTrace();
				}
			
			
		} 
			out.append("<input type='hidden' name='ROWID' value='"+row.get("id")+"' >");
			out.append("<td><button type='submit' name='submit' value='update' class='btn-u'>Update</button> &nbsp;&nbsp;&nbsp;&nbsp;<button type='submit' name='submit' value='delete' class='btn-u'>Delete</button> </td></form>");
			
			
			ROWID = "";
			out.append("</tr>");
			out.append("");
		out.append("</tbody>");
		out.append("</table>");
		out.append("<div id='report_container_" + reportID+"'></div>");
		out.append("</div></div>");
		return out;

	}

	private ArrayList<ArrayList<String>> getEditReportData(String updateSQL, ArrayList<IStarColumn> columns,
			HashMap<String, String> conditions) {
		
		return null;
	}
	
}