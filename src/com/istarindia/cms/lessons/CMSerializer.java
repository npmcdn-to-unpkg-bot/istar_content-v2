package com.istarindia.cms.lessons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.SlideDAO;
import com.istarindia.cms.lessons.CMSLesson;
import com.istarindia.cms.lessons.CMSSlide;

/**
 * 
 */

/**
 * @author Vaibhav
 *
 */
public class CMSerializer {
	public String serializeLesson(CMSLesson lesson) {
		StringBuffer out = new StringBuffer();

		for (CMSSlide slide : lesson.getSlides()) {
			out.append(serializeSlide(slide));
		}
		return out.toString();
	}

	private StringBuffer serializeSlide(CMSSlide slide) {
		StringBuffer out = new StringBuffer();
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		VelocityContext context = new VelocityContext();
		context.put("slide", slide);
		Template t = ve.getTemplate(slide.getTemplateName() + ".vm");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		out.append(writer.toString());
		return out;

	}

	public String serializeLesson(Presentaion ppt) {
		StringBuffer out = new StringBuffer();

		IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		String sql1 = "select * from slide where presentation_id=" + ppt.getId() + " order by id";
		SQLQuery query = session.createSQLQuery(sql1);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String, Object>> results = query.list();

		for (HashMap<String, Object> slide1 : results) {
			Slide slide = new Slide();
			slide.setSlideText(slide1.get("slide_text").toString());
			slide.setTemplate(slide1.get("template").toString());
			out.append(serializeSlide(slide));
		}
		return out.toString();
	}

	private StringBuffer serializeSlide(Slide slide) {
		StringBuffer out = new StringBuffer();
		CMSSlide cMSlide = new CMSSlide();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CMSSlide.class);
			InputStream in = IOUtils.toInputStream(slide.getSlideText(), "UTF-8");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			cMSlide = (CMSSlide) jaxbUnmarshaller.unmarshal(in);
			System.out.println(slide);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CMSList newList = new CMSList();
		newList.setList_type(cMSlide.getList().getList_type());
		newList.setItems(new ArrayList<>());
		for (CMSTextItem item : cMSlide.getList().getItems()) {
			if(item.getText().trim().equalsIgnoreCase("")) {
			//	cMSlide.getList().getItems().remove(item);
			} else {
				newList.getItems().add(item);
				
				
				try {
					if(item.getList().items.size() !=0) {
						System.err.println("111");
					
						CMSList childList = new CMSList();
						ArrayList<CMSTextItem> items3 = new ArrayList<>();
						childList.setItems(items3);
						for (CMSTextItem childItem : item.getList().items) {
							if(!childItem.getText().trim().startsWith("$slide")) {
								childList.getItems().add(childItem);
							}
						}
						item.setList(childList);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		cMSlide.setList(newList);
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		VelocityContext context = new VelocityContext();
		cMSlide.setTeacherNotes(slide.getTeacherNotes());
		
		context.put("slide", cMSlide);
		String templateVMFileName = cMSlide.getTemplateName() + ".vm";
		if (cMSlide.getTemplateName().equalsIgnoreCase("ONLY_TITLE_LIST")) {
			templateVMFileName = cMSlide.getList().getList_type() + "___" + cMSlide.getTemplateName() + ".vm";
		}

		Template t = ve.getTemplate(templateVMFileName);
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		out.append(writer.toString());
		return out;
	}

	public static StringBuffer serializeBlankSlide(String templateName, String slideID) {

		SlideDAO dao = new SlideDAO();
		Slide cm = new Slide();CMSSlide slide = new CMSSlide();
		if (!slideID.equalsIgnoreCase("null")) {
			cm = dao.findById(Integer.parseInt(slideID));
			
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(CMSSlide.class);
				InputStream in = IOUtils.toInputStream(cm.getSlideText(), "UTF-8");
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				slide = (CMSSlide) jaxbUnmarshaller.unmarshal(in);
				System.out.println(slide);
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		StringBuffer out = new StringBuffer();
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		VelocityContext context = new VelocityContext();
		context.put("slide", slide);

		Template t = ve.getTemplate(templateName + ".vm");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		out.append(writer.toString());
		return out;
	}
}
