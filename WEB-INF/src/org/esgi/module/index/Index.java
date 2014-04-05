package org.esgi.module.index;

import java.io.File;
import java.io.Writer;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Index extends AbstractAction{

	@Override
	public void execute(IContext context) throws Exception {
		
		//Initialisation des paramètres de la vue
		context.setTitle("Connexion");
		context.addCSSDependancy((String) context.getProperties().get("file.css"));
		
		
		
	}
	
	@Override
	public String getRoute() {
		return "/";
	}
	

}
