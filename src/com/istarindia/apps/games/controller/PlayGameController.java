package com.istarindia.apps.games.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.games.services.GameSerializer;
import com.istarindia.apps.games.services.GameService;
import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.cms.game.*;

import antlr.collections.List;
/**
 * Servlet implementation class PlayGameController
 */
@WebServlet("/play_game")
public class PlayGameController extends IStarBaseServelet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayGameController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printParams(request);
		//IstarUser user = request.getSession().getAttribute("user");
		
		Game game = new GameService().getGame();
		ArrayList<Asset> assets = game.getAssets();
		int stage_id=0;
		if(request.getParameter("stage_type").equalsIgnoreCase("INFORMATION_ONLY"))
		{
			stage_id= Integer.parseInt(request.getParameter("stage_id"));
		}
		else if(request.getParameter("stage_type").equalsIgnoreCase("MULTIPLE_OPTION_DRAG_DROP"))
		{
			stage_id = Integer.parseInt(request.getParameter("stage_id"));
			int prev_stage_id = Integer.parseInt(request.getParameter("prev_stage_id"));
			if(request.getParameterMap().containsKey("options"))
			{
				for(String op: request.getParameterValues("options") )
				{
					int option_id = Integer.parseInt(op);
					Stage stage = game.getStages().get(prev_stage_id);
					Option option = stage.getOptions().get(option_id);
					String marking_scheme =  option.getMakringScheme();
					String schemes []= marking_scheme.split(";");
					for(Asset a : assets)
					{
						for(String scheme: schemes)
						{
							if(scheme.contains(a.getName()))
							{
								new GameSerializer().updateAssets(a, scheme);
							}
						}	
							
					}
					
					
				}
				request.setAttribute("option_id", request.getParameter("option_id"));
			}
		}
		else if(request.getParameter("stage_type").equalsIgnoreCase("MULTIPLE_OPTION_SINGLE_CHOICE"))
		{
			 int prev_stage_id = Integer.parseInt(request.getParameter("prev_stage_id"));
			
			
			if(request.getParameterMap().containsKey("option_id"))
			{
				int option_id = Integer.parseInt(request.getParameter("option_id"));
				Stage stage = game.getStages().get(prev_stage_id);
				Option option = stage.getOptions().get(option_id);
				System.err.println("jumpto----"+option.getJump_to());
				stage_id = option.getJump_to();
				
			}
			
		}
		
		request.setAttribute("stage_id",stage_id );
		
		request.getRequestDispatcher("/game/newgame.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
