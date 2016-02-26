package com.istarindia.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.MediaTypes;
import com.istarindia.apps.dao.Folder;
import com.istarindia.apps.dao.FolderItems;
import com.istarindia.apps.dao.FolderItemsDAO;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.Video;
import com.istarindia.apps.dao.VideoDAO;
import com.istarindia.apps.services.CMSUtils;
import com.istarindia.apps.services.FolderService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class MediaUploadController
 */
@WebServlet("/media_upload")
public class MediaUploadController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
	public static File fileUploadPath;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MediaUploadController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printParams(request);
		
		System.out.println("I am here");		
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}
		Image transientInstance2 = new Image();
		ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
		
		String tags ="";
		String description ="";
		String title ="";
		
		String folders[] = null;
		int cmsession_id=0;
		
		
		try {
			List<FileItem> items = uploadHandler.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					File file = new File(fileUploadPath, item.getName());
					System.out.println(file.getAbsolutePath());
					item.write(file);
					System.out.println("item name "+item.getName());
					System.out.println("item size "+item.getSize());
					System.out.println("url "+"upload?getfile=" + item.getName());
					System.out.println("thumb url "+"upload?getthumb=" + item.getName());
					System.out.println("delt url "+"upload?delfile=" + item.getName());
					System.out.println("del type "+ "GET");
					System.out.println("location "+"upload?getfile=" + item.getName());

					

					if (item.getName().toString().endsWith(".mp4")) {
						VideoDAO dao = new VideoDAO();
						Session session = dao.getSession();
						Transaction tx = null;
						Video transientInstance = new Video();
						try {
							tx = session.beginTransaction();
							transientInstance.setTitle(item.getName());
							transientInstance.setDescription("This is a sample descirption.");
							transientInstance.setUrl("upload?getfile=" + item.getName());
							transientInstance.setTags(tags);
							transientInstance.setDescription(description);
							
							dao.save(transientInstance);
							tx.commit();
						} catch (HibernateException e) {
							e.printStackTrace();
							if (tx != null)
								tx.rollback();
							e.printStackTrace();
						} finally {
							session.close();
						}
						//CMSRegistry.writeAuditLog("Video with title " + transientInstance.getTitle() + " and " + transientInstance.getUrl() + " created. ", (Users) request.getSession().getAttribute("user"));
					} 
					else 
					{
						ImageDAO dao = new ImageDAO();
						Session session = dao.getSession();
						Transaction tx = null;
						try {
							tx = session.beginTransaction();
							transientInstance2.setTitle(title);
							transientInstance2.setDescription(description);
							transientInstance2.setUrl("upload?getfile=" + item.getName());
							transientInstance2.setDeleteUrl("upload?delfile=" + item.getName());
							transientInstance2.setThumbnailUrl("upload?getthumb=" + item.getName());
							transientInstance2.setTags(tags);
							transientInstance2.setSessionid(cmsession_id);
							
							dao.save(transientInstance2);
							tx.commit();
						} catch (HibernateException e) {
							if (tx != null)
								tx.rollback();
							e.printStackTrace();
						} finally {
							session.close();
						}
						
						System.out.println("-------tags---------->" + tags);
						
						
						
						for(String folder_id : folders)
						{
							System.out.println("----------------->" + folder_id);
							
							FolderItemsDAO itemDAO = new FolderItemsDAO();
							FolderItems item2 = new FolderItems();
							
							item2.setFolderId(Integer.parseInt(folder_id));
							item2.setItemType(MediaTypes.IMAGE);
							item2.setItemId(transientInstance2.getId());
							Session session1 = itemDAO.getSession();
							Transaction tx1 = null;
							try {
								tx1 = session1.beginTransaction();
								itemDAO.save(item2);
								tx1.commit();
							} catch (HibernateException e) {
								if (tx1 != null)
									tx1.rollback();
								e.printStackTrace();
							} finally {
								session1.close();
							}
						}
					
					}
					
				} else {
					if (item.getFieldName().equalsIgnoreCase("tags")) {
						tags = item.getString();
					} else if (item.getFieldName().equalsIgnoreCase("description")) {
						description = item.getString();
					} else if (item.getFieldName().equalsIgnoreCase("selected_items2")) {
						folders = item.getString().split(",");
						
					}else if (item.getFieldName().equalsIgnoreCase("title")) {
						title = item.getString();
					}else if (item.getFieldName().equalsIgnoreCase("selected_items")) {

						for (String cmsession : item.getString().split(",")) {
							if(cmsession.startsWith("session_")) {
								cmsession_id = Integer.parseInt(cmsession.replace("session_", ""));
							}
							System.out.println("session is "+cmsession);
						}
					}
					
				}
				request.setAttribute("msg", "Image with title " + transientInstance2.getTitle() + " and " + transientInstance2.getUrl() + " created. ");

			}
		} catch (FileUploadException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

		}
		request.getRequestDispatcher("/creative_creator/upload_media.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private String getMimeType(File file) {
		String mimetype = "";
		if (file.exists()) {
			if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
				mimetype = "image/png";
			} else {
				javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
				mimetype = mtMap.getContentType(file);
			}
		}
		return mimetype;
	}
	private String getSuffix(String filename) {
		String suffix = "";
		int pos = filename.lastIndexOf('.');
		if (pos > 0 && pos < filename.length() - 1) {
			suffix = filename.substring(pos + 1);
		}
		return suffix;
	}

	public String getFolderTree() {
		// TODO Auto-generated method stub
		//System.out.println(CMSFolder.init());
		StringBuffer sb = new StringBuffer();
		sb = CMSUtils.getAllFolders();
		
		FolderService d = new FolderService(); 
		Folder root = d.getRootFolder();
		StringBuffer sb1 = new StringBuffer();
		System.out.println(d.getFolderRecursively(root,sb1));
		return sb1.toString();
		
	}

}
