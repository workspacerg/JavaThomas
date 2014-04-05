package org.esgi.module.action;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IAction;
import org.esgi.web.action.IContext;

public class HelloWorld implements IAction {

	public void execute(IContext context) throws Exception {
		context.getResponse().getWriter().println("hello world !");

	}

	public String getRoute() {
		return "/test1";
	}

	public String[] getRewriteGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLayout() {
		// TODO Auto-generated method stub
		return null;
	}

}
