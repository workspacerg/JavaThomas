package org.esgi.module.admin;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AddAdmin extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {		
		String login = context.getRequest().getParameter("login");
		Map<String,Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put("login",login);
		
		boolean existLogin = ORM.find(User.class,mapWhere,null,null,null).size() > 0;
		
		MapperAjax ma = new MapperAjax();
		
		if(existLogin){
			ma.Add("success", false);
			ma.Add("message", "Veuillez choisir un autre login ");
		}
		else{				
			User admin = new User();
			admin.nom = context.getRequest().getParameter("nom");
			admin.prenom = context.getRequest().getParameter("prenom");
			admin.password = context.getRequest().getParameter("password");
			admin.login = login;
			admin.isAdmin = true;
			
			Object saveuser = ORM.save(admin);
			boolean isSave = saveuser != null;
			ma.Add("success", isSave);
			
			if(!isSave)
				ma.Add("message","Enregistrement de l'administrateur impossible.");
		}
		
		ma.Write(context);
	}
	
	@Override
	public String getRoute() {
		return "/Admin/AddAdmin";
	}
	
	@Override
	public String getLayout(){
		return null;
	}

}
