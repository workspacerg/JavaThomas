package org.esgi.module.menu.profil;

import java.io.File;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Profil extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		context.setTitle("Profil");
	}

	@Override
	public String getRoute() {
		return "^/Contact(?:/)?$";
	}

}
