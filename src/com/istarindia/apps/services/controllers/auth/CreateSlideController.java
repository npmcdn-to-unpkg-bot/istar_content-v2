package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.cms.lessons.CMSHTMLTableRow;
import com.istarindia.cms.lessons.CMSImage;
import com.istarindia.cms.lessons.CMSList;
import com.istarindia.cms.lessons.CMSTextItem;
import com.istarindia.cms.lessons.SlideService;

/**
 * Servlet implementation class CreateSlideController
 */
@WebServlet("/create_slide")
public class CreateSlideController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateSlideController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printParams(request);
		String template = request.getParameter("template");
		SlideService service = new SlideService();
		Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
		switch (template) {
		case "ONLY_TITLE":

			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				service.addTextSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"));
			} else {
				service.addTextSlideToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"),
						request.getParameter("slide_id"));
			}

			break;
		case "ONLY_TITLE_PARAGRAPH":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				service.addTextAndParaGraphSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"),
						request.getParameter("paragraph"));

			} else {
				service.addTextAndParaGraphSlideToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"), request.getParameter("slide_id"));

			}

			break;

		case "ONLY_TITLE_LIST":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				service.addTextListSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), list);

			} else {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				service.addTextListSlideToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"),
						list, request.getParameter("slide_id"));
			}

			break;
			
		case "ONLY_TITLE_IMAGE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				ImageDAO dao = new ImageDAO();

				Image img = dao.findById(Integer.parseInt(request.getParameter("image_url")));
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				service.addTextImageSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), image);
			} else {
				ImageDAO dao = new ImageDAO();

				Image img = dao.findById(Integer.parseInt(request.getParameter("image_url")));
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				service.addTextImageSlideToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"),
						image, request.getParameter("slide_id"));

			}
			break;
		case "ONLY_TITLE_TREE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewTree(request);
				service.addTextTreeSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), list);

			} else {
				CMSList list = getNewTree(request);
				service.addTextTreeSlideToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"),
						list, request.getParameter("slide_id"));
			}

			break;	
		default:
			break;
		}

		response.sendRedirect("/content/edit_lesson?lesson_id=" + ppt.getLesson().getId());

	}

	private CMSList getNewTree(HttpServletRequest request) {
		CMSList list = new CMSList();
		/*ArrayList<CMSTextItem>	items =new ArrayList<CMSTextItem>();
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));items.add(new CMSTextItem(""));*/
		list.setItems(new ArrayList<CMSTextItem>());
		for (Object key : request.getParameterMap().keySet()) {
			if (key.toString().startsWith("parent_")) {
				if (!request.getParameter(key.toString()).equalsIgnoreCase("")) {
					System.out.println("key>>>"+key.toString());
					CMSTextItem item = new CMSTextItem(request.getParameter(key.toString()));
					item.setList(getList(request,key ));
					list.getItems().add(item);
					
					System.out.println("---->" + request.getParameter(key.toString()));
				}
			}
		}

		return list;
	}

	private CMSList getList(HttpServletRequest request, Object key) {
		CMSList list = new CMSList();
		/*ArrayList<CMSTextItem>	items =new ArrayList<CMSTextItem>();
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));items.add(new CMSTextItem(""));*/
		list.setItems(new ArrayList<CMSTextItem>());
		for (Object iterable_element : request.getParameterMap().keySet()) {
			if(iterable_element.toString().endsWith("_"+key.toString())) {
				CMSTextItem item = new CMSTextItem(request.getParameter(iterable_element.toString()));
				list.getItems().add(item);
				System.out.println("element here is -----"+iterable_element);
			}
		}
		// TODO Auto-generated method stub
		return list;
	}

	private CMSList getNewList(HttpServletRequest request) {
		CMSList list = new CMSList();
		ArrayList<CMSTextItem>	items =new ArrayList<CMSTextItem>();
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));items.add(new CMSTextItem(""));
		list.setItems(items);
		for (Object key : request.getParameterMap().keySet()) {
			if (key.toString().startsWith("list_item")) {
				if (!request.getParameter(key.toString()).equalsIgnoreCase("")) {
					System.out.println(key.toString());
					CMSTextItem item = new CMSTextItem(request.getParameter(key.toString()));
					list.getItems().add(item);
					System.out.println("---->" + request.getParameter(key.toString()));
				}
			}
		}

		return list;
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
