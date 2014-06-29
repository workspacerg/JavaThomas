package org.esgi.module.index;

import java.io.StringWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.esgi.orm.ORM;
import org.esgi.orm.my.model.Evaluation;
import org.esgi.orm.my.model.Film;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SendComment extends AbstractAction{
	@Override
	public String getRoute() {
		return "/SendComment";
	}
	
	@Override
	public String getLayout() {
		return null;
	}

	@Override
	public void execute(IContext context) throws Exception {
		String comment = context.getRequest().getParameter("commentPlace");
		int note = Integer.parseInt(context.getRequest().getParameter("note"));
		int idFilm = Integer.parseInt(context.getRequest().getParameter("idfilm"));
		
		MapperAjax ma = new MapperAjax();
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("login", context.getRequest().getSession().getAttribute("login"));
		List<User> listU = (List<User>)(List<?>)ORM.find(User.class, map, null,null,null);
		User u;
		if(listU.size() != 1){
			ma.Add("success", false);
			ma.Add("message", "<h2> Utilisateur non reconnu. </h2>");
			ma.Write(context);
			return;
		}
		
		u = listU.get(0);
		
		map.clear();
		map.put("id_film", idFilm);
		List<Film> listF = (List<Film>)(List<?>)ORM.find(Film.class, map, null,null,null);
		Film f;
		if(listF.size() != 1){
			ma.Add("success", false);
			ma.Add("message", "<h2> Film non reconnu. </h2>");
			ma.Write(context);
			return;
		}
		f = listF.get(0);
		
		
		Evaluation e = new Evaluation();
		e.commentaire = comment;
		e.note = note;
		e.user = u;
		e.film = f;

		Object save = ORM.save(e);
		boolean isSave = save != null;
		ma.Add("success", isSave);
		if(!isSave)
			ma.Add("message", "Une erreur a été rencontré durant l'enregistrement de votre commentaire.");
		
		ma.Write(context);

	}
}
