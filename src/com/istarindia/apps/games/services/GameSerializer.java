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

import com.istarindia.cms.game.Game;
import com.istarindia.cms.game.Option;
import com.istarindia.cms.game.Stage;

/**
 * @author vaibhav
 *
 */
public class GameSerializer {
	public String getIntro(Game game) {
		return "<section> <h1>" + game.getName() + "</h1> <h3>The HTML Presentation Framework</h3> </section>";

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
			out.append("<section> " + stage.getQuestionText() + "</section>");
			ArrayList<Option> options = stage.getOptions();
			for (Option option : options) {

				out.append("<input type=\"radio\" name=\"option_id\" value=\"" + option.getId() + "\" checked=\"\">"
						+ option.getId() + option.getOptionText() + "");
			}
			break;
		case "MULTIPLE_OPTION_DRAG_DROP":
			out.append("<section> " + stage.getQuestionText() + "</section>");
			// out.append("<input type=\"hidden\" name=\"prev_stage_id\"
			// value=\""+s.getId()+"\" >");
			ArrayList<Option> options1 = stage.getOptions();
			for (Option option : options1) {

				out.append("<input type=\"radio\" name=\"option_id\" value=\"" + option.getId() + "\" checked=\"\">"
						+ option.getId() + option.getOptionText() + "");
			}
			break;

		default:
			break;
		}
		return null;

	}
}
