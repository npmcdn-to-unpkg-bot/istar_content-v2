/**
 * 
 */
package com.istarindia.apps.services.controllers;

import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @vauthor Vaibhav
 *
 */
public class IStarBaseServelet extends HttpServlet{

	public void printParams(HttpServletRequest request) {
		for(Object key: request.getParameterMap().keySet()) {
			System.out.println("key-> "+ key.toString() + " : value ->"+ request.getParameter(key.toString()));
		}
	}
}
