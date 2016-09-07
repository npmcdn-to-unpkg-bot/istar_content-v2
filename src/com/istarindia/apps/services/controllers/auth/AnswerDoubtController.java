package com.istarindia.apps.services.controllers.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.istarindia.apps.dao.Doubt;
import com.istarindia.apps.dao.DoubtDAO;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.notification.pojo.NotificationDoubt;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int doubt_id = Integer.parseInt(request.getParameter("doubt_id"));
		// doubt_id=3&answer=AAAAA#
		DoubtDAO dao1 = new DoubtDAO();
		Doubt d = dao1.findById(doubt_id);
		d.setAnswer(request.getParameter("answer"));
		IstarUser userAnsweredBy = ((IstarUser) request.getSession().getAttribute("user"));
		d.setAnsweredBy(userAnsweredBy.getId());
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

		// For sending notification to the students
		try {
					
			DatabaseReference ref = FirebaseDatabase.getInstance().getReference("doubt");
			Map<String, Object> hopperUpdates = new HashMap<String, Object>();
			HashMap<String, String> id = new HashMap<>();
			id.put(d.getStudentId() + "", "false");
			
			NotificationDoubt doubt = new NotificationDoubt(d.getAnswer(), d.getQuestion(), id,
					userAnsweredBy.getName());
			hopperUpdates.put("doubtlist", doubt);
			ref.push().setValue(hopperUpdates);

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// Failed to send the notification
			System.err.println("Failed to send the notification to student ---------> ");
			e.printStackTrace();
		}

		response.sendRedirect("/content/content_admin/doubt_list.jsp");
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
