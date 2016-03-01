/**
 * 
 */
package com.istarindia.apps.cmsutils.reports;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Vaibhav
 *
 */
public class IStarColumn {
	Boolean isVisible;
	String columnHandler;
	String displayName;
	String name;
	
	
	
	
	public IStarColumn(Boolean isVisible, String columnHandler, String displayName, String name) {
		super();
		this.isVisible = isVisible;
		this.columnHandler = columnHandler;
		this.displayName = displayName;
		this.name = name;
	}
	public IStarColumn() {
		super();
	}
	@XmlAttribute (name="isVisible")
	public Boolean getIsVisible() {
		return isVisible;
	}
	
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	@XmlAttribute (name="columnHandler")
	public String getColumnHandler() {
		return columnHandler;
	}
	public void setColumnHandler(String columnHandler) {
		this.columnHandler = columnHandler;
	}
	
	@XmlAttribute (name="display_name")
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	@XmlAttribute (name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
