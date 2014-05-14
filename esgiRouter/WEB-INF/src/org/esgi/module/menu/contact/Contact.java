package org.esgi.module.menu.contact;

import java.io.File;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Contact extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		context.setTitle("Contact");
	}

	@Override
	public String getRoute() {
		return "/Contact";
	}

}
