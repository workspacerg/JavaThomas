package org.esgi.module.index;

import java.io.File;
import java.io.Writer;
import java.util.List;

import javax.servlet.RequestDispatcher;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.Evaluation;
import org.esgi.orm.my.model.Film;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Index extends AbstractAction{

	@Override
	public void execute(IContext context) throws Exception {		
		//Initialisation des paramètres de la vue
		context.addCSSDependancy((String)context.getProperties().get("file.css"));
		if(context.getRequest().getSession().getAttribute("login") != null){
			List<Film> films = (List<Film>)(List<?>)ORM.find(Film.class, null,null,null,null);
			context.setAttribute("films", films);
		}
	}
	
	@Override
	public String getRoute() {
		return "/";
	}
	

}
