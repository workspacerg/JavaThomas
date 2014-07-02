package org.esgi.module.menu.admin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpSession;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.User;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Information extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		context.setTitle("Informations");
		HttpSession session;
		if((session = context.getRequest().getSession()) == null)
			return;
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("login",context.getRequest().getSession().getAttribute("login"));
		
		List<User> listU = (List<User>)(List<?>)ORM.find(User.class, map,null,null,null);
		if(listU.size() == 1)
		{
			User u = listU.get(0);
			context.setAttribute("nom", u.nom );
			context.setAttribute("prenom", u.prenom);
			context.setAttribute("login",u.login);
		}
	}

	@Override
	public String getRoute() {
		return "^/Profil/Information(?:/)?$";
	}

}
