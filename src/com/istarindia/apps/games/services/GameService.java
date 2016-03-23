package com.istarindia.apps.games.services;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.istarindia.apps.dao.StudentDAO;
import com.istarindia.apps.dao.StudentGame;
import com.istarindia.apps.dao.StudentGameDAO;
import com.istarindia.apps.services.CMSRegistry;
import com.istarindia.cms.game.*;


public class GameService {

	//static Game game ;

	public String getNextStageForm(Game game, int stage_id)
	{
		//game = getGame();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<form action=\"/content/play_game\" class=\"sky-form\"><fieldset><section><div class=\"row\"><div class=\"col col-4\">");
		if (null != game) 
		{
			
			ArrayList<Stage> stages = game.getStages();
			
			Stage s = stages.get(stage_id-1);
			System.out.println("stage_id--"+s.getId());
			//for(Stage s : stages)
			//{
				sb.append("<label>"+s.getId()+"-"+s.getStageType()+"-"+s.getQuestionText()+"</label>");
				sb.append("<input type=\"hidden\" name=\"prev_stage_id\"  value=\""+s.getId()+"\" >");
				if (s.getStageType().equalsIgnoreCase("MULTIPLE_OPTION_SINGLE_CHOICE") || s.getStageType().equalsIgnoreCase("MULTIPLE_OPTION_DRAG_DROP")) 
				{
					ArrayList<Option> options = s.getOptions();
					for (Option option : options) 
					{
					
					sb.append("<label class=\"radio\"><input type=\"radio\" name=\"option_id\" value=\""+option.getId()+"\" checked=\"\"><i class=\"rounded-x\"></i><a>"+option.getId() + option.getOptionText()+"</a></label>");
					}
				}
				else if(s.getStageType().equalsIgnoreCase("INFORMATION_ONLY") || s.getStageType().equalsIgnoreCase("MULTIPLE_OPTION_MULTIPLE_CHOICE"))
				{
					int next_stage = stage_id+1;
					System.out.println("next stage="+next_stage);
					sb.append("<input type=\"hidden\" name=\"stage_id\"  value=\""+next_stage+"\" >");
				}	
			//}
				
		}
		else
		{
			System.out.println("sdsd");
		}	
		sb.append("</div></div></section></fieldset><button class=\"btn-u btn-u-blue\" type=\"submit\">Next</button></form>");
		
		return sb.toString();
				
	}
	
	public String startGame(Game game)
	{
		return getNextStageForm(game,1);
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
	
	
	public Object getScriptEvaluation(String scheme_name,String value,String expression){
		System.out.println("scheme_name is --> "+scheme_name);
		System.out.println("value is --> "+value);
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
		jsEngine.put(scheme_name, Integer.parseInt(value));
		expression="c ="+expression;
		System.err.println("expression is --> "+expression);
		Object result = null;
		try {
			result = jsEngine.eval(expression);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("result= " + result);

	    return result;
		
	}
	
	public void updateStudentGame(int student_id, String assets, int game_id)
	{
		StudentGame stgame = new StudentGame();
		stgame.setGameId(game_id);
		stgame.setStudent(new StudentDAO().findById(student_id));
		
		if(new StudentGameDAO().findByExample(stgame).size()>0)
		{
			stgame= new StudentGameDAO().findByExample(stgame).get(0);
			stgame.setAssets(assets);
		}
		else
		{
			stgame.setAssets(assets);
		}	
		
		StudentGameDAO dao = new StudentGameDAO();
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
		
	}

}