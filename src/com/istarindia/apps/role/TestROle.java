/**
 * 
 */
package com.istarindia.apps.role;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Vaibhav
 *
 */
public class TestROle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChildLink child = new ChildLink();
		child.setDisplayName("View Course Structure");
		child.setUrl("/content/content_admin/course_structure.jsp");
		child.setValidRoles("CONTENT_ADMIN");
		ArrayList<ChildLink> children = new ArrayList<>();
		
		children.add(child);
		
		
		ParentLink parent = new ParentLink();
		parent.setDisplayName("Course");
		parent.setChildren(children);
		
		
		Menu menu = new Menu();
		ArrayList<ParentLink> links = new ArrayList<>();
		links.add(parent);
		menu.setLinks(links );
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(Menu.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(menu, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}
