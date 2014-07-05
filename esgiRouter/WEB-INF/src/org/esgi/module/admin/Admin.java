package org.esgi.module.admin;

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

public class Admin extends AbstractAction{
	@Override
	public String getRoute() {
		return "^/Admin(?:/)?$";
	}

	@Override
	public void execute(IContext context) throws Exception {
		context.setTitle("Administration");
	}
	
}
