/**
 * 
 */
package com.istarindia.apps.services;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.istarindia.apps.ListTypes;
import com.istarindia.apps.UserTypes;
import com.istarindia.apps.cmsutils.CMSFolder;
import com.istarindia.apps.cmsutils.reports.ReportCollection;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.role.Menu;

/**
 * @author Vaibhav
 *
 */
public class CMSRegistry {

	public static Menu menu;
	public static ReportCollection reportCollection;
	public static ArrayList<String> slideTemplates = new ArrayList<>();
	public static ArrayList<String> listTypes = new ArrayList<>();

	static {
		try {
			// req.getServletContext().getRealPath("/WEB-INF/fileName.properties")
			URL url = (new CMSRegistry()).getClass().getClassLoader().getResource("/menu.xml");
			File file = new File(url.toURI());
			JAXBContext jaxbContext = JAXBContext.newInstance(Menu.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			menu = (Menu) jaxbUnmarshaller.unmarshal(file);
			System.out.println(menu);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			// req.getServletContext().getRealPath("/WEB-INF/fileName.properties")
			URL url = (new CMSRegistry()).getClass().getClassLoader().getResource("/report_list.xml");
			File file = new File(url.toURI());
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportCollection.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			reportCollection = (ReportCollection) jaxbUnmarshaller.unmarshal(file);
			System.out.println(menu);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		slideTemplates.add("NO_CONTENT");
		slideTemplates.add("ONLY_TITLE");
		slideTemplates.add("ONLY_2TITLE");
		slideTemplates.add("ONLY_TITLE_PARAGRAPH");
		slideTemplates.add("ONLY_TITLE_LIST");
		slideTemplates.add("ONLY_TITLE_TREE");
		slideTemplates.add("ONLY_PARAGRAPH");
		slideTemplates.add("ONLY_TITLE_VIDEO");
		slideTemplates.add("ONLY_TITLE_PARAGRPH_IMAGE");
		slideTemplates.add("ONLY_TITLE_IMAGE");
		slideTemplates.add("ONLY_IMAGE");
		slideTemplates.add("ONLY_TITLE_IMAGE_PARAGRPH");
		
		
		
		listTypes.add(ListTypes.SIMPLE_LIST);
		listTypes.add(ListTypes.PROCESS_LIST);
		listTypes.add(ListTypes.CYCLE_LIST);
		listTypes.add(ListTypes.IDEAS_LIST);
		listTypes.add(ListTypes.SQUARE_LIST);
		listTypes.add(ListTypes.PYRAMID_LIST);
		listTypes.add(ListTypes.FUNNEL_LIST);
		listTypes.add(ListTypes.OCTOPUS_LIST);
		listTypes.add(ListTypes.BURGER_LIST);
		listTypes.add(ListTypes.POLE_LIST);
		listTypes.add(ListTypes.LADDER_LIST);
		listTypes.add(ListTypes.VERTICAL_POLE_LIST);

	}

	public static CMSFolder root = CMSFolder.init();

	public static void writeAuditLog(String string, Integer id) {
		// TODO Auto-generated method stub

	}

	public static ArrayList<ArrayList<String>> getSimulatedList(int count) {
		ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
		ArrayList<String> item = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			item = new ArrayList<String>();
			for (int j = 0; j < count; j++) {
				item.add(RandomStringUtils.randomAlphabetic(7));
			}
			items.add(item);
		}
		return items;
	}

	public static ArrayList<IstarUser> getCUsers() {
		IstarUserDAO dao = new IstarUserDAO();
		ArrayList<IstarUser> items = new ArrayList<>();
		for (IstarUser user : (ArrayList<IstarUser>) dao.findByProperty("userType", "CONTENT_CREATOR")) {
			System.out.println("user here is >>" + user.getClass().toString());
			if (user.getClass().toString().endsWith("ContentCreator")) {
				System.out.println("user here is >>" + user.getEmail());
				items.add(user);

			}
		}
		return items;
	}

	public static ArrayList<IstarUser> getCRUsers() {
		IstarUserDAO dao = new IstarUserDAO();
		ArrayList<IstarUser> items = new ArrayList<>();
		for (IstarUser user : (ArrayList<IstarUser>) dao.findByProperty("userType",  UserTypes.CONTENT_REVIEWER)) {
			System.out.println("106--->>>"+user.getEmail());
			items.add(user);
		}
		return items;
	}

	public static ArrayList<IstarUser> getCreativeCreators() {
		IstarUserDAO dao = new IstarUserDAO();
		ArrayList<IstarUser> items = new ArrayList<>();
		for (IstarUser user : (ArrayList<IstarUser>)dao.findByProperty("userType", UserTypes.CREATIVE_CREATOR)) {
			System.out.println("user here is >>"+user.getClass().toString());
				System.out.println("user here is >>"+user.getEmail());
				items.add(user);
			
		}
		return items;
	}
	
	public static Boolean checkloginStatus(HttpServletRequest request) {
		boolean status = false;
		Enumeration attrs = request.getSession().getAttributeNames();
		while (attrs.hasMoreElements()) {
			if(attrs.nextElement().toString().equalsIgnoreCase("user")) {
				status = true;
			} 
		}

		return status;

	}
}