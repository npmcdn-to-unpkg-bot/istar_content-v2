/**
 * 
 */
package com.istarindia.apps.services;

import java.util.ArrayList;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.istarindia.apps.cmsutils.CMSFolder;

/**
 * @author Vaibhav
 *
 */
public class CMSRegistry {

	public static CMSFolder root = CMSFolder.init();
	
	public static void writeAuditLog(String string, Integer id) {
		// TODO Auto-generated method stub
		
	}

	public static ArrayList<ArrayList<String>> getSimulatedList(int count) {
		ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
		ArrayList<String> item = new ArrayList<>();
		for(int i=0; i< 1000; i++) {
			item = new ArrayList<String>();
			for (int j = 0; j < count; j++) {
				item.add(RandomStringUtils.randomAlphabetic(7));
			}
			items.add(item);
		}
		return items;
	}

}
