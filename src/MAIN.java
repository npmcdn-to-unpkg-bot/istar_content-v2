import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.istarindia.apps.dao.CompanyDAO;
import com.istarindia.apps.dao.DBUTILS;
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

		int workUtnits =0;
		
		String kk = "SELECT 	* FROM 	task_log WHERE 	actor_id = 6 AND task_log.created_at >= CURRENT_DATE + INTERVAL '0 hour' AND task_log.created_at <= CURRENT_DATE + INTERVAL '23 hour'  ";
		DBUTILS db = new DBUTILS();
		List<HashMap<String, Object>> data = db.executeQuery(kk);
		for (HashMap<String, Object> hashMap : data) {
			
		}
	}

}
