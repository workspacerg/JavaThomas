package org.esgi.module.contact;

import java.io.File;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Contact extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
	
		
	}

	@Override
	public String getRoute() {
		return "/contact/contact/";
	}

	@Override
	public String[] getRewriteGroups() { 
		return new String[]{"path"};
	}

}
