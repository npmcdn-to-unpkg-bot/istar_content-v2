/**
 * 
 */
package com.istarindia.apps.cmsutils.reports;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

	public ArrayList<ArrayList<String>> getReportData(String sql1, ArrayList<IStarColumn> keys, HashMap<String, String> conditions) {
		ArrayList<ArrayList<String>> table = new ArrayList<>();
		IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		SQLQuery query = session.createSQLQuery(sql1);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		for(String key : conditions.keySet()) {
			try {
				query.setParameter(key, Integer.parseInt(conditions.get(key)));
			} catch (Exception e) {
				query.setParameter(key, conditions.get(key));
			}
			
		}

		List<HashMap<String, Object>> results = query.list();
		for (HashMap<String, Object> object : results) {
			ArrayList<String> row = new ArrayList<>();
			for (IStarColumn string : keys) {
				row.add(object.get(string.getName()) + "");
				System.out.println(object.get(string.getName()));
			}

			table.add(row);
		}
		return table;

	}

	public StringBuffer getReport(int reportID, HashMap<String, String> conditions, IstarUser user, String taskType) {
		File file = new File("/Users/mvsuryateja/git/content/istar_content/src/report_list.xml");
		ReportCollection reportCollection = new ReportCollection();
		Report report = new Report();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportCollection.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			reportCollection = (ReportCollection) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		for (Report r : reportCollection.getReports()) {
			if (r.getId() == reportID) {
				report = r;
			}
		}

		StringBuffer out = new StringBuffer();
		ArrayList<ArrayList<String>> data = getReportData(report.getSql(), report.getColumns(), conditions);
		
		out.append("<div class='row pie-progress-charts margin-bottom-60'>");
		out.append("<div class='panel panel-yellow margin-bottom-40' style='margin: 10px;    margin: 10px;border: 3px solid #f1c40f;'> <div class='panel-heading'> <h3 class='panel-title'><i class='fa fa-tasks'></i> "+report.getTitle()+" </h3> </div><div class='panel-body'></div>"
				+ "<table  class='table table-striped table-bordered display responsive nowrap dataTable datatable_report' id='datatable_report_" + reportID + "' data-graph_type='" + report.getType_of_report() + "' " + "" + "   data-graph_title='" + report.getTitle() + "' " + "data-graph_containter='report_container_" + reportID + "'>");
		out.append("<thead><tr>");
		for (IStarColumn column : report.getColumns()) {
			if(column.isVisible) {
				out.append("<th>" + column.getDisplayName() + "</th>");
			}
		}
		out.append("</tr></thead>");

		out.append("<tbody>");
		String ROWID = "";
		for (ArrayList<String> row : data) {
			out.append("<tr>");
			int i=0;
			for (IStarColumn column : report.getColumns()) {
				if(column.getName().equalsIgnoreCase("task_id")) {
					ROWID = row.get(i);
				}
				if(column.isVisible) {
					if(column.getColumnHandler().equalsIgnoreCase("NONE")) {
						out.append("<th>" + row.get(i) + "</th>");
					} else {
						out.append("<th>" + ReportColumnHandlerFactory.getInstance().getHandler(column.getColumnHandler()).getHTML(row.get(i), user, taskType, Integer.parseInt(ROWID)) + "</th>");
					}i++;
				}
			}
			ROWID = "";
			out.append("</tr>");
		}
		out.append("</tbody>");
		out.append("</table></div></div></div>");
		
		return out;

	}
}
