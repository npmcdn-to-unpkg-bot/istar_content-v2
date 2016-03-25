/**
 * 
 */
package com.istarindia.apps.games.services;

import java.io.File;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.StudentDAO;
import com.istarindia.apps.dao.StudentGame;
import com.istarindia.apps.dao.StudentGameDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.cms.game.Asset;
import com.istarindia.cms.game.Game;
import com.istarindia.cms.game.Option;
import com.istarindia.cms.game.Stage;

/**
 * @author vaibhav
 *
 */
public class GameSerializer {
	public String getIntro(Game game) {
		return getNextStage(game, 0);

	}

	public String getNextStage(Game game, int stageID) {
		Stage stage = game.getStages().get(stageID);
		for (Stage iterable_element : game.getStages()) {
			if(iterable_element.getId()==stageID) {
				stage = iterable_element;
			}
		}
		StringBuffer out = new StringBuffer();
		VelocityEngine ve = new VelocityEngine();
		VelocityContext context = new VelocityContext();
		System.err.println(stage.getQuestionText());
		System.err.println(stage.getStageType());
		switch (stage.getStageType()) {
		case "INFORMATION_ONLY":
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
			context.put("stage", stage);
			Template t = ve.getTemplate("INFORMATION_ONLY.vm");
			StringWriter writer = new StringWriter();
			t.merge(context, writer);
			out.append(writer.toString());
			return out.toString();
		case "MULTIPLE_OPTION_MULTIPLE_CHOICE":
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
			context.put("stage", stage);
			Template t1 = ve.getTemplate("INFORMATION_ONLY.vm");
			StringWriter writer1 = new StringWriter();
			t1.merge(context, writer1);
			out.append(writer1.toString());
			return out.toString();
		case "MULTIPLE_OPTION_SINGLE_CHOICE":
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
			context.put("stage", stage);
			Template t2 = ve.getTemplate("MULTIPLE_OPTION_SINGLE_CHOICE.vm");
			StringWriter writer2 = new StringWriter();
			t2.merge(context, writer2);
			out.append(writer2.toString());
			return out.toString();
		case "MULTIPLE_OPTION_DRAG_DROP":
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
			context.put("stage", stage);
			Template t3 = ve.getTemplate("MULTIPLE_OPTION_DRAG_DROP.vm");
			StringWriter writer3 = new StringWriter();
			t3.merge(context, writer3);
			out.append(writer3.toString());
			return out.toString();
			
		default:
			break;
		}
		return null;

	}

	public static  Game getGame()
	{
		Game game = new Game(); 
		try{
		
		URL url1 = (new CMSRegistry()).getClass().getClassLoader().getResource("/sample_game.xml");
		//System.out.println("url1 --> " + url1.toURI());
		File file = new File(url1.toURI());
		JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		game = (Game) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}
	

	

	public String eval(int game_id, String marking_scheme, Integer user_id) {
		StudentGame stgame = new StudentGame();
		StudentGameDAO dao = new StudentGameDAO();
		List<Asset> as = new GameSerializer().getGame().getAssets();
		stgame.setStudent(new StudentDAO().findById(user_id));
		stgame.setGameId(game_id);
		
		StringBuffer sb = new StringBuffer();
		if(dao.findByExample(stgame).size()>0)
		{
			stgame = dao.findByExample(stgame).get(0);
			String result_from_db = stgame.getAssets();
			String arr_from_db[]= result_from_db.split(";");
			String asset_array [] = marking_scheme.split(";");//{"score+9","coins-5"}
			for(String st : asset_array)
			{
				for(Asset a : as)
				{
					if(st.contains(a.getName()))
					{
						if(a.getDataType().equalsIgnoreCase("Integer"))
						{
							String old_value="0";
							for(String res: arr_from_db)
							{
								if(res.contains(a.getName()))
								{
									String val[] = res.split("=");
									old_value= val[1];
								}
							}
							
							String expression=st;
							ScriptEngineManager mgr = new ScriptEngineManager();
							ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
							jsEngine.put(a.getName(), Integer.parseInt(old_value));
							expression="c ="+expression;
							System.err.println("expression is --> "+expression);
							Object result = null;
							try {
								result = jsEngine.eval(expression);
							} catch (ScriptException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Double new_value = (Double)result;
						    System.out.println("result= " + new_value);
						    sb.append(a.getName()+"="+new_value.intValue()+";");
						}
						else
						{
							sb.append(st+";");
						}
					}
				}
			}
			
		}
		else
		{
			//split the markingscheme
			String asset_array [] = marking_scheme.split(";");
			for(String st : asset_array)
			{ 
				
				for(Asset a :as )
				{
					if(st.contains(a.getName()))
					{
						if(a.getDataType().equalsIgnoreCase("Integer"))
						{
							String expression=st;
							ScriptEngineManager mgr = new ScriptEngineManager();
							ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
							jsEngine.put(a.getName(), Integer.parseInt("0"));
							expression="c ="+expression;
							System.err.println("expression is --> "+expression);
							Object result = null;
							try {
								result = jsEngine.eval(expression);
							} catch (ScriptException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Double new_value = (Double)result;
						    System.out.println("result= " + new_value);
							sb.append(a.getName()+"="+new_value.intValue()+";");
						}
						else if(a.getDataType().equalsIgnoreCase("String"))
						{
							sb.append(st+";");
						}
						
					}
				}
			}
		}
		
		stgame.setAssets(sb.toString());
		Session session = dao.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.attachDirty(stgame);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return sb.toString();
	}
}
