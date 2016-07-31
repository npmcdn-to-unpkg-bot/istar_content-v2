package com.istarindia.cms.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import com.istarindia.apps.StatusTypes;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.services.MediaService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class BackgroundUploader
 */

public class BackgroundMediaController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
	public static File fileUploadPath;

	
	@Override
    public void init(ServletConfig config) {
    	String folder =config.getInitParameter("bg_upload_path");
    	fileUploadPath = new File(folder);
    }
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BackgroundMediaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MediaService mediaService = new MediaService();
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException(
					"Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}

		ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());

		List<FileItem> items;
		try {
			items = uploadHandler.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					File file = new File(fileUploadPath, item.getName());
					if(item.getName().matches("^[a-zA-Z0-9\\_\\.]*$") && item.getName().endsWith(".png")){  
						String comment = "";
						if(file.exists()) {
							comment = item.getName() + " is replaced with new image" ;
							request.setAttribute("message_success", item.getName() + " has been successfully replaced!");
						} else {
							comment = "New background image " + item.getName() + " is uploaded";
							request.setAttribute("message_success", "New background image has been uploaded successfully!");
						}
						
						item.write(file);
						mediaService.saveTaskLog((IstarUser)request.getSession().getAttribute("user"), 0, 0, "BACKGROUND_IMAGE",  "NONE", comment);
					}
					else {
						request.setAttribute("message_failure", "Please note the NOTE and try again!");
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message_failure", "Something went wrong! Please try again");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message_failure", "Something went wrong! Please try again");
		}

		request.setAttribute("list", getBgImageList());
		request.getRequestDispatcher("/backgrounds.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameterMap().containsKey("getfile")){	//Display the requested image
			
			File file = new File(fileUploadPath, request.getParameter("getfile"));
			if (file.exists()) {
				int bytes = 0;
				ServletOutputStream op = response.getOutputStream();
				response.setContentType("image/png");
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
			} else {
				//TODO: Return a default error image for user to re-upload with appropriate name
			}
			
		} else { //Show the list of images
			request.setAttribute("list", getBgImageList());
			request.getRequestDispatcher("/backgrounds.jsp").forward(request, response);
		}
	}
	
	protected ArrayList<Image> getBgImageList() throws IOException {
		
		ArrayList<Image> list = new ArrayList<>();
		Files.walk(Paths.get(fileUploadPath.getAbsolutePath())).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				Image img = new Image();
				img.setUrl("/video/backgrounds/" + filePath.toFile().getName());
				img.setTitle(filePath.toFile().getName());
				img.setId((new Random().nextInt(100000)));
				list.add(img);
			}
		});
		
		return list;
		
	}
 
}
