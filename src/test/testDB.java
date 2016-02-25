package test;

import com.istarindia.apps.cmsutils.CMSFolder;
import com.istarindia.apps.services.CMSUtils;

public class testDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(CMSFolder.init());
		StringBuffer sb = new StringBuffer();
		sb = CMSUtils.getAllFolders();
		System.out.println("folders>>>"+sb.toString());
	}

}
