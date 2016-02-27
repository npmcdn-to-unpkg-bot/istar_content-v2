/**
 * 
 */
package com.istarindia.cms.lessons;

import javax.xml.bind.JAXBException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.SlideDAO;

/**
 * @author Vaibhav
x *
 */
public class SlideService {
	public CMSLesson addTextSlideToLesson(CMSLesson lesson, String title, String slideTransition, String backgroundColor, String backgroundTransition) {
		CMSSlide slide = new CMSSlide();
		slide.setBackground(backgroundColor);
		slide.setBackgroundTransition(backgroundTransition);
		slide.setTransition(slideTransition);
		slide.setTemplateName("ONLY_TITLE");
		slide.setTitle(title);
		lesson.getSlides().add(slide);
		return lesson;
	}
	
	public CMSLesson addTextAndParaGraphSlideToLesson(CMSLesson lesson, String title, String slideTransition, String backgroundColor, String backgroundTransition,  String paragraph) {
		CMSSlide slide = new CMSSlide();
		slide.setBackground(backgroundColor);
		slide.setBackgroundTransition(backgroundTransition);
		slide.setTransition(slideTransition);
		slide.setTemplateName("ONLY_TITLE_PARAGRAPH");
		slide.setTitle(title);
		slide.setParagraph(paragraph);
		lesson.getSlides().add(slide);
		return lesson;
	}
	
	public CMSLesson addTextListSlideToLesson(CMSLesson lesson, String title, String slideTransition, String backgroundColor, String backgroundTransition, CMSList list) {
		CMSSlide slide = new CMSSlide();
		slide.setBackground(backgroundColor);
		slide.setBackgroundTransition(backgroundTransition);
		slide.setTransition(slideTransition);
		slide.setTemplateName("ONLY_TITLE_LIST");
		slide.setTitle(title);
		slide.setList(list);
		lesson.getSlides().add(slide);
		return lesson;
	}

	public CMSLesson addTextTreeSlideToLesson(CMSLesson lesson, String title, String slideTransition, String backgroundColor,
			String backgroundTransition, CMSList treeList) {
		CMSSlide slide = new CMSSlide();
		slide.setBackground(backgroundColor);
		slide.setBackgroundTransition(backgroundTransition);
		slide.setTransition(slideTransition);
		slide.setTemplateName("ONLY_TITLE_TREE");
		slide.setTitle(title);
		slide.setList(treeList);
		lesson.getSlides().add(slide);
		return lesson;
	}

	public CMSLesson addTextTableSlideToLesson(CMSLesson lesson, String title, String slideTransition, String backgroundColor,
			String backgroundTransition, CMSHTMLTable table) {
		CMSSlide slide = new CMSSlide();
		slide.setBackground(backgroundColor);
		slide.setBackgroundTransition(backgroundTransition);
		slide.setTransition(slideTransition);
		slide.setTemplateName("ONLY_TITLE_TABLE");
		slide.setTitle(title);
		table.setTitle(title);
		slide.getTables().add(table);
		lesson.getSlides().add(slide);
		return lesson;
	}
	
	public CMSLesson add2Text2TableSlideToLesson(CMSLesson lesson, String title, String slideTransition, String backgroundColor,
			String backgroundTransition, CMSHTMLTable table, String title2, CMSHTMLTable table2) {
		CMSSlide slide = new CMSSlide();
		slide.setBackground(backgroundColor);
		slide.setBackgroundTransition(backgroundTransition);
		slide.setTransition(slideTransition);
		slide.setTemplateName("ONLY_TITLE_TABLE_TITLE_TABLE");
		slide.setTitle(title);
		
		table.setTitle(title);
		slide.getTables().add(table);
		table2.setTitle(title2);
		slide.getTables().add(table2);
		
		lesson.getSlides().add(slide);
		return lesson;
	}

	public CMSLesson addTextTableParagraphSlideToLesson(CMSLesson lesson, String title, String slideTransition, String backgroundColor,
			String backgroundTransition, CMSHTMLTable table, String paragraph) {
		CMSSlide slide = new CMSSlide();
		slide.setBackground(backgroundColor);
		slide.setBackgroundTransition(backgroundTransition);
		slide.setTransition(slideTransition);
		slide.setTemplateName("ONLY_TITLE_TABLE_PARAGRAPH");
		slide.setTitle(title);
		table.setTitle(title);
		slide.getTables().add(table);
		slide.setParagraph(paragraph);lesson.getSlides().add(slide);
		return lesson;
	}

	public void addTextSlideToLesson(Presentaion ppt, String title, String slideTransition, String backgroundColor, String backgroundTransition) {
		CMSSlide cMSlide = new CMSSlide();
		cMSlide.setBackground(backgroundColor);
		cMSlide.setBackgroundTransition(backgroundTransition);
		cMSlide.setTransition(slideTransition);
		cMSlide.setTemplateName("ONLY_TITLE");
		cMSlide.setTitle(title);
		
		Slide slide = new Slide();
		slide.setTitle(title);
		try {
			slide.setSlideText(cMSlide.getText().toString());
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		ppt.getSlides().add(slide);
		slide.setPresentaion(ppt);
		
		SlideDAO dao= new SlideDAO();
		Session session = dao.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.save(slide);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}

	public void addTextAndParaGraphSlideToLesson(Presentaion ppt, String title, String slideTransition, String backgroundColor, String backgroundTransition, String paragraph) {
		CMSSlide cMSlide = new CMSSlide();
		cMSlide.setBackground(backgroundColor);
		cMSlide.setBackgroundTransition(backgroundTransition);
		cMSlide.setTransition(slideTransition);
		cMSlide.setTemplateName("ONLY_TITLE_PARAGRAPH");
		cMSlide.setTitle(title);
		cMSlide.setParagraph(paragraph);
		Slide slide = new Slide();
		slide.setTitle(title);
		
		try {
			slide.setSlideText(cMSlide.getText().toString());
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		ppt.getSlides().add(slide);
		slide.setPresentaion(ppt);
		
		SlideDAO dao= new SlideDAO();
		Session session = dao.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.save(slide);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}
}
