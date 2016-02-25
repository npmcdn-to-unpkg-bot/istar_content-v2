package com.istarindia.apps.cmsutils;

import java.util.ArrayList;
import java.util.List;

import com.istarindia.apps.dao.Folder;
import com.istarindia.apps.dao.FolderDAO;

public class CMSFolder {
	
	List<CMSFolder> children = new ArrayList<CMSFolder>();
	Folder dbFolder = new Folder();

	public CMSFolder() {
		super();
	}

	public CMSFolder(List<CMSFolder> children, Folder dbFolder) {
		super();
		this.children = children;
		this.dbFolder = dbFolder;
	}

	public List<CMSFolder> getChildren() {
		return children;
	}

	public void setChildren(List<CMSFolder> children) {
		this.children = children;
	}

	public Folder getDbFolder() {
		return dbFolder;
	}

	public void setDbFolder(Folder dbFolder) {
		this.dbFolder = dbFolder;
	}

	public static CMSFolder init() {

		try {
			CMSFolder rootFolder = new CMSFolder();
			FolderDAO dao = new FolderDAO();
			Folder root = dao.findById(1);

			List<Folder> children = dao.findByParentId(root.getId());
			rootFolder.setDbFolder(root);
			List<CMSFolder> children2 = new ArrayList<CMSFolder>();
			rootFolder.setChildren(children2);

			// System.out.println("Level 0 ->"+rootFolder.getDbFolder().getName());

			for (Folder folder : children) {
				CMSFolder child = new CMSFolder();
				child.setDbFolder(folder);
				List<CMSFolder> children3 = new ArrayList<CMSFolder>();

				FolderDAO dao1 = new FolderDAO();
				List<Folder> grandChildren = dao1.findByParentId(folder.getId());
				child.setChildren(children3);
				for (Folder folder2 : grandChildren) {
					CMSFolder grandChild = new CMSFolder();
					grandChild.setDbFolder(folder2);
					children3.add(grandChild);
					// System.out.println("Level 2
					// ->"+grandChild.getDbFolder().getName());
				}
				// System.out.println("Level 1 ->"+child.getDbFolder().getName());
				children2.add(child);

			}

			for (CMSFolder level1 : rootFolder.children) {
				for (CMSFolder level2 : level1.children) {
					System.out.println(level2.getDbFolder().getName());
				}
			}
			return rootFolder;
		} catch (Exception e) {
	
			return null;
		}

	}

}