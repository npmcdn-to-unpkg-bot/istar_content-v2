import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.UserTypes;
import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
import com.istarindia.apps.dao.LearningObjective;
import com.istarindia.apps.dao.LearningObjectiveDAO;
import com.istarindia.apps.dao.Lesson;
import com.istarindia.apps.dao.LessonDAO;
import com.istarindia.apps.dao.Question;
import com.istarindia.apps.dao.SlideDAO;
import com.istarindia.apps.services.OptionService;
import com.istarindia.apps.services.QuestionService;
import com.istarindia.apps.services.task.EmailSendingUtility;

import istar.TT;

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
		InputStream inputStream = TT.class.getClassLoader().getResourceAsStream(propertyFileName);
		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("property file '" + propertyFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		host = properties.getProperty("host");
		port = properties.getProperty("port");
		user1 = properties.getProperty("emailFrom");
		pass = properties.getProperty("emailFromPassword");
		
		
		
		
		List<IstarUser> users = new IstarUserDAO().findAll();
		System.out.println(users.size());
		for(IstarUser u : users)
		{
			if(!u.getUserType().equalsIgnoreCase(UserTypes.STUDENT) && !u.getUserType().equalsIgnoreCase(UserTypes.TRAINER) && !u.getUserType().equalsIgnoreCase(UserTypes.PRESENTOR))
			{
				HashMap<String, String> recipient= new HashMap<>();
				recipient.put(u.getEmail(), u.getEmail());
				
				String pwd = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
				String resultMessage = "Password has been changed to "+ pwd +" by admin\nPlease do not share the password.\n Your IP Address, MAC address are being recorded.\n Anyone using the duplicate ID, lessons will not be updated.";
				String subject = "Password Changed"; 
				try {
					EmailSendingUtility.sendEmail(host, port, user1, pass, recipient, subject, resultMessage);
					resultMessage = "The e-mail was sent successfully";
				} catch (Exception ex) {
					ex.printStackTrace();
					resultMessage = "There were an error: " + ex.getMessage();
				}
				
				System.out.println("update "+u.getUserType().toLowerCase()+" set password='" +pwd+"' where id="+u.getId()+";");
			}
		}
		System.out.println("done");

	}

}
