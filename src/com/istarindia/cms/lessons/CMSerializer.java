package com.istarindia.cms.lessons;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.Slide;
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
		Template t = ve.getTemplate(slide.getTemplateName()+".vm");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		out.append(writer.toString());
		return out;

	}
	
	
	
	public String serializeLesson(Presentaion ppt) {
		StringBuffer out = new StringBuffer();
		for (Slide slide : ppt.getSlides()) {
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

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		VelocityContext context = new VelocityContext();
		context.put("slide", cMSlide);
	
		Template t = ve.getTemplate(cMSlide.getTemplateName() + ".vm");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		out.append(writer.toString());
		return out;
	}

}
