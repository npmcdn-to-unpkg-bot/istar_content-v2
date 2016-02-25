/**
 * 
 */
package com.istarindia.apps.services;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.istarindia.apps.cmsutils.CMSFolder;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.role.Menu;

/**
 * @author Vaibhav
 *
 */
public class CMSRegistry {

	public static Menu menu;
	static {
		try {

			File file = new File("C:\\Users\\Vaibhav\\workspace\\istar_content\\src\\menu.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Menu.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			menu = (Menu) jaxbUnmarshaller.unmarshal(file);
			System.out.println(menu);

		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
	}
	public static CMSFolder root = CMSFolder.init();
	public static void writeAuditLog(String string, Integer id) {
		// TODO Auto-generated method stub
		
	}

	public static ArrayList<ArrayList<String>> getSimulatedList(int count) {
		ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
		ArrayList<String> item = new ArrayList<>();
		for(int i=0; i< 1000; i++) {
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
		for (IstarUser user : (ArrayList<IstarUser>)dao.findByProperty("userType", "CONTENT_CREATOR")) {
			if(user.getClass().toString().endsWith("ContentCreator")) {
				items.add(user);
				
			}
		}
		return items;
	}
	
	public static ArrayList<IstarUser> getCRUsers() {
		IstarUserDAO dao = new IstarUserDAO();
		ArrayList<IstarUser> items = new ArrayList<>();
		for (IstarUser user : (ArrayList<IstarUser>)dao.findByProperty("userType", "CONTENT_CREATOR")) {
			if(user.getClass().toString().endsWith("ContentReviewer")) {
				items.add(user);
				
			}
		}
		return items;	}

}