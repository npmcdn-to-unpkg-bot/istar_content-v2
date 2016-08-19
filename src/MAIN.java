import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.istarindia.apps.dao.CompanyDAO;
import com.istarindia.apps.dao.OrgAdminDAO;

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
		String kk = (new CompanyDAO().findById(Integer.parseInt("2")).getId())+"";
		
		System.err.println(kk);
		
		
		
		/*Properties properties = new Properties();
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
		System.out.println("done");*/

	}

}
