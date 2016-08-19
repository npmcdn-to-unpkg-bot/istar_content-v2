package com.istarindia.apps.games.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.Game;
import com.istarindia.apps.dao.GameDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.services.task.CreateLessonTaskManager;

/**
 * Servlet implementation class CreateGameController
 */
@WebServlet("/create_game")
public class CreateGameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateGameController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("game_id"))
		{
			GameDAO dao= new GameDAO();
			Game ppt = dao.findById(Integer.parseInt(request.getParameter("game_id")));
			ppt.setGameObject(request.getParameter("game_xml"));
			ppt.setDtype("GAME");
			Session session = dao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				dao.attachDirty(ppt);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			Lesson lesson = (new LessonDAO()).findById(Integer.parseInt(request.getParameter("lesson_id")));
			CreateLessonTaskManager.pushTaskNotification(lesson, (IstarUser) request.getSession().getAttribute("user"), "A Game for the lesson was created.");
			
			request.setAttribute("lesson", lesson);
			request.setAttribute("task_id", Integer.parseInt(request.getParameter("task_id")));

			request.getRequestDispatcher("/lesson/edit_lesson.jsp").forward(request, response);;
		}
		else
		{
			GameDAO dao= new GameDAO();
			Game ppt = new Game();
			ppt.setLesson((new LessonDAO()).findById(Integer.parseInt(request.getParameter("lesson_id"))));
			ppt.setDtype("GAME");
			Session session = dao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				dao.save(ppt);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			Lesson lesson = (new LessonDAO()).findById(Integer.parseInt(request.getParameter("lesson_id")));
			CreateLessonTaskManager.pushTaskNotification(lesson, (IstarUser) request.getSession().getAttribute("user"), "A Game for the lesson was created.");
			
			request.setAttribute("lesson", lesson);
			request.setAttribute("task_id", Integer.parseInt(request.getParameter("task_id")));

			request.getRequestDispatcher("/lesson/edit_lesson.jsp").forward(request, response);;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
