package com.istarindia.notification.pojo;

import java.util.HashMap;

public class NotificationDoubt {

	private String answer;
	private String question;
	private HashMap<String,String> id;
	private String answeredBy;
	public NotificationDoubt() {
		super();
	}
	public NotificationDoubt(String answer, String question, HashMap<String, String> id, String answeredBy) {
		super();
		this.answer = answer;
		this.question = question;
		this.id = id;
		this.answeredBy = answeredBy;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public HashMap<String, String> getId() {
		return id;
	}
	public void setId(HashMap<String, String> id) {
		this.id = id;
	}
	public String getAnsweredBy() {
		return answeredBy;
	}
	public void setAnsweredBy(String answeredBy) {
		this.answeredBy = answeredBy;
	}
	
	
	
}
