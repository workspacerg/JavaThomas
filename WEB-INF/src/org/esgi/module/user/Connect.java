package org.esgi.module.user;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.esgi.orm.ORM;
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
		mapWhere.put("login",context.getRequest().getParameter("login"));
		mapWhere.put("password", context.getRequest().getParameter("password"));

		//ORM.save(u);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		Map<String,Object> map = new HashMap<String, Object>();

		if(ORM.find(User.class, mapWhere, null, null, null).size() == 1)
		{
			List<Object> users = ORM.find(User.class, null,null,null,null);
			map.put("success", true);
			map.put("users", users);
			mapper.writeValue(sw, map);
			context.getResponse().getWriter().print(sw.toString());
		}
		else{		
			map.put("success", false);
		}

		System.out.println(sw.toString());
	}
}
