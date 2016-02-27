/**
 * 
 */
package com.istarindia.cms.lessons;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author vaibhaverma
 *
 */
@XmlRootElement(name="li")
public class CMSTextItem {
	
	
	public CMSTextItem() {
		super();
	}

	public CMSTextItem(String text) {
		super();
		this.text = text;
	}

	
	
	public String getText() {
		return text;
	}

	@XmlElement(name="p")
	public void setText(String text) {
		this.text = text;
	}



	public CMSList getList() {
		return list;
	}

	@XmlElement(name="ul")
	public void setList(CMSList list) {
		this.list = list;
	}



	String text;
	CMSList list;
}