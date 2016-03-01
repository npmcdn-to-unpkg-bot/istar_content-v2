/**
 * 
 */
package com.istarindia.apps.cmsutils.reports;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;import javax.crypto.interfaces.PBEKey;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.services.CMSRegistry;

/**
 * @author Vaibhav
 *
 */
public class ReportUtils {
	
	public ArrayList<ArrayList<String>> getReportData(String sql1, ArrayList<String> keys) {
		ArrayList<ArrayList<String>> table = new ArrayList<>();
		IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		
		//String sql1 = "SELECT content_creator.name, count(*) FROM public.task, public.content_creator WHERE   task.actor_id = content_creator.id group by content_creator.name";
		SQLQuery query = session.createSQLQuery(sql1);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String, Object>> results = query.list();
		
		for (HashMap<String, Object> object : results) {
			ArrayList<String> row = new ArrayList<>();
			row.add(object.get(keys.get(0))+"");
			row.add(object.get(keys.get(1).toString())+"");
			table.add(row);
		}
		return table;
		
	}
	
	public StringBuffer getReport(int reportID) {
		File file = new File("C:\\Users\\Vaibhav\\workspace\\istar_content\\src\\report_list.xml");
		ReportCollection reportCollection = new ReportCollection();
		Report report =  new Report();
try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportCollection.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			reportCollection = (ReportCollection) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		for (Report r : reportCollection.getReports()) {
			if(r.getId()==reportID)
			{
				report = r;
			}
			}
		
		StringBuffer out = new StringBuffer();
		ArrayList<ArrayList<String>> data = getReportData(report.getSql(), report.getColumns());
		out.append("<p>"+report.getTitle()+"</p>");
		out.append("<div class='row pie-progress-charts margin-bottom-60'>");
		out.append("<table  class='datatable_report' id='datatable_report_"+reportID+"' data-graph_type='"+report.getType_of_report()+"' "
				+ ""
				+ "   data-graph_title='"+report.getTitle()+"' "
				+ "data-graph_containter='report_container_"+reportID+"'>");
		out.append("<thead><tr>");
		for (String column : report.getColumns()) {
			out.append("<th>"+column+"</th>");
		}
		out.append("</tr></thead>");
		
		out.append("<tbody>");
		for(ArrayList<String>  row :  data) { 
			out.append("<tr>");
			for(String  cell :  row) {
				out.append("<td>"+cell+"</td>");
			}
			out.append("</tr>");
		}
		out.append("</tbody>");
		out.append("</table></div>");
		out.append("<div id='report_container_"+reportID+"'>aaaa</div>");
		
		return out;
		
		
	}
}
