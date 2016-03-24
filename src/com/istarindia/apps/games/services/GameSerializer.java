/**
 * 
 */
package com.istarindia.apps.games.services;

import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

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

	public void updateAssets(Asset a, String scheme) {
		
		
	}
}
