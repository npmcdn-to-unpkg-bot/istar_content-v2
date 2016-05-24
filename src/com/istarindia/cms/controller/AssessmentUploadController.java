package com.istarindia.cms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.UserTypes;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.services.OptionService;
import com.istarindia.apps.services.QuestionService;

/**
 * Servlet implementation class AssessmentUploadController
 */
@WebServlet("/assessment_upload")
public class AssessmentUploadController extends HttpServlet {

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
	public AssessmentUploadController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		IstarUser user = (IstarUser) request.getSession().getAttribute("user");
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
						System.err.println(fileName);
					}

				}
			}
			saveAssessment(storeFile);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveAssessment(File storeFile) throws IOException {

		FileInputStream file = new FileInputStream(storeFile);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			Row row = sheet.getRow(i);
			if (!row.getCell(1).toString().trim().equalsIgnoreCase("")) {
				String questionText = row.getCell(0).getStringCellValue();
				String[] options = new String[5];
				options[0] = row.getCell(1).toString();
				options[1] = row.getCell(2).toString();
				options[2] = row.getCell(3).toString();
				options[3] = row.getCell(4).toString();
				options[4] = row.getCell(5).toString();

				String answers = row.getCell(6).toString();

				String los = row.getCell(7).toString();

				Double timeLimit = Double.parseDouble(row.getCell(8).toString());

				String subject = row.getCell(9).getStringCellValue();
				System.err.println(questionText);
				Double assesmsn = Double.parseDouble(row.getCell(10).toString());

				// Learning Objectve(seaparated by ;; lower case ) Time Limit
				// (in seconds) 0 denotes no limit subject

				System.err.println(subject);
				Set<LearningObjective> loSet = createorUpdateLearningObjectives(los, subject);

				QuestionService questionService = new QuestionService();
				Integer ii = timeLimit.intValue();
				Integer assessment_id = assesmsn.intValue();
				Question question = questionService.createNewQuestionForAssessment(questionText, "1", 1, assessment_id,
						loSet, 1, ii);

				// Assign marking scheme for each option:
				String[] correct_answers = answers.split(";;");
				int markingScheme[] = new int[5];

				for (int z = 0; z < 5; z++) {

					for (int j = 0; j < correct_answers.length; j++) {

						if (options[z].toLowerCase().trim().equalsIgnoreCase(correct_answers[j])) {
							markingScheme[z] = 1;

						} else {
							markingScheme[z] = 0;
						}
					}
				}

				try {
					OptionService optionService = new OptionService();
					optionService.createNewOption(options[0], question, markingScheme[0]);
					optionService.createNewOption(options[1], question, markingScheme[1]);
					optionService.createNewOption(options[2], question, markingScheme[2]);
					optionService.createNewOption(options[3], question, markingScheme[3]);
					optionService.createNewOption(options[4], question, markingScheme[4]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println(questionText);
					System.err.println(question.getId());
					// e.printStackTrace();
				}

			}
		}
		file.close();
	}
	
	public static void updateLearningObj(File storeFile) throws IOException {
		FileInputStream file = new FileInputStream(storeFile);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			Row row = sheet.getRow(i);

			if (!row.getCell(1).getStringCellValue().trim().equalsIgnoreCase("")) {
				String question = row.getCell(0).getStringCellValue();

				String option1 = row.getCell(1).getStringCellValue();
				String option2 = row.getCell(2).getStringCellValue();
				String option3 = row.getCell(3).getStringCellValue();
				String option4 = row.getCell(4).getStringCellValue();
				String option5 = row.getCell(5).getStringCellValue();

				String answers = row.getCell(6).getStringCellValue();

				String los = row.getCell(7).getStringCellValue();

				int timeLimit = (int) row.getCell(8).getNumericCellValue();

				String subject = row.getCell(9).getStringCellValue();
				System.err.println(question);
				System.err.println(option1);
				// Learning Objectve(seaparated by ;; lower case ) Time Limit
				// (in seconds) 0 denotes no limit subject
				System.err.println(subject);
				Set<LearningObjective> loSet = createorUpdateLearningObjectives(los, subject);

			}
		}
		file.close();

	}

	private static Set<LearningObjective> createorUpdateLearningObjectives(String los, String subject) {

		HashSet<LearningObjective> sets = new HashSet<>();

		for (String singleLOItem : los.split(";;")) {
			if (!singleLOItem.trim().equalsIgnoreCase("")) {
				LearningObjectiveDAO dao = new LearningObjectiveDAO();
				List<LearningObjective> lll = dao.findByTitle(singleLOItem.trim().toLowerCase());
				if (lll.isEmpty()) {
					// craete new Lo

					System.err.println("Creating a new Leanring objectve");

					LearningObjective lo = new LearningObjective();
					lo.setTitle(singleLOItem.trim().toLowerCase());
					lo.setSubject(subject);
					Session session = dao.getSession();
					Transaction tx = null;
					lo.setObjType("OTHERS");
					try {
						tx = session.beginTransaction();
						dao.save(lo);
						tx.commit();
					} catch (HibernateException e) {
						if (tx != null) {
							tx.rollback();
						}
						e.printStackTrace();
					} finally {
						session.close();
					}

				} else {
					System.err.println("Update  a new Leanring objectve");

					LearningObjective lo = lll.get(0);
					lo.setSubject(subject);
					lo.setObjType("OTHERS");
					Session session = dao.getSession();
					Transaction tx = null;
					try {
						tx = session.beginTransaction();
						dao.attachDirty(lo);
						tx.commit();
					} catch (HibernateException e) {
						if (tx != null) {
							tx.rollback();
						}
						e.printStackTrace();
					} finally {
						session.close();
					}

				}
			}
		}

		return sets;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
