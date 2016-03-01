package test;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.istarindia.apps.cmsutils.CMSFolder;
import com.istarindia.apps.cmsutils.reports.IStarColumn;
import com.istarindia.apps.cmsutils.reports.Report;
import com.istarindia.apps.cmsutils.reports.ReportCollection;
import com.istarindia.apps.cmsutils.reports.ReportUtils;
import com.istarindia.apps.dao.Folder;
import com.istarindia.apps.services.CMSUtils;
import com.istarindia.apps.services.FolderService;
import com.istarindia.apps.services.LessonService;
import com.istarindia.cms.lessons.CMSLesson;

public class testDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		/*String[] keys = {"name","count"};
		//ReportUtils r = new ReportUtils();
		//r.getReportData("ss", keys);
		*/
		
		IStarColumn col = new IStarColumn(true, "NONE", "name", "name");
		String sql = "SELECT content_creator.name, count(*) FROM public.task, public.content_creator WHERE   task.actor_id = content_creator.id group by content_creator.name";
		ArrayList<IStarColumn> keys = new ArrayList<>();
		keys.add(col);keys.add(col);keys.add(col);keys.add(col);
		Report r = new Report(sql , keys , "pie", 1);
		
		
		ReportCollection c = new ReportCollection();
		ArrayList<Report> reports = new ArrayList<>();
		reports.add(r);
		c.setReports(reports);
		
		try {
			//File file = new File("C:\\Users\\Vaibhav\\workspace\\reveal\\src\\file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportCollection.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.marshal(lesson, file);
			jaxbMarshaller.marshal(c, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}

}
