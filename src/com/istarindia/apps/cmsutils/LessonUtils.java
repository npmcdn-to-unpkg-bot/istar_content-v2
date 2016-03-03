/**
 * 
 */
package com.istarindia.apps.cmsutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.cms.lessons.CMSSlide;

/**
 * @author Vaibhav
 *
 */
public class LessonUtils {

	public StringBuffer getEditForm(Lesson lesson) {
		StringBuffer out = new StringBuffer();
		if (lesson.getPresentaion() != null) {
			Presentaion ppt = lesson.getPresentaion();

			out.append("<div class=' col-md-12 '>");
			out.append("<div class='panel panel-sea'>");
			out.append("<div class='panel-heading'>");
			out.append("<h3 class='panel-title'>");
			out.append("<i class='fa fa-tasks'></i>New Slide");
			out.append("</h3>");
			out.append("</div>");
			out.append("<div class='panel-body'>");
			out.append("<form class='form-inline' role='form' action='/content/fill_tempate.jsp'>");
			out.append("<input name='ppt_id' value='" + ppt.getId() + "' type='hidden'/>");

			out.append("<div class='form-group'>");
			out.append("<select class='form-control' name='slide_type' style='margin-right: 50px'>");

			for (String template : CMSRegistry.slideTemplates) {
				out.append("<option value='" + template + "'>" + template + "</option>");
			}
			out.append("</select>");

			out.append("</div>");
			out.append("<div class='form-group'  style='margin-right: 50px'>");
			out.append("<button type='submit' class='btn-u btn-u-default'>Add Slide</button>");
			out.append("</div>");
			out.append("<div class='checkbox'  style='margin-right: 50px'>");
			out.append("<a onclick='openWin(\"/content/lesson/preview.jsp?ppt_id=" + ppt.getId() + "\")'  href='#' class='btn-u btn-u-default'>Mobile Preview</a>");
			out.append("</div>");
			out.append("<a onclick='openWin('/content/lesson/preview.jsp?ppt_id=" + ppt.getId() + "')'   href='#' class='btn-u btn-u-default'>Mobile Preview</a>");
			out.append("</form>");
			out.append("</div>");
			out.append("</div>");
			out.append("</div>");

			out.append("<div class=' col-md-12 '>");
			out.append("<div class='panel panel-sea margin-bottom-40'>");
			out.append("<div class='panel-heading'>");
			out.append("<h3 class='panel-title'><i class='fa fa-edit'></i> List of Slides</h3>");
			out.append("</div>");
			out.append("<table class='table table-striped'>");
			out.append("<thead>");
			out.append("<tr>");
			out.append("<th>#</th>");
			out.append("<th>Slide Title</th>");
			out.append("<th>Action</th>");
			out.append("</tr>");
			out.append("</thead>");
			out.append("<tbody>");
			DBUtils db = new DBUtils();
			ArrayList<ArrayList<String>> data = db.getSlides(ppt.getId());
			for (int i = 0; i < data.size(); i++) {
				out.append("<tr>");
				out.append("<td>" + data.get(i).get(0) + "</td>");
				out.append("<td>" + data.get(i).get(1) + "</td>");
				out.append("<td>");
				out.append("<a class='btn btn-success btn-xs' href='/content/fill_tempate.jsp?ppt_id=" + ppt.getId() + "&slide_id=" + data.get(i).get(0) + "&slide_type=" + 
				data.get(i).get(2) + "'>" + "<i class='fa fa-check'></i>Edit</a>");
				
				out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger btn-xs' "
						+ "href='/content/delete_slide?ppt_id=" + ppt.getId() + "&slide_id=" + data.get(i).get(0) + "'>" + "<i class='fa fa-remove'></i>Delete</a>");
						
				
				out.append("</td>");
				out.append("</tr>");
			}

			out.append("</tbody>");
			out.append("</table>");
			out.append("</div>");
			out.append("</div>");
		} else if (lesson.getGame() != null) {

		} else if (lesson.getAssessment() != null) {

		}

		return out;
	}

	public StringBuffer getEditProfileEdit(CMSSlide slide, Presentaion ppt) {

		// Add image library
		ImageDAO dao = new ImageDAO();
		//ArrayList<Image> images = (ArrayList<Image>) dao.findByProperty("sessionid", ppt.getLesson().getCmsession().getId());
		ArrayList<Image> images = (ArrayList<Image>) dao.findByProperty("sessionid", ppt.getLesson().getCmsession().getId());

		StringBuffer out = new StringBuffer();
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		VelocityContext context = new VelocityContext();
		context.put("slide", slide);
		context.put("images", images);
		context.put("list_types", CMSRegistry.listTypes);
		Template t = ve.getTemplate(slide.getTemplateName() + "_edit.vm");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		out.append(writer.toString());
		return out;

	}

	public CMSSlide convertSlide(Slide slide) {
		CMSSlide cMSlide = new CMSSlide();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CMSSlide.class);
			InputStream in = IOUtils.toInputStream(slide.getSlideText(), "UTF-8");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			cMSlide = (CMSSlide) jaxbUnmarshaller.unmarshal(in);
			cMSlide.setTeacherNotes(slide.getTeacherNotes());
			cMSlide.setStudentNotes(slide.getStudentNotes());
			System.out.println(slide);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cMSlide;

	}

}
