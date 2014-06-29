package org.esgi.module.index;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SaveInscription extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {		
		String login = context.getRequest().getParameter("login");
		Map<String,Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put("login",login);
		
		boolean existLogin = ORM.find(User.class,mapWhere,null,null,null).size() > 0;
		
		MapperAjax ma = new MapperAjax();
		
		if(existLogin){
			ma.Add("success", false);
			ma.Add("message", "Le login entré éxiste déjà ");
		}
		else{				
			User user = new User();
			user.nom = context.getRequest().getParameter("nom");
			user.prenom = context.getRequest().getParameter("prenom");
			user.login = login;
			
			Object saveuser = ORM.save(user);
			boolean isSave = saveuser != null;
			ma.Add("success", isSave);
			
			if(!isSave)
				ma.Add("message","Enregistrement de votre profil échoué.");
		}
		
		ma.Write(context);
	}
	
	@Override
	public String getRoute() {
		return "/index/SaveInscription";
	}
	
	@Override
	public String getLayout(){
		return null;
	}

}
