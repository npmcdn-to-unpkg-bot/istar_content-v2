package com.istarindia.apps.games.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istarindia.apps.services.controllers.IStarBaseServelet;
import com.istarindia.cms.game.*;
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
		//printParams(request);
	
		
		request.setAttribute("prev_stage_id", request.getParameter("prev_stage_id"));
		if(request.getParameterMap().containsKey("option_id"))
		{
			request.setAttribute("option_id", request.getParameter("option_id"));
		}
		
		
		request.getRequestDispatcher("/game/gameplay.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
