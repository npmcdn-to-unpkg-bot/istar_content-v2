/**
 * 
 */
package com.istarindia.apps.services.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @vauthor Vaibhav
 *
 */
public class IStarBaseServelet extends HttpServlet{

	public static void printParams(HttpServletRequest request) {
		for(Object key: request.getParameterMap().keySet()) {
			System.out.println("key-> "+ key.toString() + " : value ->"+ request.getParameter(key.toString()));
		}
		
	}
}
