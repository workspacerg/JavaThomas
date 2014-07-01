package org.esgi.module.menu.profil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpSession;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class SaveInformation extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		
		String newPass = context.getRequest().getParameter("newPassword");
		String confPass = context.getRequest().getParameter("confirmPassword");
		String login = (String) context.getRequest().getSession().getAttribute("login");
		
		MapperAjax ma = new MapperAjax();
		
		if(!newPass.equals(confPass)){
			ma.Add("success", false);
			ma.Add("message", "<h2>Les mots de passe entrés ne sont pas identiques</h2>");
		}
		else{		
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("login", login);
			
			List<User> listU = (List<User>)(List<?>)ORM.find(User.class, map,null,null,null);
			if(listU != null && listU.size() == 1){
				User u = listU.get(0);
				u.password = newPass;
				Object save = ORM.save(u);
				ma.Add("success", save!=null);
				if(save == null)
					ma.Add("message", "<h2>Modification du mot de passe impossible.</h2>");
			}
			else
			{
				ma.Add("success", false);
				ma.Add("message", "<h2> Utilisateur non reconnu.</h2>");
			}
		}
		
		ma.Write(context);	
	}

	@Override
	public String getRoute() {
		return "^/Profil/SaveInformation(?:/)?$";
	}
	
	@Override
	public String getLayout() {
		return null;
	}

}
