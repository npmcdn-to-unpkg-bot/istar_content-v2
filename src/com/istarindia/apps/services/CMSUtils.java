package com.istarindia.apps.services;

import com.istarindia.apps.cmsutils.CMSFolder;

public class CMSUtils {
	
	
	
	public static StringBuffer getAllFolders() {
		StringBuffer out = new StringBuffer();

		try {
			for (CMSFolder string : CMSRegistry.root.getChildren()) {
				if (string.getChildren().size() != 0) {
					for (CMSFolder child : string.getChildren()) {
						out.append("<option value='" + child.getDbFolder().getId() + "'>" + "/ROOT/" + string.getDbFolder().getName() + "/" + child.getDbFolder().getName() + "</option>");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return out;
	}
}
