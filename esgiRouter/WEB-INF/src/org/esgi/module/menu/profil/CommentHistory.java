package org.esgi.module.menu.profil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpSession;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.Evaluation;
import org.esgi.orm.my.model.User;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class CommentHistory extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		context.setTitle("Mes derniers commentaires");
		
		HttpSession session;
		if((session = context.getRequest().getSession())==null)
			return;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user",session.getAttribute("userId"));
		Map<String, Object> sort_eval = new HashMap<String, Object>();
        sort_eval.put("date","DESC");
		
		List<Evaluation> evals = (List<Evaluation>)(List<?>)ORM.find(Evaluation.class, map,sort_eval,10,null);
		context.setAttribute("evals", evals);
		
	}

	@Override
	public String getRoute() {
		return "^/Profil/CommentHistory(?:/)?$";
	}

}
