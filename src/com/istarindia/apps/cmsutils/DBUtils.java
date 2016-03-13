/**
 * 
 */
package com.istarindia.apps.cmsutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.QuestionDAO;

/**
 * @author Vaibhav
 *
 */
public class DBUtils {
	public ArrayList<ArrayList<String>> getSlides(Integer integer) {
		//select * from slide where presentation_id=1
		ArrayList<ArrayList<String>> table = new ArrayList<>();
		IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		String sql1 = "select * from slide where presentation_id="+integer+" order by id";
		SQLQuery query = session.createSQLQuery(sql1);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String, Object>> results = query.list();
		for (HashMap<String, Object> object : results) {
			try {
				ArrayList<String> row = new ArrayList<>();
				row.add(object.get("id").toString());
				row.add(object.get("title").toString());
				row.add(object.get("template").toString());
				table.add(row);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return table;
	}

	public ArrayList<ArrayList<String>> getQuestions(Integer id) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> table = new ArrayList<>();
		IstarUserDAO dao = new IstarUserDAO();
		Session session = dao.getSession();
		String sql1 = "select * from assessment_question where assessmentid="+id+" order by id";
		SQLQuery query = session.createSQLQuery(sql1);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String, Object>> results = query.list();
		QuestionDAO questionDao = new QuestionDAO();
		Question question = new Question();
		for (HashMap<String, Object> object : results) {
			try {
				//question = questionDao.findById(object.get("questionid"));
				
				ArrayList<String> row = new ArrayList<>();
				row.add(object.get("id").toString());
				row.add(object.get("title").toString());
				row.add(object.get("template").toString());
				table.add(row);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return table;
	}
}
