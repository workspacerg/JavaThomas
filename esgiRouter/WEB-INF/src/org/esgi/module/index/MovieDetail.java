package org.esgi.module.index;

import java.io.StringWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.esgi.orm.ORM;
import org.esgi.orm.my.model.Film;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieDetail extends AbstractAction{
	@Override
	public String getRoute() {
		return "/MovieDetail/[0-9]+";
	}

	@Override
	public void execute(IContext context) throws Exception {
		String path = context.getRequest().getPathInfo();
		int idx = path.lastIndexOf("/")+1;
		int filmIdx = Integer.parseInt(path.substring(idx));
		
		Map<String,Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put("id_film", filmIdx);
		
		List<Film> films = (List<Film>)(List<?>)ORM.find(Film.class, mapWhere,null,null,null);
		if(films != null && films.size() == 1){
			System.out.println(films.get(0).getSeancesTomorrow().size());
			context.setAttribute("film", films.get(0));
		}
	}
}
