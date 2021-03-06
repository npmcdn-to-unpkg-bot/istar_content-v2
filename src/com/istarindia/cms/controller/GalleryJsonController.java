package com.istarindia.cms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.istarindia.apps.MediaUtils;
import com.istarindia.apps.dao.DBUTILS;
import com.istarindia.apps.dao.Image;
import com.istarindia.apps.dao.IstarUserDAO;

/**
 * Servlet implementation class GalleryJsonController
 */
@WebServlet("/GalleryJsonController")
public class GalleryJsonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GalleryJsonController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		JSONObject json = null;
        JSONArray arr = new JSONArray();
		
		/*String sql = "SELECT 	I.url, 	I.thumbnail_url FROM 	image I, 	image_task T WHERE T .item_id = I. ID AND I.url IS NOT NULL";
		DBUTILS util = new DBUTILS();
		List<HashMap<String, Object>> results = util.executeQuery(sql);
        for (HashMap<String, Object> object : results) {
        	json = new JSONObject();
			try {
				json.put("image", object.get("url").toString());
				json.put("thumb", object.get("thumbnail_url").toString());
				json.put("folder", "small");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			arr.put(json);
		}*/

		MediaUtils mediaUtils = new MediaUtils();
		ArrayList<Image> list = mediaUtils.findAllAssessmentMedia();
		for (Image image : list) {
			json = new JSONObject();
			try {
				json.put("image", image.getUrl());
				json.put("thumb", image.getUrl());
				json.put("folder", "small");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			arr.put(json);

		}
		PrintWriter out = response.getWriter();
		out.print(arr.toString().replaceAll("\"url\"", "url"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doGet(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
