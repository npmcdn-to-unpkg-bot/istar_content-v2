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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
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
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.Video;
import com.istarindia.apps.dao.VideoDAO;
import com.istarindia.apps.services.CMSUtils;
import com.istarindia.apps.services.FolderService;
import com.istarindia.apps.services.MediaService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class MediaUploadController
 */

public class MediaUploadController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
	public static File uploadFolder;
	public static File trashFolder;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MediaUploadController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init(ServletConfig config) {
    	String fileUploadPath =config.getInitParameter("upload_path");
    	uploadFolder = new File(fileUploadPath);
    	trashFolder = new File(fileUploadPath+"/trash");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printParams(request);
		
		System.out.println("I am here");		
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}
		
		ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
		
		String tags ="";

		int slideId = 0;
		int pptId = 0;
		int itemId=0;
		int cmsessionId = 0;
		String slideType = "";
		String folders[] = null;
		String mediaTitle = "";
		int userId = ((IstarUser)request.getSession().getAttribute("user")).getId();
		
		MediaService mediaService = new MediaService();
		
		try {
			List<FileItem> items = uploadHandler.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					File file = new File(uploadFolder, item.getName());
					item.write(file);
					
					if (item.getName().toString().endsWith(".mp4")) {
						Video video = mediaService.saveVideo(itemId, item.getName(), tags, cmsessionId, slideId, mediaTitle);
						
						// TODO: return status from saveImage to return the same in request
						request.setAttribute("msg", "Video with title " + video.getTitle() + " and " + video.getUrl() + " created. ");

						mediaService.saveMediaInFolders(video.getId(), MediaTypes.VIDEO, folders);
						mediaService.updateMediTask(video.getId(), MediaTypes.VIDEO, userId);

					} else {
						Image image = mediaService.saveImage(itemId, item.getName(), tags, cmsessionId, slideId, mediaTitle);
						
						//TODO: return status from saveImage to return the same in request
						request.setAttribute("msg", "Image with title " + image.getTitle() + " and " + image.getUrl() + " created. ");
						
						mediaService.saveMediaInFolders(image.getId(), MediaTypes.IMAGE, folders);
						mediaService.updateMediTask(image.getId(), MediaTypes.IMAGE, userId);
						 						
					}
					
				} else {
					if (item.getFieldName().equalsIgnoreCase("tags")) {
						tags = item.getString();
						
					} else if (item.getFieldName().equalsIgnoreCase("selected_items")) {
						folders = item.getString().split(",");
						
					} else if (item.getFieldName().equalsIgnoreCase("item_id")) {
						itemId = Integer.parseInt(item.getString());
						
					} else if (item.getFieldName().equalsIgnoreCase("session_id")) {
						cmsessionId = Integer.parseInt(item.getString());
					
					} else if (item.getFieldName().equalsIgnoreCase("slide_id")) {
						slideId = Integer.parseInt(item.getString());
						
					} else if (item.getFieldName().equalsIgnoreCase("ppt_id")) {
						pptId = Integer.parseInt(item.getString());
						
					} else if (item.getFieldName().equalsIgnoreCase("slide_type")) {
						slideType = item.getString();
						
 					} else if (item.getFieldName().equalsIgnoreCase("new_media_title")) {
						mediaTitle = item.getString();
						
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
		
		if (slideType != "" && pptId != 0 && slideId != 0) {
			request.setAttribute("message_success", "Media file has been uploaded successfully ");
			response.sendRedirect("fill_tempate.jsp?ppt_id="+pptId+"&slide_id="+slideId+"&slide_type="+slideType);
		} else if (slideType != "" && pptId != 0&& slideId == 0 ) {
			request.setAttribute("message_success", "Media file has been uploaded successfully ");
			response.sendRedirect("fill_tempate.jsp?ppt_id="+pptId+"&slide_type="+slideType);
		} else {
			request.setAttribute("message_success", "Media file has been uploaded successfully and sent for review!");
			request.getRequestDispatcher("/creative_creator/dashboard.jsp").forward(request, response);
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MediaService mediaService = new MediaService();
		
		if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
			File file = new File(uploadFolder, request.getParameter("getfile"));
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
			String url = request.getParameter("delfile").toString().replace("/video/", "/content/media_upload?getfile=");
			String media_type = new String();
			int media_id = 0;
			if(!(new ImageDAO()).findByUrl(url).isEmpty()) {
				media_type = "IMAGE" ;
				media_id = (new ImageDAO()).findByUrl(url).get(0).getId();
				mediaService.moveImageToTrash(media_id);
			
			} else if(!(new VideoDAO()).findByUrl(url).isEmpty()) {
				media_type = "VIDEO" ;
				media_id = (new VideoDAO()).findByUrl(url).get(0).getId();
				mediaService.moveVideoToTrash(media_id);
				
			} else {
				request.setAttribute("message_failure", "Problem deleting the file!");
				request.getRequestDispatcher("/delete_media.jsp").forward(request, response);
			}
			
			mediaService.UpdateMediaTaskDeleted(media_id, media_type);
			
			try{
	    		File sourceFile = new File(uploadFolder, url.split("getfile=")[1]);
		   		FileUtils.moveFileToDirectory(sourceFile, trashFolder, true);
		    }catch(Exception e){
		    	e.printStackTrace();
		    }

			request.setAttribute("message_success", "Media file has been deleted successfully!");
			request.getRequestDispatcher("/delete_media.jsp").forward(request, response);
			
		} else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
			File file = new File(uploadFolder, request.getParameter("getthumb"));
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
			} else if(getSuffix(file.getName()).equalsIgnoreCase("m4v")){
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
