package com.istarindia.apps.games.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.dao.IstarUser;
import com.istarindia.apps.dao.IstarUserDAO;
//import com.istarindia.apps.games.services.GameSerializer;

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
		IstarUser user =(IstarUser) request.getSession().getAttribute("user");
		
		
		//GameXML game = new GameSerializer().getGame(Integer.parseInt(request.getParameter("parent_item_id")));
                GameXML game = null;
		ArrayList<Asset> assets = game.getAssets();
		int item_id=0;
		if(request.getParameter("stage_type").equalsIgnoreCase("INFORMATION_ONLY"))
		{
			item_id= Integer.parseInt(request.getParameter("item_id"));
		}
		else if(request.getParameter("stage_type").equalsIgnoreCase("MULTIPLE_OPTION_DRAG_DROP"))
		{
			item_id = Integer.parseInt(request.getParameter("item_id"));
			int prev_stage_id = Integer.parseInt(request.getParameter("prev_stage_id"));
			if(request.getParameterMap().containsKey("options"))
			{
				for(String op: request.getParameter("options").toString().split(",") )
				{
					int option_id = Integer.parseInt(op);
					Stage stage = null;
					for (Stage iterable_element : game.getStages()) {
						if(iterable_element.getId()==prev_stage_id) {
							stage = iterable_element;
						}
					}
					Option option = null;
					for (Option iterable_element : stage.getOptions()) {
						if(iterable_element.getId()==option_id) {
							option = iterable_element;
						}
					}
					String marking_scheme =  option.getMakringScheme();
					
				//String results = new GameSerializer().eval(game.getId(), marking_scheme, user.getId());
                                String results = "";
					
				System.out.println("new scheme="+results);
					
					
				}
				//request.setAttribute("option_id", request.getParameter("option_id"));
			}
		}
		else if(request.getParameter("stage_type").equalsIgnoreCase("MULTIPLE_OPTION_SINGLE_CHOICE"))
		{
			 int prev_stage_id = Integer.parseInt(request.getParameter("prev_stage_id"));
			
			
			if(request.getParameterMap().containsKey("option_id"))
			{
				int option_id = Integer.parseInt(request.getParameter("option_id"));
				Stage stage = null;
				for (Stage iterable_element : game.getStages()) {
					if(iterable_element.getId()==prev_stage_id) {
						stage = iterable_element;
					}
				}
				Option option = null;
				for (Option iterable_element : stage.getOptions()) {
					if(iterable_element.getId()==option_id) {
						option = iterable_element;
					}
				}
				System.err.println("jumpto in multiple option single choice----"+option.getJump_to());
				item_id = option.getJump_to();
				//String results =new GameSerializer().eval(game.getId(), option.getMakringScheme(), user.getId());
                                String results ="";
				String xx[]= results.split(";");
				for(Asset a : game.getAssets())
				{
					for(String x: xx)
					{
						if(x.contains(a.getName()))
						{
							request.getSession().setAttribute(a.getName(), x.split("=")[1]);
						}
					}
				}
				System.out.println("new scheme after multiple option single choice ="+results);
				
				
			}
			
		}
		
		request.setAttribute("item_id",item_id );
		
		request.getRequestDispatcher("/play_game.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
