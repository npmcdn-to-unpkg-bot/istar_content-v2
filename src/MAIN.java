import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.SlideDAO;
import com.istarindia.apps.services.OptionService;
import com.istarindia.apps.services.QuestionService;

/**
 * 
 */

/**
 * @author vaibhav
 *
 */
public class MAIN {

	/**
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		LessonDAO dao = new LessonDAO();
		List<Lesson> items = dao.findAll();
		
		for (Lesson lesson : items) {
			String theme = lesson.getLesson_theme();
			lesson.setLesson_theme_desktop(theme);
			
			Session session = dao.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				dao.attachDirty(lesson);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			
		}

	}

}
