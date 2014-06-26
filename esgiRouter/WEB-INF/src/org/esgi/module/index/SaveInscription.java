package org.esgi.module.index;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.User;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SaveInscription extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {		
		Map<String,Object> mapWhere = new HashMap<String, Object>();
		String login = context.getRequest().getParameter("login");
		mapWhere.put("login",login);
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		Map<String,Object> map = new HashMap<String, Object>();

		boolean existLogin = ORM.find(User.class,mapWhere,null,null,null).size() > 0;
		
		if(existLogin){
			map.put("success", false);
			map.put("message", "Le login entré éxiste déjà");
		}
		else{				
			User user = new User();  
			user.nom = context.getRequest().getParameter("nom");
			user.prenom = context.getRequest().getParameter("prenom");
			user.login = login;
			
			Object saveuser = ORM.save(user);
			boolean isSave = saveuser != null;
			map.put("success", isSave);
			
			if(!isSave)
				map.put("message","Enregistrement de votre profil échoué.");
		}
		
		mapper.writeValue(sw, map);
		context.getResponse().getWriter().print(sw.toString());
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
