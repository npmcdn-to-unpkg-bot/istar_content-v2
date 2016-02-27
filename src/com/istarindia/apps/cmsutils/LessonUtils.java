/**
 * 
 */
package com.istarindia.apps.cmsutils;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.Presentaion;
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
			out.append("<input name='ppt_id' value='"+ppt.getId()+"' type='hidden'/>");
			
			out.append("<div class='form-group'>");
			out.append("<select class='form-control' name='slide_type' style='margin-right: 50px'>");
			
			for(String template :  CMSRegistry.slideTemplates) {
				out.append("<option value='"+template+"'>"+template+"</option>");
			}
			//out.append("<% for(String templateName:  CMSRegistry.slideTemplates) { %>");
			//out.append("<option value='<%=templateName %>'><%=templateName %></option>");
			//out.append("<% } %>");
			
			out.append("</select>");
			
			
			
			out.append("</div>");
			out.append("<div class='form-group'  style='margin-right: 50px'>");
			out.append("<button type='submit' class='btn-u btn-u-default'>Add Slide</button>");
			out.append("</div>");
			out.append("<div class='checkbox'  style='margin-right: 50px'>");
			out.append("<button type='submit' class='btn-u btn-u-default'>Mobile Preview</button>");
			out.append("</div>");
			out.append("<button type='submit' class='btn-u btn-u-default'  style='margin-right: 50px'>Desktop Preview	</button>");
			out.append("</form>");
			out.append("</div>");
			out.append("</div>");
			out.append("</div>");
		} else if (lesson.getGame() != null) {

		} else if (lesson.getAssessment() != null) {

		}

		return out;
	}
	
	
	public StringBuffer getEditProfileEdit(CMSSlide slide) {
		StringBuffer out = new StringBuffer();
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		VelocityContext context = new VelocityContext();
		context.put("slide", slide);
		Template t = ve.getTemplate(slide.getTemplateName()+"_edit.vm");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		out.append(writer.toString());
		return out;
		
	}
}