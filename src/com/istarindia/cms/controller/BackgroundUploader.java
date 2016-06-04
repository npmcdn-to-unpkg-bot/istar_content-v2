package com.istarindia.cms.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.istarindia.apps.dao.Image;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class BackgroundUploader
 */
public class BackgroundUploader extends IStarBaseServelet {
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
    public BackgroundUploader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		printParams(request);

		if (!request.getParameterMap().containsKey("show"))  {

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
						System.out.println(file.getAbsolutePath());
						item.write(file);
					}
				}
				request.setAttribute("message_success", "Image uploaded successfully!");
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message_failure", "Something went wrong! Please try again");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message_failure", "Something went wrong! Please try again");
			}
		}

		request.setAttribute("path", fileUploadPath);
		request.getRequestDispatcher("/backgrounds.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
