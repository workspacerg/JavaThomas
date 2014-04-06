package org.esgi.module.user;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.User;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Delete extends AbstractAction {
	public String getRoute() {
		return "/user/delete";
	}
	@Override
	public String getLayout() {
		return null;
	}
	@Override
	public void execute(IContext context) throws Exception {
		String id = context.getRequest().getParameter("id");
		boolean result = ORM.remove(User.class,id);
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("success", result);
		mapper.writeValue(sw,map);
		
		context.getResponse().getWriter().print(sw.toString());
	}
}
