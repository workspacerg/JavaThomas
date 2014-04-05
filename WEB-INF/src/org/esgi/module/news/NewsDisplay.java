package org.esgi.module.news;

import java.io.IOException;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IAction;
import org.esgi.web.action.IContext;

public class NewsDisplay extends AbstractAction {

	// TODO Add Throw Exception IO.
	public void execute(IContext context) throws IOException {
		context.getResponse().getWriter().println("IN NEWS DISPLAY");
	}

	public String getRoute() {
		return "actualite/(.+)\\.html";
	}

	public String[] getRewriteGroups() {
		// TODO Auto-generated method stub
		return null;
	}

}
