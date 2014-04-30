package org.esgi.module.user;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.Film;
import org.esgi.orm.my.model.Identifiant;
import org.esgi.orm.my.model.User;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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

		List<Object> ids = ORM.find(Identifiant.class, mapWhere, null, null, null);
		
		int id = -1;
		if(ids.size() == 1)
			id = ((Identifiant)ids.get(0)).id_i;
		
		mapWhere.clear();
		mapWhere.put("identifiant", id);
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		Map<String,Object> map = new HashMap<String, Object>();

		if(ORM.find(User.class, mapWhere, null, null, null).size() == 1)
		{
			map.put("success", true);
						
			HttpSession session = context.getRequest().getSession(true);
			session.setAttribute("login",login);
			
			context.setAttribute("login", login);
		}
		else{		
			map.put("success", false);
		}

		mapper.writeValue(sw, map);
		context.getResponse().getWriter().print(sw.toString());
	}
}
