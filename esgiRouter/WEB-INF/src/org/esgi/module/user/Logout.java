package org.esgi.module.user;

import java.io.StringWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.esgi.orm.ORM;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Logout extends AbstractAction{
	@Override
	public String getRoute() {
		return "/Logout";
	}
	@Override
	public String getLayout() {
		return null;
	}
	@Override
	public void execute(IContext context) throws Exception {
		HttpSession session;
		if((session = context.getRequest().getSession()) != null)
			session.invalidate();
		context.getResponse().sendRedirect("/esgiRouter");
	}
}
