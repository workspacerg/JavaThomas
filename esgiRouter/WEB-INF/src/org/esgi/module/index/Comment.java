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
		return "/Comment/[0-9]+";
	}

	@Override
	public void execute(IContext context) throws Exception {
		String path = context.getRequest().getPathInfo();
		int idx = path.lastIndexOf("/")+1;
		int filmIdx = Integer.parseInt(path.substring(idx));
		
		Map<String,Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put("film", filmIdx);
		
		List<Evaluation> evals = (List<Evaluation>)(List<?>)ORM.find(Evaluation.class, mapWhere,null,null,null);
		context.setAttribute("evaluations", evals);
		if(evals.size() > 0){
			context.setAttribute("film", evals.get(0).film);
			context.setAttribute("average", getAverage(evals));
		}
	}
	
	private float getAverage(List<Evaluation> eval){
		float avg = 0;
		
		for(Evaluation e : eval)
			avg += e.note;
		
		return avg / eval.size();
	}
}
