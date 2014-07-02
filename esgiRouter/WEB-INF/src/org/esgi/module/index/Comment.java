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

public class Comment extends AbstractAction{
	@Override
	public String getRoute() {
		return "^/Comment/[0-9]+(?:/[0-9]+)?$";
	}

	@Override
	public void execute(IContext context) throws Exception {
		context.setTitle("Commentaires");
		String path = context.getRequest().getPathInfo();
		int idxFilm = -1;
		int idxComm = -1;
		if(path.matches("^/Comment/[0-9]+/[0-9]+$")){
			idxComm = Integer.parseInt(path.substring(path.lastIndexOf("/")+1));
			String seek = path.substring(path.substring(0,path.lastIndexOf("/")-1).lastIndexOf("/")+1,path.lastIndexOf("/"));
			idxFilm = Integer.parseInt(seek);
		}
		else
			idxFilm = Integer.parseInt(path.substring(path.lastIndexOf("/")+1));
		
		System.out.println(idxFilm);
		Map<String,Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put("film", idxFilm);
		
		List<Evaluation> evals = (List<Evaluation>)(List<?>)ORM.find(Evaluation.class, mapWhere,null,null,null);
		context.setAttribute("evaluations", evals);
		if(evals.size() > 0){
			context.setAttribute("film", evals.get(0).film);
			context.setAttribute("average", Math.round(getAverage(evals)));
			context.setTitle("Commentaires : "+evals.get(0).film.titre);
		}
		
		if(idxComm > -1)
			context.setAttribute("commentaire", idxComm);
	}
	
	private float getAverage(List<Evaluation> eval){
		float avg = 0;
		
		for(Evaluation e : eval)
			avg += e.note;
		
		return avg / eval.size();
	}
}
