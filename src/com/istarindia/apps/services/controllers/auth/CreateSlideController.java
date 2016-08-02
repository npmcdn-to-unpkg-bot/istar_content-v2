package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;

import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.ImageDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Presentaion;
import com.istarindia.apps.dao.PresentaionDAO;
import com.istarindia.apps.dao.Slide;
import com.istarindia.apps.dao.SlideDAO;
import com.istarindia.apps.dao.Task;
import com.istarindia.apps.dao.TaskDAO;
import com.istarindia.apps.dao.Video;
import com.istarindia.apps.dao.VideoDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.apps.services.task.CreateLessonTaskManager;
import com.istarindia.cms.lessons.CMSImage;
import com.istarindia.cms.lessons.CMSList;
import com.istarindia.cms.lessons.CMSTextItem;
import com.istarindia.cms.lessons.CMSVideo;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		printParams(request);
		int slide_id = 0;
		boolean is_numbered ;		
		String template = request.getParameter("template");
		SlideService service = new SlideService();
		Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
		switch (template) {
		case "ONLY_TITLE_PARAGRAPH_IMAGE":

			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id. 

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				String title = request.getParameter("title");

				slide_id = service.addTextParagraphSlideToLesson(request.getParameter("paragraph"), image,
						request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, title,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("image_bg"));
			} else {
				ImageDAO dao = new ImageDAO();
				String title = request.getParameter("title");
				int image_id = 0; // Dummy image id. 

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				service.addTextParagraphSlideToLessonUpdate(request.getParameter("paragraph"), image,
						request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt, title,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("slide_id"), request.getParameter("image_bg"));
			}

			break;


		case "ONLY_PARAGRAPH_IMAGE":

			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id. 

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());

				slide_id = service.addImageParagraphSlideToLesson(request.getParameter("paragraph"), image,
						request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("image_bg"));
			} else {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id.

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				service.addImageParagraphSlideToLessonUpdate(request.getParameter("paragraph"), image,
						request.getParameter("teacher_notes"), request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("slide_id"), request.getParameter("image_bg"));
			}

			break;

		case "ONLY_TITLE":

			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				slide_id = service.addTextSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("image_bg"));
			} else {
				service.addTextSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("slide_id"), request.getParameter("image_bg"));
			}

			break;
		case "ONLY_TITLE_PARAGRAPH":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				slide_id = service.addTextAndParaGraphSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("image_bg"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"), "ONLY_TITLE_PARAGRAPH");

			} else {
				service.addTextAndParaGraphSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("image_bg"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"),
						request.getParameter("slide_id"), "ONLY_TITLE_PARAGRAPH");

			}

			break;
			
			
		case "ONLY_TITLE_PARAGRAPH_cells_fragemented":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				slide_id = service.addTextAndParaGraphSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("image_bg"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"), "ONLY_TITLE_PARAGRAPH_cells_fragemented");

			} else {
				service.addTextAndParaGraphSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("image_bg"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"),
						request.getParameter("slide_id"), "ONLY_TITLE_PARAGRAPH_cells_fragemented");

			}

			break;


		case "ONLY_TITLE_TABLE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				slide_id = service.addTextAndParaGraphSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("image_bg"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"), "ONLY_TITLE_TABLE");

			} else {
				service.addTextAndParaGraphSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("image_bg"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"),
						request.getParameter("slide_id"), "ONLY_TITLE_TABLE");

			}

			break;
			
		case "ONLY_TITLE_LIST_NUMBERED":
			is_numbered = true;
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				slide_id = service.addTextListSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("image_bg"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, is_numbered);
			} else {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				service.addTextListSlideToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("image_bg"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"), is_numbered);
			}

			break;

		case "ONLY_LIST":
			is_numbered = false;
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				slide_id = service.addListSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("image_bg"),
						request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, is_numbered);

			} else {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				service.addListSlideToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("image_bg"),
						request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"), is_numbered);
			}

			break;

		case "ONLY_LIST_NUMBERED":
			is_numbered = true;
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				slide_id = service.addListSlideToLesson(request.getParameter("teacher_notes"), request.getParameter("image_bg"),
						request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, is_numbered);

			} else {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				service.addListSlideToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("image_bg"),
						request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"), is_numbered);
			}

			break;
		case "ONLY_TITLE_ASSESSMENT_2COLUMNS":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewList(request);
				list.setList_type("SIMPLE_LIST");
				slide_id = service.addListAssessment2ColumnSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list);

			} else {
				CMSList list = getNewList(request);
				list.setList_type("SIMPLE_LIST");
				service.addListAssessment2ColumnSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"));
			}

			break;
			

		case "ONLY_TITLE_ASSESSMENT_5COLUMNS":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewList(request);
				list.setList_type("SIMPLE_LIST");
				slide_id = service.addListAssessment5ColumnSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list);

			} else {
				CMSList list = getNewList(request);
				list.setList_type("SIMPLE_LIST");
				service.addListAssessment5ColumnSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"));
			}

			break;
		  case "ONLY_2TITLE_TABLE":
              if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
                      CMSList list = getNewList(request);
                      list.setList_type(request.getParameter("list_type"));
                      slide_id = service.add2TextTableSlideToLesson(request.getParameter("teacher_notes"),
                                      request.getParameter("student_notes"), ppt, request.getParameter("title"),request.getParameter("title2"),
                                      request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
                                      request.getParameter("backgroundTransition"), list);

              } else {
                      CMSList list = getNewList(request);
                      list.setList_type(request.getParameter("list_type"));
                      service.add2TextTableSlideToLessonUpdate(request.getParameter("teacher_notes"),
                                      request.getParameter("student_notes"), ppt, request.getParameter("title"),request.getParameter("title2"),
                                      request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
                                      request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"));
              }

              break;
		case "ONLY_2BOX":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("SIMPLE_LIST"));
				slide_id = service.add2BoxSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list,request.getParameter("image_bg"));

			} else {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("SIMPLE_LIST"));
				service.add2BoxSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"),request.getParameter("image_bg"));
			}

			break;

		case "ONLY_TITLE_IMAGE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id. To be added soon

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				slide_id = service.addTextImageSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), image,request.getParameter("image_bg"));
			} else {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id. To be added soon

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				service.addTextImageSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), image, request.getParameter("slide_id"),request.getParameter("image_bg"));

			}
			break;


		case "ONLY_2TITLE_IMAGE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id. To be added soon

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				slide_id = service.add2TextImageSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), image,request.getParameter("title"),request.getParameter("title2"));
			} else {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id. To be added soon

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				service.add2TextImageSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), image, request.getParameter("slide_id"), request.getParameter("title"),request.getParameter("title2"));

			}
			break;

		case "ONLY_TITLE_VIDEO":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				VideoDAO dao = new VideoDAO();
				int video_id = 0; // Dummy video id. To be added soon

				if (request.getParameterMap().containsKey("video_url")) {
					video_id = Integer.parseInt(request.getParameter("video_url"));
				}

				Video video = dao.findById(video_id);
				CMSVideo cmsVideo = new CMSVideo();
				cmsVideo.setUrl(video.getUrl());
				cmsVideo.setTitle(video.getTitle());
				cmsVideo.setDescription(video.getDescription());
				slide_id = service.addTextVideoSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), cmsVideo);
			} else {
				VideoDAO dao = new VideoDAO();
				int video_id = 0; // Dummy video id. To be added soon

				if (request.getParameterMap().containsKey("video_url")) {
					video_id = Integer.parseInt(request.getParameter("video_url"));
				}

				Video video = dao.findById(video_id);
				CMSVideo cmsVideo = new CMSVideo();
				cmsVideo.setUrl(video.getUrl());
				cmsVideo.setTitle(video.getTitle());
				cmsVideo.setDescription(video.getDescription());
				service.addTextVideoSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), cmsVideo, request.getParameter("slide_id"));

			}
			break;

		case "ONLY_VIDEO":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				VideoDAO dao = new VideoDAO();
				int video_id = 0; // Dummy video id. To be added soon

				if (request.getParameterMap().containsKey("video_url")) {
					video_id = Integer.parseInt(request.getParameter("video_url"));
				}

				Video video = dao.findById(video_id);
				CMSVideo cmsVideo = new CMSVideo();
				cmsVideo.setUrl(video.getUrl());
				cmsVideo.setTitle(video.getTitle());
				cmsVideo.setDescription(video.getDescription());
				slide_id = service.addVideoSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), cmsVideo);
			} else {
				VideoDAO dao = new VideoDAO();
				int video_id = 0; // Dummy video id. To be added soon

				if (request.getParameterMap().containsKey("video_url")) {
					video_id = Integer.parseInt(request.getParameter("video_url"));
				}

				Video video = dao.findById(video_id);
				CMSVideo cmsVideo = new CMSVideo();
				cmsVideo.setUrl(video.getUrl());
				cmsVideo.setTitle(video.getTitle());
				cmsVideo.setDescription(video.getDescription());
				service.addVideoSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt,
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), cmsVideo, request.getParameter("slide_id"));

			}
			break;
		case "ONLY_TITLE_TREE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewTree(request);
				slide_id = service.addTextTreeSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("image_bg"));

			} else {
				CMSList list = getNewTree(request);
				service.addTextTreeSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"), request.getParameter("image_bg"));
			}

			break;
		case "ONLY_2TITLE_2TABLE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewTree(request);
				slide_id = service.add2Text2TableSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list);

			} else {
				CMSList list = getNewTree(request);
				service.add2Text2TableSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"));
			}

			break;
		case "ONLY_2TITLE_5TABLE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewTree(request);
				slide_id = service.add2Text5TableSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list);

			} else {
				CMSList list = getNewTree(request);
				service.add2Text5TableSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"));
			}

			break;
		case "ONLY_2TITLE_TREE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewTree(request);
				slide_id = service.add2TextTreeSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("image_bg"),
						request.getParameter("backgroundTransition"), list);

			} else {
				CMSList list = getNewTree(request);
				service.add2TextTreeSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"), request.getParameter("image_bg"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id"));
			}

			break;
		case "ONLY_PARAGRAPH":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
								slide_id = service.addParaGraphSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("slideTransition"),

						request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"),
						request.getParameter("paragraph"), request.getParameter("image_bg"));

			} else {
				 service.addParaGraphSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("slideTransition"),
						request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"),
						request.getParameter("paragraph"), request.getParameter("slide_id"),
						request.getParameter("image_bg"));

			}

			break;
		case "ONLY_IMAGE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id. To be added soon

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				slide_id = service.addImageSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, "NO_TITLE", request.getParameter("slideTransition"),
						request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), image,
						request.getParameter("image_bg"));
			} else {
				ImageDAO dao = new ImageDAO();
				int image_id = 0; // Dummy image id. To be added soon

				if (request.getParameterMap().containsKey("image_url")) {
					image_id = Integer.parseInt(request.getParameter("image_url"));
				}

				Image img = dao.findById(image_id);
				CMSImage image = new CMSImage();
				image.setUrl(img.getUrl());
				image.setTitle(img.getTitle());
				image.setDescription(img.getDescription());
				service.addImageSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, "NO_TITLE", request.getParameter("slideTransition"),
						request.getParameter("backgroundColor"), request.getParameter("backgroundTransition"), image,
						request.getParameter("slide_id"), request.getParameter("image_bg"));

			}
			break;

		case "NO_CONTENT":
			//image_bg=%2Fcontent%2Fbackgrounds%2F6.png&slideTransition=Zoom&background
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				slide_id = service.addNOTextSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("image_bg"));
			} else {
				service.addNOTextSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("slide_id"), request.getParameter("image_bg"));
			}
			break;
			
		case "ONLY_2TITLE":
			//image_bg=%2Fcontent%2Fbackgrounds%2F6.png&slideTransition=Zoom&background
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				slide_id = service.add2TitleSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("image_bg"),  request.getParameter("title2"));
			} else {
				service.add2TitleSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("slide_id"), request.getParameter("image_bg"),  request.getParameter("title2"));
			}
			break;

		
		case "ONLY_PARAGRAPH_TITLE":
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				slide_id = service.addTextAndParaGraphSlideToLesson(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("image_bg"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"), "ONLY_PARAGRAPH_TITLE");

			} else {
					service.addTextAndParaGraphSlideToLessonUpdate(request.getParameter("teacher_notes"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("image_bg"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), request.getParameter("paragraph"),
						request.getParameter("slide_id"), "ONLY_PARAGRAPH_TITLE");

			}

			break;
			
		case "ONLY_TITLE_LIST":
			is_numbered = false;
			if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				slide_id = service.addListToLesson(request.getParameter("teacher_notes"), request.getParameter("image_bg"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list);

			} else {
				CMSList list = getNewList(request);
				list.setList_type(request.getParameter("list_type"));
				service.addListToLessonUpdate(request.getParameter("teacher_notes"), request.getParameter("image_bg"),
						request.getParameter("student_notes"), ppt, request.getParameter("title"), request.getParameter("title2"),
						request.getParameter("slideTransition"), request.getParameter("backgroundColor"),
						request.getParameter("backgroundTransition"), list, request.getParameter("slide_id") );
			}

			break;

		default:
			break;
		}

		SlideDAO dao = new SlideDAO();
		CMSRegistry cmsRegistry = new CMSRegistry();
		String comments = new String() ;
		Slide slide = new Slide();

		if (request.getParameter("is_edit").equalsIgnoreCase("false")) {
			slide = dao.findById(slide_id);
			
			if (request.getParameter("order_id").equalsIgnoreCase("0")) {
				slide.setOrder_id(slide_id);
			} else {
				slide.setOrder_id(Integer.parseInt(request.getParameter("order_id")));
			}
			
			Session session = dao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				dao.attachDirty(slide);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}

		} else {
			slide_id = Integer.parseInt(request.getParameter("slide_id").toString());
			slide = dao.findById(slide_id);
		}

		if(request.getParameter("is_edit").equalsIgnoreCase("false")) {
			comments = "A new Slide is added with the template => " + template + " created in the presentation wih ID ->" + ppt.getId() + ". New text -> "+ Jsoup.parse(slide.getSlideText());
			CMSRegistry.addTaskLogEntry(request, "DRAFT", comments, ppt.getLesson().getTaskID(), "LESSON", ppt.getLesson().getId(), "New slide is created");
		} else {
			comments = "Slide with ID ->" + slide_id + " has been updated. New text -> "  + Jsoup.parse(slide.getSlideText());
			CMSRegistry.addTaskLogEntry(request, "DRAFT", comments, ppt.getLesson().getTaskID(), "LESSON", ppt.getLesson().getId(), "Slide is edited");
		}
		
		Task t = new Task();
		t.setItemId(ppt.getLesson().getId());
		t.setItemType("LESSON");
		t = new TaskDAO().findByExample(t).get(0);
		response.sendRedirect("/content/edit_lesson?task_id=" + t.getId());

	}

	private CMSList getNewTree(HttpServletRequest request) {
		CMSList list = new CMSList();
		/*
		 * ArrayList<CMSTextItem> items =new ArrayList<CMSTextItem>();
		 * items.add(new CMSTextItem("")); items.add(new
		 * CMSTextItem(""));items.add(new CMSTextItem("")); items.add(new
		 * CMSTextItem(""));items.add(new CMSTextItem(""));
		 */
		list.setItems(new ArrayList<CMSTextItem>());
		for (Object key : request.getParameterMap().keySet()) {
			if (key.toString().startsWith("parent_")) {
				if (!request.getParameter(key.toString()).equalsIgnoreCase("")) {
					//System.out.println("key>>>" + key.toString());
					CMSTextItem item = new CMSTextItem(request.getParameter(key.toString()),request.getParameter("desc_"+key.toString()));
					item.setList(getList(request, key));
					list.getItems().add(item);

					//System.out.println("---->" + request.getParameter(key.toString()));
				}
			
			}
			
		}

		return list;
	}

	private CMSList getList(HttpServletRequest request, Object key) {
		CMSList list = new CMSList();
		/*
		 * ArrayList<CMSTextItem> items =new ArrayList<CMSTextItem>();
		 * items.add(new CMSTextItem("")); items.add(new
		 * CMSTextItem(""));items.add(new CMSTextItem(""));
		 */
		list.setItems(new ArrayList<CMSTextItem>());
		for (Object iterable_element : request.getParameterMap().keySet()) {
			if (iterable_element.toString().endsWith("_" + key.toString())&&(!(iterable_element.toString().startsWith("desc")))) {
				CMSTextItem item = new CMSTextItem(request.getParameter(iterable_element.toString()));
				list.getItems().add(item);
				//System.out.println("element here is -----" + iterable_element);
			}
		}
		// TODO Auto-generated method stub
		return list;
	}

	private CMSList getNewList(HttpServletRequest request) {
		CMSList list = new CMSList();
		ArrayList<CMSTextItem> items = new ArrayList<CMSTextItem>();
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		items.add(new CMSTextItem(""));
		list.setItems(items);
		for (Object key : request.getParameterMap().keySet()) {
			if (key.toString().startsWith("list_item")) {
				if (!request.getParameter(key.toString()).equalsIgnoreCase("") || !request.getParameter("desc_"+key.toString()).equalsIgnoreCase("")) {
					CMSTextItem item = new CMSTextItem(request.getParameter(key.toString()),request.getParameter("desc_"+key.toString()));
					//System.err.println(request.getParameter(key.toString())+","+request.getParameter("desc_"+key.toString()));
					list.getItems().add(item);
				}
			}
		}

		return list;
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
