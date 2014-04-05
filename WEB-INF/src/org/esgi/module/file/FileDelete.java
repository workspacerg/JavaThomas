package org.esgi.module.file;

import java.io.File;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class FileDelete extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		File file = new File (context.getProperties().get("file.repository").toString() +"/"+ context.getParameter("path"));
		file.delete();
		
		String[] multiPart = context.getRequest().getRequestURI().split("/");
		StringBuilder sb = new StringBuilder();
		
		// On retire le delete et le fichier (rustique !)
		for(int i = 1 ; i < multiPart.length - 2 ; i++)
			sb.append("/" + multiPart[i]);
		
		// On oublie pas le slash pour la regex de base
		context.getResponse().sendRedirect(sb.toString() + "/");
		
	}

	@Override
	public String getRoute() {
		return "/file/list(/.+)?/delete";
	}

	@Override
	public String[] getRewriteGroups() { 
		return new String[]{"path"};
	}

}
