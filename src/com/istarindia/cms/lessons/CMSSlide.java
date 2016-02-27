/**
 * 
 */
package com.istarindia.cms.lessons;

import java.beans.Transient;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * @author vaibhaverma
 *
 */
@XmlRootElement(name = "slide")

public class CMSSlide {
	String transition="zoom";
	String title;
	String paragraph;
	List<CMSHTMLTable> tables = new ArrayList<CMSHTMLTable>();
	CMSList list;
	CMSImage image;
	CMSVideo video;
	String background="rgba(255, 255, 255, .4)";
	String backgroundTransition="zoom";
	String position;

	String templateName;

	String teacherNotes;
	String studentNotes;

	@XmlElement(name = "student_notes")
	public String getStudentNotes() {
		return studentNotes;
	}

	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}

	@XmlElement(name = "teacher_notes")
	public String getTeacherNotes() {
		return teacherNotes;
	}

	public void setTeacherNotes(String teacherNotes) {
		this.teacherNotes = teacherNotes;
	}

	public String getTemplateName() {
		return templateName;
	}

	@XmlAttribute(name = "template")
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	

	public String getTitle() {
		return (title);
	}

	@XmlElement(name = "h1")
	public void setTitle(String title) {
		this.title = title;
	}

	public String getParagraph() {
		return paragraph;
	}

	@XmlElement(name = "p")
	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	public List<CMSHTMLTable> getTables() {
		return tables;
	}

	@XmlElement(name = "tables")
	public void setTables(List<CMSHTMLTable> tables) {
		this.tables = tables;
	}

	public CMSList getList() {
		return list;
	}

	@XmlElement(name = "ul")
	public void setList(CMSList list) {
		this.list = list;
	}

	public CMSImage getImage() {
		return image;
	}

	@XmlElement(name = "img")
	public void setImage(CMSImage image) {
		this.image = image;
	}

	public CMSVideo getVideo() {
		return video;
	}

	@XmlElement(name = "video")
	public void setVideo(CMSVideo video) {
		this.video = video;
	}

	@XmlAttribute(name = "position")
	public String getPosition() {
		return position;
	}

	
	public void setPosition(String position) {
		this.position = position;
	}

	@XmlAttribute(name = "transition")
	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

	@XmlAttribute(name = "background")
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	@XmlAttribute(name = "background_transition")
	public String getBackgroundTransition() {
		return backgroundTransition;
	}

	public void setBackgroundTransition(String backgroundTransition) {
		this.backgroundTransition = backgroundTransition;
	}

	public StringWriter getText() throws JAXBException {
	       StringWriter out = new StringWriter();

		JAXBContext jaxbContext = JAXBContext.newInstance(CMSSlide.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(this, new StreamResult(out));
		return out;
	}
	
	
	

}