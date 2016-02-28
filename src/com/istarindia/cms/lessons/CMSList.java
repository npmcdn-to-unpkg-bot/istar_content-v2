/**
 * 
 */
package com.istarindia.cms.lessons;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author vaibhaverma
 *
 */
public class CMSList {
	ArrayList<CMSTextItem> items;
	String list_type;
	
	public ArrayList<CMSTextItem> getItems() {
		return items;
	}

	@XmlElement(name = "li")
	public void setItems(ArrayList<CMSTextItem> items) {
		this.items = items;
	}

	public String getList_type() {
		return list_type;
	}

	@XmlAttribute(name = "list_type")
	public void setList_type(String list_type) {
		this.list_type = list_type;
	}

}