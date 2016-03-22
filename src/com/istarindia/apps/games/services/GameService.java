package com.istarindia.apps.games.services;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
				sb.append("<label>"+s.getId()+s.getQuestionText()+"</label>");
				
				if (!s.getStageType().equalsIgnoreCase("INFORMATION_ONLY")) 
				{
					ArrayList<Option> options = s.getOptions();
					for (Option option : options) 
					{
					sb.append("<label class=\"radio\"><input type=\"radio\" name=\"stage_id\" value=\""+option.getJump_to()+"\" checked=\"\"><i class=\"rounded-x\"></i><a>"+option.getId() + option.getOptionText()+"</a></label>");
					}
				}
				else 
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
		System.out.println("url1 --> " + url1.toURI());
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
}
