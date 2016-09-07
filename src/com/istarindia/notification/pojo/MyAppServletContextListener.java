package com.istarindia.notification.pojo;

import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class MyAppServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}

	// Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		InputStream targetStream = getClass().getClassLoader()
				.getResourceAsStream("istarNotification-a99cf1d1dd05.json");
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setDatabaseUrl("https://istarnotification.firebaseio.com/").setServiceAccount(targetStream).build();
		FirebaseApp.initializeApp(options);
		System.out.println("ServletContextListener started");
	}

}