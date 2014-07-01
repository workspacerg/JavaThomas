package org.esgi.module.user;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.esgi.orm.ORM;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Connect extends AbstractAction{
	@Override
	public String getRoute() {
		return "/user/connect";
	}
	@Override
	public String getLayout() {
		return null;
	}
	@Override
	public void execute(IContext context) throws Exception {
		Map<String,Object> mapWhere = new HashMap<String, Object>();
		String login = context.getRequest().getParameter("login");
		mapWhere.put("login",login);
		mapWhere.put("password", context.getRequest().getParameter("password"));
		
		MapperAjax ma = new MapperAjax();
		
		List<User> listU = new ArrayList<User>();
		if((listU = (List<User>)(List<?>)ORM.find(User.class, mapWhere, null, null, null)).size() == 1)
		{
			ma.Add("success", true);
						
			HttpSession session = context.getRequest().getSession(true);
			session.setAttribute("login",login);
			session.setAttribute("isAdmin", listU.get(0).isAdmin);
			
			context.setAttribute("login", login);
		}
		else{		
			ma.Add("success", false);
		}

		ma.Write(context);
	}
}
