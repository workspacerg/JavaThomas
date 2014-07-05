package org.esgi.module.index;

import java.io.File;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		context.setTitle("Accueil");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("estAffiche", "1");
		List<Film> films = (List<Film>)(List<?>)ORM.find(Film.class, map,null,null,null);
		context.setAttribute("films", films);
	}
	
	@Override
	public String getRoute() {
		return "^/$";
	}
	

}
