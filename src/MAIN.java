import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

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
import com.istarindia.apps.services.task.EmailSendingUtility;

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
	private static String host;
	private static String port;
	private static String user1;
	private static String pass;
	public static void main(String[] args) throws IOException, AddressException, MessagingException {
		
		Properties properties = new Properties();
		String propertyFileName = "app.properties";
		InputStream inputStream = MAIN.class.getClassLoader().getResourceAsStream(propertyFileName);
		if (inputStream != null) {
			properties.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propertyFileName + "' not found in the classpath");
		}
		
		
		
		host = properties.getProperty("host");
		port = properties.getProperty("port");
		user1 = properties.getProperty("emailFrom");
		pass = properties.getProperty("emailFromPassword");
		
		EmailSendingUtility.sendEmail(host, port, user1, pass, "vaibhav@istarindia.com,vaibhav1@istarindia.com", "Kamini", "aaaaaaaaaaaaaaaaaaaa");


	}

}
