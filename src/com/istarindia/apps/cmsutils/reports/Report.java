/**
 * 
 */
package com.istarindia.apps.cmsutils.reports;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Vaibhav
 *
 */
public class Report {
	String sql;
	ArrayList<IStarColumn> columns;
	String title;
	String type_of_report;
	int id;
	String updateSQL;
	String tableName;
	
	
	
	
	
	
	@XmlElement (name="table_name",  required=false)
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@XmlElement (name="update_sql",  required=false)
	public String getUpdateSQL() {
		return updateSQL;
	}
	public void setUpdateSQL(String updateSQL) {
		this.updateSQL = updateSQL;
	}
	public Report() {
		super();
	}
	public Report(String sql, ArrayList<IStarColumn> columns, String type_of_report, int id) {
		super();
		this.sql = sql;
		this.columns = columns;
		this.type_of_report = type_of_report;
		this.id = id;
	}
	
	@XmlElement (name="sql")
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	@XmlElement (name="column")
	public ArrayList<IStarColumn> getColumns() {
		return columns;
	}
	
	public void setColumns(ArrayList<IStarColumn> columns) {
		this.columns = columns;
	}
	
	@XmlAttribute (name="type_of_report")
	public String getType_of_report() {
		return type_of_report;
	}
	public void setType_of_report(String type_of_report) {
		this.type_of_report = type_of_report;
	}
	
	@XmlAttribute (name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlElement (name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
	
}
