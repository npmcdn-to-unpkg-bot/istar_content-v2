/**
 * 
 */
package com.istarindia.apps.role;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Vaibhav
 *
 */
public class ParentLink {
	String displayName;
	
	ArrayList<ChildLink> children = new ArrayList<>();

	@XmlAttribute(name="display_name")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@XmlElement(name="child_links")
	public ArrayList<ChildLink> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ChildLink> children) {
		this.children = children;
	}

	
	
	
	
}
