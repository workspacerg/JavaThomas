package org.esgi.module.file;

import java.io.File;
import java.io.Writer;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IAction;
import org.esgi.web.action.IContext;

public class FileList implements IAction {

	@Override
	public void execute(IContext context) throws Exception {
		
		
		//Initialisation des paramètres de la vue
		context.setTitle("File manager");
		context.addCSSDependancy((String) context.getProperties().get("file.css"));
		
		
		//context.addJSDependancy((String) context.getProperties().get("file.js"));
		//context.addJSDependancy((String) context.getProperties().get("file.js"));
		//context.addJSDependancy("test.js");
		
		//context.addKeyWord("Test");
		//context.addKeyWord("Rayane");
		//context.setDescription("ma descr");
		context.inlineCSS("header h2{ color: white; }");
		
		//System.out.println("Mon Test: " + (String) context.getProperties().get("file.js"));
		
		// pour éviter erreur outputstream
		Writer writer = context.getResponse().getWriter();
		
		File repo = new File((String) context.getProperties().get("file.repository")); 
		String path = (String) context.getParameter("path");
		
		if (null != path) { 
			repo = new File(repo, "/"+path);
		}
		else
			path = "";
		

		
		context.setAttribute("files", repo.listFiles());
		context.setAttribute("path",path);
		
	}


	public String getRoute() {
		return "/file/list/(?:(.+)?/)?$";
	}


	public String[] getRewriteGroups() {
		return new String[]{"path"};
	}


	@Override
	public String getLayout() {
		// TODO Auto-generated method stub
		return "default";
	}

}
