package org.esgi.module.index;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Inscription extends AbstractAction {
	@Override
	public void execute(IContext context) throws Exception {		
		context.setTitle("Inscription");
	}
	
	@Override
	public String getRoute() {
		return "^/Register$";
	}

}
