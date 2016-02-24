/**
 * 
 */
package com.istarindia.apps.role;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Vaibhav
 *
 */
public class ChildLink {
	String displayName;
	String url;
	String validRoles;
	
	
	@XmlAttribute(name="display_name")
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@XmlAttribute(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@XmlAttribute(name="valid_roles")
	public String getValidRoles() {
		return validRoles;
	}
	public void setValidRoles(String validRoles) {
		this.validRoles = validRoles;
	}
	
	
}
