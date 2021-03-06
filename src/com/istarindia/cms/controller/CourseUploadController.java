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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.istarindia.apps.UserTypes;
import com.istarindia.apps.cmsutils.ErrorMessages;
import com.istarindia.apps.dao.ContentAdmin;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.services.BulkUploadService;

/**
 * Servlet implementation class CourseUploadController
 */
@WebServlet("/course_upload")
public class CourseUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/** The Constant MEMORY_THRESHOLD. */

	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB

	/** The Constant MAX_FILE_SIZE. */
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB

	/** The Constant MAX_REQUEST_SIZE. */
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	/** The Constant UPLOAD_DIRECTORY. */
	private static final String UPLOAD_DIRECTORY = "upload";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CourseUploadController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IstarUser user = (IstarUser) request.getSession().getAttribute("user");
		if (user.getUserType().equals(UserTypes.CONTENT_ADMIN)) {
			// configures upload settings
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// sets memory threshold - beyond which files are stored in disk
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			// sets temporary location to store files
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			ServletFileUpload upload = new ServletFileUpload(factory);

			// sets maximum size of upload file
			upload.setFileSizeMax(MAX_FILE_SIZE);

			// sets maximum size of request (include file + form data)
			upload.setSizeMax(MAX_REQUEST_SIZE);

			// constructs the directory path to store upload file
			// this path is relative to application's directory
			String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
			System.err.println(uploadPath); 
			// creates the directory if it does not exist
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			try {
				// parses the request's content to extract file data
				@SuppressWarnings("unchecked")
				List<FileItem> formItems = upload.parseRequest(request);
				// String assesment_id = "0";
				File storeFile = null;
				if (formItems != null && formItems.size() > 0) {
					// iterates over form's fields
					for (FileItem item : formItems) {
						// processes only fields that are not form fields
						if (!item.isFormField()) {
							String fileName = new File(item.getName()).getName();
							String filePath = uploadPath + File.separator + fileName;
							storeFile = new File(filePath);
							item.write(storeFile);
							
						}

					}
				}
				new BulkUploadService().readFile(storeFile, (ContentAdmin) user);
				//System.out.println(formItems.get(0).getName());
				request.setAttribute("message_success", "Upload has been done successfully!");
				request.getRequestDispatcher("/content_admin/course_structure.jsp").forward(request, response);
			}catch (org.apache.poi.poifs.filesystem.NotOLE2FileException e) {
				e.printStackTrace();
				System.out.println("java.io.FileNotFoundException");
				request.setAttribute("message_failure", "There was an error: " + e.getMessage());
				request.getRequestDispatcher("/content_admin/course_structure.jsp").forward(request, response);
			} catch (java.io.FileNotFoundException e) {
				System.out.println("java.io.FileNotFoundException");
				request.setAttribute("message_failure", ErrorMessages.UPLOAD_FILE_MISSING);
				request.getRequestDispatcher("/content_admin/course_structure.jsp").forward(request, response);
			} catch (Exception ex) {
				System.out.println("Exception");
				ex.printStackTrace();
				request.setAttribute("message_failure", "There was an error: " + ex.getMessage());
				request.getRequestDispatcher("/content_admin/course_structure.jsp").forward(request, response);

			}
		} else {
			response.sendRedirect(request.getContextPath() + "/content");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}