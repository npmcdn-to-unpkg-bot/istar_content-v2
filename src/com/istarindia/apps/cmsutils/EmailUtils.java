package com.istarindia.apps.cmsutils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;

public class EmailUtils {
	
	  public static void sendEmail(HttpServletRequest request, String emailTo,  String subject, String message) throws AddressException, MessagingException {

		  
	        String senderEmail = "vaibhav@istarindia.com";
	        String senderPassword = "U6Zt0CrZBDnKvnHTNl2EKA";
	        
	        // sets SMTP server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", "smtp.mandrillapp.com");
	        properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        
	        // creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(senderEmail, senderPassword);
	            }
	        };

	        //Create transport session
	        Session session = Session.getInstance(properties, auth);
	        InternetAddress[] iAdressArray = InternetAddress.parse(emailTo);
	 
	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(senderEmail));
	        msg.setRecipients(Message.RecipientType.TO,  iAdressArray);
	        msg.setSubject(subject);
	        msg.setSentDate(new Date());
	        msg.setText(message);
	 
	        // sends the e-mail
	        try {
	        	Transport.send(msg); 
	        } catch (Exception e) {
	        	e.printStackTrace();

				StringBuffer messageNew = new StringBuffer();
				messageNew.append("To: " + emailTo);
				messageNew.append("\nSubject -> " + subject);
				messageNew.append("\nMessage -> " + message);
				messageNew.append("\n\n\nException details: " );
				messageNew.append("\nURL: " + (String) request.getAttribute("javax.servlet.error.request_uri"));
				messageNew.append("\nServlet Name : " + (String) request.getAttribute("javax.servlet.error.servlet_name"));
				messageNew.append("\nException Type : " + ((Throwable) request.getAttribute("javax.servlet.error.exception")).getClass().getName());
				messageNew.append("\nException message: " + ((Throwable) request.getAttribute("javax.servlet.error.exception")).getMessage());
				messageNew.append("\n\n\nFull Stacktrace: " +  ExceptionUtils.getFullStackTrace(((Throwable) request.getAttribute("javax.servlet.error.exception"))));

	        	sendEmail(request, senderEmail , "Error sending Email! Find the details attached within" , messageNew.toString());
	        }
	        
	 
	    }

}
