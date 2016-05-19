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
	Boolean is_updatetable;
	String table_column;
	
	
	
	
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
	
	
	
	
	@XmlAttribute (name="is_updatetable")
	public Boolean getIs_updatetable() {
		return is_updatetable;
	}
	public void setIs_updatetable(Boolean is_updatetable) {
		this.is_updatetable = is_updatetable;
	}
	
	@XmlAttribute (name="table_column")
	public String getTable_column() {
		return table_column;
	}
	public void setTable_column(String table_column) {
		this.table_column = table_column;
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
