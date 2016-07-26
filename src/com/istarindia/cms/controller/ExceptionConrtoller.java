package com.istarindia.cms.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.services.task.EmailSendingUtility;

/**
 * Servlet implementation class ExceptionHandleConrtoller
 */
@WebServlet(urlPatterns = { "/exception_handler" })
public class ExceptionConrtoller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String deployment_type;
	private String host;
	private String port;
	private String user1;
	private String pass;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExceptionConrtoller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
			Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
			String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
			
			if (servletName == null) {
				servletName = "Unknown";
			}
			String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
			if (requestUri == null) {
				requestUri = "Unknown";
			}
			
			Properties properties = new Properties();
			String propertyFileName = "app.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertyFileName + "' not found in the classpath");
			}
			
			
			deployment_type = properties.getProperty("deployment_type");
			host = properties.getProperty("host");
			port = properties.getProperty("port");
			user1 = properties.getProperty("emailFrom");
			pass = properties.getProperty("emailFromPassword");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");
			Date timeOfIncident = new Date();
			
			String subject = "CMS issue at " + dateFormat.format(timeOfIncident) ; 
			
			StringBuffer message = new StringBuffer();
			message.append("URL: " + requestUri);
			message.append("\n  Servlet Name : " + servletName);
			message.append("\n  Exception Type : " +  throwable.getClass( ).getName( ));
			message.append("\n  The exception message: " + throwable.getMessage( ));
			
			HashMap<String, String> recipient= new HashMap<>();
			recipient.put("surya@istarindia.com", "surya@istarindia.com");
			
			new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						if (deployment_type.trim().equalsIgnoreCase("prod")) {
							EmailSendingUtility.sendEmail(host, port, user1, pass, recipient, subject, message.toString()); 
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			};
		} catch (Exception e) { System.err.println("efcewv");
		}
		
		request.getRequestDispatcher("/500.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
