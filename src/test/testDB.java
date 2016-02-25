package test;

import com.istarindia.apps.cmsutils.CMSFolder;
import com.istarindia.apps.dao.Folder;
import com.istarindia.apps.services.CMSUtils;
import com.istarindia.apps.services.FolderService;
import com.istarindia.apps.services.LessonService;

public class testDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(CMSFolder.init());
		StringBuffer sb = new StringBuffer();
		sb = CMSUtils.getAllFolders();
		System.out.println("folders>>>"+sb.toString());
		FolderService d = new FolderService(); 
		Folder root = d.getRootFolder();
		StringBuffer sb1 = new StringBuffer();
		System.out.println(d.getFolderRecursively(root,sb1));
		System.out.println(d.getSubFolders(root).size());
	}

}
