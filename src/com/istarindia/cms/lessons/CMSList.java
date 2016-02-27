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
	String type;
	ArrayList<CMSTextItem> items;
	String list_type;/*
						 * This is where we can use types of list either as a
						 * css class or as ... shapeType not sure need to ask
						 * Sir about it
						 */

	public ArrayList<CMSTextItem> getItems() {
		return items;
	}

	@XmlElement(name = "li")
	public void setItems(ArrayList<CMSTextItem> items) {
		this.items = items;
	}

	public String getType() {
		return type;
	}

	@XmlAttribute(name = "type")
	public void setType(String type) {
		this.type = type;
	}

	public String getList_type() {
		return list_type;
	}

	@XmlAttribute(name = "list_type")
	public void setList_type(String list_type) {
		this.list_type = list_type;
	}

}