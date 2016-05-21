package com.istarindia.apps.services.controllers.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.Doubt;
import com.istarindia.apps.dao.DoubtDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.StudentNoteDAO;
import com.istarindia.apps.services.controllers.IStarBaseServelet;

/**
 * Servlet implementation class AnswerDoubtController
 */
@WebServlet("/answer_doubt")
public class AnswerDoubtController extends IStarBaseServelet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerDoubtController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int doubt_id = Integer.parseInt(request.getParameter("doubt_id"));
		//doubt_id=3&answer=AAAAA#
		DoubtDAO dao1 = new DoubtDAO();
		Doubt d = dao1.findById(doubt_id);
		d.setAnswer(request.getParameter("answer"));
		d.setAnsweredBy(((IstarUser)request.getSession().getAttribute("user")).getId());
		Session session = dao1.getSession();
		Transaction tx = null;
		
		
		try {
			tx = session.beginTransaction();
			dao1.attachDirty(d);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		response.sendRedirect("/content/content_admin/doubt_list.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
