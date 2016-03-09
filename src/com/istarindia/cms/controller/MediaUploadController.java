package com.istarindia.cms.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
import org.imgscalr.Scalr;

import com.istarindia.apps.MediaTypes;
import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.Folder;
import com.istarindia.apps.dao.FolderItems;
import com.istarindia.apps.dao.FolderItemsDAO;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.Video;
import com.istarindia.apps.dao.VideoDAO;
import com.istarindia.apps.services.CMSUtils;
import com.istarindia.apps.services.FolderService;
import com.istarindia.apps.services.TaskService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class MediaUploadController
 */
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

    @Override
    public void init(ServletConfig config) {
    	//C:\Users\Vaibhav\Pictures
    	String folder =config.getInitParameter("upload_path");
    	
    	fileUploadPath = new File(folder);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printParams(request);
		
		System.out.println("I am here");		
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}
		
		ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
		
		String tags ="";
		
		int item_id=0;
		String folders[] = null;
		
		
		
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
						System.out.println("item id="+item_id);
						Video transientInstance = dao.findById(item_id);
						try {
							tx = session.beginTransaction();
							
						
							transientInstance.setUrl("/content/media_upload?getfile=" + item.getName());
							transientInstance.setTags(tags);
							
							
						
							dao.attachDirty(transientInstance);
							tx.commit();
						} catch (HibernateException e) {
							e.printStackTrace();
							if (tx != null)
								tx.rollback();
							e.printStackTrace();
						} finally {
							session.close();
						}
						
						for(String folder_id : folders)
						{
							System.out.println("----------------->" + folder_id);
							
							FolderItemsDAO itemDAO = new FolderItemsDAO();
							FolderItems item2 = new FolderItems();
							
							item2.setFolderId(Integer.parseInt(folder_id));
							item2.setItemType(MediaTypes.VIDEO);
							item2.setItemId(transientInstance.getId());
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
						
						System.err.println();
						TaskDAO d = new TaskDAO();
						Task t = null;
						for(Task tt :d.findByItemId(item_id))
						{
							if(tt.getItemType().equalsIgnoreCase(MediaTypes.VIDEO))
							{
								t= tt;
								break;
							}	
						}
						System.err.println("task here is "+t.getId());
						t.setStatus(StatusTypes.COMPLETED);
						Session session111 = d.getSession();
						Transaction tx111 = null;
						try {
							tx111 = session111.beginTransaction();
							d.attachDirty(t);
							tx111.commit();
						} catch (HibernateException e) {
							if (tx111 != null)
								tx111.rollback();
							e.printStackTrace();
						} finally {
							session111.close();
						}
						//CMSRegistry.writeAuditLog("Video with title " + transientInstance.getTitle() + " and " + transientInstance.getUrl() + " created. ", (Users) request.getSession().getAttribute("user"));
					} 
					else 
					{
						ImageDAO dao = new ImageDAO();
						Session session = dao.getSession();
						Transaction tx = null;
						Image transientInstance2 = dao.findById(item_id);
						
						
						try {
							tx = session.beginTransaction();
							
							
							transientInstance2.setUrl("/content/media_upload?getfile=" + item.getName());
							transientInstance2.setDeleteUrl("/content/media_upload?delfile=" + item.getName());
							transientInstance2.setThumbnailUrl("/content/media_upload?getthumb=" + item.getName());
							transientInstance2.setTags(tags);
							
							request.setAttribute("msg", "Image with title " + transientInstance2.getTitle() + " and " + transientInstance2.getUrl() + " created. ");
							dao.attachDirty(transientInstance2);
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
						
						TaskDAO d = new TaskDAO();
						Task t = null;
						for(Task tt :d.findByItemId(item_id))
						{
							if(tt.getItemType().equalsIgnoreCase(MediaTypes.IMAGE))
							{
								t= tt;
								break;
							}	
						}
						System.out.println("task here is "+t.getId());
						t.setStatus(StatusTypes.COMPLETED);
						Session session11 = d.getSession();
						Transaction tx11 = null;
						try {
							tx11 = session11.beginTransaction();
							d.attachDirty(t);
							tx11.commit();
						} catch (HibernateException e) {
							if (tx11 != null)
								tx11.rollback();
							e.printStackTrace();
						} finally {
							session11.close();
						}
						
						
					
					}
					
				} else {
					if (item.getFieldName().equalsIgnoreCase("tags")) {
						tags = item.getString();
					}  else if (item.getFieldName().equalsIgnoreCase("selected_items")) {
						folders = item.getString().split(",");
						
					}
					else if (item.getFieldName().equalsIgnoreCase("item_id")) {
						item_id = Integer.parseInt(item.getString());
					}
					
				}
				

			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

		}
		
		
		
		request.getRequestDispatcher("/creative_creator/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
			File file = new File(fileUploadPath, request.getParameter("getfile"));
			if (file.exists()) {
				int bytes = 0;
				ServletOutputStream op = response.getOutputStream();

				response.setContentType(getMimeType(file));
				response.setContentLength((int) file.length());
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

				byte[] bbuf = new byte[1024];
				DataInputStream in = new DataInputStream(new FileInputStream(file));

				while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
					op.write(bbuf, 0, bytes);
				}

				in.close();
				op.flush();
				op.close();
			}
		} else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
			File file = new File(fileUploadPath, request.getParameter("delfile"));
			if (file.exists()) {
				file.delete(); // TODO:check and report success
			}
		} else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
			File file = new File(fileUploadPath, request.getParameter("getthumb"));
			if (file.exists()) {
				String mimetype = getMimeType(file);
				if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
					BufferedImage im = ImageIO.read(file);
					if (im != null) {
						BufferedImage thumb = Scalr.resize(im, 165);
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						if (mimetype.endsWith("png")) {
							ImageIO.write(thumb, "PNG", os);
							response.setContentType("image/png");
						} else if (mimetype.endsWith("jpeg")) {
							ImageIO.write(thumb, "jpg", os);
							response.setContentType("image/jpeg");
						} else {
							ImageIO.write(thumb, "GIF", os);
							response.setContentType("image/gif");
						}
						ServletOutputStream srvos = response.getOutputStream();
						response.setContentLength(os.size());
						response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
						os.writeTo(srvos);
						srvos.flush();
						srvos.close();
					}
				}
			} 
		} else {
			PrintWriter writer = response.getWriter();
			writer.write("call POST with multipart form data");
		}
	}
	
	private String getMimeType(File file) {
		String mimetype = "";
		if (file.exists()) {
			if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
				mimetype = "image/png";
			} else if(getSuffix(file.getName()).equalsIgnoreCase("mp4")){
				mimetype = "application/octet-stream";
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
